import pycurl
import certifi

from io import BytesIO

from .db import get_db


def get_page_html_markup(url):
    buffer = BytesIO()
    curl = pycurl.Curl()
    url = url.encode('utf-8')

    curl.setopt(curl.URL, url)
    curl.setopt(curl.WRITEDATA, buffer)
    curl.setopt(curl.CAINFO, certifi.where())
    curl.perform()
    curl.close()

    body = buffer.getvalue()
    # To let it work with not English characters
    encoded_body = body.decode('utf-8')

    return encoded_body


def check_json_fields_existance(fields_list, json_dict):
    res = True
    for field in fields_list:
        if not field in json_dict:
            res = False
            break
    return res


def fetchone_by_pattern_attribute_value(table_name, query):
    cursor = get_db().cursor()

    cursor.execute(f"SELECT * FROM {table_name} LIMIT 0")
    column_names = [desc[0] for desc in cursor.description]

    cursor.execute(query)
    data = cursor.fetchone()

    if data is None:
        return None

    attribute_data_dict = dict()
    dict_length = len(data)

    for i in range(dict_length):
        attribute = column_names[i]
        value = data[i]

        attribute_data_dict[attribute] = value

    return attribute_data_dict
