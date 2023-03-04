from flask import Blueprint, request, jsonify, current_app
from . import constants
import json
import subprocess
import uuid
from .support_functions import check_json_fields_existance
from .auth import validate_token
import os


bp = Blueprint('the_tyre', __name__, url_prefix='/api')

FIELD_TOKEN = constants.FIELD_TOKEN
FIELD_STATUS = constants.FIELD_STATUS
FIELD_MESSAGE = constants.FIELD_MESSAGE
FIELD_UUID = 'uuid'
FIELD_WORKFLOW = 'workflow'
FIELD_WORKFLOW_STATUS = 'workflow_status'
FIELD_PROCESSORS = 'processors'

STATUS_OK = constants.STATUS_OK
STATUS_BAD = constants.STATUS_BAD

TYPE_FETCH = "fetch"
TYPE_PARSE = "parse"
TYPE_SAVE = "save"

PROCESSORS = {
    "is_day_off": {
        "id": 1,
        "num_args": 1,
        "type": TYPE_FETCH
    },
    "dummy_parser": {
        "id": 2,
        "num_args": 0,
        "type": TYPE_PARSE
    },
    "save_to_db": {
        "id": 3,
        "num_args": 2,
        "type": TYPE_SAVE
    },
    "http_request": {
        "id": 4,
        "num_args": 4,
        "type": TYPE_FETCH
    }
}

PROCESSORS_DIR = '/var/processors'
STATUSFILE_NAME = 'status.json'

@bp.post('/handle-workflow')
def handle_workflow():
    fields = [FIELD_TOKEN, FIELD_WORKFLOW]
    if not check_json_fields_existance(fields, request.json):
        return jsonify(constants.WRONG_FORMAT), 400

    workflow = request.json[FIELD_WORKFLOW]
    if not isinstance(workflow, dict):
        return jsonify(constants.WRONG_FORMAT), 400

    token = str(request.json[FIELD_TOKEN])
    token_status = validate_token(token)
    if token_status[FIELD_STATUS] == STATUS_BAD:
        return jsonify(token_status), 400

    for key in workflow.keys():
        params_list = workflow[key]

        if not key in PROCESSORS:
            response = {
                FIELD_STATUS: STATUS_BAD,
                FIELD_MESSAGE: f"there is no such processor - {key}"
            }
            return jsonify(response), 400

        if not isinstance(params_list, list):
            response = {
                FIELD_STATUS: STATUS_BAD,
                FIELD_MESSAGE: f"{key} processor arguments are not list"
            }
            return jsonify(response), 400

        if len(params_list) != PROCESSORS[key]['num_args']:
            response = {
                FIELD_STATUS: STATUS_BAD,
                FIELD_MESSAGE: f"{key} processor should have \
{PROCESSORS[key]['num_args']} arguments"
            }
            return jsonify(response), 400

    uuid_str = str(uuid.uuid4())
    json_str = json.dumps(workflow)
    subprocess.Popen(
        ['python', 'processors_watch.py', uuid_str, json_str],
        stdout=None,
        stderr=subprocess.STDOUT)

    response = {
        FIELD_STATUS: STATUS_OK,
        FIELD_UUID: uuid_str,
        FIELD_MESSAGE: "processors will be executed as specified"
    }
    return response, 200


@bp.post('/get-processors-list')
def get_processors_list():
    fields = [FIELD_TOKEN]
    if not check_json_fields_existance(fields, request.json):
        return jsonify(constants.WRONG_FORMAT), 400

    token = str(request.json[FIELD_TOKEN])
    token_status = validate_token(token)
    if token_status[FIELD_STATUS] == STATUS_BAD:
        return jsonify(token_status), 400

    response = {
        FIELD_STATUS: STATUS_OK,
        FIELD_MESSAGE: "sent processors list",
        FIELD_PROCESSORS: PROCESSORS
    }
    return jsonify(response), 200


@bp.post('/get-workflow-status')
def get_workflow_status():
    fields = [FIELD_TOKEN, FIELD_UUID]
    if not check_json_fields_existance(fields, request.json):
        return jsonify(constants.WRONG_FORMAT), 400

    token = str(request.json[FIELD_TOKEN])
    token_status = validate_token(token)
    if token_status[FIELD_STATUS] == STATUS_BAD:
        return jsonify(token_status), 400

    status_json_path = os.path.join(PROCESSORS_DIR,
                                    request.json[FIELD_UUID], STATUSFILE_NAME)
    try:
        with open(status_json_path) as status_json:
            workflow_status = json.load(status_json)
    except OSError:
        response = {
            FIELD_STATUS: STATUS_BAD,
            FIELD_MESSAGE: 'there is no workflow with provided uuid'
        }
        return jsonify(response), 400

    response = {
        FIELD_STATUS: STATUS_OK,
        FIELD_MESSAGE: 'sent workflow status .json file',
        FIELD_WORKFLOW_STATUS: workflow_status
    }
    return jsonify(response), 200
