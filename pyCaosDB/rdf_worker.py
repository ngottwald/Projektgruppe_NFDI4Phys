import rdflib
from rdflib import URIRef, Literal, BNode

class Record:
    def __init__(self, name):
        self.Name = name

class RDFWorker:
    __doc__ = """
    """

    def __init__(self, db):
        self.g = rdflib.Graph()
        self.db = db

        self.records = {}
        self.properties = {}        

    """

    """
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
        print(self.g.serialize(format="turtle").decode("utf-8"))
    
    def add_property_entry(self, subj_name, prop_name, prop_value, prop_type):
        property_element = self.db.Property(name=prop_name, value=prop_value, datatype=prop_type)

        if(subj_name in self.properties):
            self.properties[subj_name].append(property_element)
        else:
            self.properties[subj_name] = [property_element]
            self.add_record_entry(subj_name)

    def add_record_entry(self, subj_name):
        record_element = self.db.RecordType(name=subj_name)
        self.records[subj_name] = record_element
    
    def get_datatype_of_Literal(self, literal):
        if type(literal.datatype) == None:
            if literal.datatype.find('integer') > -1:
                return 'INT'
            elif literal.datatype.find('nonNegativeInteger') > -1:
                return 'UNSIGNED INT'
            else:
                return type(literal.value)   
        else:
            return type(literal.value) 

    def import_rdf_data(self, rdf_file_path):
        if rdf_file_path:
            result = self.g.parse(rdf_file_path)

        for idx, (subj, pred, obj) in enumerate(self.g):
            if len(subj.toPython().split("#")) > 1:
                subj_name = subj.toPython().split("#")[1]
            else:
                subj_name = subj.toPython().split("#")[0]
                

            if type(obj) == Literal :
                prop_type = self.db.TEXT
                self.add_property_entry(subj_name, pred, obj.value, prop_type)

        print(f'All records: {self.records}')
        print(f'All properties: {self.properties}')

    def export_caosdb_data_model(self):        

        container = self.db.Container()

        for record_key in self.records:
            container_input = []
            container_input.append(self.records["Camera"])
            i = 0
            for prop in self.properties["Camera"]:  
                self.records["Camera"].add_property(prop)
                container_input.append(prop)

        container.extend(container_input)
        container.insert()




