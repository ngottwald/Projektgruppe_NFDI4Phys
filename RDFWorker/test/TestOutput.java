import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.junit.Test;
import org.xml.sax.SAXException;

import input.Input;
import output.Output;

public class TestOutput{ // maybe some sort of sanity check for the output?

    @Test
    public void testOutputOne() throws IOException, ParserConfigurationException, SAXException{
    	Input in = new Input();
    	in.input("input/experiments");
    	Output ou = new Output();
    	boolean r = ou.output(in.getExperiment(), "testing//output//");
    	assertTrue(r == true);
    }
    
    @Test
    public void testOutputTwo() throws IOException, ParserConfigurationException, SAXException{
    	
    	Input in = new Input();
    	in.input("input/experiments");
    	Output ou = new Output();
    	boolean r = ou.output(in.getExperiment(), "testing//output//");
    	
		Dataset d = RDFDataMgr.loadDataset("testing/output/2021-11-15.owl");
		
		Model model = d.getDefaultModel();
		
		// list the statements in the Model
		StmtIterator iter = model.listStatements();

		List<String> statements = new ArrayList<String>();
		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object
		    
		    String curr = "";

		    curr = curr + subject.toString().substring(subject.toString().indexOf("#")+1) + " ";
		    
		    curr = curr + predicate.toString().substring(predicate.toString().indexOf("#")+1) + " ";
		    if (object instanceof Resource) {
		    	curr = curr + object.toString();
		    } else {
		        // object is a literal
		    	curr = curr + "" + object.toString() + "";
		    }
		    statements.add(curr);
		}
		
		assertTrue(statements.size() == 25);
		
		for(String s : statements) {
			assertTrue(s.startsWith("Camera"));
		}
    }
}