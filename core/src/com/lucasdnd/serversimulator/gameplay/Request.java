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
	
	private boolean dispose;
	
	public Request(long totalTicks, float y, int priceToPay) {
		sr = new ShapeRenderer();
		x = 0f;
		this.y = y;
		this.totalTicks = totalTicks;
		this.priceToPay = priceToPay;
	}
	
	public void update(ServerSimulator game) {
		
		// Move it
		ticks++;
		
		// Update its position on the screen
		float playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH - width;
		x = (playableAreaWidth * ticks) / totalTicks;
		if (ticks >= totalTicks) {
			dispose = true;
		}
	}
	
	public void render() {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
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
}
