import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.*;

public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		String personURI = "http://somewhere/JohnSmith";
		String fullName = "John Smith";
		
		Model model = ModelFactory.createDefaultModel();
		
		Resource johnSmith = model.createResource(personURI);
		johnSmith.addProperty(VCARD.FN, fullName);
		
		model.write(System.out);
		
		RDFDataMgr.write(System.out, model, Lang.RDFXML);
		
		RDFDataMgr.write(System.out, model, Lang.NTRIPLES);
	}

}
