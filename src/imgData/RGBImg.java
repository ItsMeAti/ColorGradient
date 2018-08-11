package imgData;

import java.awt.image.BufferedImage;

import colorData.RGB;

public class RGBImg {
	private RGB[][] pixels;
	private BufferedImage image;
	private int width,height;
	
	//Constructor (memory allocation)
	public RGBImg(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new RGB[width][height];
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	//Sets up the 2D array of colors and colors the images pixel on a given coordinate
	//Sets the given images pixel's color on a specific coordinate 
	public void setRgb(int width, int height, float r, float g, float b) {
		RGB color = new RGB(r,g,b);
		this.pixels[width][height] = color;
		
		int r1 = Math.round(r); 
		int g1 = Math.round(g); 
		int b1 = Math.round(b);
		
		
		
		int rgb = r1<<16 | g1<<8 | b1;
		
		image.setRGB(width, height, rgb);
	}
	
	//Generates XYZ image from sRGB
	public XYZImg toXYZ() {
		XYZImg result = new XYZImg(width, height);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {	
				float X = pixels[x][y].RGBToXYZ().getX();
				float Y = pixels[x][y].RGBToXYZ().getY();
				float Z = pixels[x][y].RGBToXYZ().getZ();
				result.setXYZ(x, y, X, Y, Z);
			}
		}
		return result;
	}
	
	//Generates CIELab image from sRGB
	public LabImg toLab() {
		LabImg result = new LabImg(width, height);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {	
				float L = pixels[x][y].RGBToLab().getL();
				float a = pixels[x][y].RGBToLab().getA();
				float b = pixels[x][y].RGBToLab().getB();
				result.setLab(x, y, L, a, b);
			}
		}
		return result;
	}

	public RGB[][] getPixels() {
		return pixels;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}	

}
