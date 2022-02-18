import rdflib
import datetime
from rdflib import Graph, URIRef, Literal, BNode
from lxml import etree

class Record:
    def __init__(self, name):
        self.Name = name

class Property:
    def __init__(self, name, valueType, value, unit):
        self.Name = name
        self.ValueType = valueType
        self.Value = value
        self.Unit = unit

class RDFWorker:
    __doc__ = """
    """

    def __init__(self, db):
        self.g = rdflib.Graph()
        self.db = db

        self.records = {}
        self.properties = {}
        self.recordTypes = {}

        self.id = 0;        

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

    """ 
        Properties get added to an dictionary having as key the recordType Name.
        Recordtype element gets created, propertis added and pushed to a recordType list.
    """
    def add_properties_to_record_type(self, recordTypeName, propertyName, propertyValue, propertyType, propertyUnit):
        propertyObj = Property(propertyName, propertyType, propertyValue, propertyUnit)

        if(recordTypeName in self.properties):
            self.properties[recordTypeName].append(propertyObj)
        else:
            self.properties[recordTypeName] = [propertyObj]

        propertyElement = self.db.Property(name=propertyObj.Name, datatype=propertyObj.ValueType, unit=propertyObj.Unit)

        if(not recordTypeName in self.recordTypes):
            self.create_record_type(recordTypeName)
        self.recordTypes[recordTypeName].add_property(propertyElement)

    """ 
        Using existing recordType to create an record
    """
    def create_record(self, recordTypeName):
        ct = datetime.datetime.now()
        recordElement = self.db.Record(f'{recordTypeName} {ct}')
        recordElement.add_parent(recordTypeName)
        for prop in self.properties[recordTypeName]:
            # TODO: Maybe some logic to check if the property has a valid value necessary
            propertyElement = self.db.Property(name=prop.Name, value=prop.Value)
            if prop.ValueType == self.db.DOUBLE:
                prop.Value = float(prop.Value)
            recordElement.add_property(name=prop.Name, value=prop.Value)

        self.records[recordTypeName] = recordElement

    def create_record_type(self, recordTypeName):
        if not self.check_for_excisting_record_type(recordTypeName):
            recordTypeElement = self.db.RecordType(name=recordTypeName)
            self.recordTypes[recordTypeName] = recordTypeElement

    def read_record_from_caosdb_into_file(self, recordName, fileName):
        response = self.db.execute_query(f'FIND RECORD "{recordName}"')

        self.parse_xml_into_rdf(response.to_xml())

    def parse_xml_into_rdf(self, xmlRoot):
        rdfString =     '<?xml version="1.0"?> \n\
\t<rdf:RDF xmlns="http://www.semanticweb.org/tobiasvente/ontologies/2020/11/NFDI4Phys#" \n\
\t\txml:base="http://www.semanticweb.org/tobiasvente/ontologies/2020/11/NFDI4Phys" \n\
\t\txmlns:owl="http://www.w3.org/2002/07/owl#" \n\
\t\txmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" \n\
\t\txmlns:xml="http://www.w3.org/XML/1998/namespace" \n\
\t\txmlns:xsd="http://www.w3.org/2001/XMLSchema#" \n\
\t\txmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" \n\
\t\txmlns:Geiger="http://www.semanticweb.org/tobiasvente/ontologies/2020/11/NFDI4Phys#Geiger/"> \n\n\n'

        for child in xmlRoot:
            if child.tag == 'Record':
                rdfString += f'\t\t<owl:NamedIndividual rdf:about="{child.get("name")}"> \n'
                for subchild in child:
                    subchildName = subchild.get("name")
                    if(subchildName != None and len(subchildName.split('#')) > 1):
                        rdfString += f'\t\t\t<{subchildName.split("#")[1]}'
                        if subchild.get('unit') != None:
                            rdfString += f' rdf:datatype="{subchild.get("unit")}"'
                        rdfString += f'>{subchild.text}</{subchildName.split("#")[1]}>\n'

        rdfString += f'\t\t</owl:NamedIndividual> \n'
        rdfString += '</rdf:RDF>'

        file1 = open(fileName, 'w')
        file1.write(rdfString)
        file1.close()

    def check_for_excisting_record_type(self, recordTypeName):
        response = self.db.execute_query(f'FIND RECORD "Camera 01"')

    def isFloat(self, element: any) -> bool:
        try:
            float(element)
            print('isFloat: True')
            return True
        except ValueError:
            return False


    def determine_data_type(self, datatypeRaw, value):
        if(datatypeRaw == None and not self.isFloat(value)):
            return self.db.TEXT
        else:
            datatypeString = datatypeRaw.split("#")[1]
            if(datatypeString == 'integer'):
                return self.db.INTEGER
            elif(datatypeString == 'boolean'):
                return self.db.BOOLEAN
            elif(datatypeString == 'double' or self.isFloat(value)):
                return self.db.DOUBLE
            else:
                return self.db.TEXT
    

    def import_rdf_data(self, rdf_file_path):
        if rdf_file_path:
            result = self.g.parse(rdf_file_path)

        output_string = ''
        for idx, (subj, pred, obj) in enumerate(self.g):
            if len(subj.toPython().split("#")) > 1:
                subj_name = subj.toPython().split("#")[1]
            else:
                subj_name = subj.toPython().split("#")[0]

            output_string += f'{subj} - {pred} - {obj} \n'

            if type(obj) == Literal :
                prop_type = self.determine_data_type(obj.datatype, obj)
                self.add_properties_to_record_type(subj_name , pred, obj, prop_type, obj.datatype)

        file1 = open('output.txt', 'w')
        file1.write(output_string)
        file1.close()
    def export_caosdb_data_model(self):        

        container = self.db.Container()
        container_input = []

        # create RecordType
        for recordTypeName in self.recordTypes:
            
            for prop in self.properties[recordTypeName]:  
                container_input.append(self.db.Property(name=prop.Name, datatype=prop.ValueType))

            container_input.append(self.recordTypes[recordTypeName])                

        try:
            container.extend(container_input)
            container.insert()
        except:
            print("RecordType still exists")

        # Write data
        for recordTypeName in self.recordTypes:
            self.create_record(recordTypeName)
            self.records[recordTypeName].insert()




