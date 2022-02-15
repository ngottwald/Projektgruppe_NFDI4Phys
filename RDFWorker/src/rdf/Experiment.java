package rdf;

import java.util.ArrayList;
import java.util.List;

public class Experiment {
	
	private String name; // or other identifier?
	
	private String timestamp;
	
	private String university;
	
	private String room;
	
	private String members;
	
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

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
