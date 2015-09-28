package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;

public class Player {
	
	// Status
	private int money = 10000;
	private int servicePrice = 100;
	private int expenses;
	
	// Demand related stuff
	private float demand = 0.25f;
	
	private ArrayList<Server> servers;
	private Software software;
	
	public Player() {
		this.servers = new ArrayList<Server>();
		this.software = new Software();
	}
	
	public void update() {
		
	}
	
	public void render() {
		
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
}
