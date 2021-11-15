package input;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rdf.EMCCD;
import rdf.Experiment;

public class Input {
	
	private Experiment experiment;
	
	/**
	 * Reads in the input folder and parsed the xml files depending on the format.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Input() throws ParserConfigurationException, SAXException, IOException {
		
		experiment = new Experiment();
		File dir = new File("input");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
		  for (File child : directoryListing) {
			  
			// xml files that contain the sif data
		  	if(child.getName().startsWith("sif")){
		  		EMCCD emccd = new EMCCD();
		  		emccd.setLinearity(99);
		  		emccd.setWellDepth(200000);
		  		emccd.setCoolingTemperature(-80);
		  		emccd.setPixelLength(16);
		  		emccd.setPixelWidth(16);
		  		
		  		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
			        .newInstance();
		  		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		  		Document document = documentBuilder.parse(child);
		  		NodeList nodeList = document.getElementsByTagName("*");
		  		for (int i = 0; i < nodeList.getLength(); i++) {
		  			Node node = nodeList.item(i);
		  			if (node.getNodeType() == Node.ELEMENT_NODE) {
		  				// the properties get read out and the correct values are assigned 
		  				if(node.getNodeName().equals("property")) {
			        	   
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("HeadModel")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   if(value.contains("DU970P")) {
			        			   emccd.setMaximumReadoutRate(1515);
			        			   emccd.setReadNoiseElectrons(1);
			        			   emccd.setImageAreaLength(25.6);
			        			   emccd.setImageAreaWidth(3.2);
			        			   emccd.setNumberOfPixels(1600*200);
			        		   }
			        		   if(value.contains("DU971P")) {
			        			   emccd.setMaximumReadoutRate(1515);
			        			   emccd.setReadNoiseElectrons(1);
			        			   emccd.setImageAreaLength(25.6);
			        			   emccd.setImageAreaWidth(6.4);
			        			   emccd.setNumberOfPixels(1600*400);
			        		   }
			        		   if(value.contains("BV") || value.contains("UVB")) {
			        			   emccd.setBackIllumination(true);
			        		   }
			        		   if(value.contains("BVF")) {
			        			   emccd.setFringeSuppression(true);
			        		   }
			        		   if(value.contains("LM-C")) {
			        			   emccd.setcMount(true);
			        		   }
			        		   if(value.contains("LM-NIKON-F") || value.contains("LMS-NIKON-F-NS25B")) {
			        			   emccd.setfMount(true);
			        		   }
			        		   if(value.contains("FI") || value.contains("UV")) {
			        			   emccd.setDarkNoisecountsPerSecond(0.00007);
			        		   }
			        		   if(value.contains("BV") || value.contains("UVB")) {
			        			   emccd.setDarkNoisecountsPerSecond(0.00020);
			        		   }
			        		   if(value.contains("BVF")) {
			        			   emccd.setDarkNoisecountsPerSecond(0.00010);
			        		   }
			        		   if(value.contains("XW-RECR")) {
			        			   emccd.setCoolingTemperature(-95);
			        		   }
			        		   if(value.contains("ACC-XW-CHIL-160")) {
			        			   emccd.setCoolingTemperature(-100);
			        		   }
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("Sensitivity")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setSensorQuantumEfficiency(Double.parseDouble(value));
			        		   emccd.setDetectionQuantumEfficiency(Double.parseDouble(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("TimeStamp")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setTimeStampAccuracy(Integer.parseInt(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("VerticalShiftSpeed")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setVerticalClockSpeed(Double.parseDouble(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("PixelReadOutTime")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setFrameRate(Double.parseDouble(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("SubImageRight")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setFrameBinningX(Integer.parseInt(value));
			        		   emccd.setResolutionBinningX(Integer.parseInt(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("SubImageTop")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setFrameBinningY(Integer.parseInt(value));
			        		   emccd.setResolutionBinningY(Integer.parseInt(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("UnstabalizedTemperature")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setOperatingTemperature(Double.parseDouble(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("DetectionWavelength")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setWavelength(Integer.parseInt(value));
			        	   }
			        	   if(node.getAttributes().getNamedItem("name").getNodeValue().equals("EMRealGain")) {
			        		   String value = node.getAttributes().getNamedItem("value").getNodeValue();
			        		   emccd.setEmccdGain(Integer.parseInt(value));
			        	   }
			           }
			       }
			   }
		  		experiment.addDevice(emccd);
		  	}

		    else if(child.getName().startsWith("spe")) {
		    	// ...
		    	}
		    	}
		  } else {
		    System.out.println("No files in directory");
		  }
		
	}
	
	public Experiment getExperiment() {
		return this.experiment;
	}
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
}
