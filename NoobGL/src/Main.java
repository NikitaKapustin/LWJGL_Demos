import org.lwjgl.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.util.*;

public class Main {
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static void main(String[] args){
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("A DEMO FOR NOOBS");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glEnable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1.0f);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glViewport(0, 0, WIDTH, HEIGHT);
		
		set_perspective_projection();
		
		glMatrixMode(GL_MODELVIEW);
		Instance instance = new Instance(Model.tetrahedron, 0, 0, -10);
		Instance grid = new Instance(Model.orientational_grid, -10, -10, -10);
		Model.tetrahedron.load();
		Model.orientational_grid.load();
		//Model.cube.load();
		long last_time = System.currentTimeMillis();
		while(!Display.isCloseRequested()){
			long time = System.currentTimeMillis();
			long time_elapsed = time-last_time;
			process_events(time_elapsed);
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			instance.render();
			grid.render();
			Display.update();
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
		gluPerspective(90.0f, (float)WIDTH/HEIGHT, 5.0f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
	}
	static void process_events(long time_elapsed){
		
	}
}
