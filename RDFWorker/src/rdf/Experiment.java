package rdf;

import java.util.ArrayList;
import java.util.List;

public class Experiment {
	
	private String name; // or other identifier?
	
	private List<Device> devices;
	
	public Experiment() {
		super();
		this.devices = new ArrayList<Device>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	
	public void addDevice(Device device) {
		this.devices.add(device);
	}

}
