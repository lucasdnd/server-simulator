package com.lucasdnd.serversimulator.gameplay;

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
	
	public void update(float serverY) {
		if (request != null) {
			request.update();
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
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
