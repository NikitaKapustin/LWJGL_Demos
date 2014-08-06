package util;
public class Vec3 {
	public float x, y, z;
	public Vec3(double x, double y, double z){
		this.x = (float)x; this.y = (float)y; this.z = (float)z;
	}
	public Vec3(float x, float y, float z){
		this.x = x; this.y = y; this.z = z;
	}
	public Vec3 scalar_product(float s){
		return new Vec3(s*x, s*y, s*z);
	}
	public float length(){
		return (float)Math.sqrt(x*x+y*y+z*z);
	}
	public Vec3 getNormalized(){
		float l = length();
		if(l<=0) return this;
		return new Vec3(x/l, y/l, z/l);
	}
	public void normalize(){
		float l = length();
		if(l > 0){
			x /= l;
			y /= l;
			z /= l;
		}
	}
	public void setLength(float length){
		float l = length();
		if(l>0){
			x *= length/l;
			y *= length/l;
			z *= length/l;
		}
	}
	public void add(Vec3... components){
		for(Vec3 v: components){
			x+=v.x;
			y+=v.y;
			z+=v.z;
		}
	}
	public static float dot(Vec3 v1, Vec3 v2){
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}
	public static Vec3 cross(Vec3 v1, Vec3 v2){
		return new Vec3(v1.y*v2.z - v1.z*v2.y, v1.z*v2.x - v1.x*v2.z, v1.x*v2.y - v1.y*v2.x);
	}
	public static Vec3 sum(Vec3... components){
		Vec3 r = new Vec3(0, 0, 0);
		for(Vec3 v: components){
			r.x+=v.x;
			r.y+=v.y;
			r.z+=v.z;
		}
		return r;
	}

	@Override
	public String toString(){
		return "("+x+", "+y+", "+z+")";
	}
}