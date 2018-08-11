package imgData;

import java.awt.image.BufferedImage;

import colorData.Lab;
import colorData.RGB;

public class LabImg {
	
	private Lab[][] pixels;
	private BufferedImage image;
	private int width,height;
	
	//Constructor (memory allocation)
	public LabImg(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new Lab[width][height];
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	//Sets up the 2D array of colors and colors the images pixel on a given coordinate
	//Sets the given images pixel's color on a specific coordinate 
	public void setLab(int width, int height, float L, float a, float b) {
		Lab color = new Lab(L,a,b);
		pixels[width][height] = color;
		
		color.mapTo0_255();
		
		int r1 = Math.round(color.getL()); 
		int g1 = Math.round(color.getA()); 
		int b1 = Math.round(color.getB());
		
		if(r1<0)
			r1=0;
		
		if(r1>255)
			r1=255;
		
		if(g1<0)
			g1=0;
		
		if(g1>255)
			g1=255;
		
		if(b1<0)
			b1=0;
		
		if(b1>255)
			b1=255;
		
		int rgb = r1<<16 | g1<<8 | b1;
		
		image.setRGB(width, height, rgb);
	}
	
		//Apply a Cx kernel, returns Lab image
		//coordinates for sobel kernel
		//[x-1][y-1]  [x][y-1]  [x+1][y-1]
		//[x-1][y]    [x][y]    [x+1][y]
		//[x-1][y+1]  [x][y+1]  [x+1][y+1]
		public LabImg applyCxKernel() {
			LabImg result = new LabImg(width-2, height-2);
			for(int x = 1; x < result.getWidth(); x++) {
				for(int y = 1; y < result.getHeight(); y++) {
					//[0][0]
					float L0 = pixels[x-1][y-1].getL();
					float a0 = pixels[x-1][y-1].getA();
					float b0 = pixels[x-1][y-1].getB();

					//[2][0]
					float L1 = pixels[x+1][y-1].getL();
					float a1 = pixels[x+1][y-1].getA();
					float b1 = pixels[x+1][y-1].getB();

					//[0][1] 
					float L2 = pixels[x-1][y].getL();
					float a2 = pixels[x-1][y].getA();
					float b2 = pixels[x-1][y].getB();
					
					//[2][1]
					float L3 = pixels[x+1][y].getL();
					float a3 = pixels[x+1][y].getA();
					float b3 = pixels[x+1][y].getB();
					
					//[0][2]
					float L4 = pixels[x-1][y+1].getL();
					float a4 = pixels[x-1][y+1].getA();
					float b4 = pixels[x-1][y+1].getB();
					
					//[2][2]
					float L5 = pixels[x+1][y+1].getL();
					float a5 = pixels[x+1][y+1].getA();
					float b5 = pixels[x+1][y+1].getB();
					
					float L = (1*L0) + (-1*L1) + (2*L2) + (-2*L3) + (1*L4) + (-1*L5);
					float a = (1*a0) + (-1*a1) + (2*a2) + (-2*a3) + (1*a4) + (-1*a5);
					float b = (1*b0) + (-1*b1) + (2*b2) + (-2*b3) + (1*b4) + (-1*b5);
					
					result.setLab(x-1, y-1, L,a,b);
				}
			}
			return result;
		}
		
		//Returns a Cy kernel
		public LabImg applyCyKernel() {
			LabImg result = new LabImg(width-2, height-2);
			for(int x = 1; x < result.getWidth(); x++) {
				for(int y = 1; y < result.getHeight(); y++) {
					//[0][0]
					float L0 = pixels[x-1][y-1].getL();
					float a0 = pixels[x-1][y-1].getA();
					float b0 = pixels[x-1][y-1].getB();
					
					//[1][0]
					float L1 = pixels[x][y-1].getL();
					float a1 = pixels[x][y-1].getA();
					float b1 = pixels[x][y-1].getB();
					
					//[2][0]
					float L2 = pixels[x+1][y-1].getL();
					float a2 = pixels[x+1][y-1].getA();
					float b2 = pixels[x+1][y-1].getB();
					
					//[0][2]
					float L3 = pixels[x-1][y+1].getL();
					float a3 = pixels[x-1][y+1].getA();
					float b3 = pixels[x-1][y+1].getB();
					
					//[1][2]
					float L4 = pixels[x][y+1].getL();
					float a4 = pixels[x][y+1].getA();
					float b4 = pixels[x][y+1].getB();
					
					//[2][2]
					float L5 = pixels[x+1][y+1].getL();
					float a5 = pixels[x+1][y+1].getA();
					float b5 = pixels[x+1][y+1].getB();
					
					float L = (1*L0) + (2*L1) + (1*L2) + (-1*L3) + (-2*L4) + (-1*L5);
					float a = (1*a0) + (2*a1) + (1*a2) + (-1*a3) + (-2*a4) + (-1*a5);
					float b = (1*b0) + (2*b1) + (1*b2) + (-1*b3) + (-2*b4) + (-1*b5);
					
					result.setLab(x-1, y-1, L, a, b);
				}
			}
			return result;
		}
	
	//Returns the gradients direction from Cx,Cy
	public LabImg direction(LabImg Cx, LabImg Cy) {
		LabImg result = new LabImg(Cx.getWidth(),Cx.getHeight());
		for(int x = 0; x < result.getWidth()-1; x++) {
			for(int y = 0; y < result.getHeight()-1; y++) {
				float CxL = Cx.getPixel(x, y).getL();
				float Cxa = Cx.getPixel(x, y).getA();
				float Cxb = Cx.getPixel(x, y).getB();
				
				float CyL = Cy.getPixel(x, y).getL();
				float Cya = Cy.getPixel(x, y).getA();
				float Cyb = Cy.getPixel(x, y).getB();
				
				float thetaL = (float) Math.atan2(CyL, CxL);
				float thetaa = (float) Math.atan2(Cya, Cxa);
				float thetab = (float) Math.atan2(Cyb, Cxb);
				
				float thetaLmapped = map(thetaL, (float)-Math.PI, (float) Math.PI, 0, 255);
				float thetaamapped = map(thetaa, (float)-Math.PI, (float) Math.PI, 0, 255);
				float thetabmapped = map(thetab, (float)-Math.PI, (float) Math.PI, 0, 255);
				
				result.setLab(x, y, thetaLmapped, thetaamapped, thetabmapped);
			}
		}
		return result;
	}
	
	//Returns the gradients magnitude from Cx, Cy
	public LabImg magnitude(LabImg Cx, LabImg Cy) {
		LabImg result = new LabImg(Cx.getWidth(),Cx.getHeight());
		for(int x = 0; x < result.getWidth()-1; x++) {
			for(int y = 0; y < result.getHeight()-1; y++) {
				float CxL = Cx.getPixel(x, y).getL();
				float Cxa = Cx.getPixel(x, y).getA();
				float Cxb = Cx.getPixel(x, y).getB();
				
				float CyL = Cy.getPixel(x, y).getL();
				float Cya = Cy.getPixel(x, y).getA();
				float Cyb = Cy.getPixel(x, y).getB();
				
				float magnitudeL = (float) Math.hypot(CxL,CyL);
				float magnitudea = (float) Math.hypot(Cxa,Cya);
				float magnitudeb = (float) Math.hypot(Cxb,Cyb);
				
				result.setLab(x, y, magnitudeL,magnitudea,magnitudeb);
			}
		}
		return result;
	}
	
	//Transforms an image from CIE-L*a*b* to sRGB color space - gives back the sRGB image
	public RGBImg labImageToRGBImage() {
		RGBImg result = new RGBImg(image.getWidth(), image.getHeight());
		for(int x = 0; x < image.getWidth()-1; x++) {
			for(int y = 0; y < image.getHeight()-2; y++) {
				float L = pixels[x][y].getL();
				float a = pixels[x][y].getA();
				float b = pixels[x][y].getB();
				Lab clrLab = new Lab(L, a, b);
				RGB clrRGB = clrLab.LabToRGB();
				float r = clrRGB.getR();
				float g = clrRGB.getG();
				float b_ = clrRGB.getB();
				
				if(r<0)
					r=0;
				if(g<0)
					g=0;
				if(b_<0)
					b_=0;
				
				if(r>255)
					r=255;
				if(g>255)
					g=255;
				if(b_>255)
					b_=255;
				
				result.setRgb(x, y, r,g,b_);
			}
		}
		return result;
	}
	
	//Transforms an sRGB to grayscale image by averaging its individual color channels
	public RGBImg imageToGrayscale() {
		RGBImg result = new RGBImg(image.getWidth(), image.getHeight());
		for(int x = 0; x < image.getWidth()-1; x++) {
			for(int y = 0; y < image.getHeight()-1; y++) {
				int r = Math.round(pixels[x][y].LabToRGB().getR());
				int g = Math.round(pixels[x][y].LabToRGB().getG());
				int b = Math.round(pixels[x][y].LabToRGB().getB());
				
				if(r<0)
					r=0;
				if(g<0)
					g=0;
				if(b<0)
					b=0;
				
				if(r>255)
					r=255;
				if(g>255)
					g=255;
				if(b>255)
					b=255;
				
				int gray = (r+g+b)/3;
				result.setRgb(x, y, gray,gray,gray);
			}
		}
		return result;
	}
	
	public float map(float x,float fromX, float toX, float fromY, float toY) {
		float y = (x-fromX) / (toX-fromX) * (toY-fromY) + fromY; 
				return y;
	}

	public Lab[][] getPixels() {
		return pixels;
	}
	
	public Lab getPixel(int x, int y) {
		return pixels[x][y];
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
