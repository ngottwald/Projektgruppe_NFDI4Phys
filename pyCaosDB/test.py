import unittest
from rdflib import URIRef, Literal, BNode
from rdf_worker import get_datatype_of_Literal

class NamesTestCase(unittest.TestCase):

   def test_literal_parsing(self):
       literal = Literal("Test")
       result = get_datatype_of_Literal(literal)
       self.assertEqual(result, "INTEGER")