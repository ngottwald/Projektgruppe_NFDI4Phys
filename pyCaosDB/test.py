import unittest
from unittest.mock import patch
import caosdb as db
from rdflib import URIRef, Literal, BNode
from rdf_worker import RDFWorker

class TestRDFWorker(unittest.TestCase):

   @classmethod
   def setUpClass(cls):
     pass

   @classmethod
   def tearDownClass(cls):
     pass

   def setUp(self):
     self.rdf_worker = RDFWorker(db)
     self.rdf_worker.import_rdf_data("RDFFiles/NFDI4Phys.owl")
     pass

   def tearDown(self):      
     pass

   def test_add_property(self):
      self.rdf_worker.records = {}
      self.rdf_worker.properties = {} 

      subj_name = 'test_subj_name'
      prop_name = 'test_prop_name'
      prop_value = '1'
      prop_type = self.rdf_worker.db.TEXT
      self.rdf_worker.add_property_entry(subj_name, prop_name, prop_value, prop_type)
      self.assertEqual(len(self.rdf_worker.properties), 1)
      self.assertEqual(len(self.rdf_worker.records), 1)

      self.rdf_worker.add_property_entry(subj_name, prop_name, prop_value, prop_type)
      self.assertEqual(len(self.rdf_worker.properties), 1)
      self.assertEqual(len(self.rdf_worker.records), 1)
