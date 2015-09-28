package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;

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
	private ArrayList<Server> servers;
	private Software software;
	
	// Settings and stuff
	private final int maxServers = 5;
	
	public Player() {
		this.servers = new ArrayList<Server>();
		this.software = new Software();
		addNewServer();
	}
	
	public void update() {
		software.update();
		for (Server s : servers) {
			s.update();
		}
	}
	
	public void render() {
		for (Server s : servers) {
			s.render();
		}
	}
	
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
	
	public void optimizeSoftware() {
		software.optimize();
		for (Server s : servers) {
			s.addNewThread();
		}
	}
	
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
