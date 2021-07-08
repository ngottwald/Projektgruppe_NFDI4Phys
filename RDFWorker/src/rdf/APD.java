package rdf;

public class APD {
	
	// input
	private double couplingEfficiancy;
	private boolean freeSpace;
	
	private String gain;
	
	// operation mode
	private boolean linearMode;
	private boolean photoconductiveMode;
	private boolean geigerMode;
	private boolean photovoltaicMode;
	private double linearity;
	private int percent;	
	
	// output mode
	private boolean ttl; // no info what this means
	private boolean nim;
	private int timeResolution; // in pico seconds
	
	private double afterPulsingProbability;
	
	public APD() {
		super();
	}

	public double getCouplingEfficiancy() {
		return couplingEfficiancy;
	}

	public void setCouplingEfficiancy(double couplingEfficiancy) {
		this.couplingEfficiancy = couplingEfficiancy;
	}

	public boolean isFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(boolean freeSpace) {
		this.freeSpace = freeSpace;
	}

	public String getGain() {
		return gain;
	}

	public void setGain(String gain) {
		this.gain = gain;
	}

	public boolean isLinearMode() {
		return linearMode;
	}

	public void setLinearMode(boolean linearMode) {
		this.linearMode = linearMode;
	}

	public boolean isPhotoconductiveMode() {
		return photoconductiveMode;
	}

	public void setPhotoconductiveMode(boolean photoconductiveMode) {
		this.photoconductiveMode = photoconductiveMode;
	}

	public boolean isGeigerMode() {
		return geigerMode;
	}

	public void setGeigerMode(boolean geigerMode) {
		this.geigerMode = geigerMode;
	}

	public boolean isPhotovoltaicMode() {
		return photovoltaicMode;
	}

	public void setPhotovoltaicMode(boolean photovoltaicMode) {
		this.photovoltaicMode = photovoltaicMode;
	}

	public double getLinearity() {
		return linearity;
	}

	public void setLinearity(double linearity) {
		this.linearity = linearity;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public boolean isTtl() {
		return ttl;
	}

	public void setTtl(boolean ttl) {
		this.ttl = ttl;
	}

	public boolean isNim() {
		return nim;
	}

	public void setNim(boolean nim) {
		this.nim = nim;
	}

	public int getTimeResolution() {
		return timeResolution;
	}

	public void setTimeResolution(int timeResolution) {
		this.timeResolution = timeResolution;
	}

	public double getAfterPulsingProbability() {
		return afterPulsingProbability;
	}

	public void setAfterPulsingProbability(double afterPulsingProbability) {
		this.afterPulsingProbability = afterPulsingProbability;
	}

}
