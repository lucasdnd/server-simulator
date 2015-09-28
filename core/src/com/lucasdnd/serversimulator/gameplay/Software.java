package com.lucasdnd.serversimulator.gameplay;

import java.util.Random;

public class Software {

	private int features = 0;
	private int optimization = 0;
	private int bugs = 0;
	private boolean nonBlockingIO = false;
	
	public Software() {
		
	}
	
	public void update() {
		
	}
	
	public void addFeatures() {
		features++;
		if (new Random().nextBoolean()) {
			bugs++;
		}
	}
	
	public void fixBug() {
		if (bugs > 0) {
			bugs--;
		}
	}
	
	public void optimize() {
		optimization++;
	}
	
	public int getFeatures() {
		return features;
	}

	public int getOptimization() {
		return optimization;
	}

	public int getBugs() {
		return bugs;
	}

	public boolean isNonBlockingIO() {
		return nonBlockingIO;
	}

	public void setNonBlockingIO(boolean nonBlockingIO) {
		this.nonBlockingIO = nonBlockingIO;
	}
}
