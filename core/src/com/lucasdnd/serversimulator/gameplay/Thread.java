package com.lucasdnd.serversimulator.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ServerSimulator;

public class Thread {
	
	private int id;
	private float x, y;
	private float requestWidth, ioWidth, responseWidth;
	private float height;
	ShapeRenderer sr;
	
	private Request request;
	
	public Thread(int id) {
		this.id = id;
		sr = new ShapeRenderer();
		height = ServerSimulator.BLOCK_SIZE;
	}
	
	public void update() {
		if (request != null) {
			request.update();
		}
	}
	
	public void render() {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.LIGHT_GRAY);

		// Request line
		sr.rect(x, y, requestWidth, height);
		sr.rect(x, y, ioWidth, height);
		sr.rect(x, y, responseWidth, height);
		sr.end();
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
