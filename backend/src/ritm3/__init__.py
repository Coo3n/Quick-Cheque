import os

from flask import Flask


def create_app(test_config=None):
    app = Flask(__name__, instance_relative_config=True)
    app.config.from_mapping(
        SECRET_KEY=os.environ['FLASK_SECRET_KEY'],
        DB_HOST=os.environ['DB_HOST'],
        DB_PORT=os.environ['DB_PORT'],
        POSTGRES_DB=os.environ['POSTGRES_DB'],
        POSTGRES_USER=os.environ['POSTGRES_USER'],
        POSTGRES_PASSWORD=os.environ['POSTGRES_PASSWORD']
    )

    if test_config is None:
        app.config.from_pyfile('config.py', silent=True)
    else:
        app.config.from_mapping(test_config)

    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    from . import db
    db.init_app(app)

    from . import auth
    app.register_blueprint(auth.bp)
    
    from . import the_tyre
    app.register_blueprint(the_tyre.bp)
    app.add_url_rule('/', endpoint='index')

    return app
