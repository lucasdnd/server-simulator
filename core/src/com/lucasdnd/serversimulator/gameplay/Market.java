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
	private float baseDemand, demand, demandPriceInfluence;
	private long requestGenerationTicks = 0l;
	private long maxRequestGenerationTicks = 30l;
	
	public Market() {
		baseDemand = 1f;
		r = new Random();
	}
	
	public void update(ServerSimulator game) {
		
		// Demand update
		int features = game.getPlayer().getSoftware().getFeatures();
		demand = baseDemand + ((float)features + 1) * 0.5f + demandPriceInfluence;

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

	public void changeDemandPriceInfluenceBy(float f) {
		demandPriceInfluence += f;
	}
	
}
