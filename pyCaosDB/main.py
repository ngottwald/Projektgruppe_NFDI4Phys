# This is a sample Python script.

# Press Umschalt+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

import caosdb as db
import matplotlib
import matplotlib.pyplot as plt
import rdf_worker


_ = db.configure_connection(
    url="https://demo.indiscale.com/",
    password_method="plain",
    username="admin",
    password="caosdb"
)


def find_guitar():
    response = db.execute_query("FIND RECORD Guitar")
    return response


def analyse():
    analyses = db.execute_query("FIND RECORD Analysis with quality_factor")
    qfs = [el.get_property("quality_factor").value for el in analyses]
    plt.hist(qfs)
    plt.xlabel("quality factors")
    plt.ylabel("count")
    plt.show()


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Strg+8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print_hi('PyCharm')
    guitar_record = find_guitar()
    print(guitar_record)

    # analyse()

    rdf_worker1 = rdf_worker.RDFWorker("RDFFiles/NFDI4Phys.owl")
    rdf_worker1.show_rdf_graph()
    rdf_worker1.export_caosdb_data_model(db)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
