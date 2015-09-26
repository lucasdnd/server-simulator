package com.lucasdnd.serversimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ui.SideBar;

public class TimeController {
	private long ticks;
	public final static long FPS = 60;
	
	private long time;	// 1 time == one real second
	public static final long ONE_DAY = 600;	// how many seconds in one day. 3600 = 10 minutes
	
	private String text;
	private FontUtils font;
	private float x = 0f;
	private float textWidth = 160f;
	private final float boxHeight = 26f;
	private final float textPaddingX = 4f;
	private final float textPaddingY = 4f;
	
	public TimeController() {
		font = new FontUtils();
		text = "";
	}
	
	protected void update() {
		ticks++;
		if (ticks % FPS == 0) {
			time++;
		}
		text = (int)(((float)getHourOfDay() / (float)ONE_DAY) * 100f) + "% of day " + getDay();
		textWidth = font.getTextWidth(text) + textPaddingX * 2f;
		x = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH - textWidth;
	}
	
	public void render(ShapeRenderer uiShapeRenderer) {
		uiShapeRenderer.begin(ShapeType.Filled);
		uiShapeRenderer.setColor(Color.BLACK);
		uiShapeRenderer.rect(x, Gdx.graphics.getHeight() - boxHeight, textWidth, boxHeight);
		uiShapeRenderer.end();
		font.drawWhiteFont(text, x + textPaddingX, Gdx.graphics.getHeight() - textPaddingY, true);
	}
	
	public boolean isNight() {
		return (int)(time % ONE_DAY) > ONE_DAY / 2;
	}
	
	public int getHourOfDay() {
		return (int)(time % ONE_DAY);
	}
	
	public int getDay() {
		return (int)(time / ONE_DAY);
	}

	public long getTicks() {
		return ticks;
	}

	public long getTime() {
		return time;
	}
	
	public void setTicks(long ticks) {
		this.ticks = ticks;
		time = ticks / FPS;
	}
}
