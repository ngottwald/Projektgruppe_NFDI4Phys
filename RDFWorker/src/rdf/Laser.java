package rdf;

public class Laser extends Source{
	
	// laser mode
	
	//polarization
	private int polarizationContrast; // ratio
	
	// direction	
	private boolean eliptical;
	private boolean polarization;
	private boolean horizontal;
	private boolean vertical;
	
	// spatial mode
	private int mSquared;
	private boolean tem00;
	private int beamDiameter; // in mm
	private int beamDivergence; // in milli radiants
	
	public Laser() {
		super();
	}

	public int getPolarizationContrast() {
		return polarizationContrast;
	}

	public void setPolarizationContrast(int polarizationContrast) {
		this.polarizationContrast = polarizationContrast;
	}

	public boolean isEliptical() {
		return eliptical;
	}

	public void setEliptical(boolean eliptical) {
		this.eliptical = eliptical;
	}

	public boolean isPolarization() {
		return polarization;
	}

	public void setPolarization(boolean polarization) {
		this.polarization = polarization;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public int getmSquared() {
		return mSquared;
	}

	public void setmSquared(int mSquared) {
		this.mSquared = mSquared;
	}

	public boolean isTem00() {
		return tem00;
	}

	public void setTem00(boolean tem00) {
		this.tem00 = tem00;
	}

	public int getBeamDiameter() {
		return beamDiameter;
	}

	public void setBeamDiameter(int beamDiameter) {
		this.beamDiameter = beamDiameter;
	}

	public int getBeamDivergence() {
		return beamDivergence;
	}

	public void setBeamDivergence(int beamDivergence) {
		this.beamDivergence = beamDivergence;
	}

}
