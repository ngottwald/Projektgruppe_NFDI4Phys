import unittest
from rdflib import URIRef, Literal, BNode
from rdf_worker import RDFWorker

class NamesTestCase(unittest.TestCase):

   def test_literal_parsing(self):
       rdf_worker = RDFWorker("RDFFiles/NFDI4Phys.owl")
       literal = Literal("Test")
       result = rdf_worker.get_datatype_of_Literal(literal)
       self.assertEqual(result, "INTEGER")

