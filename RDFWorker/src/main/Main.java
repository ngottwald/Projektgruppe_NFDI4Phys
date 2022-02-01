package main;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import adapter.ParseRdf;
import input.Input;
import output.Output;
import visual.Visual;



public class Main {
	
	public static void main(String[] args) throws IOException {
		try {
			Input input = new Input();
			input.input("input/experiments");
			Output output = new Output();
			output.output(input.getExperiment(), "output//");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseRdf rdf = new ParseRdf("output/2021-11-15.owl");
		rdf.printExperiment();
		
		Visual v = new Visual(rdf.getExperiment());
	}
	
}
