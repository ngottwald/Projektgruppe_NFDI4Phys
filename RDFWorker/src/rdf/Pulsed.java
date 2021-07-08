package rdf;

public class Pulsed extends Laser{
	
	// private boolean CW/Pulsed
	
	private int pulseDuration; // time
	private int repetitionRate; // frequency
	public int getPulseDuration() {
		return pulseDuration;
	}
	public void setPulseDuration(int pulseDuration) {
		this.pulseDuration = pulseDuration;
	}
	public int getRepetitionRate() {
		return repetitionRate;
	}
	public void setRepetitionRate(int repetitionRate) {
		this.repetitionRate = repetitionRate;
	}

}
