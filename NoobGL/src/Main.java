import org.lwjgl.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.input.Keyboard.*;

import util.*;

public class Main {
	public static final int WIDTH = 1280, HEIGHT = 720;
	static final float CAMERA_SPEED = 10f; //(units/s)
	static Instance camera;
	public static void main(String[] args){
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("A DEMO FOR NOOBS");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glShadeModel(GL_SMOOTH);
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1.0f);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glViewport(0, 0, WIDTH, HEIGHT);
		
		set_perspective_projection();
		
		glMatrixMode(GL_MODELVIEW);
		Instance instance = new Instance(Model.cube, -10, -10, -10);
		Instance grid = new Instance(Model.orientational_grid, 0, 0, 0);
		camera = new Instance(null, 10, 10, 10);
		Model.tetrahedron.load();
		Model.orientational_grid.load();
		Model.cube.load();
		long last_time = System.currentTimeMillis();
		while(!Display.isCloseRequested()){
			long time = System.currentTimeMillis();
			long time_elapsed = time-last_time;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			
			process_events(time_elapsed);
			
			instance.render();
			grid.render();
			
			Display.update();
			last_time = time;
		}
	}
	static void set_orthogonal_projection(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	static void set_perspective_projection(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		//glMultMatrix(Mat4.perspective(90.0f, (float)WIDTH/HEIGHT, 5.0f, 1000.0f).toFloatBuffer()); //No perspective correction
		gluPerspective(90.0f, (float)WIDTH/HEIGHT, 2.0f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
	}
	static void process_events(long time_elapsed){
		Quaternion camera_rotation = new Quaternion();
		double half_angle = time_elapsed/2000.0; //1 radian/s
		if (isKeyDown(KEY_UP))
			camera_rotation.multiply(new Quaternion(Math.cos(half_angle), new Vec3(Math.sin(half_angle), 0, 0)));
		if (isKeyDown(KEY_DOWN)) 
			camera_rotation.multiply(new Quaternion(Math.cos(half_angle), new Vec3(-Math.sin(half_angle), 0, 0)));
		if (isKeyDown(KEY_LEFT)) 
			camera_rotation.multiply(new Quaternion(Math.cos(half_angle), new Vec3(0, Math.sin(half_angle), 0)));
		if (isKeyDown(KEY_RIGHT)) 
			camera_rotation.multiply(new Quaternion(Math.cos(half_angle), new Vec3(0, -Math.sin(half_angle), 0)));
		if (isKeyDown(KEY_RSHIFT)) 
			camera_rotation.multiply(new Quaternion(Math.cos(half_angle), new Vec3(0, 0, Math.sin(half_angle))));
		if (isKeyDown(KEY_NUMPAD1)||isKeyDown(KEY_END)) 
			camera_rotation.multiply(new Quaternion(Math.cos(half_angle), new Vec3(0, 0, -Math.sin(half_angle))));
		camera.orientation.multiply(camera_rotation);
		
		Mat4 orientation_matrix = camera.orientation.toMat4();
		float[] orientation_matrix_values = orientation_matrix.a;
		Vec3 displacement = new Vec3(0, 0, 0);
		if (isKeyDown(KEY_W))
			displacement.add(new Vec3(-orientation_matrix_values[2], -orientation_matrix_values[6], -orientation_matrix_values[10]));
		if (isKeyDown(KEY_A))
			displacement.add(new Vec3(-orientation_matrix_values[0], -orientation_matrix_values[4], -orientation_matrix_values[8]));
		if (isKeyDown(KEY_S))
			displacement.add(new Vec3(orientation_matrix_values[2], orientation_matrix_values[6], orientation_matrix_values[10]));
		if (isKeyDown(KEY_D))
			displacement.add(new Vec3(orientation_matrix_values[0], orientation_matrix_values[4], orientation_matrix_values[8]));
		displacement.setLength(CAMERA_SPEED*time_elapsed/1000.0f);
		camera.position.add(displacement);
		
		Mat4 view_matrix = Mat4.translate(camera.position.scalar_product(-1)).multiply(orientation_matrix);
		glMultMatrix(view_matrix.toFloatBuffer());
	}
}
