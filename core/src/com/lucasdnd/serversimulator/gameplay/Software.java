package com.lucasdnd.serversimulator.gameplay;

public class Software {

	private int features = 0;
	private int optimization = 0;
	private int bugs = 0;
	private boolean nonBlockingIO = false;
	
	// Times are in 1/10s of seconds
	private int requestTime = 30;
	private int ioTime = 80;
	private int responseTime = 30;
	
	public Software() {
		
	}
	
	public void update() {
		
	}
	
	public int getFeatures() {
		return features;
	}

	public void setFeatures(int features) {
		this.features = features;
	}

	public int getOptimization() {
		return optimization;
	}

	public void setOptimization(int optimization) {
		this.optimization = optimization;
	}

	public int getBugs() {
		return bugs;
	}

	public void setBugs(int bugs) {
		this.bugs = bugs;
	}

	public boolean isNonBlockingIO() {
		return nonBlockingIO;
	}

	public void setNonBlockingIO(boolean nonBlockingIO) {
		this.nonBlockingIO = nonBlockingIO;
	}

	public int getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public int getIoTime() {
		return ioTime;
	}

	public void setIoTime(int ioTime) {
		this.ioTime = ioTime;
	}
	
}
