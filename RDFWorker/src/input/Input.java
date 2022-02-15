package input;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rdf.CW;
import rdf.EMCCD;
import rdf.Experiment;

public class Input {
	
	private Experiment experiment;
	
	public Input(String university, String room, String members) {
		this.experiment = new Experiment();
		//this.experiment.setName(UUID.randomUUID().toString());
		this.experiment.setName("0000");
		this.experiment.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		this.experiment.setUniversity(university);
		this.experiment.setRoom(room);
		this.experiment.setMembers(members);
	}
	
	/**
	 * Reads in the input folder and parsed the xml files depending on the format.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */	
	public boolean input(String directory) throws ParserConfigurationException, SAXException, IOException {
		
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null && directoryListing.length > 0) {
		  for (File child : directoryListing) {
			  //experiment.setName(child.getName());
			  File[] exDir = child.listFiles();
			  for(File exChild : exDir) {
				  // get xml files that contain the sif data
				  if(exChild.getName().startsWith("sif")){
					  EMCCD emccd = new EMCCD();
					  emccd.setLinearity(99);
					  emccd.setWellDepth(200000);
					  emccd.setCoolingTemperature(-80);
					  emccd.setPixelLength(16);
					  emccd.setPixelWidth(16);
			  		
			  		  DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				        .newInstance();
			  		  DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			  		  Document document = documentBuilder.parse(exChild);
			  		  NodeList nodeList = document.getElementsByTagName("*");
			  		  for (int i = 0; i < nodeList.getLength(); i++) {
			  			  Node node = nodeList.item(i);
			  			  if (node.getNodeType() == Node.ELEMENT_NODE) {
			  				  // the properties of the xml get read out and the correct values are assigned 
			  				  if(node.getNodeName().equals("property")) {
				        	   
				        	     if(node.getAttributes().getNamedItem("name").getNodeValue().equals("HeadModel")) {
				        		     String value = node.getAttributes().getNamedItem("value").getNodeValue();
				        		     if(value.contains("DU970P")) {
				        			     emccd.setMaximumReadoutRate(649);				        			     
				        			     emccd.setImageAreaLength(25.6);
				        			     emccd.setImageAreaWidth(3.2);
				        			     emccd.setNumberOfPixels(1600*200);
				        		     }
				        		     if(value.contains("DU971P")) {
				        			     emccd.setMaximumReadoutRate(396);				        			     
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
				        		     if(value.equals("0")) {
				        		    	 String time = String.valueOf(emccd.getFrameRate());
				        		    	 if(time.startsWith("50")) {
				        		    		 emccd.setReadNoiseElectrons(2.8);
				        		    	 }
				        		    	 if(time.startsWith("1")) {
				        		    		 emccd.setReadNoiseElectrons(6.7);
				        		    	 }
				        		    	 if(time.startsWith("3")) {
				        		    		 emccd.setReadNoiseElectrons(8.5);
				        		    	 }
				        		     }
				        		     if(value.equals("1")) {
				        		    	 emccd.setReadNoiseElectrons(1);
				        		     }
				        	     }
				             }
				         }
				     }
			  		  experiment.addDevice(emccd);
			  	  }				  
				  // get xml files that contain the laser data
			      else if(exChild.getName().startsWith("laser")) {
			    	  CW cw = new CW();
			  		
			  		  DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				        .newInstance();
			  		  DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			  		  Document document = documentBuilder.parse(exChild);
			  		  NodeList nodeList = document.getElementsByTagName("*");
			  		  for (int i = 0; i < nodeList.getLength(); i++) {
			  			  Node node = nodeList.item(i);
			  			  if (node.getNodeType() == Node.ELEMENT_NODE) {
			  				  // the properties of the xml get read out and the correct values are assigned 
			  				  if(node.getNodeName().equals("property")) {
				        	   
				        	     if(node.getAttributes().getNamedItem("name").getNodeValue().equals("Power")) {
				        		     String value = node.getAttributes().getNamedItem("value").getNodeValue();
				        		     cw.setMilliwat(Double.parseDouble(value));
				        	     }
				        	     if(node.getAttributes().getNamedItem("name").getNodeValue().equals("Wavelength")) {
				        		     String value = node.getAttributes().getNamedItem("value").getNodeValue();
				        		     cw.setWavelength(Double.parseDouble(value));
				        	     }
				             }
				         }
				     }
			  		  experiment.addDevice(cw);
			    }
			}
		    }
		  } else {
		    System.out.println("No files in directory");
		    return false;
		  }
		
		return true;
	}
	
	public Experiment getExperiment() {
		return this.experiment;
	}
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
}
