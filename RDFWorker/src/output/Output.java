package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import rdf.*;

public class Output {
	
	/**
	 * Reads an experiment and generates a RDF-File based on this.
	 * @param experiment the experiment which gets converted into a RDF-File.
	 * @throws IOException
	 */

	public boolean output(Experiment experiment, String output) throws IOException {		
		
		File src = new File("rdf//NFDI4Phys.owl");
		File dest = new File(output + experiment.getName() + ".owl");
		if(dest.exists() && !dest.isDirectory()) {
			dest.delete();			
		}
		Files.copy(src.toPath(),dest.toPath());
		
		FileWriter fw = new FileWriter(output + experiment.getName() + ".owl", true);
		BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out= new PrintWriter(bw);
		
	    // The RDF-File gets written directly.
		for(Device d : experiment.getDevices()) {
			if(d instanceof Camera) {
				if(d instanceof EMCCD) {
					EMCCD emccd = (EMCCD) d;
					out.println("	<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/tobiasvente/ontologies/2020/11/NFDI4Phys#Camera\">");
					out.println("		<rdf:type rdf:resource=\"http://www.semanticweb.org/tobiasvente/ontologies/2020/11/NFDI4Phys#EMCCD\"/>");
					out.println("		<Back_Ilumination rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + emccd.isBackIllumination() + "</Back_Ilumination>");
					out.println("		<Binning>" + emccd.getResolutionBinningX() + "x" + emccd.getResolutionBinningY() + "</Binning>");
					out.println("		<Cooling_Temperature rdf:datatype=\"http://www.w3.org/2001/XMLSchema#Celsius\">" + emccd.getCoolingTemperature() + "</Cooling_Temperature>");
					out.println("		<C-Mount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + emccd.iscMount() + "</C-Mount>");
					out.println("		<Dark_Noise rdf:datatype=\"http://www.w3.org/2001/XMLSchema#electrons_per_pixel_per_second\">"+ emccd.getDarkNoisecountsPerSecond() + "</Dark_Noise>");
					out.println("		<Detection_Efficiency>" + emccd.getSensorQuantumEfficiency() + "%</Detection_Efficiency>");
					//out.println("		<Electronic_Noise>" + emccd.getElectronsPerPixelPerSeconds() + " electrons per pixel per second and " + emccd.getNoiseTemperature() + " Celsius</Electronic_Noise>");
					out.println("		<Frame_Binning>" + emccd.getFrameBinningX() + "x" + emccd.getFrameBinningY() + "</Frame_Binning>");
					out.println("		<Frame_Rate rdf:datatype=\"http://www.w3.org/2001/XMLSchema#MHz\">" + emccd.getFrameRate() + "</Frame_Rate>");
					out.println("		<Image_Area>" + emccd.getImageAreaLength() + "x" + emccd.getImageAreaWidth() + "</Image_Area>");
					out.println("		<F-Mount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + emccd.isfMount() + "</F-Mount>");
					out.println("		<Fringe_Supression rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + emccd.isFringeSuppression() + "</Fringe_Supression>");
					out.println("		<Linearity>>" + emccd.getLinearity() + "%</Linearity>");
					out.println("		<Maximum_Readout_Rate rdf:datatype=\"http://www.w3.org/2001/XMLSchema#MHz\">" + emccd.getMaximumReadoutRate() + "</Maximum_Readout_Rate>");
					out.println("		<Number_Of_Pixels rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + emccd.getNumberOfPixels() + "</Number_Of_Pixels>");
					out.println("		<Operating_Temperature rdf:datatype=\"http://www.w3.org/2001/XMLSchema#Celsius\">" + emccd.getOperatingTemperature() + "</Operating_Temperature>");
					out.println("		<Pixel_Size rdf:datatype=\"http://www.w3.org/2001/XMLSchema#micro_meter\">" + emccd.getPixelLength() + "x" + emccd.getPixelWidth() + "</Pixel_Size>");
					out.println("		<Quantum_Efficiency>" + emccd.getSensorQuantumEfficiency() + "%</Quantum_Efficiency>");
					out.println("		<Read_Noise rdf:datatype=\"http://www.w3.org/2001/XMLSchema#electrons_per_pixel_per_second\">"+ emccd.getReadNoiseElectrons() + "</Read_Noise>");
					out.println("		<Time_Stamp_Accuracy rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + emccd.getTimeStampAccuracy() + "</Time_Stamp_Accuracy>");
					out.println("		<T-Mount rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">" + emccd.istMount() + "</T-Mount>");
					out.println("		<Vertical_Clock_Speed rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + emccd.getVerticalClockSpeed() + "</Vertical_Clock_Speed>");
					out.println("		<Wavelength rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nm\">" + emccd.getWavelength() + "</Wavelength>");
					out.println("		<Well_Depth rdf:datatype=\"http://www.w3.org/2001/XMLSchema#electrons_per_pixel\">" + emccd.getWellDepth() + "</Well_Depth>");
					out.println("	</owl:NamedIndividual>");
					out.println("</rdf:RDF>");
				}
				if(d instanceof CCD) {
					// ...
				}
				if(d instanceof CMOS) {
					// ...
				}
			}
			
			if(d instanceof Detector) {
				if(d instanceof SNPD) {
					
				}
				if(d instanceof Photomultiplier) {
					// ...
				}
				if(d instanceof Photodiode) {
					// ...
				}
				if(d instanceof APD) {
					// ...
				}
			}
			
			if(d instanceof Source) {
				if(d instanceof LED) {
					
				}
				if(d instanceof Laser) {
					// ...
				}
				if(d instanceof Photodiode) {
					// ...
				}
				if(d instanceof APD) {
					// ...
				}
			}
		}	    
	    out.close();
		
		return true;
	}

}
