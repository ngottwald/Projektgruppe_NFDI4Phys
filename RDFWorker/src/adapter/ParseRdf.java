package adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import rdf.*;

public class ParseRdf {
	
	Experiment experiment;
	
	/**
	 * Parses a rdf file to java classes.
	 * @param file the rdf filepath.
	 */
	public ParseRdf(String file) {
		parse(file);
	}
	
	/**
	 * Parses the rdf file.
	 * @param file the rdf filepath.
	 */
	public boolean parse(String file) {
		experiment = new Experiment();
		experiment.setName(file.substring(file.lastIndexOf("/")+1,file.indexOf(".")));
		
		Dataset d = RDFDataMgr.loadDataset(file);
		
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
		
		if(statements.isEmpty()) {
			return false;
		}
		
		evaluateAll(statements);
		
		return true;
	}
	
	/**
	 * Evaluates all statements of the rdf.
	 * @param statements is the list of all statements in the rdf.
	 */
	public void evaluateAll(List<String> statements) {
		
		List<String> ex = new ArrayList<String>();
		List<String> camera = new ArrayList<String>();
		List<String> apd = new ArrayList<String>();
		List<String> laser = new ArrayList<String>();
		
		for(String curr : statements) {
			String[] split = curr.split("\\s+");		
			
			if(split[0].equals("Experiment")) {
				ex.add(split[1] + " " + split[2]);
			}
			if(split[0].equals("Camera")) {
				camera.add(split[1] + " " + split[2]);
			}
			if(split[0].equals("APD")) {
				apd.add(split[1] + " " + split[2]);
			}
			if(split[0].equals("Laser")) {
				laser.add(split[1] + " " + split[2]);
			}			
		}
		Experiment e = evaluateExperiment(ex);
		Camera c = evaluateCamera(camera);
		//evaluateAPD(apd);
		Laser l = evaluateLaser(laser);
		experiment.setName(e.getName());
		experiment.setTimestamp(e.getTimestamp());
		experiment.setUniversity(e.getUniversity());
		experiment.setRoom(e.getRoom());
		experiment.setMembers(e.getMembers());
		experiment.addDevice(c);
		experiment.addDevice(l);
	}
	
	/**
	 * Evaluates the camera part of the rdf.
	 * @param statements is the list of statements corresponding to cameras.
	 * @return the generated Camera class of the rdf.
	 */
	public Camera evaluateCamera(List<String> statements) {		
		Camera camera = new Camera();
		
		for(String s : statements) {
			String[] split = s.split("\\s+");
			if(split[0].equals("type") && !split[1].contains("NamedIndividual")) {
				if(split[1].contains("EMCCD")) {
					camera = new EMCCD();
				}
				else if(split[1].contains("CCD")) {
					camera = new CCD();
				}
				else if(split[1].contains("CMOS")) {
					camera = new CMOS();
				}
			}
		}
		for(String s : statements) {
			String[] split = s.split("\\s+");			

			if(split[0].equals("Back_Ilumination")) {
				camera.setBackIllumination(Boolean.parseBoolean(split[1].substring(0,split[1].indexOf("^"))));
			}
			if(split[0].equals("Binning")) {
				camera.setFrameBinningX(Integer.parseInt(split[1].substring(0,split[1].indexOf("x"))));
				camera.setFrameBinningY(Integer.parseInt(split[1].substring(split[1].indexOf("x")+1,split[1].length())));
				camera.setResolutionBinningX(Integer.parseInt(split[1].substring(0,split[1].indexOf("x"))));
				camera.setResolutionBinningY(Integer.parseInt(split[1].substring(split[1].indexOf("x")+1,split[1].length())));
			}
			if(split[0].equals("Cooling_Temperature")) {
				camera.setCoolingTemperature(Integer.parseInt(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("C-Mount")) {
				camera.setcMount(Boolean.parseBoolean(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Dark_Noise")) {
				camera.setDarkNoisecountsPerSecond(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Detection_Efficiency")) {
				camera.setDetectionQuantumEfficiency(Double.parseDouble(split[1].substring(0,split[1].length()-1)));			
			}
			if(split[0].equals("Frame_Rate")) {
				camera.setFrameRate(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Image_Area")) {
				camera.setImageAreaLength(Double.parseDouble(split[1].substring(0,split[1].indexOf("x"))));		
				camera.setImageAreaWidth(Double.parseDouble(split[1].substring(split[1].indexOf("x")+1,split[1].length())));
			}
			if(split[0].equals("F-Mount")) {
				camera.setfMount(Boolean.parseBoolean(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Fringe_Supression")) {
				camera.setFringeSuppression(Boolean.parseBoolean(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Linearity")) {
				camera.setLinearity(Double.parseDouble(split[1].substring(1,split[1].length()-1)));			
			}
			if(split[0].equals("Maximum_Readout_Rate")) {
				camera.setMaximumReadoutRate(Integer.parseInt(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Number_Of_Pixels")) {
				camera.setNumberOfPixels(Integer.parseInt(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Operating_Temperature")) {
				camera.setOperatingTemperature(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Pixel_Size")) {
				camera.setPixelLength(Integer.parseInt(split[1].substring(0,split[1].indexOf("x"))));	
				camera.setPixelWidth(Integer.parseInt(split[1].substring(split[1].indexOf("x")+1,split[1].indexOf("^"))));	
			}
			if(split[0].equals("Quantum_Efficiency")) {
				camera.setSensorQuantumEfficiency(Double.parseDouble(split[1].substring(0,split[1].length()-1)));	
				camera.setDetectionQuantumEfficiency(Double.parseDouble(split[1].substring(0,split[1].length()-1)));
			}
			if(split[0].equals("Read_Noise")) {
				camera.setReadNoiseElectrons(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Time_Stamp_Accuracy")) {
				camera.setTimeStampAccuracy(Integer.parseInt(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("T-Mount")) {
				camera.settMount(Boolean.parseBoolean(split[1].substring(0,split[1].indexOf("^"))));
			}
			if(split[0].equals("Vertical_Clock_Speed")) {
				camera.setVerticalClockSpeed(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));
			}
			if(split[0].equals("Wavelength")) {
				camera.setWavelength(Integer.parseInt(split[1].substring(0,split[1].indexOf("^"))));			
			}
			if(split[0].equals("Well_Depth")) {
				camera.setWellDepth(Integer.parseInt(split[1].substring(0,split[1].indexOf("^"))));			
			}
		}
		return camera;
	}
	
	public void evaluateAPD(List<String> statements) {
		
	}
	
	/** Evaluates the laser part of the rdf.
	 * @param statements is the list of statements corresponding to lasers.
	 * @return the generated Laser class of the rdf.
	 */
	public Laser evaluateLaser(List<String> statements) {		
		Laser laser = new Laser();
		
		for(String s : statements) {
			String[] split = s.split("\\s+");
			if(split[0].equals("type") && !split[1].contains("NamedIndividual")) {
				if(split[1].contains("CW")) {
					laser = new CW();
				}
				else if(split[1].contains("Pulsed")) {
					laser = new Pulsed();
				}
			}
		}
		for(String s : statements) {
			String[] split = s.split("\\s+");			

			if(split[0].equals("Power")) {
				laser.setMilliwat(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));
			}
			if(split[0].equals("Wavelength")) {
				laser.setWavelength(Double.parseDouble(split[1].substring(0,split[1].indexOf("^"))));
			}
		}
		return laser;
	}
	
	/** Evaluates the experiment part of the rdf.
	 * @param statements is the list of statements corresponding to experiment.
	 * @return the generated Experiment class of the rdf.
	 */
	public Experiment evaluateExperiment(List<String> statements) {		
		Experiment experiment = new Experiment();
		
		for(String s : statements) {
			String[] split = s.split("\\s+");			

			if(split[0].equals("Id")) {
				experiment.setName(split[1].substring(0,split[1].length()));
			}
			if(split[0].equals("Timestamp")) {
				experiment.setTimestamp(split[1].substring(0,split[1].length()));
			}
			if(split[0].equals("University")) {
				experiment.setUniversity(split[1].substring(0,split[1].length()));
			}
			if(split[0].equals("Room")) {
				experiment.setRoom(split[1].substring(0,split[1].length()));
			}
			if(split[0].equals("Members")) {
				experiment.setMembers(split[1].substring(0,split[1].length()));
			}

		}
		return experiment;
	}
	
	/**
	 * Prints the output of an experiment.
	 */
	public void printExperiment() {
		System.out.println("Experiment name: " + experiment.getName() + "\n");
		
		for(Device d : experiment.getDevices()) {
			if(d instanceof Camera) {
				System.out.println("Camera");
				if(d instanceof EMCCD) {
					EMCCD e = (EMCCD) d;					
					System.out.println("	Type: EMCCD");
					System.out.println("	Back Ilumination: " + e.isBackIllumination());
					System.out.println("	Binning: " + e.getFrameBinningX() + "x" + e.getFrameBinningY());
					System.out.println("	Cooling Temperature: " + e.getCoolingTemperature() + " Celsius");
					System.out.println("	C-Mount: " + e.iscMount());
					System.out.println("	Dark Noise: " + e.getDarkNoisecountsPerSecond() + " electrons per pixel per second");
					System.out.println("	Detection Efficiency : " + e.getDetectionQuantumEfficiency() + "%");
					System.out.println("	Frame Binning: " + e.getFrameBinningX() + "x" + e.getFrameBinningY());
					System.out.println("	Frame Rate: " + e.getFrameRate() + "MHz");
					System.out.println("	Image Area: " + e.getImageAreaLength() + "x" + e.getImageAreaWidth());
					System.out.println("	F-Mount: " + e.isfMount());
					System.out.println("	Fringe Suppression: " + e.isFringeSuppression());
					System.out.println("	Linearity:>" + e.getLinearity() + "%");
					System.out.println("	Maximum Readout Rate: " + e.getMaximumReadoutRate() + "MHz");
					System.out.println("	Number of Pixels: " + e.getNumberOfPixels());
					System.out.println("	Operating Temperature: " + e.getOperatingTemperature() + " Celsius");
					System.out.println("	Pixel Size: " + e.getPixelLength() + "x" + e.getPixelWidth() + " micro meter");
					System.out.println("	Quantum Efficiency: " + e.getDetectionQuantumEfficiency() + "%");
					System.out.println("	Read Noise: " + e.getReadNoiseElectrons() + " electrons per pixel per second");
					System.out.println("	Time Stamp Accuracy: " + e.getTimeStampAccuracy());
					System.out.println("	T-Mount: " + e.istMount());
					System.out.println("	Vertical Clock Speed: " + e.getVerticalClockSpeed());
					System.out.println("	Wavelength: " + e.getWavelength() + "nm");
					System.out.println("	Well Depth: " + e.getWellDepth() + " electrons per pixel");
				}				
			}
			if(d instanceof APD) {
				System.out.println("APD");
			}
			if(d instanceof Laser) {
				System.out.println("Laser");
				if(d instanceof CW) {
					CW cw = (CW) d;					
					System.out.println("	Type: CW");
					System.out.println("	Power: " + cw.getMilliwat() + "mW");
					System.out.println("	Wavelength: " + cw.getWavelength() + "nm");
				}
			}
		}
	}

	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

}
