package rdf;

public class Camera extends Device{
	
	// sensor options
	private boolean backIllumination = false;
	private boolean fringeSuppression = false;
	private double sensorQuantumEfficiency;
	
	// time resolution
	private int timeStampAccuracy; // in nano seconds
	private double verticalClockSpeed; // in micro seconds
	private double frameRate; // in frames per second
	private int frameBinningX; // x times y
	private int frameBinningY; // x times y
	private int maximumReadoutRate; // in MHz
	
	private int wellDepth; // electrons per pixel
	
	// lens mount
	private boolean cMount = false;
	private boolean tMount = false;
	private boolean fMount = false;
	
	private double linearity; // in percent
	
	// resolution
	private int resolutionBinningX; // x times y
	private int resolutionBinningY; // x times y
	private int pixelWidth; // in microns
	private int pixelLength; // in microns
	private int numberOfPixels;
	private double imageAreaWidth; // in millimeters
	private double imageAreaLength; // in millimeters
	
	private double operatingTemperature; // in celcius
	
	// noise
//	private boolean checkManufacturer; // no idea what this means
	private int coolingTemperature; // in celcius
	private int electronsPerPixelPerSeconds;
	private int noiseTemperature;
	private double darkNoisecountsPerSecond;
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
	public double getVerticalClockSpeed() {
		return verticalClockSpeed;
	}
	public void setVerticalClockSpeed(double d) {
		this.verticalClockSpeed = d;
	}
	public double getFrameRate() {
		return frameRate;
	}
	public void setFrameRate(double d) {
		this.frameRate = d;
	}
	public int getFrameBinningX() {
		return frameBinningX;
	}
	public void setFrameBinningX(int frameBinningX) {
		this.frameBinningX = frameBinningX;
	}
	public int getFrameBinningY() {
		return frameBinningY;
	}
	public void setFrameBinningY(int frameBinningY) {
		this.frameBinningY = frameBinningY;
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
	public int getResolutionBinningX() {
		return resolutionBinningX;
	}
	public void setResolutionBinningX(int resolutionBinningX) {
		this.resolutionBinningX = resolutionBinningX;
	}
	public int getResolutionBinningY() {
		return resolutionBinningY;
	}
	public void setResolutionBinningY(int resolutionBinningY) {
		this.resolutionBinningY = resolutionBinningY;
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
	public double getImageAreaWidth() {
		return imageAreaWidth;
	}
	public void setImageAreaWidth(double d) {
		this.imageAreaWidth = d;
	}
	public double getImageAreaLength() {
		return imageAreaLength;
	}
	public void setImageAreaLength(double d) {
		this.imageAreaLength = d;
	}
	public double getOperatingTemperature() {
		return operatingTemperature;
	}
	public void setOperatingTemperature(double d) {
		this.operatingTemperature = d;
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

	public double getDarkNoisecountsPerSecond() {
		return darkNoisecountsPerSecond;
	}

	public void setDarkNoisecountsPerSecond(double d) {
		this.darkNoisecountsPerSecond = d;
	}
}
