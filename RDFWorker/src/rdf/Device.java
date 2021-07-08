package rdf;

public class Device {
	
	// interfaces
	private boolean bnc;
	private boolean sma;
	private boolean usb3;
	private boolean ethernet;
	
	// manufacturer
	
	// maybe summerize all three in a single attribute?
	private String model;
	private String type;
	private String version;	
	
	private String sdk;
	private String software;

	public Device() {
		super();
	}

	public boolean isBnc() {
		return bnc;
	}

	public void setBnc(boolean bnc) {
		this.bnc = bnc;
	}

	public boolean isSma() {
		return sma;
	}

	public void setSma(boolean sma) {
		this.sma = sma;
	}

	public boolean isUsb3() {
		return usb3;
	}

	public void setUsb3(boolean usb3) {
		this.usb3 = usb3;
	}

	public boolean isEthernet() {
		return ethernet;
	}

	public void setEthernet(boolean ethernet) {
		this.ethernet = ethernet;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSdk() {
		return sdk;
	}

	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}
	
}
