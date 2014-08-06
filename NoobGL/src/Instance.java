import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.util.Renderable;

import util.Quaternion;

public class Instance{
	Renderable model;
	float[] position;
	Quaternion orientation;
	Instance(Renderable model){
		this.model = model;
	}
	Instance(Renderable model, float x, float y, float z){
		this.model = model;
		this.position = new float[]{x,y,z};
	}
	public void render(){
		glPushMatrix();
		glTranslatef(position[0], position[1], position[2]);
		model.render();
		glPopMatrix();
	}
}
