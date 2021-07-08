package rdf;

public class Camera extends Device{
	
	// sensor options
	private boolean backIllumination;
	private boolean fringeSuppression;
	private double sensorQuantumEfficiency;
	
	// time resolution
	private int timeStampAccuracy; // in nano seconds
	private int verticalClockSpeed; // in micro seconds
	private int frameRate; // in frames per second
	private int frameBinning; // x times y
	private int maximumReadoutRate; // in MHz
	
	private int wellDepth; // electrons per pixel
	
	// lens mount
	private boolean cMount;
	private boolean tMount;
	private boolean fMount;
	
	private double linearity;	
	
	// resolution
	private int resolutionBinning; // x times y
	private int pixelWidth; // in microns
	private int pixelLength; // in microns
	private int numberOfPixels;
	private int imageAreaWidth; // in millimeters
	private int imageAreaLength; // in millimeters
	
	private int operatingTemperature; // in celcius
	
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
	
	public Camera() { // what attributes are mandatory for a camera?
		super();
	}
	
	public boolean isBackIllumination() {
		return backIllumination;
	}
	public void setBackIllumination(boolean backIllumination) {
		this.backIllumination = backIllumination;
	}
	public boolean isFringeSuppression() {
		return fringeSuppression;
	}
	public void setFringeSuppression(boolean fringeSuppression) {
		this.fringeSuppression = fringeSuppression;
	}
	public double getSensorQuantumEfficiency() {
		return sensorQuantumEfficiency;
	}
	public void setSensorQuantumEfficiency(double sensorQuantumEfficiency) {
		this.sensorQuantumEfficiency = sensorQuantumEfficiency;
	}
	public int getTimeStampAccuracy() {
		return timeStampAccuracy;
	}
	public void setTimeStampAccuracy(int timeStampAccuracy) {
		this.timeStampAccuracy = timeStampAccuracy;
	}
	public int getVerticalClockSpeed() {
		return verticalClockSpeed;
	}
	public void setVerticalClockSpeed(int verticalClockSpeed) {
		this.verticalClockSpeed = verticalClockSpeed;
	}
	public int getFrameRate() {
		return frameRate;
	}
	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}
	public int getFrameBinning() {
		return frameBinning;
	}
	public void setFrameBinning(int frameBinning) {
		this.frameBinning = frameBinning;
	}
	public int getMaximumReadoutRate() {
		return maximumReadoutRate;
	}
	public void setMaximumReadoutRate(int maximumReadoutRate) {
		this.maximumReadoutRate = maximumReadoutRate;
	}
	public int getWellDepth() {
		return wellDepth;
	}
	public void setWellDepth(int wellDepth) {
		this.wellDepth = wellDepth;
	}
	public boolean iscMount() {
		return cMount;
	}
	public void setcMount(boolean cMount) {
		this.cMount = cMount;
	}
	public boolean istMount() {
		return tMount;
	}
	public void settMount(boolean tMount) {
		this.tMount = tMount;
	}
	public boolean isfMount() {
		return fMount;
	}
	public void setfMount(boolean fMount) {
		this.fMount = fMount;
	}
	public double getLinearity() {
		return linearity;
	}
	public void setLinearity(double linearity) {
		this.linearity = linearity;
	}
	public int getResolutionBinning() {
		return resolutionBinning;
	}
	public void setResolutionBinning(int resolutionBinning) {
		this.resolutionBinning = resolutionBinning;
	}
	public int getPixelWidth() {
		return pixelWidth;
	}
	public void setPixelWidth(int pixelWidth) {
		this.pixelWidth = pixelWidth;
	}
	public int getPixelLength() {
		return pixelLength;
	}
	public void setPixelLength(int pixelLength) {
		this.pixelLength = pixelLength;
	}
	public int getNumberOfPixels() {
		return numberOfPixels;
	}
	public void setNumberOfPixels(int numberOfPixels) {
		this.numberOfPixels = numberOfPixels;
	}
	public int getImageAreaWidth() {
		return imageAreaWidth;
	}
	public void setImageAreaWidth(int imageAreaWidth) {
		this.imageAreaWidth = imageAreaWidth;
	}
	public int getImageAreaLength() {
		return imageAreaLength;
	}
	public void setImageAreaLength(int imageAreaLength) {
		this.imageAreaLength = imageAreaLength;
	}
	public int getOperatingTemperature() {
		return operatingTemperature;
	}
	public void setOperatingTemperature(int operatingTemperature) {
		this.operatingTemperature = operatingTemperature;
	}
//	public boolean isCheckManufacturer() {
//		return checkManufacturer;
//	}
//	public void setCheckManufacturer(boolean checkManufacturer) {
//		this.checkManufacturer = checkManufacturer;
//	}
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

	public int getDarkNoisecountsPerSecond() {
		return darkNoisecountsPerSecond;
	}

	public void setDarkNoisecountsPerSecond(int darkNoisecountsPerSecond) {
		this.darkNoisecountsPerSecond = darkNoisecountsPerSecond;
	}
}
