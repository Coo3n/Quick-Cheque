#import re
#import requests
#
#from flask import flash
#from psycopg2.extensions import AsIs
#
#from .support_functions import get_page_html_markup
#from .db import get_db
#
#
#def fetcher(api_link):
#    pass
#
#
#def parse_data(parsing_pattern, unparsed_data):
#    def get_failure_response(unparsed_data):
#        response = {
#            'data': unparsed_data,
#            'status': 'failure',
#            'message': 'there is no such processor',
#        }
#        return response
#
#    def get_success_reponse(parsed_data, processor_name):
#        response = {
#            'data': parsed_data,
#            'status': 'success',
#            'message': f'data is parsed for {processor_name} processor',
#        }
#        return response
#
#    processor_name = None
#
#    if parsing_pattern == 'is_day_off':
#        parsed_data = {'result': unparsed_data}
#        processor_name = 'is_day_off'
#    else:
#        return get_failure_response(unparsed_data)
#
#    return get_success_reponse(parsed_data, processor_name)
#
#
#def is_day_off(api_request):
#    rest_api_response = requests.get(api_request).text
#    response = {
#        'data': rest_api_response,
#        'status': 'success',
#        'message': 'data is fetched for is_day_off processor',
#    }
#    return response
#
#
#def save_to_db(data, table_name):
#    def is_data_empty():
#        return data is None
#
#    def does_table_exist_in_db():
#        schema_name = 'public'
#
#        query = f"""
#            SELECT EXISTS (
#                SELECT FROM information_schema.tables 
#                WHERE  table_schema = '{schema_name}'
#                AND    table_name   = '{table_name}'
#            );
#        """
#        
#        with db.cursor() as cur:
#            cur.execute(query)
#            return cur.fetchone()[0]
#
#    def insert_data_in_table_by_dict_keys():
#         with db.cursor() as cur:
#             # AsIs is used to insert multiple values to a table
#             columns = data.keys()
#             values = [data[column] for column in columns]
#             insert_statement = f'INSERT INTO {table_name} (%s) VALUES %s'
#             cur.execute(insert_statement, (AsIs(','.join(columns)), tuple(values)))
#         db.commit()
#
#    db = get_db()
#
#    response = None
#
#    if is_data_empty():
#        response = {
#            'status': 'failure',
#            'message': 'the data to store is empty',
#        }
#    elif not does_table_exist_in_db():
#        response = {
#            'status': 'failure',
#            'message': "the required table doesn't exist"
#        }
#    else:
#        insert_data_in_table_by_dict_keys()
#        response = {
#            'status': 'success',
#            'message': 'the db was successfully updated'
#        }
#
#    return response


# def get_road_signs():
#     def get_unparsed_row(row_string, start, tag):
#         tag_length = len(tag)
#         start = start - tag_length
#         unparsed_string = row_string[start:]
#         unparsed_string = unparsed_string[:unparsed_string.find('</tr>')]
#         return unparsed_string

#     def get_parsed_header(unparsed_header):
#         parsed_header = unparsed_header.replace('<th>', "")
#         parsed_header = parsed_header.replace("\n", "")
#         parsed_header = parsed_header.split("</th>")
#         return parsed_header

#     # ! ARCHITECTURE
#     # ! Create temp file +
#     # ! Fetch required data +
#     # ! Parse the fetched data
#     # ! Store all info in an array

#     # Fetch HTML markup using curl
#     url = 'https://ru.wikipedia.org/wiki/Дорожные_знаки_России'
#     markup = get_page_html_markup(url)

#     # TODO. Delete it when everything is done
#     # Write HTML markup in the file
#     temp_file_name = 'temp.txt'
#     file = open(temp_file_name, 'w')
#     file.write(markup)
#     file.close()

#     # Find occurrences of the key in the file using Python regular expressions
#     key = 'Номер знака'
#     key_occurences_in_file = [i.start() for i in re.finditer(key, markup)]

#     # Check if there is key occurence in the file
#     process_name = 'Дорожные Знаки'

#     if key_occurences_in_file == []:
#         return f"Потерян ключ для обработки процесса {process_name}!"

#     # Parse the fetched data
#     # TODO. After everything has been done "data_header" should be added in
#     # TODO. "fetched_data"
#     fetched_data = []
#     data_header = []

#     for key_occurence in key_occurences_in_file:
#         # If there is no header, add it
#         if data_header == []:
#             tag = "<th>"
#             unparsed_header = get_unparsed_row(markup, key_occurence, tag)
#             data_header = get_parsed_header(unparsed_header)

#             if data_header[-1] == "":
#                 del(data_header[-1])

#             if data_header[-1] == "Пояснение":
#                 del(data_header[-1])

#             fetched_data += data_header

#         # TODO. Add a function to fetch data from tables themselves

#     # TODO. Has been created to debug. Delete it later
#     flash(fetched_data)
