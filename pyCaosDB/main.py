import caosdb as db
import matplotlib
import matplotlib.pyplot as plt
import rdf_worker


def main():
    # Shows a few examples how to use the CaosDB library.
    conf = dict(db.configuration.get_config().items("Connection"))
    print("##### Config:\n{}\n".format(conf))

    # Query the server, the result is a Container
    # result = db.Query("FIND Record").execute()
    # print("##### First query result:\n{}\n".format(result[0]))


if __name__ == '__main__':
    main()
    # db.Info()
    # a = db.Property(name="a", datatype=db.DOUBLE)
    # container = db.Container()
    # container.extend([a])
    # container.insert()
    # result = db.Query("FIND Record").execute()
    # print("##### First query result:\n{}\n".format(result[0]))
    # guitar_record = find_guitar()
    # print(guitar_record)

    # analyse()

    rdf_worker1 = rdf_worker.RDFWorker("RDFFiles/NFDI4Phys.owl")
    #rdf_worker1.show_rdf_graph()
    rdf_worker1.export_caosdb_data_model(db)
