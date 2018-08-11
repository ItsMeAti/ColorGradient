package imgData;

import java.awt.image.BufferedImage;

import colorData.XYZ;


public class XYZImg {

	private XYZ[][] pixels;
	private BufferedImage image;
	
	//Constructor (memory allocation)
	public XYZImg(int width, int height) {
		this.pixels = new XYZ[width][height];
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	//Sets up the 2D array of colors and colors the images pixel on a given coordinate
	//Sets the given images pixel's color on a specific coordinate 
	public void setXYZ(int width, int height, float x, float y, float z) {
		XYZ color = new XYZ(x,y,z);
		this.pixels[width][height] = color;
		
		int r1 = Math.round(x); 
		int g1 = Math.round(y); 
		int b1 = Math.round(z);
		
		int rgb = r1<<16 | g1<<8 | b1;
		
		image.setRGB(width, height, rgb);
	}

	public XYZ[][] getPixels() {
		return pixels;
	}

	public BufferedImage getImage() {
		return image;
	}
	

}
