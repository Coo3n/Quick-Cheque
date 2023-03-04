import sys
import json
import copy
# TODO. Redo it in a way to take data from the workflow.
import requests

from support_functions import is_test_running


class Parser():
    def __init__(self, json_dict: dict):
        self.__DIVIDER = '.'
        self.__got_json = json_dict

    def __does_index_list_exist(self, index, the_list):
        return index < len(the_list)

    def __get_keys_list(self, query):
        return query.split(self.__DIVIDER)

    def __is_key_list_type(self, key):
        return key.find('[') != -1 and key.find(']') != -1

    def __does_list_type_exist_in_query(self, query):
        keys_list = self.__get_keys_list(query)

        for key in keys_list:
            if self.__is_key_list_type(key):
                return True
        
        return False

    def __get_clear_key(self, key):
        start_brace_index = key.find('[')
        return key[:start_brace_index]

    def __get_index(self, key):
        start_brace_index = key.find('[') + 1
        end_brace_index = key.find(']')
        index = key[start_brace_index:end_brace_index]

        if index == "":
            return ""
            
        return int(index)
    
    def __add(self, structure, existent_structure, new_value):
        def is_keys_list_empty(keys_list):
            return keys_list == [] or keys_list == ['']

        def insert_value(keys_list, current_dict=dict()):
            if not current_dict:
                current_dict = structure

            if is_keys_list_empty(keys_list):
                first_key = ""
            else:
                first_key = keys_list[0]

                if self.__is_key_list_type(first_key):
                    first_key = self.__get_clear_key(first_key)

                current_dict = current_dict[first_key]

            if type(current_dict) is list:
                keys_list = keys_list[1:]

                for inner_dict in current_dict:
                    insert_value(keys_list, inner_dict)
            else:
                if first_key == "":
                    current_dict.update(new_value)
                else:
                    keys_list = keys_list[1:]
                    insert_value(keys_list, current_dict)

        keys_list = self.__get_keys_list(existent_structure)
        insert_value(keys_list)

    def __get_existent_structure(self, query, existent_key_by_level):
        def does_key_exist_in_level(level, key):
            return key in existent_key_by_level[level]

        keys_list = self.__get_keys_list(query)
        existent_structure = ""

        for level, key in enumerate(keys_list[:-1]):
            if not self.__does_index_list_exist(level, existent_key_by_level):
                existent_key_by_level.append([])

            if does_key_exist_in_level(level, key):
                existent_structure += f"{key}."
            else:
                existent_key_by_level[level].append(key)

        existent_structure = existent_structure[:-1]
        return existent_structure

    def __get_non_existent_structure(self, query, existent_structure):
        non_existent_structure = query.replace(existent_structure, '')

        if non_existent_structure[0] == self.__DIVIDER:
            non_existent_structure = non_existent_structure[1:]

        return non_existent_structure

    def __get_existent_list_length(self, query):
        keys_list = self.__get_keys_list(query)
        last_key = keys_list[-1]

        current_dict = self.__got_json

        for key in keys_list:
            if key == last_key:
                key = self.__get_clear_key(key)
                return len(current_dict[key])

            if self.__is_key_list_type(key):
                key = self.__get_clear_key(key)
                current_dict = current_dict[key][0]
            else:
                current_dict = current_dict[key]

    def __get_anchor(self, query, key):
        anchor = dict()

        if (self.__is_key_list_type(key)):
            anchor_list_length = self.__get_existent_list_length(query)
            key = self.__get_clear_key(key)

            if anchor_list_length == 0:
                anchor[key] = [None]
            else:
                anchor[key] = [None] * anchor_list_length
        else:
            anchor[key] = None

        return anchor

    def __does_index_exist(self, dirty_key):
        index = self.__get_index(dirty_key)
        return index != ""

    def get_current_structure(self):
        return self.__got_json

    def change(self, query, new_value):
        def get_ready_exec_statement(query, new_value):
            keys_list = query.split(self.__DIVIDER)
            statement = "structure"

            for key in keys_list:
                if self.__is_key_list_type(key):
                    clear_key = self.__get_clear_key(key)
                    clear_index = key.replace(clear_key, "")
                    statement += f"['{clear_key}']{clear_index}"
                else:
                    statement += f"['{key}']"
            
            statement += f' = {new_value}'
            return statement

        exec_statement = get_ready_exec_statement(query, new_value)
        local_variables = {
            'structure': self.__got_json,
            f'{new_value}': new_value
        }
        exec(exec_statement, {}, local_variables)

    def fetch(self, query):
        keys_list = self.__get_keys_list(query)
        current_dict = self.__got_json

        for key in keys_list:
            if self.__is_key_list_type(key):
                index = self.__get_index(key)
                key = self.__get_clear_key(key)
                try:
                    current_dict = current_dict[key][index]
                except IndexError:
                    current_dict = None
            else:
                current_dict = current_dict[key]

        return current_dict

    def attach(self, existent_structure, new_value):
        def get_ready_exec_statement():
            keys_list = self.__get_keys_list(existent_structure)
            statement = "structure" 

            for key in keys_list:
                if self.__is_key_list_type(key):
                    clear_key = self.__get_clear_key(key)

                    if self.__does_index_exist(key):
                        key_index = self.__get_index(key)
                        statement += f"['{clear_key}'][{key_index}]"
                    else:
                        statement += f"['{clear_key}']"
                else:
                    statement += f"['{key}']"
            
            last_key = keys_list[-1]

            if self.__is_key_list_type(last_key):
                if self.__does_index_exist(last_key):
                    statement += f".update({new_value})"
                else: 
                    statement += f".append(new_value)"
            else:
                statement += f".update({new_value})"

            return statement

        structure = self.__got_json

        if self.__does_list_type_exist_in_query(existent_structure):
            exec_statement = get_ready_exec_statement()
            local_variables = {
                'structure': structure, 
                'new_value': new_value
            }
            exec(exec_statement, {}, local_variables)
        else:
            self.__add(structure, existent_structure, new_value)

    def save(self, queries_list):
        def get_structure():
            structure = dict()

            for query in queries_list:
                existent_structure = self.__get_existent_structure(
                    query, 
                    existent_key_by_level
                )

                non_existent_structure = self.__get_non_existent_structure(
                    query, 
                    existent_structure
                )

                # * Anchor key is the lowest by the level dictionary.
                keys_list = self.__get_keys_list(non_existent_structure)
                anchor_key = keys_list[-1]
                new_structure = self.__get_anchor(query, anchor_key)
                del keys_list[-1]

                # * The code creates a structure from the last elemnt to the
                # * first one because it is the easiest way to solve the 
                # * problem.
                key_list = list(reversed(keys_list))

                for key in key_list:
                    if (self.__is_key_list_type(key)):
                        key = self.__get_clear_key(key)

                        value_query = "".join(keys_list)
                        value_list_length = self.__get_existent_list_length(
                            value_query
                        )

                        temp_structure = dict()
                        temp_structure[key] = []

                        for _ in range(value_list_length):
                            new_structure_copy = copy.copy(new_structure)
                            temp_structure[key].append(new_structure_copy)
                    else:
                        temp_structure = dict()
                        temp_structure[key] = new_structure

                    new_structure = temp_structure

                if structure:
                    self.__add(structure, existent_structure, new_structure)
                else:
                    structure = new_structure

            return structure

        def fill_structure():
            def get_exec_statement_motif(query):
                keys_list = self.__get_keys_list(query)
                statement = "structure"

                for key in keys_list:
                    if self.__is_key_list_type(key):
                        clear_key = self.__get_clear_key(key)
                        statement += f"['{clear_key}'][]"
                    else:
                        statement += f"['{key}']"

                return statement

            def insert_indexes_to_query(query, indexes_list):
                for index in indexes_list:
                    first_brace_occurence = query.find('[]')
                    full_brace_length = len("[]")

                    start = query[:first_brace_occurence]
                    index_string = f"[{index}]"
                    end = query[first_brace_occurence + full_brace_length:]

                    query = start + index_string + end

                return query

            def get_ready_exec_statement(query, counter, exec_statement_motif):
                fetch_query = insert_indexes_to_query(query, counter)
                fetched_value = self.fetch(fetch_query)

                if type(fetched_value) is str:
                    value = f'"{fetched_value}"'
                else:
                    value = fetched_value

                statement = insert_indexes_to_query(
                    exec_statement_motif, 
                    counter
                )

                statement = f"{statement} = {value}"

                return statement

            def get_next_counter_index(counter, lists_length):
                counter[len(lists_length) - 1] += 1

                for index in reversed(range(1, len(lists_length))):
                    if counter[index] > lists_length[index]:
                        counter[index] = 0
                        counter[index - 1] += 1

                return counter

            def was_counter_fully_used(counter, lists_length):
                return counter[0] > lists_length[0]

            def fill_by_original_json(query):
                keys_list = self.__get_keys_list(query)
                lists_length = []

                for index, key in enumerate(keys_list):
                    if self.__is_key_list_type(key):
                        fetch_list_query = ".".join(keys_list[:index + 1])

                        current_list_length = self.__get_existent_list_length(
                            fetch_list_query
                        )
                        
                        lists_length.append(current_list_length - 1)

                exec_statement_motif = get_exec_statement_motif(query)
                counter = [0] * len(lists_length)

                is_counter_done = False

                while not is_counter_done:
                    exec_statement = get_ready_exec_statement(
                        query,
                        counter,
                        exec_statement_motif
                    )

                    exec(exec_statement, {}, {'structure': structure})

                    if counter:
                        counter = get_next_counter_index(counter, lists_length)
                        is_counter_done = was_counter_fully_used(
                            counter, 
                            lists_length
                        )
                    else: 
                        is_counter_done = True

            for query in queries_list:
                fill_by_original_json(query)

        existent_key_by_level = list()

        structure = get_structure()
        fill_structure()

        self.__got_json = structure


def get_json_response_by_api_link():
    url = sys.argv[1]
    response = requests.get(url)
    json_string = response.text
    return json_string


def get_json_response_by_api_link_as_dict():
    json_string = get_json_response_by_api_link()
    json_dict = json.loads(json_string)
    return json_dict


def usage():
    print('usage: json_parser.py <opt:api-link>')

if not is_test_running():
    if len(sys.argv) < 2:
        usage()
        sys.exit(1)

# ! CONSOLE PROTOTYPE - START

# unparsed_json = get_json_response_by_api_link_as_dict()
# print(json.dumps(unparsed_json, indent=4))

# print('---------------------------RESULT---------------------------')

# my_parser = Parser(unparsed_json)

# new_value = {"test2": "test2"}
# my_parser.attach(unparsed_json, "source[0].annotations", new_value)

# result = unparsed_json
# print(json.dumps(result, indent=4))

# ! CONSOLE PROTOTYPE - END

if not is_test_running():
    sys.exit(0)
