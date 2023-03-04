import sys
import json


def die(msg):
    print(msg)
    sys.exit(1)


def main():
    if len(sys.argv) != 2:
        die(f'usage: {sys.argv[0]} data_file_path')

    data_file_path = sys.argv[1]
    try:
        with open(data_file_path, mode='r', encoding='utf8') as data_file:
            data_json = json.load(data_file)
    except (OSError, json.JSONDecodeError) as e:
        die(str(e))

    # Parse cat facts API response
    FLD_LEN = 'length'
    FLD_FCT = 'fact'

    result = {
        FLD_FCT: None,
        FLD_LEN: None,
    }
    
    for fld in [FLD_LEN, FLD_FCT]:
        try:
            result[fld]=data_json[fld]
        except KeyError as e:
            die(str(e))

    print(json.dumps(result))
    sys.exit(0)


if __name__ == '__main__':
    main()
