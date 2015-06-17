package com.jboudny.cooltext;

public class Color {

	public static int mix(int c0, int c1, double w) {
		int r0 = (c0 >> 24) & 0xFF; 
		int r1 = (c1 >> 24) & 0xFF; 
		
		int g0 = (c0 >> 16) & 0xFF; 
		int g1 = (c1 >> 16) & 0xFF; 
		
		int b0 = (c0 >> 8) & 0xFF; 
		int b1 = (c1 >> 8) & 0xFF; 	
		
		int a0 = c0 & 0xFF; 
		int a1 = c1 & 0xFF; 	
		
		int rm = (r0 + (int)((r1 - r0) * w)) & 0xFF;
		int gm = (g0 + (int)((g1 - g0) * w)) & 0xFF;
		int bm = (b0 + (int)((b1 - b0) * w)) & 0xFF;
		int am = (a0 + (int)((a1 - a0) * w)) & 0xFF;
		
		int ret = (rm << 24) | (gm << 16) | (bm << 8) | (am);
		
		return ret;
	}
	
}
