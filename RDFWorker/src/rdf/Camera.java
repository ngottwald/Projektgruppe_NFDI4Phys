package rdf;

public class Camera {
	
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
	
	private boolean cmos;
	private boolean ccd;
	private boolean emccd;
	private double emccdGain;
	
	// resolution
	private int resolutionBinning; // x times y
	private int pixelWidth; // in microns
	private int pixelLength; // in microns
	private int numberOfPixels;
	private int imageAreaWidth; // in millimeters
	private int imageAreaLength; // in millimeters
	
	private int operatingTemperature; // in celcius
	
	// noise
	
	// detection efficency
	private int wavelength; // in nano meters
	private double detectionQuantumEfficiency;
}
