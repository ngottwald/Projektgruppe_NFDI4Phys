import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import input.Input;

public class TestInput{ // maybe check if every property is loaded?

    @Test
    public void testInputOne() throws ParserConfigurationException, SAXException, IOException{
    	Input in = new Input();
    	boolean r = in.input("input/experiments");
    	assertTrue(r == true);
    }
    
    @Test
    public void testInputTwo() throws ParserConfigurationException, SAXException, IOException{
    	Input in = new Input();
    	boolean r = in.input("testing");
    	assertTrue(r == false);
    }
}