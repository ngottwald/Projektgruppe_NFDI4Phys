import caosdb as db
import matplotlib
import matplotlib.pyplot as plt
import rdf_worker
import sys


def read_from_caosdb(db):

    response = db.execute_query(f'FIND RECORD "Camera 01"')
    print(response)
    print(type(response))
    pass

def main():
    print(f'Number of arguments {len(sys.argv)}')
    # Shows a few examples how to use the CaosDB library.
    try:
        conf = dict(db.configuration.get_config().items("Connection"))
        print("##### Config:\n{}\n".format(conf))
    except:
        print('Ensure to be in the correct network.')

    rdf_worker1 = rdf_worker.RDFWorker(db)
    rdf_worker1.test_db_connection()

    rdf_worker1.import_rdf_data("RDFFiles/2021-11-15.owl")
    rdf_worker1.export_caosdb_data_model()
    rdf_worker1.read_record_from_caosdb_into_file("Camera 2022-01-23 16:45:00.399284", "test.xml")


if __name__ == '__main__':
    main()
