package rdf;

public class Detector extends Device{
	
	private int operatingTemperature; // in celcius
	
	private int activeArea; // x times y in micro metres
	
	// maximim count rate / power
	private int countsPerSecond;
	
	private int deadTime; // in nano seconds
	
	// time resolution
	private boolean nanoSeconds;
	private boolean picoSeconds;
//	private boolean checkManufacturer;
	
	// noise
//	private boolean checkManufacturer; // no idea what this means
	private int coolingTemperature; // in celcius
	private int electronsPerPixelPerSeconds;
	private int noiseTemperature;
	private int darkNoisecountsPerSecond;
	private int readNoiseElectrons;
	
	// detection efficency
	private int wavelength; // in nano meters
	private double detectionQuantumEfficiency;
	
	public Detector() { // what attributes are mandatory?
		super();
	}
	
	public int getOperatingTemperature() {
		return operatingTemperature;
	}
	public void setOperatingTemperature(int operatingTemperature) {
		this.operatingTemperature = operatingTemperature;
	}
	public int getActiveArea() {
		return activeArea;
	}
	public void setActiveArea(int activeArea) {
		this.activeArea = activeArea;
	}
	public int getCountsPerSecond() {
		return countsPerSecond;
	}
	public void setCountsPerSecond(int countsPerSecond) {
		this.countsPerSecond = countsPerSecond;
	}
	public int getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(int deadTime) {
		this.deadTime = deadTime;
	}
	public boolean isNanoSeconds() {
		return nanoSeconds;
	}
	public void setNanoSeconds(boolean nanoSeconds) {
		this.nanoSeconds = nanoSeconds;
	}
	public boolean isPicoSeconds() {
		return picoSeconds;
	}
	public void setPicoSeconds(boolean picoSeconds) {
		this.picoSeconds = picoSeconds;
	}
	public int getCoolingTemperature() {
		return coolingTemperature;
	}
	public void setCoolingTemperature(int coolingTemperature) {
		this.coolingTemperature = coolingTemperature;
	}
	public int getElectronsPerPixelPerSeconds() {
		return electronsPerPixelPerSeconds;
	}
	public void setElectronsPerPixelPerSeconds(int electronsPerPixelPerSeconds) {
		this.electronsPerPixelPerSeconds = electronsPerPixelPerSeconds;
	}
	public int getNoiseTemperature() {
		return noiseTemperature;
	}
	public void setNoiseTemperature(int noiseTemperature) {
		this.noiseTemperature = noiseTemperature;
	}
	public int getDarkNoisecountsPerSecond() {
		return darkNoisecountsPerSecond;
	}
	public void setDarkNoisecountsPerSecond(int darkNoisecountsPerSecond) {
		this.darkNoisecountsPerSecond = darkNoisecountsPerSecond;
	}
	public int getReadNoiseElectrons() {
		return readNoiseElectrons;
	}
	public void setReadNoiseElectrons(int readNoiseElectrons) {
		this.readNoiseElectrons = readNoiseElectrons;
	}
	public int getWavelength() {
		return wavelength;
	}
	public void setWavelength(int wavelength) {
		this.wavelength = wavelength;
	}
	public double getDetectionQuantumEfficiency() {
		return detectionQuantumEfficiency;
	}
	public void setDetectionQuantumEfficiency(double detectionQuantumEfficiency) {
		this.detectionQuantumEfficiency = detectionQuantumEfficiency;
	}

}
