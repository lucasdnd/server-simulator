package com.lucasdnd.serversimulator.gameplay;

import com.lucasdnd.serversimulator.ServerSimulator;

public class Player {
	
	// Status
	private int money = 10000;
	private int servicePrice = 100;
	private int expenses;
	
	// Times are in 1/10s of seconds
	private int requestTime = 30;
	private int ioTime = 80;
	private int responseTime = 30;

	// Game objects
	private Server server;
	private Software software;
	
	// Use some better curves here
	private int[] featuresPrices = {5000, 7000, 10000, 13000, 17000, 21000, 25000, 30000, 35000, 40000};
	private int[] threadPrices = {5000, 7000, 10000, 13000, 17000, 21000, 25000, 30000, 35000, 40000};
	private int[] optimizationPrices = {2000, 4000, 6000, 8000, 10000, 12000, 14000, 16000, 18000, 20000};
	private int[] bugFixPrices = {3000, 4000, 6000, 9000, 13000, 18000, 24000, 31000, 39000, 48000};
	private int asyncIOPrice = 50000;
	
	private final int minServicePrice = 50;
	private final int maxServicePrice = 1000;
	
	public Player() {
		this.software = new Software();
		this.server = new Server(software);
	}
	
	public void update(ServerSimulator game) {
		software.update(game);
		server.update(game);
	}
	
	public void render(ServerSimulator game) {
		server.render(game);
		software.render(game);
	}
	
	public void increaseServicePrice() {
		servicePrice += 50;
		if (servicePrice >= maxServicePrice) {
			servicePrice = maxServicePrice;
		}
	}
	
	public void decreaseServicePrice() {
		servicePrice -= 50;
		if (servicePrice <= minServicePrice) {
			servicePrice = minServicePrice;
		}
	}
	
	/**
	 * Earn money
	 * @param value
	 */
	public void earnMoney(int value) {
		money += value;
	}
	
	public void earnServiceMoney() {
		money += servicePrice;
	}
	
	/**
	 * If the player has enough money, subtract it and return true
	 * @param value
	 * @return
	 */
	public boolean spendMoney(int value) {
		if (money >= value) {
			money -= value;
			return true;
		}
		return false;
	}
	
	/**
	 * The price of stuff 
	 */
	public int getFeaturesPrice() {
		if (software.getFeatures() >= featuresPrices.length) {
			return 0;
		}
		return featuresPrices[software.getFeatures()];
	}
	public int getOptimizationPrice() {
		if (software.getOptimization() >= optimizationPrices.length) {
			return 0;
		}
		return optimizationPrices[software.getOptimization()];
	}
	public int getBugFixPrice() {
		if (software.getBugs() >= bugFixPrices.length) {
			return 0;
		}
		return bugFixPrices[software.getBugs()];
	}
	public int getThreadPrice() {
		if (software.getThreads().size() >= Software.maxThreads) {
			return 0;
		} else if (software.getThreads().size() >= threadPrices.length) {
			return threadPrices[threadPrices.length - 1];
		}
		return threadPrices[software.getThreads().size() - 1];
	}
	public int getAsyncIOPrice() {
		if (software.isNonBlockingIO()) {
			return 0;
		}
		return asyncIOPrice;
	}
	
	/**
	 * Creates a new Request. It goes to the next available thread
	 */
	public void createNewRequest() {
	
		// Total time the request needs to complete
		long totalTicks = (long)(requestTime / 10f + ioTime / 10f + responseTime / 10f);
		totalTicks *= 60;
		
		// Get a free thread for the new Request
		for (Thread t : software.getThreads()) {
			if (t.getRequest() == null) {
				t.setY(100f);
				t.setRequest(new Request(totalTicks, t.getY(), servicePrice));
				return;
			}
		}
		
		// If none are found, increment the lost requests
		software.incrementLostRequests();
	}
	
	/**
	 * Improves the request times
	 */
	public void optimizeSoftware() {
		software.optimize();
		requestTime -= 2;
		ioTime -= 6;
		responseTime -= 2;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(int servicePrice) {
		this.servicePrice = servicePrice;
	}

	public int getExpenses() {
		return expenses;
	}

	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Software getSoftware() {
		return software;
	}

	public int getRequestTime() {
		return requestTime;
	}
	
	public long getRequestTicks() {
		return requestTime * 6l;
	}

	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}

	public int getIoTime() {
		return ioTime;
	}
	
	public long getIoTicks() {
		return ioTime * 6l;
	}

	public void setIoTime(int ioTime) {
		this.ioTime = ioTime;
	}

	public int getResponseTime() {
		return responseTime;
	}
	
	public long getResponseTicks() {
		return responseTime * 6l;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public long getTotalTicks() {
		return getRequestTicks() + getIoTicks() + getResponseTicks();
	}
}
