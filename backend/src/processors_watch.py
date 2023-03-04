import os
import sys
import subprocess
import time
import datetime
import json
import itertools


def usage():
    die(f"usage: {sys.argv[0]} procs_uuid procs_options_json")


def die(msg):
    with open(LOGFILE_PATH, mode='a', encoding='utf8') as logfile:
        logfile.write(str(datetime.datetime.now()) + '\t' + msg + '\n')
    sys.exit(1)


def write_statusdic_to_statusfile(status_dic, statusfile_path):
    with open(statusfile_path, mode='w', encoding='utf8') as statusfile:
        json.dump(status_dic, statusfile, ensure_ascii=False)

        
PROCESSORS_DIR = "/var/processors"
PROCS_FILES = "/var/app/processors"

LOGFILE_PATH = os.path.join(PROCESSORS_DIR, 'log.txt')

STATUS_FINISHED = 'finished'
STATUS_FAILED = 'failed'
STATUS_WAITING = 'waiting'
STATUS_RUNNING = 'running'
STATUS_CANCELED = 'canceled'


def main():
    def write_statusfile():
        write_statusdic_to_statusfile(status_dic, statusfile_path)


    if len(sys.argv) < 2:
        usage()
    
    group_name = sys.argv[1]
    procs_options_json_str = sys.argv[2]
    
    group_dir = os.path.join(PROCESSORS_DIR, group_name)
    os.mkdir(group_dir)
    
    try:
        procs_options = json.loads(procs_options_json_str)
    except ValueError as e:
        die(str(e))
    
    statusfile_path = os.path.join(group_dir, 'status.json')
    status_dic = {}
    for key in procs_options:
        status_dic[key] = STATUS_WAITING
    write_statusfile()
    
    proc_output_path = None
    proc_input_path = None
    keys = list(procs_options.keys())
    for i in range(len(keys)):
        proc_name = keys[i]
        proc_dir = os.path.join(group_dir, str(i+1) + "__" + proc_name)
        os.mkdir(proc_dir)
    
        proc_file_path = os.path.join(PROCS_FILES, proc_name + ".py")
        cmd_left = [ "python", proc_file_path ]
        cmd_right = procs_options[proc_name]
        if i == 0:
            cmd = [*cmd_left, *cmd_right]
        else:
            cmd = [*cmd_left, *[proc_input_path], *cmd_right]
            
        proc_output_path = os.path.join(proc_dir, "output.txt")
        proc_output_file = open(proc_output_path, mode='w', encoding='utf8')
        proc = subprocess.Popen(list(cmd),
                                stdout=proc_output_file,
                                stderr=subprocess.STDOUT)
        status_dic[proc_name] = STATUS_RUNNING
        write_statusfile()
    
        while proc.poll() is None:
            # Do something
            time.sleep(1)
    
        proc_output_file.close()
        return_code = proc.returncode
    
        if return_code == 0:
            status_dic[proc_name] = STATUS_FINISHED
            write_statusfile()
        elif return_code == 1:
            status_dic[proc_name] = STATUS_FAILED
            for j in range(i+1,len(keys)):
                proc_name = keys[j]
                status_dic[proc_name] = STATUS_CANCELED
            write_statusfile()
            die(f"processor {proc_name} failed")
    
        proc_input_path = proc_output_path
    
    sys.exit(0)

if __name__ == "__main__":
    main()
