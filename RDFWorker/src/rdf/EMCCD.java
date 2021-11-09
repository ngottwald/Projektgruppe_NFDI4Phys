package rdf;

public class EMCCD extends Camera{
	
	private double emccdGain;
	
	public EMCCD() {
		super();
	}

	public double getEmccdGain() {
		return emccdGain;
	}

	public void setEmccdGain(double emccdGain) {
		this.emccdGain = emccdGain;
	}

}
