package com.lucasdnd.serversimulator.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.FontUtils;
import com.lucasdnd.serversimulator.Resources;
import com.lucasdnd.serversimulator.ServerSimulator;

public class SideBar {
	private int x, y;
	private final float margin = 20f;
	public static final float lineWeight = 4f;
	public static final int SIDEBAR_WIDTH = 400;
	
	// Status bar attributes
	private float barHeight;
	private float barWidth;
	
	// Main Buttons
	private Button newGameButton, saveGameButton, loadGameButton, quitButton;
	
	private FontUtils font;
	
	public SideBar(int x, int y) {
		this.x = x;
		this.y = y;
		barHeight = margin * 1.8f;
		barWidth = SIDEBAR_WIDTH;
		font = new FontUtils();
		
		// Buttons
		newGameButton = new Button("New", this.x + margin, margin * 4);
		saveGameButton = new Button("Save", this.x + margin * 5 + 6f, margin * 4);
		loadGameButton = new Button("Load", this.x + margin * 10 + 4f, margin * 4);
		quitButton = new Button("Quit", this.x + margin * 15, margin * 4);
	}
	
	public void update() {
		newGameButton.update();
		saveGameButton.update();
		loadGameButton.update();
		quitButton.update();
	}
	
	public void render(ShapeRenderer sr) {
		
		float height = Gdx.graphics.getHeight();
		
		// Black background
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(x, y, SIDEBAR_WIDTH, height);
		sr.end();
		
		// Player stuff
		font.drawWhiteFont("$",  x + margin, height - margin, true);
		font.drawWhiteFont("Service price: ", x + margin, height - margin * 3, true);
		font.drawWhiteFont("Expenses: ",  x + margin, height - margin * 5, true);
		
		// Software
		drawBackgroundBar(sr, x, height - margin * 8, barWidth, barHeight - 12f);
		font.drawBlackFont("Software",  x + margin, height - margin * 7, true);
		
		// Hardware
		drawBackgroundBar(sr, x, height - margin * 16, barWidth, barHeight - 12f);
		font.drawBlackFont("Hardware",  x + margin, height - margin * 15, true);
		
		// Status
		drawBackgroundBar(sr, x, height - margin * 21, barWidth, barHeight - 12f);
		font.drawBlackFont("Status",  x + margin, height - margin * 20, true);
		
		font.drawWhiteFont("Request time:",  x + margin, height - margin * 22, true);
		font.drawWhiteFont("I/O time:",  x + margin, height - margin * 24, true);
		font.drawWhiteFont("Response time:",  x + margin, height - margin * 26, true);
		
		font.drawWhiteFont("Servers:",  x + margin * 12, height - margin * 22, true);
		font.drawWhiteFont("Threads:",  x + margin * 12, height - margin * 24, true);
		font.drawWhiteFont("Bugs:",  x + margin * 12, height - margin * 26, true);
		
		// New, save, load, quit
		newGameButton.render();
		saveGameButton.render();
		loadGameButton.render();
		quitButton.render();
		
		// Game name and version
		font.drawWhiteFont(ServerSimulator.GAME_NAME, x + margin,                  margin * 1.5f, true);
		font.drawWhiteFont(ServerSimulator.VERSION,   x + SIDEBAR_WIDTH - margin * 4 - 6f, margin * 1.5f, true);
	}
	
	private void drawBackgroundBar(ShapeRenderer sr, float x, float y, float width, float height) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		sr.rect(x, y, width, height);
		sr.end();
	}
	
	private void drawRectFrame(ShapeRenderer sr, float x, float y, float width, float height) {
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
	
	private void drawRectFill(ShapeRenderer sr, Color c, float x, float y, float width, float height, int value, int maxValue) {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		float lineValue = lineWidth * ((float)value / (float)maxValue) - lineWeight * 2f;
		if (lineValue <= 0) {
			return;
		} else if (lineValue >= lineWidth - lineWeight * 2f) {
			lineValue = lineWidth - lineWeight * 2f;
		}
		sr.begin(ShapeType.Filled);
		if (c == null) {
			sr.setColor(Color.LIGHT_GRAY);
		} else {
			sr.setColor(c);
		}
		
		sr.rect(x + lineWeight, y - lineHeight, lineValue, lineHeight);
		sr.end();
	}
	
	public int getX() {
		return x;
	}

	public Button getSaveGameButton() {
		return saveGameButton;
	}

	public Button getNewGameButton() {
		return newGameButton;
	}

	public Button getLoadGameButton() {
		return loadGameButton;
	}

	public Button getQuitButton() {
		return quitButton;
	}
}
