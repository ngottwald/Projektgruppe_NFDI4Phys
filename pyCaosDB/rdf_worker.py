import rdflib
from rdflib import URIRef, Literal, BNode


class RDFWorker:
    __doc__ = """
    """

    def __init__(self, rdf_file_path):
        self.g = rdflib.Graph()
        self.literals = []

        if rdf_file_path:
            result = self.g.parse(rdf_file_path)

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
    
    def handle_Literal(self, literal):
        datatype = self.get_datatype_of_Literal(literal)
        value = literal.value
        
        if not (value, datatype) in self.literals:
            self.literals.append((value, datatype))

    def get_value_of_Literal(self, literal):
        return literal.value
    
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

    def export_caosdb_data_model(self, db):
        Camera = URIRef("http://www.semanticweb.org/tobiasvente/ontologies/2020/11/NFDI4Phys#Camera")
        subPropOf = URIRef("http://www.w3.org/2000/01/rdf-schema#subPropertyOf")
        isType = URIRef("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
        owlClass = URIRef("http://www.w3.org/2002/07/owl#Class")

        count_test_sub = 0

        self.show_rdf_graph()

        records = {}
        properties = {}

        subject_list = []
        pred_list = []
        for idx, (subj, pred, obj) in enumerate(self.g):
            if len(subj.toPython().split("#")) > 1:
                subjName = subj.toPython().split("#")[1]
            else:
                subjName = subj.toPython().split("#")[0]

            if obj == owlClass:
                print(f'Class: {subjName} of Type {type(subj)}')
                record = db.RecordType(name=subj)
                records[subjName] = record
                

            if type(obj) == Literal and len(records) :
                print(f'{subj}  obj:{obj.toPython()} .language:{obj.language} .value:{obj.value} .datatype:{obj.datatype} ')
                print(f'\n {pred}')
                propertyElement = db.Property(name=pred, value=obj.value, datatype=db.TEXT)
                if(subjName in properties):
                    properties[subjName].append(propertyElement)
                else:
                     properties[subjName] = [propertyElement]


        container = db.Container()

        # for record_key in records:
        container_input = []
        container_input.append(records["Camera"])
        i = 0
        for prop in properties["Camera"]:
            print(prop)    
            records["Camera"].add_property(prop)
            container_input.append(prop)

        print(container_input)
        container.extend(container_input)
        container.insert()

        print(f'All records: {records}')
        print(f'All properties: {properties}')



