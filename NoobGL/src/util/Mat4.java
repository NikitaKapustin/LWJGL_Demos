package util;
import java.nio.*;

import org.lwjgl.BufferUtils;


public class Mat4 {
	public float [] a;
	public Mat4(){
		a = new float[16];
	}
	public Mat4(float... a){
		this.a = a;
	}
	public Mat4 multiply(Mat4 b){
		Mat4 r = new Mat4();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					r.a[4*i+j]+=a[4*i+k]*b.a[4*k+j];
				}
			}
		}
		return r;
	}
	public FloatBuffer toFloatBuffer(){
		FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(16);
		dataBuffer.put(a);
		dataBuffer.flip();
		return dataBuffer;
	}
	public Mat4 transpose(){
		return new Mat4(new float[]{a[0],a[4],a[8],a[12],a[1],a[5],a[9],a[13],a[2],a[6],a[10],a[14],a[3],a[7],a[11],a[15]});
	}
	public static Mat4 identity(){
		return new Mat4(new float[]{1,0,0,0,0,1,0,0,0,0,1,0,0,0,1});
	}
	public static Mat4 perspective(float angle, float aspect, float near, float far){
		Mat4 r = new Mat4();
		r.a[5] = (float)(1/Math.tan(angle/2));
		r.a[0] = r.a[5]/aspect;
		r.a[10] = - (far + near) / (far - near);
		r.a[11]= -1;
		r.a[14] = - (2*far*near) / (far - near);
		return r;
	}
	public static Mat4 translate(Vec3 t){
		return new Mat4(new float[]{1,0,0,t.x,0,1,0,t.y,0,0,1,t.z,0,0,0,1}).transpose();
	}
}
