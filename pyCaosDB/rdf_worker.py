import rdflib
from rdflib import URIRef, Literal, BNode
from lxml import etree

class Record:
    def __init__(self, name):
        self.Name = name

class Property:
    def __init__(self, name, valueType, value):
        self.Name = name
        self.ValueType = valueType
        self.Value = value

class RDFWorker:
    __doc__ = """
    """

    def __init__(self, db):
        self.g = rdflib.Graph()
        self.db = db

        self.records = {}
        self.properties = {}
        self.recordTypes = {}        

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

    """ 
        Properties get added to an dictionary having as key the recordType Name.
        Recordtype element gets created, propertis added and pushed to a recordType list.
    """
    def addPropertiesToRecordType(self, recordTypeName, propertyName, propertyValue, propertyType):
        propertyObj = Property(propertyName, propertyType, propertyValue)

        if(recordTypeName in self.properties):
            self.properties[recordTypeName].append(propertyObj)
        else:
            self.properties[recordTypeName] = [propertyObj]

        propertyElement = self.db.Property(name=propertyObj.Name, datatype=propertyObj.ValueType)

        self.createRecordType(recordTypeName)
        self.recordTypes[recordTypeName].add_property(propertyElement)

    """ 
        Using existing recordType to create an record
    """
    def createRecord(self, recordTypeName):
        recordElement = self.db.Record()
        recordElement.add_parent(name=recordTypeName)
        for prop in self.properties[recordTypeName]:
            # TODO: Maybe some logic to check if the property has a valid value necessary
            propertyElement = self.db.Property(name=prop.Name, value=prop.Value)
            recordElement.add_property(name=prop.name, value=prop_value)

        self.records.append(recordElement)

    def createRecordType(self, recordTypeName):
        if not self.checkForExcistingRecordType(recordTypeName):
            recordTypeElement = self.db.RecordType(name=recordTypeName)
            self.recordTypes[recordTypeName] = recordTypeElement

    def readRecordFromCaosDBIntoFile(self, recordName, fileName):
        response = self.db.execute_query(f'FIND RECORD "{recordName}"')
        xmlStringTree = etree.tostring(response.to_xml(), pretty_print=True)
        file1 = open(fileName, 'wb')
        file1.write(xmlStringTree)
        file1.close()

    def checkForExcistingRecordType(self, recordTypeName):
        response = self.db.execute_query(f'FIND RECORD "Camera 01"')

    
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
                # self.add_property_entry(subj_name, pred, obj.value, prop_type)
                self.addPropertiesToRecordType(subj_name , pred, obj.value, prop_type)

        # print(f'All records: {self.recordTypes}')
        # print(f'All properties: {self.properties}')

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




