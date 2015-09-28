package com.lucasdnd.serversimulator.gameplay;

public class Software {

	private int features = 0;
	private int optimization = 0;
	private int bugs = 0;
	private boolean nonBlockingIO = false;
	
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
}
