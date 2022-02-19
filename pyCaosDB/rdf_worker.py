import rdflib
import datetime
import os
from rdflib import Graph, URIRef, Literal, BNode
from lxml import etree

class Record:
    def __init__(self, name):
        self.Name = name

class Property:
    def __init__(self, name, valueType, value, unit, id=None):
        self.Name = name
        self.ValueType = valueType
        self.Value = value
        self.Unit = unit
        self.Id = id

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

    def test_db_connection(self):
        if not self.db.Info():
            raise Exception("No connection to database")

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
    def add_properties_to_record_type(self, recordTypeName, property_name, propertyValue, propertyType, propertyUnit):
        prop_id = self.get_entity_id('PROPERTY', property_name)
        if prop_id > 0:
            propertyObj = Property(property_name, propertyType, propertyValue, propertyUnit, prop_id)
            propertyElement = self.db.Property(name=propertyObj.Name, id=prop_id,datatype=propertyObj.ValueType, unit=propertyObj.Unit)
        else:
            propertyObj = Property(property_name, propertyType, propertyValue, propertyUnit)
            propertyElement = self.db.Property(name=propertyObj.Name, datatype=propertyObj.ValueType, unit=propertyObj.Unit)

        if(recordTypeName in self.properties):
            self.properties[recordTypeName].append(propertyObj)
        else:
            self.properties[recordTypeName] = [propertyObj]
        

        if(not recordTypeName in self.recordTypes):
            self.create_record_type(recordTypeName)
        self.recordTypes[recordTypeName].add_property(propertyElement)

    """ 
        Using existing recordType to create an record
    """
    def create_record(self, recordTypeName):
        ct = datetime.datetime.now().strftime("%y_%m_%d_%H%M%S")
        recordElement = self.db.Record(f'{recordTypeName}_{ct}')
        recordElement.add_parent(recordTypeName)
        for prop in self.properties[recordTypeName]:
            # TODO: Maybe some logic to check if the property has a valid value necessary
            propertyElement = self.db.Property(name=prop.Name, value=prop.Value)
            if prop.ValueType == self.db.DOUBLE:
                prop.Value = float(prop.Value)
            elif prop.ValueType == self.db.INTEGER:
                prop.Value = int(prop.Value)
            recordElement.add_property(name=prop.Name, value=prop.Value)

        self.records[recordTypeName] = recordElement

    def create_record_type(self, record_type_name):
        record_type_id = self.get_entity_id('RECORDTYPE', record_type_name)
        if record_type_id > 0:
            record_type_element = self.db.RecordType(name=record_type_name, id=record_type_id)    
        else:
            record_type_element = self.db.RecordType(name=record_type_name)
        self.recordTypes[record_type_name] = record_type_element

    def read_record_from_caosdb_into_file(self, recordName, fileName):
        response = self.db.execute_query(f'FIND RECORD "{recordName}"')

        self.parse_xml_into_rdf(response.to_xml(), fileName)

    def parse_xml_into_rdf(self, xmlRoot, fileName):
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


    def exists_in_db(self, entity_type, entity_name):
        if entity_type.upper() == 'RECORD' or entity_type.upper()  == 'PROPERTY' or entity_type.upper()  == 'RECORDTYPE':
            response = self.db.execute_query(f'FIND {entity_type.upper() } "{entity_name}"')
            return len(response) > 0
        else:
            raise ValueError("Wrong entity_type. Should be 'RECORD' or 'PROPERTY'")

    def get_entity_id(self, entity_type, entity_name):
        if entity_type.upper() == 'RECORD' or entity_type.upper()  == 'PROPERTY' or entity_type.upper()  == 'RECORDTYPE':
            response = self.db.execute_query(f'FIND {entity_type.upper() } "{entity_name}"')
            if len(response) > 0:
                return response[0].id
            else:
                return -1

    def isFloat(self, element: any) -> bool:
        try:
            float(element)
            return True
        except ValueError:
            return False


    def determine_data_type(self, datatypeRaw, value):
        if(datatypeRaw == None and not self.isFloat(value)):
            return self.db.TEXT
        else:
            datatypeString = datatypeRaw.split("#")[1]
            if(datatypeString == 'integer'):
                if '.' in str(value):
                    return self.db.DOUBLE
                else:
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

            output_string += f'{subj} - {pred} - {obj} :: {type(obj)}\n'

            if type(obj) == Literal :
                prop_type = self.determine_data_type(obj.datatype, obj)
                self.add_properties_to_record_type(subj_name , pred, obj, prop_type, obj.datatype)
            elif type(obj) == URIRef and (str(pred) == "http://www.w3.org/1999/02/22-rdf-syntax-ns#type") and not (str(obj) == "http://www.w3.org/2002/07/owl#NamedIndividual"):
                print(f'subj_name:{subj_name} pred:{pred} obj:{obj}')
                self.add_properties_to_record_type(subj_name , pred, obj, self.db.TEXT, '')

        file1 = open('output.txt', 'w')
        file1.write(output_string)
        file1.close()

    def write_log_file(self, target, entity, value_list):
        if not 'logs'in os.listdir():
            os.mkdir('logs')

        ct = datetime.datetime.now()
        log_file = open(f'logs/{entity}_{target}_{ct.strftime("%y%m%d_%H%M%S")}.log', 'w')
        log_file.write(f'[{ct}] {target}:\n\n')
        for value in value_list:
            log_file.write(str(value))
        log_file.close()

    def export_caosdb_data_model(self):

        # create RecordType
        for recordTypeName in self.recordTypes:
            print(recordTypeName)
            container = self.db.Container()
            container_insert = []
            container_update = []

            # RecordTypes
            if self.exists_in_db('RECORDTYPE', recordTypeName):
                container_update.append(self.recordTypes[recordTypeName])
            else:
                container_insert.append(self.recordTypes[recordTypeName]) 


            # Properties
            for prop in self.properties[recordTypeName]:  
                if not self.exists_in_db('PROPERTY', prop.Name):
                    container_insert.append(self.db.Property(name=prop.Name, datatype=prop.ValueType))
                else:
                    container_update.append(self.db.Property(name=prop.Name, id=prop.Id ,datatype=prop.ValueType))            

            self.write_log_file('insert', recordTypeName, container_insert)
            self.write_log_file('update', recordTypeName, container_update)
 
            try:
                container.extend(container_insert)
                container.insert()
                container.clear()

                container.extend(container_update)
                container.update()
            except Exception as e:
                print(f'RecordType "{recordTypeName}" still exists \n')
                print(f'The error: {e} \n')
            finally:
                container.clear()

        # Write data
        flag = True
        for recordTypeName in self.recordTypes:
            try:
                self.create_record(recordTypeName)
                self.records[recordTypeName].insert()
            except Exception as e:
                print(f'Error at {recordTypeName} has occoured\n')
                print(f'The error: {e} \n')




