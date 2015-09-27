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
	
	public void Player() {
		servers = new ArrayList<Server>();
		software = new Software();
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
}
