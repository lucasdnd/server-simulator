package com.lucasdnd.serversimulator.gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ServerSimulator;
import com.lucasdnd.serversimulator.ui.SideBar;

public class Server {
	
	private int id;
	private float x, y;
	private float width = ServerSimulator.BLOCK_SIZE * 4f;
	private final float height = 96f;
	private float lineWeight = ServerSimulator.BLOCK_SIZE;
	private ShapeRenderer sr;
	
	private final float maxThreads = 5;
	private ArrayList<Thread> threads;
	
	public Server(Software software, int id) {
		
		this.id = id;
		sr = new ShapeRenderer();
		
		threads = new ArrayList<Thread>();
		for (int i = 0; i < software.getOptimization() + 1; i++) {
			addNewThread();
		}
	}
	
	public void update(ServerSimulator game) {
		
		// Drawing stuff
		width = height;
		x = (Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) / 2f - (width / 2f);
		float offsetY = 32f;
		y = Gdx.graphics.getHeight() - (offsetY / 2f) - (height * id + (offsetY * id));
		
		// Threads
		for (Thread t : threads) {
			t.update(game, y);
		}
	}
	
	public void render() {
		
		for (Thread t : threads) {
			t.render(y);
		}
		
		drawRectFrame();
	}
	
	public void addNewThread() {
		if (threads.size() < maxThreads) {
			threads.add(new Thread(threads.size()));
		}
	}
	
	private void drawRectFrame() {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x, y, lineWidth, lineWeight);
		
		// Bottom
		sr.rect(x, y - lineHeight, lineWidth, lineWeight); 
		
		sr.end();
	}

	public ArrayList<Thread> getThreads() {
		return threads;
	}
}
