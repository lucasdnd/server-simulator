package com.lucasdnd.serversimulator.gameplay;

import com.lucasdnd.serversimulator.ServerSimulator;

/**
 * Think of it as the "business". It contains all the main information about the stuff the player currently has.
 * It also has the Software and the Server. They get updated from here.
 * 
 * @author lucasdnd
 *
 */
public class Player {
	
	// Status
	private int money = 100;
	private int servicePrice = 10;
	private int expenses;
	
	// Times are in 1/10s of seconds
	private int requestTime = 30;
	private int ioTime = 80;
	private int responseTime = 30;

	// Game objects
	private Server server;
	private Software software;
	
	// Use some better curves here
	private int[] featuresPrices = {50, 70, 100, 130, 170, 210, 250, 300, 350, 400};
	private int[] threadPrices = {50, 70, 10, 130, 170, 210, 250, 300, 350, 400};
	private int[] optimizationPrices = {20, 40, 60, 80, 100, 120, 140, 160, 180, 200};
	private int[] bugFixPrices = {30, 40, 60, 90, 130, 180, 240, 310, 390, 480};
	private int asyncIOPrice = 200;
	
	private final int minServicePrice = 5;
	private final int maxServicePrice = 50;
	
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
	
	public void increaseServicePrice(Market market) {
		float originalServicePrice = servicePrice;
		servicePrice += 5;
		if (servicePrice >= maxServicePrice) {
			servicePrice = maxServicePrice;
		}
		
		if (originalServicePrice != servicePrice) {
			market.changeDemandPriceInfluenceBy(-0.25f);
		}
	}
	
	public void decreaseServicePrice(Market market) {
		float originalServicePrice = servicePrice;
		servicePrice -= 5;
		if (servicePrice <= minServicePrice) {
			servicePrice = minServicePrice;
		}
		if (originalServicePrice != servicePrice) {
			market.changeDemandPriceInfluenceBy(0.25f);
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
		if (software.isAsyncIO()) {
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
