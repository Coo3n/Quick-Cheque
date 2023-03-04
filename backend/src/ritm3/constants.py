
# Global API response fields
FIELD_STATUS = 'status'
FIELD_TOKEN = 'token'
FIELD_MESSAGE = 'message'

# Possible API statuses
STATUS_OK = 'success'
STATUS_BAD = 'failure'

WRONG_FORMAT = {
    FIELD_STATUS: STATUS_BAD,
    FIELD_MESSAGE: 'wrong request format'
}
