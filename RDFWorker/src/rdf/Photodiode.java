package rdf;

public class Photodiode extends Detector{

	// operation mode
	private boolean linearMode;
	private boolean photoconductiveMode;
	private boolean geigerMode;
	private boolean photovoltaicMode;
	private double linearity;
	private int percent;
	
	public Photodiode() {
		super();
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
}
