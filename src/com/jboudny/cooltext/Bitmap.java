package com.jboudny.cooltext;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap {

	private int[] data;
	
	public final int width, height;
	
	public Bitmap(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.data = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new int[width*height];
	}
	
	public void fillRect(int x0, int y0, int x1, int y1, int color) {
		for (int y = y0; y <= y1; y++) {
			for (int x = x0; x <= x1; x++) {
				drawPixel(x, y, color);
			}
		}
	}
	
	public void drawBitmap(int x, int y, Bitmap bitmap) {
		for (int bx = 0; bx < bitmap.width; bx++) {
			for (int by = 0; by < bitmap.height; by++) {
				
				int px = bitmap.getPixel(bx, by);
				
				if ((px & 0xFF) != 0) { 
					this.drawPixel(bx + x, by + y, bitmap.getPixel(bx, by));
				}	
			}
		}
	}
	
	public void drawBitmapRotated(int posx, int posy, Bitmap bitmap, double angle) {
		double sin = Math.sin(angle / (180 / Math.PI));
		double cos = Math.cos(angle / (180 / Math.PI));
		
		int halfw = bitmap.width/2;
		int halfh = bitmap.height/2;
		
		int px0 = (int) Math.round(halfw*cos + -halfh*sin);
		int py0 = (int) Math.round(-halfw*sin + -halfh*cos);
		
		int px1 = (int) Math.round(-halfw*cos + -halfh*sin);
		int py1 = (int) Math.round(halfw*sin + -halfh*cos);
		
		int px2 = (int) Math.round(-halfw*cos + halfh*sin);
		int py2 = (int) Math.round(halfw*sin + halfh*cos);
		
		int px3 = (int) Math.round(halfw*cos + halfh*sin);
		int py3 = (int) Math.round(-halfw*sin + halfh*cos);
		
		int minX;
		int minY;
		int maxX;
		int maxY;
		
		minX = px0;
		if (px1 < minX) minX = px1;	
		if (px2 < minX) minX = px2;
		if (px3 < minX) minX = px3;
		
		minX += halfw;
		
		if (minX < 0) minX = 0;
		
		maxX = px0;
		if (px1 > maxX) maxX = px1;	
		if (px2 > maxX) maxX = px2;
		if (px3 > maxX) maxX = px3;
		
		minY = py0;
		if (py1 < minY) minY = py1;	
		if (py2 < minY) minY = py2;
		if (py3 < minY) minY = py3;
		
		minY += halfh;
		
		if (minY < 0) minY = 0;
		
		maxY = py0;
		if (py1 > maxY) maxY = py1;	
		if (py2 > maxY) maxY = py2;
		if (py3 > maxY) maxY = py3;
		
		
		maxX += halfw;	
		maxY += halfh;
		
		if (maxX > this.width - 1) maxX = this.width - 1;
		if (maxY > this.height - 1) maxY = this.height - 1;
		
		if (minX > 0) {
		System.out.println(minX);
		}
		
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				int cx = x - halfw;
				int cy = y - halfh;
				
				int bx = (int) Math.round(halfw + cx * cos + cy * sin);
				int by = (int) Math.round(halfh + -cx * sin + cy * cos);
				
				int color = bitmap.getPixel(bx, by);
				
				if ((color & 0xFF) != 0) {
					this.drawPixel(posx + x, posy + y, color);
				}
				
			}
		}
	}
	
	public void drawBitmapScaledRotated(int posx, int posy, Bitmap bitmap, double angle, double scale, int colorn) {
		double sin = Math.sin(angle / (180 / Math.PI));
		double cos = Math.cos(angle / (180 / Math.PI));
		
		double invertedScale = 1/scale;
		
		int halfw = (int) (bitmap.width/2);
		int halfh = (int) (bitmap.height/2);
		
		int halfwd = (int) (halfw*scale);
		int halfhd = (int) (halfh*scale);
		
		int px0 = (int) Math.round(halfwd*cos + -halfhd*sin);
		int py0 = (int) Math.round(-halfwd*sin + -halfhd*cos);
		
		int px1 = (int) Math.round(-halfwd*cos + -halfhd*sin);
		int py1 = (int) Math.round(halfwd*sin + -halfhd*cos);
		
		int px2 = (int) Math.round(-halfwd*cos + halfhd*sin);
		int py2 = (int) Math.round(halfwd*sin + halfhd*cos);
		
		int px3 = (int) Math.round(halfwd*cos + halfhd*sin);
		int py3 = (int) Math.round(-halfwd*sin + halfhd*cos);
		
		int minX;
		int minY;
		int maxX;
		int maxY;
		
		minX = px0;
		if (px1 < minX) minX = px1;	
		if (px2 < minX) minX = px2;
		if (px3 < minX) minX = px3;
		
		minX += halfw;
		
		if (minX < 0) minX = 0;
		
		maxX = px0;
		if (px1 > maxX) maxX = px1;	
		if (px2 > maxX) maxX = px2;
		if (px3 > maxX) maxX = px3;
		
		minY = py0;
		if (py1 < minY) minY = py1;	
		if (py2 < minY) minY = py2;
		if (py3 < minY) minY = py3;
		
		minY += halfh;
		
		if (minY < 0) minY = 0;
		
		maxY = py0;
		if (py1 > maxY) maxY = py1;	
		if (py2 > maxY) maxY = py2;
		if (py3 > maxY) maxY = py3;
		
		
		maxX += halfw;	
		maxY += halfh;
		
		if (maxX > this.width - 1) maxX = this.width - 1;
		if (maxY > this.height - 1) maxY = this.height - 1;
		
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				int cx = x - halfw;
				int cy = y - halfh;
				
				int bx = (int) Math.round(halfw + cx*invertedScale * cos + cy*invertedScale * sin);
				int by = (int) Math.round(halfh + -cx*invertedScale * sin + cy*invertedScale * cos);
				
				int color = bitmap.getPixel(bx, by);
				
				if ((color & 0xFF) != 0) {
					this.drawPixel(posx + x, posy + y, colorn);
				}
				
			}
		}
	}
	
	public void drawPixel(int x, int y, int color) {
		if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
			return;
		}
		
		this.data[y * this.width + x] = color;
	}
	
	public int getPixel(int x, int y) {
		if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
			return 0;
		}
		
		return this.data[y * this.width + x];
	}
	
	public void clear(int color) {
		for (int i = 0; i < data.length; i++) {
			data[i] = color;
		}	
	}
	
}
