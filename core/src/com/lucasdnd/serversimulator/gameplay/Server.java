package com.lucasdnd.serversimulator.gameplay;

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
	private final float marginTop = 64f;
	private float lineWeight = ServerSimulator.BLOCK_SIZE;
	private ShapeRenderer sr;
	
	public Server(Software software, int id) {
		this.id = id;
		sr = new ShapeRenderer();
	}
	
	public void update(ServerSimulator game) {
		
		// Drawing stuff
		
		// Width: the part inside the Server is the IO time
		float requestTime = game.getPlayer().getRequestTime();
		float ioTime = game.getPlayer().getIoTime();
		float responseTime = game.getPlayer().getResponseTime();
		float totalTime = requestTime + ioTime + responseTime;
		float playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		width = playableAreaWidth * ioTime / totalTime;
		
		x = (Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) / 2f - (width / 2f);
		float offsetY = 32f;
		y = Gdx.graphics.getHeight() - (offsetY / 2f) - (height * id + (offsetY * id)) - marginTop;
	}
	
	public void render(ServerSimulator game) {
		
		drawRectFrame();
		
		// This rect represents the fact the server is "blocked" to processing one request a time
		if (game.getPlayer().getSoftware().isNonBlockingIO() == false) {
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.LIGHT_GRAY);
			sr.rect(x + lineWeight, y - height + lineWeight, width - lineWeight, height - lineWeight);
			sr.end();
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
}
