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

    if len(sys.argv) < 3:
        return
    mode = sys.argv[1]
    argument_1 = sys.argv[2]

    # Shows a few examples how to use the CaosDB library.
    try:
        conf = dict(db.configuration.get_config().items("Connection"))
        print("##### Config:\n{}\n".format(conf))
    except:
        print('Ensure to be in the correct network.')

    rdf_worker1 = rdf_worker.RDFWorker(db)
    rdf_worker1.test_db_connection()

    print(mode)
    if mode == "1" :
        rdf_worker1.import_rdf_data(argument_1)
        rdf_worker1.export_caosdb_data_model()
    elif mode == "2":
        if len(sys.argv) >= 4:
            argument_2 = sys.argv[3]
            rdf_worker1.read_record_from_caosdb_into_file(argument_1, argument_2)
        else:
            return
        


if __name__ == '__main__':
    main()
