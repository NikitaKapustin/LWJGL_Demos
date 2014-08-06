package util;
import java.nio.*;
import org.lwjgl.BufferUtils;

import static util.Vec3.*;

public class Quaternion {
	float r; Vec3 v;
	public Quaternion(){
		this.r = 1; this.v = new Vec3(0, 0, 0);
	}
	public Quaternion(double r, Vec3 v){
		this((float)r, v);
	}
	public Quaternion(float r, Vec3 v){
		this.r = r; this.v = v;
	}
	public void multiply(Quaternion q){
		this.r = r*q.r - Vec3.dot(v, q.v);
		this.v = sum(v.scalar_product(q.r), q.v.scalar_product(r), Vec3.cross(v,q.v));
	}
	public Mat4 toMat4(){
		return new Mat4(1 - 2*(v.y*v.y+v.z*v.z), 2*(v.x*v.y-v.z*r), 2*(v.x*v.z+v.y*r), 0, 2*(v.x*v.y+v.z*r), 1 - 2*(v.x*v.x+v.z*v.z), 2*(v.y*v.z-v.x*r), 0, 2*(v.x*v.z-v.y*r), 2*(v.y*v.z+v.x*r), 1 - 2*(v.x*v.x+v.y*v.y), 0, 0, 0, 0, 1);
	}
	public FloatBuffer toFloatBuffer(){
		float [] a = {1 - 2*(v.y*v.y+v.z*v.z), 2*(v.x*v.y-v.z*r), 2*(v.x*v.z+v.y*r), 0, 2*(v.x*v.y+v.z*r), 1 - 2*(v.x*v.x+v.z*v.z), 2*(v.y*v.z-v.x*r), 0, 2*(v.x*v.z-v.y*r), 2*(v.y*v.z+v.x*r), 1 - 2*(v.x*v.x+v.y*v.y), 0, 0, 0, 0, 1};
		FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(16);
		dataBuffer.put(a);
		dataBuffer.flip();
		return dataBuffer;
	}
	public static Quaternion product(Quaternion q1, Quaternion q2){
		return new Quaternion(q1.r*q2.r - Vec3.dot(q1.v, q2.v), Vec3.sum(q1.v.scalar_product(q2.r), q2.v.scalar_product(q1.r), Vec3.cross(q1.v,q2.v)));
	}
}
