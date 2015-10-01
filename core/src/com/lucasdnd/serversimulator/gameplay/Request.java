package com.lucasdnd.serversimulator.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ServerSimulator;
import com.lucasdnd.serversimulator.ui.SideBar;

public class Request {

	private float x, y;
	private final float width = ServerSimulator.BLOCK_SIZE;
	private final float height = width;
	private long ticks, totalTicks;
	private ShapeRenderer sr;
	
	private int priceToPay;	// A snapshot of the price when the request was created
	private boolean dispose; // Mark this request to be disposed
	
	// Current Request state
	private int state = 0;
	public final static int REQUEST = 0;
	public final static int WAITING_FOR_IO = 1;
	public final static int IO = 2;
	public final static int WAITING_FOR_RESPONSE = 3;
	public final static int RESPONSE = 4;
	
	public Request(long totalTicks, float y, int priceToPay) {
		sr = new ShapeRenderer();
		x = 0f;
		this.y = y;
		this.totalTicks = totalTicks;
		this.priceToPay = priceToPay;
	}
	
	public void update(ServerSimulator game, float threadY) {
		
		// Waiting: do nothing
		if (state == WAITING_FOR_IO || state == WAITING_FOR_RESPONSE) {
			return;
		}
		
		// Move it
		ticks++;
		
		// Update its position on the screen
		float playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH - width;
		x = (playableAreaWidth * ticks) / totalTicks;
		y = threadY;
		
		// State change
		if (state == REQUEST) {
			if (ticks >= game.getPlayer().getRequestTicks()) {
				state = WAITING_FOR_IO;
			}
		} else if (state == IO) {
			if (ticks >= game.getPlayer().getRequestTicks() + game.getPlayer().getIoTicks()) {
				state = WAITING_FOR_RESPONSE;
			}
		} else if (state == RESPONSE) {
			if (ticks >= game.getPlayer().getTotalTicks()) {
				dispose = true;
			}
		}
	}
	
	public void render() {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(x, y, width, height);
		sr.end();
	}
	
	public float getX() {
		return x;
	}
	
	public boolean canDispose() {
		return dispose;
	}
	
	public long getTicks() {
		return ticks;
	}
	
	public int getPriceToPay() {
		return priceToPay;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public float getY() {
		return y;
	}
}
