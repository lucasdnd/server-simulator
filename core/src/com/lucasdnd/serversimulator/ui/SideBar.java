package com.lucasdnd.serversimulator.ui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.FontUtils;
import com.lucasdnd.serversimulator.ServerSimulator;
import com.lucasdnd.serversimulator.gameplay.Player;

public class SideBar {
	private int x, y;
	private final float margin = 20f;
	public static final float lineWeight = 4f;
	public static final int SIDEBAR_WIDTH = 400;
	
	// Status bar attributes
	private float barHeight;
	private float barWidth;
	
	// Buttons
	private Button newFeaturesButton, optimizeButton, bugFixButton, asyncIOButton, buyServerButton;
	private Button newGameButton, saveGameButton, loadGameButton, quitButton;
	
	private FontUtils font;
	
	public SideBar(int x, int y) {
		this.x = x;
		this.y = y;
		barHeight = margin * 1.8f;
		barWidth = SIDEBAR_WIDTH;
		font = new FontUtils();
		
		// Buttons
		final float height = Gdx.graphics.getHeight();
		final float buttonHeight = 24f;
		final float buttonTextPaddingY = 2f;
		newFeaturesButton = new Button("+", this.x + margin, height - margin * 7, buttonHeight, buttonTextPaddingY);
		optimizeButton = new Button("+", this.x + margin, height - margin * 9, buttonHeight, buttonTextPaddingY);
		bugFixButton =  new Button("+", this.x + margin, height - margin * 11, buttonHeight, buttonTextPaddingY);
		asyncIOButton =  new Button("+", this.x + margin, height - margin * 13, buttonHeight, buttonTextPaddingY);
		buyServerButton = new Button("+", this.x + margin, height - margin * 17, buttonHeight, buttonTextPaddingY);
		
		newGameButton = new Button("New", this.x + margin, margin * 4);
		saveGameButton = new Button("Save", this.x + margin * 5 + 6f, margin * 4);
		loadGameButton = new Button("Load", this.x + margin * 10 + 4f, margin * 4);
		quitButton = new Button("Quit", this.x + margin * 15, margin * 4);
	}
	
	public void update() {
		newFeaturesButton.update();
		optimizeButton.update();
		bugFixButton.update();
		asyncIOButton.update();
		buyServerButton.update();
		
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
		Player player = ((ServerSimulator)Gdx.app.getApplicationListener()).getPlayer();
		
		font.drawWhiteFont(printMoney(player.getMoney()),  x + margin, height - margin, true);
		font.drawWhiteFont("Expenses: " + printMoney(player.getExpenses()), x + margin * 9, height - margin, true);
		font.drawWhiteFont("Service price: " + printMoney(player.getServicePrice()), x + margin, height - margin * 3, true);
		
		// Software
		drawBackgroundBar(sr, x, height - margin * 6, barWidth, barHeight - 12f);
		font.drawBlackFont("Software",  x + margin, height - margin * 5, true);
		newFeaturesButton.render();
		font.drawWhiteFont("$ 30: New features",  x + margin * 4, height - margin * 7, true);
		optimizeButton.render();
		font.drawWhiteFont("$ 20: Optimize",  x + margin * 4, height - margin * 9, true);
		bugFixButton.render();
		font.drawWhiteFont("$ 10: Fix a bug",  x + margin * 4, height - margin * 11, true);
		asyncIOButton.render();
		font.drawWhiteFont("$ 500: Async I/O",  x + margin * 4, height - margin * 13, true);
		
		// Hardware
		drawBackgroundBar(sr, x, height - margin * 16, barWidth, barHeight - 12f);
		font.drawBlackFont("Hardware",  x + margin, height - margin * 15, true);
		buyServerButton.render();
		font.drawWhiteFont("$ 60: Buy Server",  x + margin * 4, height - margin * 17, true);
		
		// Status
		drawBackgroundBar(sr, x, height - margin * 20, barWidth, barHeight - 12f);
		font.drawBlackFont("Status",  x + margin, height - margin * 19, true);
		font.drawWhiteFont("Request time: " + printTime(player.getRequestTime()),  x + margin, height - margin * 21, true);
		font.drawWhiteFont("I/O time: " + printTime(player.getIoTime()),  x + margin, height - margin * 23, true);
		font.drawWhiteFont("Response time: " + printTime(player.getResponseTime()),  x + margin, height - margin * 25, true);
		
		font.drawWhiteFont("Servers: 2",  x + margin * 13, height - margin * 21, true);
		font.drawWhiteFont("Threads: 3",  x + margin * 13, height - margin * 23, true);
		font.drawWhiteFont("Bugs: 0",  x + margin * 13, height - margin * 25, true);
		
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
	
	private String printMoney(int money) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
		decimalFormatSymbols.setCurrencySymbol("");
		((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
		return "$ " + nf.format((float)money / 100).trim();
	}
	
	private String printTime(int time) {
		return (time / 10) + "." + (time % 10) + "s";
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

	public Button getNewFeaturesButton() {
		return newFeaturesButton;
	}

	public Button getOptimizeButton() {
		return optimizeButton;
	}

	public Button getBugFixButton() {
		return bugFixButton;
	}

	public Button getAsyncIOButton() {
		return asyncIOButton;
	}

	public Button getBuyServerButton() {
		return buyServerButton;
	}
}
