import sys


def is_test_running():
    return "pytest" in sys.modules
