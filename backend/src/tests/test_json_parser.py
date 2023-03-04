from ctypes import Structure
import sys

# Docker required. 
# Append all processors to sys.path to fetch their modules from them.
sys.path.append('/var/app/processors')

from json_parser import Parser


def get_api_response_example():
    structure = {
        "data": [
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2019,
                "Year": "2019",
                "Population": 328239523,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2018,
                "Year": "2018",
                "Population": 327167439,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2017,
                "Year": "2017",
                "Population": 325719178,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2016,
                "Year": "2016",
                "Population": 323127515,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2015,
                "Year": "2015",
                "Population": 321418821,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2014,
                "Year": "2014",
                "Population": 318857056,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2013,
                "Year": "2013",
                "Population": 316128839,
                "Slug Nation": "united-states"
            }
        ],
        "source": [
            {
                "measures": [
                    "Population"
                ],
                "annotations": {
                    "source_name": "Census Bureau",
                    "source_description": "The American Community Survey (ACS) is conducted by the US Census and sent to a portion of the population every year.",
                    "dataset_name": "ACS 1-year Estimate",
                    "dataset_link": "http://www.census.gov/programs-surveys/acs/",
                    "table_id": "B01003",
                    "topic": "Diversity",
                    "subtopic": "Demographics"
                },
                "name": "acs_yg_total_population_1",
                "substitutions": []
            }
        ]
    }

    return structure


class TestAttach:
    def __get_structure_for_attach_tests(self):
        structure = {
            "a": {
                "b": {
                    "c": {
                        "d": 4,
                        "e": 5
                    }
                }
            }
        }

        return structure


    def test_if_attach_worked_correctly_with_empty_query(self):
        structure = self.__get_structure_for_attach_tests()
        existent_structure = ""
        new_value = {"w": 25}
    
        Parser(structure).attach(existent_structure, new_value)

        got_structure = structure

        expected_structure = {
            "a": {
                "b": {
                    "c": {
                        "d": 4,
                        "e": 5
                    }
                }
            },
            "w": 25
        }

        assert got_structure == expected_structure


    def test_if_attach_worked_correctly_on_middle_without_list(self):
        """
        Put new value on the middle of a dict and see if this works correctly
        (where the middle is the level which is somewhere between the uppest 
        and deepest of elements in a dict).
        """

        structure = self.__get_structure_for_attach_tests()
        existent_structure = "a"
        new_value = {"z": 26}

        Parser(structure).attach(existent_structure, new_value)

        got_result = structure

        expected_result = {
            "a": {
                "b": {
                    "c": {
                        "d": 4,
                        "e": 5
                    }
                },
                "z": 26
            }
        }

        assert got_result == expected_result


    def test_if_attach_worked_correctly_on_achor_without_list(self):
        """
        Put new value on an achor of a dict and see if this works correctly
        (where the anchor is the deepest level of elements in a dict).
        """

        structure = self.__get_structure_for_attach_tests()
        existent_srtucture = "a.b.c"
        new_value = {"f": 6}

        Parser(structure).attach(existent_srtucture, new_value)

        got_result = structure

        expected_result = {
            "a": {
                "b": {
                    "c": {
                        "d": 4,
                        "e": 5,
                        "f": 6
                    }
                }
            }
        }

        assert got_result == expected_result


    def test_attach_with_list(self):
        structure = {
            "country-greeting": [
                {
                    "country": "Russia",
                    "greeting": "Привет"
                },
                {
                    "country": "the UK",
                    "greeting": "Hi"
                }
            ]
        }

        existent_structure = "country-greeting[0]"

        new_value = {"code": None}

        Parser(structure).attach(existent_structure, new_value)
    
        got_result = structure

        expected_result = {
            "country-greeting": [
                {
                    "country": "Russia",
                    "greeting": "Привет",
                    "code": None
                },
                {
                    "country": "the UK",
                    "greeting": "Hi"
                }
            ]
        }

        assert got_result == expected_result


class TestFetch:
    def test_if_empty_list_can_be_fetched(self):
        structure = get_api_response_example() 
        got_result = Parser(structure).fetch("source[0].substitutions")
        expected_result = []
        assert got_result == expected_result


    def test_if_all_children_fetched_correctly(self):
        """
        Tests if all children of the object are fetched correctly in a case 
        when query isn't written fully.
        """

        structure = get_api_response_example()
        query = "data"

        got_result = Parser(structure).fetch(query)
        expected_result = [
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2019,
                "Year": "2019",
                "Population": 328239523,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2018,
                "Year": "2018",
                "Population": 327167439,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2017,
                "Year": "2017",
                "Population": 325719178,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2016,
                "Year": "2016",
                "Population": 323127515,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2015,
                "Year": "2015",
                "Population": 321418821,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2014,
                "Year": "2014",
                "Population": 318857056,
                "Slug Nation": "united-states"
            },
            {
                "ID Nation": "01000US",
                "Nation": "United States",
                "ID Year": 2013,
                "Year": "2013",
                "Population": 316128839,
                "Slug Nation": "united-states"
            }
        ]

        assert got_result == expected_result


    def test_fetch(self):
        structure = {
            "countries": [
                {
                    "name": "Germany",
                    "traffic rating": 0.9,
                    "public transport rating": 0.8
                },
                {
                    "name": "the UK",
                    "traffic rating": 0.8,
                    "public transport rating": 0.8
                }
            ]
        }

        query = 'countries[0].traffic rating'
        got_result = Parser(structure).fetch(query)
        expected_result = 0.9
    
        assert got_result == expected_result


class TestSave:
    def test_save(self):
        structure = {
            "countries": [
                {
                    "Germany": {
                        "traffic rating": 0.9,
                        "public transport rating": 0.8
                    },
                    "the UK": {
                        "traffic rating": 0.8,
                        "public transport rating": 0.8
                    }
                }
            ]
        }

        mp = Parser(structure)
        queries_list = [
            'countries[].Germany.traffic rating',
	        'countries[].the UK'
        ]
        mp.save(queries_list)
        got_structure = mp.get_current_structure()

        expected_structure = {
            "countries": [
                {
                    "Germany": {
                        "traffic rating": 0.9,
                    },
                    "the UK": {
                        "traffic rating": 0.8,
                        "public transport rating": 0.8
                    }
                }
            ]
        }

        assert got_structure == expected_structure

    def test_if_empty_list_can_be_saved(self):
        structure = get_api_response_example()
        queries_list = ["source[]"]
        
        mp = Parser(structure)
        mp.save(queries_list)
        got_structure = mp.get_current_structure() 

        expected_structure = {
            "source": [
                {
                    "measures": [
                        "Population"
                    ],
                    "annotations": {
                        "source_name": "Census Bureau",
                        "source_description": "The American Community Survey (ACS) is conducted by the US Census and sent to a portion of the population every year.",
                        "dataset_name": "ACS 1-year Estimate",
                        "dataset_link": "http://www.census.gov/programs-surveys/acs/",
                        "table_id": "B01003",
                        "topic": "Diversity",
                        "subtopic": "Demographics"
                    },
                    "name": "acs_yg_total_population_1",
                    "substitutions": []
                }
            ]
        }

        assert got_structure == expected_structure


class TestChange:
    def test_if_dict_value_can_be_changed(self):
        structure = {
            'a': {
                'b': {
                    'c': 3,
                    'd': 4
                }
            }
        }
        query = 'a.b.c'
        new_value = 123

        mp = Parser(structure)
        mp.change(query, new_value)
        got_structure = mp.get_current_structure()
        expected_structure = {
            'a': {
                'b': {
                    'c': 123,
                    'd': 4
                }
            }
        }

        assert got_structure == expected_structure
    
    def test_if_list_value_can_be_changed(self):
        structure = {
            'a': {
                'b': [
                    'c',
                    'd',
                    'e'
                ]
            }
        }
        query = 'a.b[0]'
        new_value = 'z'

        mp = Parser(structure)
        mp.change(query, new_value)
        got_structure = mp.get_current_structure()
        expected_structure = {
            'a': {
                'b': [
                    'z',
                    'd',
                    'e'
                ]
            }
        }

        assert got_structure == expected_structure

    def test_if_dict_value_inside_list_can_be_changed(self):
        structure = {
            'a': {
                'b': [
                    {
                        'c': 3,
                        'd': 4
                    },
                    'f'
                ]
            }
        }
        query = 'a.b[0].d'
        new_value = 123

        mp = Parser(structure)
        mp.change(query, new_value)
        got_structure = mp.get_current_structure()
        expected_structure = {
            'a': {
                'b': [
                    {
                        'c': 3,
                        'd': 123
                    },
                    'f'
                ]
            }
        }

        assert got_structure == expected_structure
        

class TestMixed:
    def test_save_attach_fetch(self):
        structure = get_api_response_example()
        mp = Parser(structure)

        queries_list = [
            "source[].measures[]",
            "source[].annotations.source_name",
            "source[].annotations.source_description",
            "source[].annotations.dataset_name"
        ]
        mp.save(queries_list)
        got_saved_structure = mp.get_current_structure()

        existent_structure = "source[0].measures[]"
        new_value = "test1"
        mp.attach(existent_structure, new_value)

        existent_structure = "source[0].annotations"
        new_value = {"test2": "test2"}
        mp.attach(existent_structure, new_value)

        expected_structure = {
            "source": [
                {
                    "measures": [
                        "Population",
                        "test1"
                    ],
                    "annotations": {
                        "source_name": "Census Bureau",
                        "source_description": "The American Community Survey (ACS) is conducted by the US Census and sent to a portion of the population every year.",
                        "dataset_name": "ACS 1-year Estimate",
                        "test2": "test2"
                    }
                }
            ]
        }

        query = "source[0].annotations.source_description"
        got_fetched_value = mp.fetch(query)
        expected_fected_value = "The American Community Survey (ACS) is conducted by the US Census and sent to a portion of the population every year." 

        assert got_saved_structure == expected_structure
        assert got_fetched_value == expected_fected_value


class TestGetCurrentStructure:
    def test_get_current_structure(self):
        structure = {
            'a': 5,
            'b': 6
        }

        mp = Parser(structure)
        mp.change('a', 23)
        mp.change('b', 34)
        mp.attach('', {'c': 45})
        got_structure = mp.get_current_structure()

        expected_structure = {
            'a': 23,
            'b': 34,
            'c': 45
        }

        assert got_structure == expected_structure
