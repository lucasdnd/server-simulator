package com.lucasdnd.serversimulator.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.FontUtils;
import com.lucasdnd.serversimulator.ServerSimulator;
import com.lucasdnd.serversimulator.gameplay.Player;
import com.lucasdnd.serversimulator.gameplay.Software;

/**
 * The sidebar, with all the buttons and info.
 * 
 * @author lucasdnd
 *
 */
public class SideBar {
	private int x, y;
	private final float margin = 20f;
	public static final float lineWeight = 4f;
	public static final int SIDEBAR_WIDTH = 400;
	
	// Status bar attributes
	private float barHeight;
	private float barWidth;
	
	// Buttons
	private Button increasePriceButton, decreasePriceButton;
	private Button newFeaturesButton, optimizeButton, buyThreadButton, bugFixButton, asyncIOButton;
	private Button newGameButton;
	
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
		
		increasePriceButton = new Button("+", this.x + margin * 17, height - margin * 3, buttonHeight, buttonTextPaddingY);
		decreasePriceButton = new Button("-", this.x + margin * 15, height - margin * 3, buttonHeight, buttonTextPaddingY);
		
		newFeaturesButton = new Button("+", this.x + margin, height - margin * 9, buttonHeight, buttonTextPaddingY);
		buyThreadButton = new Button("+", this.x + margin, height - margin * 11, buttonHeight, buttonTextPaddingY);
		optimizeButton = new Button("+", this.x + margin, height - margin * 13, buttonHeight, buttonTextPaddingY);
		bugFixButton =  new Button("+", this.x + margin, height - margin * 15, buttonHeight, buttonTextPaddingY);
		asyncIOButton =  new Button("+", this.x + margin, height - margin * 17, buttonHeight, buttonTextPaddingY);
		
		newGameButton = new Button("New Game", this.x + margin, margin * 4);
	}
	
	public void update(ServerSimulator game) {
		increasePriceButton.update(game);
		decreasePriceButton.update(game);
		
		newFeaturesButton.update(game);
		buyThreadButton.update(game);
		optimizeButton.update(game);
		bugFixButton.update(game);
		asyncIOButton.update(game);
		
		newGameButton.update(game);
	}
	
	public void render(ServerSimulator game, ShapeRenderer sr) {
		
		float height = Gdx.graphics.getHeight();
		
		// Black background
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(x, y, SIDEBAR_WIDTH, height);
		sr.setColor(Color.WHITE);
		sr.rect(x, y, 2f, height);
		sr.end();
		
		// Player stuff
		Player player = game.getPlayer();
		
		font.drawWhiteFont(printMoney(player.getMoney()),  x + margin, height - margin, true);
		font.drawWhiteFont("Expenses: " + printMoney(player.getExpenses()), x + margin * 9, height - margin, true);
		font.drawWhiteFont("Service price: " + printMoney(player.getServicePrice()), x + margin, height - margin * 3, true);
		
		increasePriceButton.render();
		decreasePriceButton.render();
		
		// Software
		drawBackgroundBar(sr, x, height - margin * 8, barWidth, barHeight - 12f);
		font.drawBlackFont("Software",  x + margin, height - margin * 7, true);
		
		newFeaturesButton.render();
		if (player.getSoftware().getFeatures() >= Software.maxFeatures) {
			font.drawWhiteFont("Features at maximum",  x + margin * 4, height - margin * 9, true);
		} else {
			font.drawWhiteFont(printMoney(player.getFeaturesPrice()) + ": New features",  x + margin * 4, height - margin * 9, true);
		}
		
		buyThreadButton.render();
		if (player.getSoftware().getThreads().size() >= Software.maxThreads) {
			font.drawWhiteFont("Maximum threads",  x + margin * 4, height - margin * 11, true);
		} else {
			font.drawWhiteFont(printMoney(player.getThreadPrice()) + ": Buy Thread",  x + margin * 4, height - margin * 11, true);
		}
		
		optimizeButton.render();
		if (player.getSoftware().getOptimization() >= Software.maxOptimization) {
			font.drawWhiteFont("Optimization at maximum",  x + margin * 4, height - margin * 13, true);
		} else {
			font.drawWhiteFont(printMoney(player.getOptimizationPrice()) + ": Optimize",  x + margin * 4, height - margin * 13, true);
		}
	
		bugFixButton.render();
		if (player.getSoftware().getBugs() == 0) {
			font.drawWhiteFont("No bugs to fix",  x + margin * 4, height - margin * 15, true);
		} else {
			font.drawWhiteFont(printMoney(player.getBugFixPrice()) + ": Fix a bug",  x + margin * 4, height - margin * 15, true);
		}
		
		asyncIOButton.render();
		if (player.getSoftware().isAsyncIO()) {
			font.drawWhiteFont("Already async IO",  x + margin * 4, height - margin * 17, true);
		} else {
			font.drawWhiteFont(printMoney(player.getAsyncIOPrice()) + ": Async I/O",  x + margin * 4, height - margin * 17, true);
		}
		
		// Status
		drawBackgroundBar(sr, x, height - margin * 20, barWidth, barHeight - 12f);
		font.drawBlackFont("Status",  x + margin, height - margin * 19, true);
		
		font.drawWhiteFont("Threads: " + player.getSoftware().getFreeThreads() + "/" + (player.getSoftware().getThreads().size()),  x + margin, height - margin * 21, true);
		font.drawWhiteFont("Bugs: " + player.getSoftware().getBugs(),  x + margin * 13, height - margin * 21, true);
		
		font.drawWhiteFont("Request time: " + printTime(player.getRequestTime()),  x + margin, height - margin * 23, true);
		font.drawWhiteFont("I/O time: " + printTime(player.getIoTime()),  x + margin, height - margin * 25, true);
		font.drawWhiteFont("Response time: " + printTime(player.getResponseTime()),  x + margin, height - margin * 27, true);
		
		font.drawWhiteFont("Total requests: " + player.getSoftware().getTotalRequests(), x + margin, height - margin * 29, true);
		
		// New, save, load, quit
		newGameButton.render();
		
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
		return "$ " + money;
	}
	
	private String printTime(int time) {
		return (time / 10) + "." + (time % 10) + "s";
	}
	
	public int getX() {
		return x;
	}

	public Button getNewGameButton() {
		return newGameButton;
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

	public Button getIncreasePriceButton() {
		return increasePriceButton;
	}

	public Button getDecreasePriceButton() {
		return decreasePriceButton;
	}

	public Button getBuyThreadButton() {
		return buyThreadButton;
	}
	
}
