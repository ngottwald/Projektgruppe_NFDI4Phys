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

    def __init__(self, db, logger):
        self.g = rdflib.Graph()
        self.db = db

        self.records = {}
        self.properties = {}
        self.recordTypes = {}
        self.Logger = logger

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
    def add_properties_to_record_type(self, record_type_name, property_name, propertyValue, propertyType, propertyUnit):
        prop_id = self.get_entity_id('PROPERTY', property_name)
        if prop_id > 0:
            propertyObj = Property(property_name, propertyType, propertyValue, propertyUnit, prop_id)
            propertyElement = self.db.Property(name=propertyObj.Name, id=prop_id,datatype=propertyObj.ValueType, unit=propertyObj.Unit)
        else:
            propertyObj = Property(property_name, propertyType, propertyValue, propertyUnit)
            propertyElement = self.db.Property(name=propertyObj.Name, datatype=propertyObj.ValueType, unit=propertyObj.Unit)

        if(record_type_name in self.properties):
            self.properties[record_type_name].append(propertyObj)
        else:
            self.properties[record_type_name] = [propertyObj]
        

        if(not record_type_name in self.recordTypes):
            self.create_record_type(record_type_name)
        self.recordTypes[record_type_name].add_property(propertyElement)

    """
    For more complex data structres it is necessary to have record types having record types
    """
    def add_record_type_to_record_type(self, record_type_name_target, record_type_name):
        self.recordTypes[record_type_name_target].add_property(self.recordTypes[record_type_name])

    """ 
        Using existing recordType to create an record
    """
    def create_record(self, recordTypeName):
        ct = datetime.datetime.now().strftime("%y_%m_%d_%H%M%S")
        recordElement = self.db.Record()
        recordElement.add_parent(recordTypeName)

        for el in self.recordTypes[recordTypeName].get_properties():
            if el.name in self.recordTypes:
                self.create_record(el.name)
                self.records[el.name].insert()
                recordElement.add_property(el.name, self.records[el.name].id)

        for prop in self.properties[recordTypeName]:
            # TODO: Maybe some logic to check if the property has a valid value necessary
            propertyElement = self.db.Property(name=prop.Name, value=prop.Value)
            if prop.ValueType == self.db.DOUBLE:
                prop.Value = float(prop.Value)
            elif prop.ValueType == self.db.INTEGER:
                prop.Value = int(prop.Value)
            recordElement.add_property(name=prop.Name, value=prop.Value)

        self.records[recordTypeName] = recordElement

    """
    """
    def create_record_type(self, record_type_name):
        record_type_id = self.get_entity_id('RECORDTYPE', record_type_name)
        if record_type_id > 0:
            record_type_element = self.db.RecordType(name=record_type_name, id=record_type_id)    
        else:
            record_type_element = self.db.RecordType(name=record_type_name)
        self.recordTypes[record_type_name] = record_type_element

    """
    The find_command will be executed on the configured caosdb and the result get saved into an rdf file.
    """
    def read_record_from_caosdb_into_file(self, find_command, fileName):
        # response = self.db.execute_query(f'FIND RECORD "{recordName}"')
        response = self.db.execute_query(find_command)

        self.parse_xml_into_rdf(response.to_xml(), fileName)
        print(response)

    def parse_xml_into_rdf(self, xmlRoot, fileName):
        rdfString =     '<?xml version="1.0"?> \n\
\t<rdf:RDF xmlns="http://www.semanticweb.org/unisiegen/ontologies/2020/11/NFDI4Phys#" \n\
\t\txml:base="http://www.semanticweb.org/unisiegen/ontologies/2020/11/NFDI4Phys" \n\
\t\txmlns:owl="http://www.w3.org/2002/07/owl#" \n\
\t\txmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" \n\
\t\txmlns:xml="http://www.w3.org/XML/1998/namespace" \n\
\t\txmlns:xsd="http://www.w3.org/2001/XMLSchema#" \n\
\t\txmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" \n\
\t\txmlns:Geiger="http://www.semanticweb.org/unisiegen/ontologies/2020/11/NFDI4Phys#Geiger/"> \n\n\n'

        sub_records = []

        for child in xmlRoot:
            if child.tag == 'Record':
                for subchild in child:
                    subchildName = subchild.get("name")
                    if subchild.tag == 'Parent':
                        rdfString += f'\t\t<owl:NamedIndividual rdf:about="base:#{subchildName}"> \n'
                        continue
                    if subchildName != None and subchildName == subchild.get("datatype"):
                        sub_records.append(subchild)
                    if(subchildName != None):
                        if len(subchildName.split('#')) > 1:
                            rdfString += f'\t\t\t<{subchildName.split("#")[1]}'
                        else:
                            rdfString += f'\t\t\t<{subchildName}'
                        if subchild.get('unit') != None:
                            unit_name = subchild.get("unit").split('#')[1]
                            rdfString += f' rdf:datatype="xsd:{unit_name}"'
                        if len(subchildName.split('#')) > 1:
                            rdfString += f'>{subchild.text}</{subchildName.split("#")[1]}>\n'
                        else:
                            rdfString += f'>{subchild.text}</{subchildName}>\n'
                rdfString += f'\t\t</owl:NamedIndividual> \n'

        for record in sub_records:
            print(record.text)
            rdfString += f'\t\t<owl:NamedIndividual rdf:about="base:#{record.get("name")}"> \n'
            response = self.db.execute_query(f'FIND RECORD {record.text}')
            for child in response.to_xml():
                if child.tag == 'Record':
                    for subchild in child:
                        subchildName = subchild.get("name")
                        if subchildName != None and subchildName == subchild.get("datatype"):
                            sub_records.append(subchild)
                        if(subchildName != None and len(subchildName.split('#')) > 1):
                            rdfString += f'\t\t\t<{subchildName.split("#")[1]}'
                            if subchild.get('unit') != None:
                                unit_name = subchild.get("unit").split('#')[1]
                                rdfString += f' rdf:datatype="xsd:#{unit_name}"'
                            rdfString += f'>{subchild.text}</{subchildName.split("#")[1]}>\n'

            rdfString += f'\t\t</owl:NamedIndividual> \n'
        rdfString += '</rdf:RDF>'

        try:
            file1 = open(fileName, 'w')
            file1.write(rdfString)
            file1.close()
            self.Logger.info(f'File "{fileName}" written')
        except :
            self.Logger.exception("message")

    """
    To check if an entity still exists on the configured database
    """
    def exists_in_db(self, entity_type, entity_name):
        if entity_type.upper() == 'RECORD' or entity_type.upper()  == 'PROPERTY' or entity_type.upper()  == 'RECORDTYPE':
            response = self.db.execute_query(f'FIND {entity_type.upper() } "{entity_name}"')
            return len(response) > 0
        else:
            raise ValueError("Wrong entity_type. Should be 'RECORD' or 'PROPERTY'")
    """
    Try to get an id of a given entity. Needed for updating entities.
    """
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

    """
    Try to determinate datatype if no datatype is given by try to parse the value in a valid datatype.
    """
    def determine_data_type(self, datatype_raw, value):
        if(datatype_raw == None and not self.isFloat(value)):
            return self.db.TEXT
        elif datatype_raw == None and self.isFloat(value):
            return self.db.DOUBLE
        else:
            datatypeString = datatype_raw.split("#")[1]
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
    
    """
    Import the an rdf file and create entites holding the data.
    """
    def import_rdf_data(self, rdf_file_path):
        if rdf_file_path:
            result = self.g.parse(rdf_file_path)


        for idx, (subj, pred, obj) in enumerate(self.g):
            if len(subj.toPython().split("#")) > 1:
                subj_name = subj.toPython().split("#")[1]
            else:
                subj_name = subj.toPython().split("#")[0]


            if type(obj) == Literal :
                prop_type = None
                prop_type = self.determine_data_type(obj.datatype, obj)
                self.add_properties_to_record_type(subj_name , pred, obj, prop_type, obj.datatype)
            elif type(obj) == URIRef and (str(pred) == "http://www.w3.org/1999/02/22-rdf-syntax-ns#type") and not (str(obj) == "http://www.w3.org/2002/07/owl#NamedIndividual"):
                self.add_properties_to_record_type(subj_name , pred, obj, self.db.TEXT, '')

        self.add_record_type_to_record_type('Experiment', 'Camera')
        self.add_record_type_to_record_type('Experiment', 'Laser')

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
        for record_type_name in ["Camera", "Laser", "Experiment"]:
            print(record_type_name)
            self.Logger.info(f'Read data for [{record_type_name}]')
            container = self.db.Container()
            container_insert = []
            container_update = []

            # RecordTypes
            if self.exists_in_db('RECORDTYPE', record_type_name):
                container_update.append(self.recordTypes[record_type_name])
            else:
                container_insert.append(self.recordTypes[record_type_name]) 


            # Properties
            for prop in self.properties[record_type_name]:  
                if not self.exists_in_db('PROPERTY', prop.Name):
                    container_insert.append(self.db.Property(name=prop.Name, datatype=prop.ValueType))
                else:
                    container_update.append(self.db.Property(name=prop.Name, id=prop.Id ,datatype=prop.ValueType))            

            # self.write_log_file('insert', record_type_name, container_insert)
            # self.write_log_file('update', record_type_name, container_update)
 
            try:
                container.extend(container_insert)
                container.insert()
                container.clear()

                container.extend(container_update)
                container.update()
            except Exception as e:
                print(f'RecordType "{record_type_name}" still exists \n')
                print(f'The error: {e} \n')
                self.Logger.error(f'RecordType "{record_type_name}" still exists')
                self.Logger.error(f'The error: {e} ')
            finally:
                container.clear()

        # Write data
        self.create_record('Experiment')
        print(self.records)
        self.records['Experiment'].insert()


        # flag = True
        # for record_type_name in self.recordTypes:
        #     try:
        #         self.create_record(record_type_name)
        #         self.records[record_type_name].insert()
        #     except Exception as e:
        #         print(f'Error at {record_type_name} has occoured\n')
        #         print(f'The error: {e} \n')




