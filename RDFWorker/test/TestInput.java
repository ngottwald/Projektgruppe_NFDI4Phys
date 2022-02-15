import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import input.Input;
import rdf.*;

public class TestInput{ // maybe check if every property is loaded?

    @Test
    public void testInputOne() throws ParserConfigurationException, SAXException, IOException{
    	Input in = new Input("Test", "Test", "Test");
    	boolean r = in.input("input/experiments");
    	assertTrue(r == true);
    }
    
    @Test
    public void testInputTwo() throws ParserConfigurationException, SAXException, IOException{
    	Input in = new Input("Test", "Test", "Test");
    	boolean r = in.input("testing/test");
    	assertTrue(r == false);
    }
    
    @Test
    public void testInputThree() throws ParserConfigurationException, SAXException, IOException{
    	Input in = new Input("Test", "Test", "Test");
    	boolean r = in.input("input/experiments");
    	Experiment ex = in.getExperiment();
    	
    	for(Device d : ex.getDevices()) {
    		assertTrue(d instanceof Camera);
    		assertTrue(d instanceof EMCCD);
    		EMCCD e = (EMCCD) d;
    		assertTrue(e.isBackIllumination() == true);
    		assertTrue(e.getFrameBinningX() == 1600);
    		assertTrue(e.getFrameBinningY() == 200);
    		assertTrue(e.getCoolingTemperature() == -80);
    		assertTrue(e.getWavelength() == 550);
    	}
    	
    }
}