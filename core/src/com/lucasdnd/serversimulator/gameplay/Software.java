package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.lucasdnd.serversimulator.ServerSimulator;

/**
 * The core mechanics. Updates threads, request routing, etc.
 * 
 * This abstraction should be simpler... probably just a LinkedList and a better state control.
 * My goal is to eventually remove the Server class.
 * 
 * @author lucasdnd
 *
 */
public class Software {

	// Status
	private int features = 0;	// Directly influences demand
	private ArrayList<Thread> threads;	// Can deal with more requests simultaneously
	private int optimization = 0;	// Decreases request/io/response times
	private int bugs = 0;	// Have a chance to happen when implementing new features. Can cause requests to fail
	private boolean asyncIO = false;	// Sync or async IO
	
	private int totalRequests = 0;	// Total number of requests served
	
	public static final int maxFeatures = 10;
	public static final int maxThreads = 10;
	public static final int maxOptimization = 10;
	public static final int maxBugs = 10;
	
	public Software() {
		threads = new ArrayList<Thread>();
		addNewThread();
	}
	
	public void update(ServerSimulator game) {
		
		LinkedList<Thread> freeThreads = new LinkedList<Thread>();
		
		for (Thread thread : threads) {
			
			// Normal update
			thread.update(game, game.getPlayer().getServer().getY());
			
			if (thread.getRequest() != null) {
				
				// IO routing
				if (thread.getRequest().getState() == Request.WAITING_FOR_IO) {
					
					Server server = game.getPlayer().getServer();
					if (asyncIO) {
						// Send the request to the server immediately
						thread.getRequest().setState(Request.IO);
						server.getRequests().add(thread.getRequest());
						thread.setRequest(null);
					} else if (asyncIO == false && server.isPerformingIO() == false) {
						// Server is free to get the Request
						server.setPerformingIO(true);
						thread.getRequest().setState(Request.IO);
					}
					
				} else if (thread.getRequest().getState() == Request.WAITING_FOR_RESPONSE && asyncIO == false) {
					
					// In sync mode, when the request is done with the I/O, go to Response
					thread.getRequest().setState(Request.RESPONSE);
					game.getPlayer().getServer().setPerformingIO(false);
					
				}
			}
			
			// Get the free threads so we can use them later
			if (thread.getRequest() == null) {
				freeThreads.add(thread);
			}
		}
		
		// Async IO:
		// Check if any Server requests are done with the IO and direct them to a free thread for the Response
		if (asyncIO) {
			
			ArrayList<Request> serverRequestsToRemove = new ArrayList<Request>();
			
			for (Request request : game.getPlayer().getServer().getRequests()) {
				if (request.getState() == Request.WAITING_FOR_RESPONSE && freeThreads.size() > 0) {
					serverRequestsToRemove.add(request);
					request.setState(Request.RESPONSE);
					
					for (Thread t : threads) {
						if (t.getRequest() == null) {
							t.setRequest(request);
							break;
						}
					}
				}
			}
			
			// If a Request was waiting in the Server during the change to Async IO, get it going
			for (Thread thread : threads) {
				if (thread.getRequest() != null && thread.getRequest().getState() == Request.WAITING_FOR_RESPONSE) {
					thread.getRequest().setState(Request.RESPONSE);
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

	public boolean isAsyncIO() {
		return asyncIO;
	}

	public void setAsyncIO(boolean asyncIO) {
		this.asyncIO = asyncIO;
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
