import sys
import requests


def usage():
    print(f'usage: {sys.argv[0]} url METHOD header data')
    print(f'       only GET and POST methods supported')
    sys.exit(1)


def main():
    if len(sys.argv) != 5:
        usage()
    
    url = sys.argv[1]
    method = sys.argv[2]
    header = sys.argv[3]
    data_file = sys.argv[4]
    
    # TODO: make checks for arguments sanity
    # add try catch statement so the processor won't crash
    
    if method == 'GET':
        request = requests.get(url)
        print(request.text)
        
        sys.exit(0)
    elif method == 'POST':
    
        # TODO: make working POST requests
        # this part is not working now
        request = requests.post(url=url, headers=headers, data=data)
        print(request.text)
    
        sys.exit(0)
    
    usage()


if __name__ == '__main__':
    main()
