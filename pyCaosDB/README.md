# pyCaosDB

## Installation:

pip install -r requirements.txt

Tool for import and export rdf data into CaosDB

## Running application:

For input mode:
$ python main.py 1 RDFFiles/0000.owl

For output mode:
$ python main.py 2 "FIND RECORD Experiment" test.rdf

For running test:
python -m unittest [unit_testfile].py

## Deployment/usage in combinatio with java

Copy the files main.py, rdf_worker.py into RDFWorker resources.
Ensure the folder resources/import exists.
Logs will be written into RDFWorker/logs.