package com.lucasdnd.serversimulator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FontUtils {
	
	private SpriteBatch fontBatch;
	
	public FontUtils() {
		this.fontBatch = new SpriteBatch();
	}
	
	public int getTextWidth(String text) {
		float charWidth = Resources.get().blackFont.getSpaceWidth();
		float textWidth = charWidth * text.length();
		return (int)textWidth;
	}
	
	public void drawWhiteFont(String text, float x, float y, boolean withShadow) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().grayFont.draw(fontBatch, text, x + 1f, y - 1f);
		}
		Resources.get().whiteFont.draw(fontBatch, text, x, y);
		fontBatch.end();
	}
	
	public void drawWhiteFont(String text, float x, float y, boolean withShadow, int align, int space) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().grayFont.draw(fontBatch, text, x + 1f, y - 1f, space, align, false);
		}
		Resources.get().whiteFont.draw(fontBatch, text, x, y, space, align, false);
		fontBatch.end();
	}
	
	public void drawRedFont(String text, float x, float y, boolean withShadow, int align, int space) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().grayFont.draw(fontBatch, text, x + 1f, y - 1f, space, align, false);
		}
		Resources.get().redFont.draw(fontBatch, text, x, y, space, align, false);
		fontBatch.end();
	}
	
	public void drawGrayFont(String text, float x, float y, boolean withShadow, int align, int space) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().lightGrayFont.draw(fontBatch, text, x + 1f, y - 1f, space, align, false);
		}
		Resources.get().grayFont.draw(fontBatch, text, x, y, space, align, false);
		fontBatch.end();
	}

	public void drawGreenFont(String text, float x, float y, boolean withShadow, int align, int space) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().lightGrayFont.draw(fontBatch, text, x + 1f, y - 1f, space, align, false);
		}
		Resources.get().greenFont.draw(fontBatch, text, x, y, space, align, false);
		fontBatch.end();
	}
}
