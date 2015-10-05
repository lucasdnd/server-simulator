package com.lucasdnd.serversimulator;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Controls the passing of time.
 * 
 * @author lucasdnd
 *
 */
public class TimeController {
	private long ticks;
	public final static long FPS = 60;
	
	private long time;	// 1 time == one real second
	public static final long ONE_DAY = 600;	// how many seconds in one day. 3600 = 10 minutes
	
	private String text;
	private FontUtils font;
	private float textWidth = 160f;
	private final float textPaddingX = 4f;
	
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
	}
	
	public void render(ShapeRenderer uiShapeRenderer, float x, float y) {
		font.drawWhiteFont(text, x, y, true);
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
