package com.lucasdnd.serversimulator.gameplay;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.lucasdnd.serversimulator.ServerSimulator;


public class Market {
	
	private Random r;
	private float demand;
	private long requestGenerationTicks = 0l;
	private long maxRequestGenerationTicks = 60l;
	
	public Market() {
		r = new Random();
	}
	
	public void update(int features) {
		
		// Demand update
		demand = ((float)features + 1) * 0.1f;
		
		// Request generation
		requestGenerationTicks++;
		if (requestGenerationTicks % maxRequestGenerationTicks == 0) {
			if (r.nextFloat() <= demand) {
				((ServerSimulator)Gdx.app.getApplicationListener()).getPlayer().createNewRequest();
			}
		}
	}
	
	public float getDemand() {
		return demand;
	}
	
}
