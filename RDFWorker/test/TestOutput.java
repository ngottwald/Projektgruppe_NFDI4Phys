import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import input.Input;
import output.Output;

public class TestOutput{ // maybe some sort of sanity check for the output?

    @Test
    public void testOutput() throws IOException, ParserConfigurationException, SAXException{
    	Input in = new Input();
    	in.input("input");
    	Output ou = new Output();
    	boolean r = ou.output(in.getExperiment());
    	assertTrue(r == true);
    }
}