package rdf;

public class Source extends Device{
	
	// output
	private boolean fiber;
	private boolean freeSpace;
	
	private int operatingTemperature; // in celsius
	
	// spectrum
	private int bandwidth; // in nanometres
	private int wavelength; // in nanometres;
	
	// power
	private int milliwat;
	private double powerStability; // in percent
	private double noise; // in percent
	
	public Source() {
		super();
	}

	public boolean isFiber() {
		return fiber;
	}

	public void setFiber(boolean fiber) {
		this.fiber = fiber;
	}

	public boolean isFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(boolean freeSpace) {
		this.freeSpace = freeSpace;
	}

	public int getOperatingTemperature() {
		return operatingTemperature;
	}

	public void setOperatingTemperature(int operatingTemperature) {
		this.operatingTemperature = operatingTemperature;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public int getWavelength() {
		return wavelength;
	}

	public void setWavelength(int wavelength) {
		this.wavelength = wavelength;
	}

	public int getMilliwat() {
		return milliwat;
	}

	public void setMilliwat(int milliwat) {
		this.milliwat = milliwat;
	}

	public double getPowerStability() {
		return powerStability;
	}

	public void setPowerStability(double powerStability) {
		this.powerStability = powerStability;
	}

	public double getNoise() {
		return noise;
	}

	public void setNoise(double noise) {
		this.noise = noise;
	}
}
