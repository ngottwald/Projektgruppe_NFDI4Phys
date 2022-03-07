package visual;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import rdf.*;

public class Visual{
	
	/** Reads in an experiment and creates a png showing the graph of the experiment.
	 * @param experiment the experiment that needs to be converted to a graph
	 * @throws IOException
	 */
	public Visual(Experiment experiment) throws IOException{
		writeDot(experiment);
		createGraph(experiment.getName());
	}
	
	/** Writes the dot graph th the corresponding experiment.
	 * @param experiment the experiment that needs to be converted to a graph.
	 * @throws IOException
	 */
	public void writeDot(Experiment experiment) throws IOException {
		File src = new File("resources/graph.dot");
		
		new PrintWriter("resources/graph.dot").close();
	    PrintWriter out= new PrintWriter("resources/graph.dot");
	    
	    out.println("graph G {");
	    out.println("	rankdir = TB;");
	    out.println("	subgraph {");
	    out.println("	Experiment [label=\n"
	    		+ "    <<b>Experiment</b><BR ALIGN=\"LEFT\"\n"
	    		+ "    />" + experiment.getName() + "<BR ALIGN=\"CENTER\"/>>];");
	    out.println("	University [label=\n"
	    		+ "    <<b>University</b><BR ALIGN=\"LEFT\"\n"
	    		+ "    />" + experiment.getUniversity() + "<BR ALIGN=\"CENTER\"/>>];");
	    out.println("	Experiment -- University;");
	    out.println("	Room [label=\n"
	    		+ "    <<b>Room</b><BR ALIGN=\"LEFT\"\n"
	    		+ "    />" + experiment.getRoom() + "<BR ALIGN=\"CENTER\"/>>];");
	    out.println("	University -- Room;");
	    out.println("	Members [label=\n"
	    		+ "    <<b>Members</b><BR ALIGN=\"LEFT\"\n"
	    		+ "    />" + experiment.getMembers() + "<BR ALIGN=\"CENTER\"/>>];");
	    out.println("	University -- Members;");
	    out.println("	ActiveDevices [label=\n"
	    		+ "    <<b>Acitve Devices</b>>];");
	    out.println("	ActiveDevices -- Experiment;");
	    out.println("	{rank = min; Experiment;}");
	    
	    for(Device d : experiment.getDevices()) {
	    	if(d instanceof Camera) {
	    		Camera c = (Camera) d;
	    		out.println("	Camera [label=\n"
	    				+ "    <<b>Camera</b>>];");
	    		out.println("	ActiveDevices -- Camera;");
	    		if(d instanceof EMCCD) {
		    		out.println("	EMCCD [label=\n"
		    				+ "    <<b>EMCCD</b>>];");
		    		out.println("	Camera -- EMCCD");
		    		EMCCD e = (EMCCD) d;
		    	    out.println("	EmccdGain [label=\n"
		    	    		+ "    <<b>Gain</b><BR ALIGN=\"LEFT\"\n"
		    	    		+ "    />" + e.getEmccdGain() + "<BR ALIGN=\"CENTER\"/>>];");
		    	    out.println("	EMCCD -- EmccdGain;");
		    	    
		    		out.println("	Binning [label=\n"
		    				+ "    <<b>Binning</b>>];");
		    		out.println("	EMCCD -- Binning");
		    		out.println("	binVal [label=\n"
		    				+ "    <<b>" + e.getResolutionBinningX() + " times " + e.getResolutionBinningY() + "</b>>];");
		    		out.println("	Binning -- binVal;");
	    		}
	    		if(d instanceof CMOS) {
		    		out.println("	CMOS [label=\n"
		    				+ "    <<b>CMOS</b>>];");
		    		out.println("	Camera -- CMOS;");
	    		}
	    		if(d instanceof CCD) {
		    		out.println("	CCD [label=\n"
		    				+ "    <<b>CCD</b>>];");
		    		out.println("	Camera -- CCD;");
	    		}
	    		
	    		out.println("	SensorOptions [label=\n"
	    				+ "    <<b>Sensor Options</b>>];");
	    		out.println("	Camera -- SensorOptions;");
	    	    out.println("	QuantumEfficiency [label=\n"
	    	    		+ "    <<b>Quantum Efficiency</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.getSensorQuantumEfficiency() + "%" + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	SensorOptions -- QuantumEfficiency;");
	    	    out.println("	FringeSuppression [label=\n"
	    	    		+ "    <<b>Fringe Suppression</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.isFringeSuppression() + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	SensorOptions -- FringeSuppression;");
	    	    out.println("	BackIllumination [label=\n"
	    	    		+ "    <<b>Back Illumination</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.isBackIllumination() + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	SensorOptions -- BackIllumination;");
	    	    
	    		out.println("	TimeResolution [label=\n"
	    				+ "    <<b>TimeResolution</b>>];");
	    		out.println("	Camera -- TimeResolution;");
	    		out.println("	TimeStampAccuracy [label=\n"
	    				+ "    <<b>Time Stamp Accuracy</b>>];");
	    		out.println("	TimeResolution -- TimeStampAccuracy;");
	    		out.println("	timeResVal [label=\n"
	    				+ "    <<b>" + c.getTimeStampAccuracy() + " Nano seconds" + "</b>>];");
	    		out.println("	TimeStampAccuracy -- timeResVal;");
	    		out.println("	VerticalClockSpeed [label=\n"
	    				+ "    <<b>Vertical Clock Speed</b>>];");
	    		out.println("	TimeResolution -- VerticalClockSpeed;");
	    		out.println("	clockSpeedVal [label=\n"
	    				+ "    <<b>" + c.getVerticalClockSpeed() + " Micro seconds" + "</b>>];");
	    		out.println("	VerticalClockSpeed -- clockSpeedVal;");
	    		out.println("	FrameRate [label=\n"
	    				+ "    <<b>Frame Rate</b>>];");
	    		out.println("	TimeResolution -- FrameRate;");
	    		out.println("	frameRateVal [label=\n"
	    				+ "    <<b>" + c.getFrameRate() + " Frames per second" + "</b>>];");
	    		out.println("	FrameRate -- frameRateVal;");
	    		out.println("	MaximumReadoutRate [label=\n"
	    				+ "    <<b>Maximum Readout Rate</b>>];");
	    		out.println("	TimeResolution -- MaximumReadoutRate;");
	    		out.println("	maxReadVal [label=\n"
	    				+ "    <<b>" + c.getMaximumReadoutRate() + " MHz" + "</b>>];");
	    		out.println("	MaximumReadoutRate -- maxReadVal;");
	    		
	    		out.println("	WellDepth [label=\n"
	    				+ "    <<b>Well Depth</b>>];");
	    		out.println("	Camera -- WellDepth;");
	    		out.println("	wellDepthVal [label=\n"
	    				+ "    <<b>" + c.getWellDepth()+ " Electrons" + "</b>>];");
	    		out.println("	WellDepth -- wellDepthVal;");
	    		
	    		out.println("	LensMount [label=\n"
	    				+ "    <<b>Lens Mount</b>>];");
	    		out.println("	Camera -- LensMount;");
	    	    out.println("	CMount [label=\n"
	    	    		+ "    <<b>C-Mount</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.iscMount() + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	LensMount -- CMount;");
	    	    out.println("	TMount [label=\n"
	    	    		+ "    <<b>T-Mount</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.istMount() + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	LensMount -- TMount;");
	    	    out.println("	FMount [label=\n"
	    	    		+ "    <<b>F-Mount</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.isfMount() + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	LensMount -- FMount;");
	    	    
	    	    out.println("	Linearity [label=\n"
	    	    		+ "    <<b>Linearity</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.getLinearity() + "%" + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	Camera -- Linearity;");
	    	    
	    		out.println("	Resolution [label=\n"
	    				+ "    <<b>Resolution</b>>];");
	    		out.println("	Camera -- Resolution;");
	    	    out.println("	Resolution -- Binning;"); // ???
	    		out.println("	PixelSize [label=\n"
	    				+ "    <<b>Pixel Size</b>>];");
	    		out.println("	Resolution -- PixelSize;");
	    		out.println("	pixelSizeVal [label=\n"
	    				+ "    <<b>" + c.getPixelLength() + " x " + c.getPixelWidth() + "</b>>];");
	    		out.println("	PixelSize -- pixelSizeVal;");
	    	    out.println("	NumberOfPixels [label=\n"
	    	    		+ "    <<b>Number of Pixels</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.getNumberOfPixels() + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	Resolution -- NumberOfPixels;");
	    		out.println("	ImageArea [label=\n"
	    				+ "    <<b>Image Area</b>>];");
	    		out.println("	Resolution -- ImageArea;");
	    		out.println("	imageAreaVal [label=\n"
	    				+ "    <<b>" + c.getImageAreaLength() + "mm x " + c.getImageAreaWidth() + "mm" + "</b>>];");
	    		out.println("	ImageArea -- imageAreaVal;");
	    		
	    	    out.println("	OperatingTemperature [label=\n"
	    	    		+ "    <<b>Operating Temperature</b><BR ALIGN=\"LEFT\"\n"
	    	    		+ "    />" + c.getOperatingTemperature() + " Celsius" + "<BR ALIGN=\"CENTER\"/>>];");
	    	    out.println("	Camera -- OperatingTemperature;");
	    	    
	    		out.println("	Noise [label=\n"
	    				+ "    <<b>Noise</b>>];");
	    		out.println("	Camera -- Noise;");
	    		out.println("	CoolingTemperature [label=\n"
	    				+ "    <<b>Cooling Temperature</b>>];");
	    		out.println("	Noise -- CoolingTemperature;");
	    		out.println("	coolTempVal [label=\n"
	    				+ "    <<b>" + c.getCoolingTemperature() + " Celsius" + "</b>>];");
	    		out.println("	CoolingTemperature -- coolTempVal;");
	    		out.println("	DarkNoise [label=\n"
	    				+ "    <<b>Dark Noise</b>>];");
	    		out.println("	Noise -- DarkNoise;");
	    		out.println("	darkNoiseVal [label=\n"
	    				+ "    <<b>" + c.getDarkNoisecountsPerSecond() + " Counts per second" + "</b>>];");
	    		out.println("	DarkNoise -- darkNoiseVal;");
	    		out.println("	ElectronicNoise [label=\n"
	    				+ "    <<b>Electronic Noise</b>>];");
	    		out.println("	Noise -- ElectronicNoise;");
	    		out.println("	elecNoiseVal1 [label=\n"
	    				+ "    <<b>" + c.getElectronsPerPixelPerSeconds() + " Electrons per pixel per second" + "</b>>];");
	    		out.println("	ElectronicNoise -- elecNoiseVal1;");
	    		out.println("	elecNoiseVal2 [label=\n"
	    				+ "    <<b>" + c.getNoiseTemperature() + " Celsius" + "</b>>];");
	    		out.println("	ElectronicNoise -- elecNoiseVal2;");
	    		out.println("	ReadNoise [label=\n"
	    				+ "    <<b>ReadNoise</b>>];");
	    		out.println("	Noise -- ReadNoise;");
	    		out.println("	readNoiseVal [label=\n"
	    				+ "    <<b>" + c.getReadNoiseElectrons() + " Electrons" + "</b>>];");
	    		out.println("	ReadNoise -- readNoiseVal;");
	    		
	    		out.println("	DetectionEfficiency [label=\n"
	    				+ "    <<b>Detection Efficiency</b>>];");
	    		out.println("	Camera -- DetectionEfficiency;");
	    		out.println("	detEffVal1 [label=\n"
	    				+ "    <<b>" + c.getDetectionQuantumEfficiency() + "%" + "</b>>];");
	    		out.println("	DetectionEfficiency -- detEffVal1;");
	    		out.println("	detEffVal2 [label=\n"
	    				+ "    <<b>" + c.getWavelength() + "nm" + "</b>>];");
	    		out.println("	DetectionEfficiency -- detEffVal2;");
	    		
	    		
	    	}
	    	if(d instanceof Detector) {
	    		// ...
	    	}
	    	if(d instanceof Laser) {
	    		Source s = (Source) d;
	    		
	    		out.println("	Source [label=\n"
	    				+ "    <<b>Source</b>>];");
	    		out.println("	ActiveDevices -- Source;");
	    		
		    	out.println("	Laser [label=\n"
		    				+ "    <<b>Laser</b>>];");
		    	out.println("	Source -- Laser");
		    		
		    	if(d instanceof CW) {
			    	out.println("	CW [label=\n"
			    			+ "    <<b>CW</b>>];");
			    	out.println("	Laser -- CW");
		    	}
		    	if(d instanceof Pulsed) {
			    	out.println("	Pulsed [label=\n"
			    			+ "    <<b>Pulsed</b>>];");
			    	out.println("	Laser -- Pulsed");
		    	}
		    	if(d instanceof CW && d instanceof Pulsed) {
			    	out.println("	CW/Pulsed [label=\n"
			    			+ "    <<b>CW/Pulsed</b>>];");
			    	out.println("	CW -- CW/Pulsed");
			    	out.println("	Pulsed -- CW/Pulsed");
	    		}
	    		if(d instanceof LED) {
		    		out.println("	LED [label=\n"
		    				+ "    <<b>LED</b>>];");
		    		out.println("	Source -- LED;");
	    		}
	    		out.println("	Spectrum [label=\n"
	    				+ "    <<b>Spectrum</b>>];");
	    		out.println("	Source -- Spectrum;");
//	    		out.println("	Bandwidth [label=\n"
//	    				+ "    <<b>Bandwidth</b>>];");
//	    		out.println("	Spectrum -- Bandwidth;");
//	    		out.println("	bandwidthVal [label=\n"
//	    				+ "    <<b>" + s.getBandwidth() + " nm" + "</b>>];");
//	    		out.println("	Bandwidth -- bandwidthVal;");
	    		out.println("	Wavelength [label=\n"
	    				+ "    <<b>Wavelength</b>>];");
	    		out.println("	Spectrum -- Wavelength;");
	    		out.println("	wavelengthVal [label=\n"
	    				+ "    <<b>" + s.getWavelength() + " nm" + "</b>>];");
	    		out.println("	Wavelength -- wavelengthVal;");
	    		
	    		out.println("	Power [label=\n"
	    				+ "    <<b>Power</b>>];");
	    		out.println("	Source -- Power;");
	    		out.println("	powerVal [label=\n"
	    				+ "    <<b>" + s.getMilliwat() + " mW" + "</b>>];");
	    		out.println("	Power -- powerVal;");
	    	}
	    	if(d instanceof FrequencyConverter) {
	    		// ...
	    	}	    	
	    }
	    
//		out.println("	Detector [label=\n"
//				+ "    <<b>Detector</b>>];");
//		out.println("	ActiveDevices -- Detector;");
//		out.println("	APD [label=\n"
//				+ "    <<b>APD</b>>];");
//		out.println("	Detector -- APD;");
	    out.println("	}");
	    out.println("}");
	    
	    out.close();
	}
	
	
	/** Creates a png based on a previously generated dot file.
	 * @param name the filename of the created png.
	 * @throws IOException
	 */
	public void createGraph(String name) throws IOException {
		try (InputStream dot = getClass().getResourceAsStream("/graph.dot")) {
		    MutableGraph g = new Parser().read(dot);
		    File f = new File("output/png/" + name + ".png");
		    Files.deleteIfExists(f.toPath());
		    Graphviz.fromGraph(g).width(6000).render(Format.PNG).toFile(new File("output/png/" + name + ".png"));
		}
	}
}