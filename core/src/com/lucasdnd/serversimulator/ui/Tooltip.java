package com.lucasdnd.serversimulator.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.serversimulator.FontUtils;
import com.lucasdnd.serversimulator.Resources;

public class Tooltip {
	private float x, y;
	private final float height = 32f;
	private final float textMarginX = 2f;
	private final float textMarginY = 5f;
	private final float tooltipPaddingX = 32f;
	private final float lineWeight = 4f;
	
	private FontUtils font;
	private ShapeRenderer sr;
	
	private String text;
	private float charSize, textSize;
	private boolean shouldDraw;
	
	public Tooltip() {
		font = new FontUtils();
		sr = new ShapeRenderer();
	}
	
	public void render() {
		if (shouldDraw) {
			drawTooltipFrame(x, y, textSize, height);
			drawTooltipBackground(x, y, textSize, height);
			font.drawWhiteFont(text, x + textMarginX, y - textMarginY, false, Align.center, (int)textSize);
		}
	}
	
	public void setTooltip(String text, float x, float y) {
		this.x = x;
		this.y = y;
		charSize = Resources.get().blackFont.getSpaceWidth();
		textSize = charSize * text.length();
		textSize += tooltipPaddingX;
		this.text = text;
		shouldDraw = true;
	}
	
	private void drawTooltipFrame(float x, float y, float width, float height) {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x + lineWeight, y, lineWidth - lineWeight * 2, lineWeight);
		
		// Bottom
		sr.rect(x + lineWeight, y - lineHeight, lineWidth - lineWeight * 2, lineWeight); 
		
		sr.end();
	}
	
	private void drawTooltipBackground(float x, float y, float width, float height) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.LIGHT_GRAY);
		sr.rect(x + lineWeight, y - height + lineWeight, width - lineWeight, height - lineWeight);
		sr.end();
	}
	
	public void hide() {
		shouldDraw = false;
	}
}
