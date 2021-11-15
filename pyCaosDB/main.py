import caosdb as db
import matplotlib
import matplotlib.pyplot as plt
import rdf_worker


def main():
    # Shows a few examples how to use the CaosDB library.
    conf = dict(db.configuration.get_config().items("Connection"))
    print("##### Config:\n{}\n".format(conf))




if __name__ == '__main__':
    main()

    rdf_worker1 = rdf_worker.RDFWorker("RDFFiles/NFDI4Phys.owl")
