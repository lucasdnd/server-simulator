package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;
import java.util.Random;

public class Software {

	// Status
	private int features = 0;
	private ArrayList<Thread> threads;
	private int optimization = 0;
	private int bugs = 0;
	private boolean nonBlockingIO = false;
	
	private int totalRequests = 0;
	private int lostRequests = 0;
	
	public static final int maxFeatures = 10;
	public static final int maxThreads = 30;
	public static final int maxOptimization = 10;
	public static final int maxBugs = 10;
	
	public Software() {
		threads = new ArrayList<Thread>();
		threads.add(new Thread(threads.size()));
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
	
	public ArrayList<Thread> getThreads() {
		return threads;
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
