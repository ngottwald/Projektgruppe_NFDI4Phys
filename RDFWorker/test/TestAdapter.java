import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import adapter.ParseRdf;
import rdf.*;

public class TestAdapter {

    @Test
    public void testAdapterOne() throws ParserConfigurationException, SAXException, IOException{
    	ParseRdf prdf = new ParseRdf("testing/experiments/test.owl");
    	
    	for(Device d : prdf.getExperiment().getDevices()) {
    		assertTrue(d instanceof Camera);
    		assertTrue(d instanceof EMCCD);
    		EMCCD e = (EMCCD) d;
    		assertTrue(e.isBackIllumination() == false);
    		assertTrue(e.getFrameBinningX() == 1920);
    		assertTrue(e.getFrameBinningY() == 1080);
    		assertTrue(e.getCoolingTemperature() == -273);
    		assertTrue(e.getWavelength() == 333);
    	}    	
    }
    
}
