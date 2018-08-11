package util;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import colorData.RGB;

public class Util {
		
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	public static float Lerp(float a, float b, float t) {
		return a+(b-a) *t;	
	}
	
	public RGB LerpRGB(RGB a, RGB b, float t) {
		float r = a.getR() + (b.getR()-a.getR()) * t;
		float g = a.getG() + (b.getG()-a.getG()) * t;
		float bl = a.getB() + (b.getB()-a.getB()) * t;
		return new RGB(r,g,bl);
		
	}
	
}
