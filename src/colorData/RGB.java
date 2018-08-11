package colorData;

public class RGB {
	
	private float r,g,b;

	public RGB(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	//sR, sG and sB (Standard RGB) input range = 0 ÷ 255
	//X, Y and Z output refer to a D65/2° standard illuminant.
	public XYZ RGBToXYZ() {

		float R = ( this.r / 255 );
		float G = ( this.g / 255 );
		float B = ( this.b / 255 );

		if ( R > 0.04045 ) 
			R = (float) Math.pow((R + 0.055) / 1.055, 2.4);
		else                   
			R = R / 12.92f;
		if ( G > 0.04045 ) 
			G = (float) Math.pow((G + 0.055 ) / 1.055, 2.4);
		else                   
			G = G / 12.92f;
		if ( B > 0.04045 ) 
			B = (float) Math.pow((B + 0.055 ) / 1.055, 2.4);
		else                   
			B = B / 12.92f;

		R = R * 100;
		G = G * 100;
		B = B * 100;

		float X = R * 0.4124f + G * 0.3576f + B * 0.1805f;
		float Y = R * 0.2126f + G * 0.7152f + B * 0.0722f;
		float Z = R * 0.0193f + G * 0.1192f + B * 0.9505f;
		
		
		return new XYZ(X,Y,Z);
	}
	
	//sR, sG and sB (Standard RGB) input range = 0 ÷ 255
	public Lab RGBToLab() {
		XYZ xyz = RGBToXYZ();
		return xyz.XYZToLab();
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "R: " + r + ", G: " + g + ", B: " +b;
	}

}
