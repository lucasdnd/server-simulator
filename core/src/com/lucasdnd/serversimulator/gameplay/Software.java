package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;
import java.util.Random;

/**
 * The core mechanics
 * @author lucasdnd
 *
 */
public class Software {

	// Status
	private int features = 0;	// Increases demand
	private ArrayList<Thread> threads;	// Can deal with more requests simultaneously
	private int optimization = 0;	// Decreases request/io/response times
	private int bugs = 0;	// Have a chance to happen when implementing new features. Can cause requests to fail
	private boolean nonBlockingIO = false;	// Sync or async IO
	
	private int totalRequests = 0;	// Total number of requests served
	private int lostRequests = 0;	// Requests lost due to server busy (no threads available to work)
	
	public static final int maxFeatures = 10;
	public static final int maxThreads = 30;
	public static final int maxOptimization = 10;
	public static final int maxBugs = 10;
	
	public Software() {
		threads = new ArrayList<Thread>();
		addNewThread();
	}
	
	public void update() {
		
	}
	
	public void addFeatures() {
		features++;
		if (new Random().nextBoolean()) {
			bugs++;
		}
	}
	
	public void addNewThread() {
		threads.add(new Thread(threads.size()));
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

	public int getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

	public int getLostRequests() {
		return lostRequests;
	}

	public void setLostRequests(int lostRequests) {
		this.lostRequests = lostRequests;
	}
}
