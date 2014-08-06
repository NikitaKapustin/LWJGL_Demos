import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.util.Renderable;

import util.Mat4;
import util.Quaternion;
import util.Vec3;

public class Instance{
	Renderable model;
	Vec3 position;
	Quaternion orientation;
	Instance(Renderable model){
		this(model, 0, 0, 0);
	}
	Instance(Renderable model, float x, float y, float z){
		this.model = model;
		this.position = new Vec3(x,y,z);
		this.orientation = new Quaternion();
	}
	public void render(){
		glPushMatrix();
		//glTranslatef(position.x, position.y, position.z);
		glMultMatrix(Mat4.translate(position).toFloatBuffer());
		model.render();
		glPopMatrix();		
	}
}
