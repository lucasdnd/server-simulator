package com.lucasdnd.serversimulator.gameplay;

import java.util.Random;

import com.lucasdnd.serversimulator.ServerSimulator;

/**
 * Takes care of when Requests are created, based on the demand
 * @author lucasdnd
 *
 */
public class Market {
	
	private Random r;
	private float demand;
	private long requestGenerationTicks = 0l;
	private long maxRequestGenerationTicks = 60l;
	
	public Market() {
		r = new Random();
	}
	
	public void update(ServerSimulator game) {
		
		// Demand update
		int features = game.getPlayer().getSoftware().getFeatures();
		demand = ((float)features + 1) * 0.1f;
		
		maxRequestGenerationTicks = (long)(60f / demand * 10f / 100f);
		
		// Request generation
		requestGenerationTicks++;
		if (requestGenerationTicks % maxRequestGenerationTicks == 0) {
			if (r.nextFloat() <= demand) {
				game.getPlayer().createNewRequest();
			}
		}
	}
	
	public float getDemand() {
		return demand;
	}
	
	public long getMaxRequestGenerationTicks() {
		return maxRequestGenerationTicks;
	}
	
}
