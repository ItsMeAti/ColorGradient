package colorData;
public class XYZ {
	
	private float x,y,z;

	public XYZ(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//D65/2°(CIE 1931) standard illuminant.
	public Lab XYZToLab() {
		float X = this.x / 95.047f;
		float Y = this.y / 100.000f;
		float Z = this.z / 108.883f;

		if ( X > 0.008856 ) 
			X = (float) Math.pow(X, 0.333333333);	
		else         
			X = ( 7.787f * X ) + ( 16f / 116f );
			
		if ( Y > 0.008856 ) 
			Y = (float) Math.pow(Y, 0.333333333);
		else                    
			Y = ( 7.787f * Y ) + ( 16f / 116f );
		
		if ( Z > 0.008856 ) 
			Z = (float) Math.pow(Z, 0.333333333);
		else                   
			Z = ( 7.787f * Z ) + ( 16f / 116f );

		float L = ( 116 * Y ) - 16;
		float a = 500 * ( X - Y );
		float b = 200 * ( Y - Z );
		
		return new Lab(L,a,b);
	}
	
	//X, Y and Z input refer to a D65/2° standard illuminant.
	//sR, sG and sB (standard RGB) output range = 0 ÷ 255
	public RGB XYZToRGB() {
		float var_X = this.x / 100;
		float var_Y = this.y / 100;
		float var_Z = this.z / 100;

		float var_R = var_X *  3.2406f + var_Y * -1.5372f + var_Z * -0.4986f;
		float var_G = var_X * -0.9689f + var_Y *  1.8758f + var_Z *  0.0415f;
		float var_B = var_X *  0.0557f + var_Y * -0.2040f + var_Z *  1.0570f;

		if ( var_R > 0.0031308 ) 
			var_R = (float) (1.055f * Math.pow(var_R, 1/2.4f) - 0.055f);
		else                     
			var_R = 12.92f * var_R;
		if ( var_G > 0.0031308 ) 
			var_G = (float) (1.055f * Math.pow(var_G, 1/2.4f) - 0.055f);
		else                     
			var_G = 12.92f * var_G;
		if ( var_B > 0.0031308 ) 
			var_B = (float) (1.055f * Math.pow(var_B, 1/2.4f) - 0.055f);
		else                     
			var_B = 12.92f * var_B;

		float sR = var_R * 255;
		float sG = var_G * 255;
		float sB = var_B * 255;
		
		return new RGB(sR,sG,sB);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "X: " + x + ", Y: " + y + ", Z: " +z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

}
