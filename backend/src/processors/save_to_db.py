import sys
import psycopg2
from psycopg2 import sql
import json


def die(msg):
    print(msg)
    sys.exit(1)


def does_table_exist_in_db(db, table_name):
    schema_name = 'public'

    query = f"""
        SELECT EXISTS (
            SELECT FROM information_schema.tables 
            WHERE  table_schema = '{schema_name}'
            AND    table_name   = '{table_name}'
        );
    """
    
    with db.cursor() as cur:
        cur.execute(query)
        return cur.fetchone()[0]


def insert_data_in_table_by_dict_keys(db, table_name, data):
    try:
        with db.cursor() as cur:
            # AsIs is used to insert multiple values to a table
            columns = data.keys()
            values = [data[column] for column in columns]

            sql_query = sql.SQL("INSERT INTO {} ({}) values ({})").format(
                sql.SQL(table_name),
                sql.SQL(', ').join(map(sql.Identifier, columns)),
                sql.SQL(', ').join(map(sql.Literal, tuple(values)))
            )

            cur.execute(sql_query)
            db.commit()
    except psycopg2.Error as e:
        die(str(e))

        
def main():
    if len(sys.argv) != 4:
        die(f'usage: {sys.argv[0]} data_file_path connection_str table_name')

    data_file_path = sys.argv[1]
    connection_str = sys.argv[2]
    table_name = sys.argv[3]

    try:
        db = psycopg2.connect(connection_str)
    except psycopg2.Error as e:
        die(str(e))

    try:
        with open(data_file_path, mode='r', encoding='utf8') as data_file:
            data_json = json.load(data_file)
    except (OSError, json.JSONDecodeError) as e:
        die(str(e))

    if not does_table_exist_in_db(db, table_name):
        die(f'no such table - {table_name}')

    insert_data_in_table_by_dict_keys(db, table_name, data_json)
    exit(0)

    
if __name__ == '__main__':
    main()
