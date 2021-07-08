package rdf;

public class SNPD extends Detector{
	
	// input
	private double couplingEfficiancy;
	private boolean freeSpace;
	
	public SNPD(){
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
	

}
