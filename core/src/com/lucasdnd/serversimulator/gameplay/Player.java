package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;

import com.lucasdnd.serversimulator.ServerSimulator;

public class Player {
	
	// Status
	private int money = 100000000;
	private int servicePrice = 100;
	private int expenses;
	
	// Times are in 1/10s of seconds
	private int requestTime = 30;
	private int ioTime = 80;
	private int responseTime = 30;

	// Game objects
	private ArrayList<Server> servers;
	private Software software;
	
	// Settings and stuff
	public static final int maxServers = 5;
	
	// Use some better curves here
	private int[] featuresPrices = {6000, 8000, 10500, 13500, 17000, 21000, 25500, 30500, 36000, 42000};
	private int[] optimizationPrices = {2000, 4000, 6000, 8000, 10000, 12000, 14000, 16000, 18000, 20000};
	private int[] bugFixPrices = {3000, 4000, 6000, 9000, 13000, 18000, 24000, 31000, 39000, 48000};
	private int[] buyServerPrices = {12000, 15000, 18000, 23000};
	private int asyncIOPrice = 50000;
	
	public Player() {
		this.servers = new ArrayList<Server>();
		this.software = new Software();
		addNewServer();
	}
	
	public void update(ServerSimulator game) {
		software.update();
		for (Server s : servers) {
			s.update(game);
		}
	}
	
	public void render(ServerSimulator game) {
		for (Server s : servers) {
			s.render(game);
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
	public int getAsyncIOPrice() {
		if (software.isNonBlockingIO()) {
			return 0;
		}
		return asyncIOPrice;
	}
	public int getBuyServerPrice() {
		if (servers.size() > buyServerPrices.length) {
			return 0;
		}
		return buyServerPrices[servers.size() - 1];
	}
	
	/**
	 * Creates a new Request. It goes to the next available thread
	 */
	public void createNewRequest() {
		long totalTicks = (long)(requestTime / 10f + ioTime / 10f + responseTime / 10f);
		totalTicks *= 60;
		for (Server s : servers) {
			for (Thread t : s.getThreads()) {
				if (t.getRequest() == null) {
					t.setRequest(new Request(totalTicks, t.getY()));
					return;
				}
			}
		}
	}
	
	/**
	 * Increases the number of threads the software can use. Also reduces the request times
	 */
	public void optimizeSoftware() {
		software.optimize();
		for (Server s : servers) {
			s.addNewThread();
		}
	}
	
	/**
	 * Add a new server
	 */
	public void addNewServer() {
		if (servers.size() < maxServers) {
			servers.add(new Server(software, servers.size()));
		}
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

	public ArrayList<Server> getServers() {
		return servers;
	}

	public void setServers(ArrayList<Server> servers) {
		this.servers = servers;
	}

	public Software getSoftware() {
		return software;
	}

	public int getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}

	public int getIoTime() {
		return ioTime;
	}

	public void setIoTime(int ioTime) {
		this.ioTime = ioTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}
}
