package com.flywind.app.services;
//http://www.f4.fhtw-berlin.de/~barthel/ImageJ/ColorInspector/HTMLHelp/farbraumJava.htm
public class RGBUtil {
	public static void main(String[] args) {
		int[] rgb=new int[]{0,0,0};
		int[] xyy=new int[]{0,0,0};
		rgb2xyY(rgb[0],rgb[1],rgb[2],xyy);
		System.out.println("-"+xyy[0]+"-"+xyy[1]+"-"+xyy[2]);
	}
	
	public static void rgb2xyY(int R, int G, int B, int []xyy) {
		//http://www.brucelindbloom.com
		
		float rf, gf, bf;
		float r, g, b, X, Y, Z;
		  
		// RGB to XYZ
		r = R/255.f; //R 0..1
		g = G/255.f; //G 0..1
		b = B/255.f; //B 0..1
		 
		if (r <= 0.04045)
			r = r/12;
		else
			r = (float) Math.pow((r+0.055)/1.055,2.4);
			  
		if (g <= 0.04045)
			g = g/12;
		else
			g = (float) Math.pow((g+0.055)/1.055,2.4);
		
		if (b <= 0.04045)
			b = b/12;
		else
			b = (float) Math.pow((b+0.055)/1.055,2.4);
		  
		X =  0.436052025f*r  + 0.385081593f*g  + 0.143087414f *b;
		Y =  0.222491598f*r  + 0.71688606f *g  + 0.060621486f *b;
		Z =  0.013929122f*r  + 0.097097002f*g  + 0.71418547f  *b;
		 
		float x;
		float y;
					  
		float sum = X + Y + Z;
		if (sum != 0) {
		x = X / sum;
		y = Y / sum;
		}
		else {
			float Xr = 0.964221f;  // reference white
			float Yr = 1.0f;
			float Zr = 0.825211f;
								 
		x = Xr / (Xr + Yr + Zr);
		y = Yr / (Xr + Yr + Zr);
		}
		   
		xyy[0] = (int) (255*x + .5);
		xyy[1] = (int) (255*y + .5);
		xyy[2] = (int) (255*Y + .5);
		 
	} 	
}
