import caosdb as db
import matplotlib
import matplotlib.pyplot as plt
import rdf_worker


def main():
    # Shows a few examples how to use the CaosDB library.
    conf = dict(db.configuration.get_config().items("Connection"))
    print("##### Config:\n{}\n".format(conf))

    rdf_worker1 = rdf_worker.RDFWorker(db)
    rdf_worker1.import_rdf_data("RDFFiles/NFDI4Phys.owl")
    # rdf_worker1.export_caosdb_data_model()


if __name__ == '__main__':
    main()
