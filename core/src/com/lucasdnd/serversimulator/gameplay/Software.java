package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.lucasdnd.serversimulator.ServerSimulator;

/**
 * The core mechanics
 * @author lucasdnd
 *
 */
public class Software {

	// Status
	private int features = 0;	// Directly influences demand
	private ArrayList<Thread> threads;	// Can deal with more requests simultaneously
	private int optimization = 0;	// Decreases request/io/response times
	private int bugs = 0;	// Have a chance to happen when implementing new features. Can cause requests to fail
	private boolean nonBlockingIO = false;	// Sync or async IO
	
	private int totalRequests = 0;	// Total number of requests served
	
	public static final int maxFeatures = 10;
	public static final int maxThreads = 10;
	public static final int maxOptimization = 10;
	public static final int maxBugs = 10;
	
	public Software() {
		threads = new ArrayList<Thread>();
		addNewThread();
	}
	
	/**
	 * This abstraction should be simpler... probably just a LinkedList and a better state control.
	 * That would remove the Server class, etc.
	 * 
	 * @param game
	 */
	public void update(ServerSimulator game) {
		
		LinkedList<Thread> freeThreads = new LinkedList<Thread>();
		
		for (Thread t : threads) {
			
			// Normal update
			t.update(game, game.getPlayer().getServer().getY());
			
			if (t.getRequest() != null) {
				
				// IO routing
				if (t.getRequest().getState() == Request.WAITING_FOR_IO) {
					
					Server server = game.getPlayer().getServer();
					if (nonBlockingIO) {
						t.getRequest().setState(Request.IO);
						server.getRequests().add(t.getRequest());
						t.setRequest(null);
					} else if (nonBlockingIO == false && server.isPerformingIO() == false) {
						server.setPerformingIO(true);
						t.getRequest().setState(Request.IO);
					}
					
				} else if (t.getRequest().getState() == Request.WAITING_FOR_RESPONSE && nonBlockingIO == false) {
					
					// Its own request is done with the IO (in sync mode)
					t.getRequest().setState(Request.RESPONSE);
					game.getPlayer().getServer().setPerformingIO(false);
					
				}
			}
			
			// Get the free threads so we can use them later
			if (t.getRequest() == null) {
				freeThreads.add(t);
			}
		}
		
		// Sync IO, with another free thread:
		if (nonBlockingIO == false) {
			for (Thread t : threads) {
				if (t.getRequest() != null) {
					if (t.getRequest().getState() == Request.WAITING_FOR_RESPONSE) {
						if (freeThreads.size() > 0) {
							freeThreads.removeFirst().setRequest(t.getRequest());
							game.getPlayer().getServer().setPerformingIO(false); // Free up the server to continue working on another request
						}
					}
				}
			}
		}
		// Async IO:
		// Check if any Server requests are done with the IO and direct them to a free thread for the Response
		else {
			ArrayList<Request> serverRequestsToRemove = new ArrayList<Request>();
			
			for (Request r : game.getPlayer().getServer().getRequests()) {
				if (r.getState() == Request.WAITING_FOR_RESPONSE && freeThreads.size() > 0) {
					serverRequestsToRemove.add(r);
					r.setState(Request.RESPONSE);
					
					for (Thread t : threads) {
						if (t.getRequest() == null) {
							t.setRequest(r);
							break;
						}
					}
				}
			}
			
			// If a Request was in the Server during the change to Async IO
			for (Thread t : threads) {
				if (t.getRequest() != null && t.getRequest().getState() == Request.WAITING_FOR_RESPONSE) {
					t.getRequest().setState(Request.RESPONSE);
				}
			}
			
			game.getPlayer().getServer().getRequests().removeAll(serverRequestsToRemove);
		}
	}
	
	public void render(ServerSimulator game) {
		for (Thread t : threads) {
			t.render();
		}
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
	
	public int getFreeThreads() {
		int freeThreads = 0;
		for (Thread t : threads) {
			if (t.getRequest() == null) {
				freeThreads++;
			}
		}
		return freeThreads;
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
	
	public void incrementTotalRequests() {
		totalRequests++;
	}

}
