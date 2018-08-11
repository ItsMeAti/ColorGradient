package colorData;

public class Lab {
	
	private float L,a,b;

	public Lab(float l, float a, float b) {
		this.L = l;
		this.a = a;
		this.b = b;
	}
	
	public float getL() {
		return L;
	}

	public float getA() {
		return a;
	}

	public float getB() {
		return b;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "L: " + L + ", a: " + a + ", b: " +b;
	}

	//D65/2°(CIE 1931) standard illuminant.
	public XYZ LabToXYZ() {
		float var_Y = ( this.L + 16 ) / 116;
		float var_X = this.a / 500 + var_Y;
		float var_Z = var_Y - this.b / 200;

		if (Math.pow(var_Y, 3)  > 0.008856 ) 
			var_Y = (float) Math.pow(var_Y, 3);
		else                       
			var_Y = ( var_Y - 16 / 116 ) / 7.787f;
		if (Math.pow(var_X, 3)  > 0.008856 ) 
			var_X = (float) Math.pow(var_X, 3);
		else                       
			var_X = ( var_X - 16 / 116 ) / 7.787f;
		if (Math.pow(var_Z, 3)  > 0.008856 ) 
			var_Z = (float) Math.pow(var_Z, 3);
		else                       
			var_Z = ( var_Z - 16 / 116 ) / 7.787f;

		float X = var_X * 95.047f;
		float Y = var_Y * 100.000f;
		float Z = var_Z * 108.883f;

		return new XYZ(X,Y,Z);
	}
	
	public RGB LabToRGB() {
		return LabToXYZ().XYZToRGB();
	}
	
	public Lab mapTo0_255() {
		float L = (this.L-(-100))/(100-(-100)) * (255-0) + 0;
		float a = (this.a-(-128))/(128-(-128)) * (255-0) + 0;
		float b = (this.b-(-128))/(128-(-128)) * (255-0) + 0;
		return new Lab(L,a,b);
	}

}
