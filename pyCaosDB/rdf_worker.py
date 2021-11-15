import rdflib


class RDFWorker:

    def __init__(self, rdf_file_path):
        self.g = rdflib.Graph()

        if rdf_file_path:
            result = self.g.parse(rdf_file_path)

    def show_rdf_graph(self):
        # loop through each triple in the graph (subj, pred, obj)
        for subj, pred, obj in self.g:
            # check if there is at least one triple in the Graph
            if (subj, pred,  obj) not in self.g:
                raise Exception("It better be!")

        # print the number of "triples" in the Graph
        print("graph has {} statements.".format(len(self.g)))
        # prints graph has 86 statements.

        # print out the entire Graph in the RDF Turtle format
        # print(self.g.serialize(format="turtle").decode("utf-8"))

    def export_caosdb_data_model(self, db):
        for subj, pred, obj in self.g:
            print("Subject: " + subj);
            print("Pred: " + pred);
            print("Obj: " + obj);
        # TODO: Modify the db using the subj, pred, obj
