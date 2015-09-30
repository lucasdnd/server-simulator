package com.lucasdnd.serversimulator.gameplay;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ServerSimulator;

public class Thread {
	
	private int id;
	private float x, y;
	private float requestWidth, ioWidth, responseWidth;
	private final float height = ServerSimulator.BLOCK_SIZE;
	private final float serverOffsetY = ServerSimulator.BLOCK_SIZE * 2f;
	private ShapeRenderer sr;
	
	private Request request;
	
	public Thread(int id) {
		this.id = id;
		sr = new ShapeRenderer();
	}
	
	public void update(ServerSimulator game, float serverY) {
		
		if (request != null) {
			request.update();
			if (request.canDispose()) {
				
				int bugs = game.getPlayer().getSoftware().getBugs();
				if (bugs > 0 && new Random().nextInt(100) <= Math.pow(bugs, 2)) {
					
					// BUG! No money
					request = null;
					
				} else {
					
					// $$$
					game.getPlayer().earnServiceMoney();
					request = null;
				}
			}
		}
		
		float b = ServerSimulator.BLOCK_SIZE;
		y = serverY - (id * b * 2f) - serverOffsetY;
		
		requestWidth = b * 32f;
		ioWidth = b * 32f;
		responseWidth = b * 14f;
	}
	
	public void render(float serverY) {
		sr.begin(ShapeType.Filled);
		
		// Request line
		sr.setColor(Color.GOLD);
		sr.rect(x, y, requestWidth, height);
		
		// I/O line
		sr.setColor(Color.CYAN);
		sr.rect(requestWidth, y, ioWidth, height);
		
		// Response line
		sr.setColor(Color.CORAL);
		sr.rect(requestWidth + ioWidth, y, responseWidth, height);
		
		sr.end();
		
		if (request != null) {
			request.render();
		}
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
	public float getY() {
		return y;
	}
}
