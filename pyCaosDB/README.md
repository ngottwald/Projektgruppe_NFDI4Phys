# pyCaosDB

How to setup CaosDB: https://gitlab.com/caosdb/caosdb-server/-/blob/dev/README_SETUP.md

Short introduction to CaosDB: https://demo.indiscale.com/

pyCaodDB tutorial: https://docs.indiscale.com/caosdb-pylib/tutorials/index.html

## Installation:

pip install -r requirements.txt

Tool for import and export rdf data into CaosDB.

## Running application:

For input mode:
$ python main.py 1 RDFFiles/0000.owl

An owl file gets imported and transfered into data objects for caosdb. They are differenced into recordtypes (description of data) and records (the real data).
The application checks if recordtype exists and only an update is necesarry.
In general it is dynamic but in our case we set the input direction fix, because for the experiment the camera and laser have to exists.

For output mode:
$ python main.py 2 "FIND RECORD Experiment" test.owl

For running test:
python -m unittest [unit_testfile].py

## Deployment/usage in combination with java

Copy the files main.py, rdf_worker.py into RDFWorker resources.
Ensure the folder resources/import exists.
Logging does not work very well if the scripts are executed by the java application. Need some optimization and debugging.

Description of the process:
- Select experiment files -> choose the .sif file
- Import experiment files -> .sif file gets convertet into xml
- Add additional data (university, room number, team members)
- Create RDF-File -> use data from .xml and create structured .owl file
- Show visualization -> visualization of the data structure
- Upload -> load data into caosdb
- Import experiment -> enter data element you want to get from caosdb (here 'experiment')