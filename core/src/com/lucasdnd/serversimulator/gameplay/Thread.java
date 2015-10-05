package com.lucasdnd.serversimulator.gameplay;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ServerSimulator;
import com.lucasdnd.serversimulator.ui.SideBar;

/**
 * The "worker". It will receive a Request and update it. 
 * 
 * @author lucasdnd
 *
 */
public class Thread {
	
	private int id;
	private float x, y;
	private float requestWidth, ioWidth, responseWidth;
	private final float height = ServerSimulator.BLOCK_SIZE;
	private final float serverOffsetY = ServerSimulator.BLOCK_SIZE * 2f;
	private ShapeRenderer sr;
	
	// Game objects
	private Request request;
	
	public Thread(int id) {
		this.id = id;
		sr = new ShapeRenderer();
	}
	
	public void update(ServerSimulator game, float serverY) {
		
		if (request != null) {
			
			// Request is going...
			request.update(game, y);
			
			// Request reached the end!
			if (request.canDispose()) {
				
				int bugs = game.getPlayer().getSoftware().getBugs();
				if (bugs > 0 && new Random().nextInt(100) <= Math.pow(bugs, 2)) {
					
					// BUG! No money
					request = null;
					
				} else {
					
					// $$$
					game.getPlayer().earnMoney(request.getPriceToPay());
					game.getPlayer().getSoftware().incrementTotalRequests();
					request = null;
				}
			}
		}
		
		// Update its position on the screen
		y = serverY - (id * ServerSimulator.BLOCK_SIZE * 2f) - serverOffsetY;
		
		float requestTime = game.getPlayer().getRequestTime();
		float ioTime = game.getPlayer().getIoTime();
		float responseTime = game.getPlayer().getResponseTime();
		float totalTime = requestTime + ioTime + responseTime;
		
		float playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		
		requestWidth = playableAreaWidth * requestTime / totalTime;
		ioWidth = playableAreaWidth * ioTime / totalTime;
		responseWidth = playableAreaWidth * responseTime / totalTime;
	}
	
	public void render() {
		
		if (request != null) {
			
			sr.begin(ShapeType.Filled);
			
			if (request.getState() == Request.REQUEST) {
				sr.setColor(Color.CORAL);
				sr.rect(x, y, requestWidth, height);
			} else if (request.getState() == Request.IO) {
				sr.setColor(Color.GREEN);
				sr.rect(requestWidth + ServerSimulator.BLOCK_SIZE, y, ioWidth - ServerSimulator.BLOCK_SIZE, height);				
			} else if (request.getState() == Request.RESPONSE) {
				sr.setColor(Color.CORAL);
				sr.rect(requestWidth + ioWidth + ServerSimulator.BLOCK_SIZE, y, responseWidth - ServerSimulator.BLOCK_SIZE, height);
				
			}
			
			sr.end();
		
			// Render the Request dot
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

	public void setY(float y) {
		this.y = y;
	}
}
