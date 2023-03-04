import jwt
import re
import datetime

from flask import Blueprint, request, jsonify, current_app
from werkzeug.security import check_password_hash, generate_password_hash

from .db import get_db
from .support_functions import fetchone_by_pattern_attribute_value, \
    check_json_fields_existance

from . import constants


bp = Blueprint('api', __name__, url_prefix='/api')

JWT_ALGORITHM = 'HS256'

FIELD_STATUS = constants.FIELD_STATUS
FIELD_TOKEN = constants.FIELD_TOKEN
FIELD_MESSAGE = constants.FIELD_MESSAGE

FIELD_USERNAME = 'username'
FIELD_PASSWORD = 'password'
FIELD_EMAIL = 'email'
FIELD_EXPIRES = 'expires'

STATUS_OK = constants.STATUS_OK
STATUS_BAD = constants.STATUS_BAD

MAX_USERNAME = 32
MIN_USERNAME = 4

MAX_PASSWORD = 16
MIN_PASSWORD = 8

MAX_EMAIL = 40

REGEX_PASSWORD = re.compile(r'[A-za-z0-9]+')
REGEX_USERNAME = re.compile(r'[a-zA-z0-9]+')
REGEX_EMAIL = re.compile(
    r'([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+'
)

DB_TABLE_USERS = 'users_list'

TOKEN_VALID = {
    FIELD_STATUS: STATUS_OK,
    FIELD_MESSAGE: 'authentication confirmed'
}
TOKEN_INVALID = {
    FIELD_STATUS: STATUS_BAD,
    FIELD_MESSAGE: 'token invalid'
}
TOKEN_EXPIRED = {
    FIELD_STATUS: STATUS_BAD,
    FIELD_MESSAGE: 'token expired'
}

DB_TABLE_USERS = 'users_list'

@bp.post('/register')
def register():
    fields = [FIELD_USERNAME, FIELD_PASSWORD, FIELD_EMAIL]
    if not check_json_fields_existance(fields, request.json):
        return jsonify(constants.WRONG_FORMAT), 400

    username = str(request.json[FIELD_USERNAME])
    password = str(request.json[FIELD_PASSWORD])
    email = str(request.json[FIELD_EMAIL])

    if len(username) < MIN_USERNAME:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'username is too small',
        }
        return jsonify(response), 400

    if len(username) > MAX_USERNAME:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'username is too big'
        }
        return jsonify(response), 400

    if len(password) < MIN_PASSWORD:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'password is too small'
        }
        return jsonify(response), 400

    if len(password) > MAX_PASSWORD:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'password is too big'
        }
        return jsonify(response), 400

    if len(email) > MAX_EMAIL:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'email is too big'
        }
        return jsonify(response), 400

    if not re.fullmatch(REGEX_PASSWORD, password):
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'password is not valid'
        }
        return jsonify(response), 400

    if not re.fullmatch(REGEX_USERNAME, username):
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'username is not valid'
        }
        return jsonify(response), 400

    if not re.fullmatch(REGEX_EMAIL, email):
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'email is not valid'
        }
        return jsonify(response), 400

    db = get_db()
    try:
        db.cursor().execute(
            "INSERT INTO users_list (username, password, email)"
            "VALUES (%s, %s, %s)",
            (username, generate_password_hash(password), email)
        )
        db.commit()
    except db.IntegrityError:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'username already exists'
        }
        return jsonify(response), 400

    response = {
        FIELD_STATUS: STATUS_OK,
        FIELD_MESSAGE: 'user created, try to login now'
    }
    return jsonify(response), 200


@bp.post('/login')
def login():
    def does_user_exist(user):
        return user is not None

    def is_password_hash_correct(user, password):
        return check_password_hash(user[FIELD_PASSWORD], password)

    def is_user_correct(user, password):
        if not does_user_exist(user):
            return False

        if not is_password_hash_correct(user, password):
            return False

        return True

    fields = [FIELD_USERNAME, FIELD_PASSWORD]
    if not check_json_fields_existance(fields, request.json):
        return jsonify(constants.WRONG_FORMAT), 400

    username = str(request.json[FIELD_USERNAME])
    password = str(request.json[FIELD_PASSWORD])

    query = f"SELECT * FROM {DB_TABLE_USERS} WHERE username = '{username}';"
    user = fetchone_by_pattern_attribute_value(DB_TABLE_USERS, query)

    if not is_user_correct(user, password):
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'wrong username or password'
        }
        return jsonify(response), 400

    token = jwt.encode(
        {
            FIELD_USERNAME: username,
            FIELD_EXPIRES: str(datetime.datetime.utcnow() +
                               datetime.timedelta(minutes=10000))
        },
        current_app.config['SECRET_KEY'],
        JWT_ALGORITHM
    )

    response = {
        FIELD_STATUS: STATUS_OK,
        FIELD_MESSAGE: 'logged in, token generated',
        FIELD_TOKEN: token
    }
    return jsonify(response), 200


def validate_token(token):
    try:
    	jwt_body = jwt.decode(
    	    token,
    	    current_app.config['SECRET_KEY'],
    	    JWT_ALGORITHM
    	)
    except jwt.exceptions.InvalidTokenError as e:
        current_app.logger.warning(e)
        return TOKEN_INVALID

    fields = [FIELD_USERNAME, FIELD_EXPIRES]
    if not check_json_fields_existance(fields, jwt_body):
        return TOKEN_INVALID

    query = (
        f"SELECT * FROM {DB_TABLE_USERS} "
        f"WHERE username = '{jwt_body[FIELD_USERNAME]}';"
    )
    user = fetchone_by_pattern_attribute_value(DB_TABLE_USERS, query)

    if user is None:
        return TOKEN_INVALID

    permitted_time = datetime.datetime.strptime(
        jwt_body[FIELD_EXPIRES],
        "%Y-%m-%d %H:%M:%S.%f"
    )

    current_time = datetime.datetime.utcnow()

    if permitted_time < current_time:
        return TOKEN_EXPIRED

    return TOKEN_VALID


@bp.post('/auth_test')
def auth_test():
    fields = [FIELD_TOKEN]
    if not check_json_fields_existance(fields, request.json):
        return jsonify(constants.WRONG_FORMAT), 400

    token = str(request.json[FIELD_TOKEN])
    token_status = validate_token(token)
    if token_status[FIELD_STATUS] == STATUS_BAD:
        return jsonify(token_status), 400
    
    return jsonify(token_status), 200
