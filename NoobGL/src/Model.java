import static org.lwjgl.opengl.GL15.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Renderable;

import static org.lwjgl.opengl.GL11.*;

public class Model implements Renderable{
	public float[] vertex_data;
	public float[] colour_data;
	public int[] index_data;
	public int vertex_buffer_ID, colour_buffer_ID, index_buffer_ID;
	public int draw_type = GL_TRIANGLES;
	public static final Model tetrahedron = new Model();
	static{
		tetrahedron.vertex_data = new float[]{0, 0, 3, 0, (float)Math.sqrt(8), -1, (float)Math.sqrt(6), -(float)Math.sqrt(2), -1, -(float)Math.sqrt(6), -(float)Math.sqrt(2), -1};
		tetrahedron.colour_data = new float[]{1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
		tetrahedron.index_data = new int[]{0, 2, 1, 1, 3, 0, 2, 3, 1, 0, 3, 2}; //TRIANGLES //(counter-clockwise)
		//tetrahedron.index_data = new int[]{0, 1, 1, 2, 2, 3, 3, 0, 0, 2, 1, 3}; tetrahedron.draw_type = GL_LINES; //LINES
	}
	public static final Model cube = new Model();
	static{
		cube.vertex_data = new float[]{1,1,1, 1,1,-1, 1,-1,-1, 1,-1,1, -1,1,1, -1,1,-1, -1,-1,-1, -1,-1,1};
		cube.colour_data = new float[cube.vertex_data.length];
		Arrays.fill(cube.colour_data, 1.0f);
		cube.index_data = new int[]{4,0,5, 4,7,0, 4,5,7, 2,6,1, 2,3,6, 2,1,3, 0,1,5, 7,3,0, 5,6,7, 6,5,1, 3,7,6, 1,0,3};
	}
	public static final Model orientational_grid = new Model();
	static{
		orientational_grid.vertex_data = new float[3*75*2*2*3];
		for(int i = 0; i < 75; i++){
			
			orientational_grid.vertex_data[900*0+12*i+ 0] = 0;
			orientational_grid.vertex_data[900*0+12*i+ 1] = 75;
			orientational_grid.vertex_data[900*0+12*i+ 2] = i-37;
			orientational_grid.vertex_data[900*0+12*i+ 3] = 0;
			orientational_grid.vertex_data[900*0+12*i+ 4] = -75;
			orientational_grid.vertex_data[900*0+12*i+ 5] = i-37;
			orientational_grid.vertex_data[900*0+12*i+ 6] = 0;
			orientational_grid.vertex_data[900*0+12*i+ 7] = i-37;
			orientational_grid.vertex_data[900*0+12*i+ 8] = 75;
			orientational_grid.vertex_data[900*0+12*i+ 9] = 0;
			orientational_grid.vertex_data[900*0+12*i+10] = i-37;
			orientational_grid.vertex_data[900*0+12*i+11] = -75;
			
			orientational_grid.vertex_data[900*1+12*i+ 0] = i-37;
			orientational_grid.vertex_data[900*1+12*i+ 1] = 0;
			orientational_grid.vertex_data[900*1+12*i+ 2] = 75;
			orientational_grid.vertex_data[900*1+12*i+ 3] = i-37;
			orientational_grid.vertex_data[900*1+12*i+ 4] = 0;
			orientational_grid.vertex_data[900*1+12*i+ 5] = -75;
			orientational_grid.vertex_data[900*1+12*i+ 6] = 75;
			orientational_grid.vertex_data[900*1+12*i+ 7] = 0;
			orientational_grid.vertex_data[900*1+12*i+ 8] = i-37;
			orientational_grid.vertex_data[900*1+12*i+ 9] = -75;
			orientational_grid.vertex_data[900*1+12*i+10] = 0;
			orientational_grid.vertex_data[900*1+12*i+11] = i-37;
			
			orientational_grid.vertex_data[900*2+12*i+ 0] = 75;
			orientational_grid.vertex_data[900*2+12*i+ 1] = i-37;
			orientational_grid.vertex_data[900*2+12*i+ 2] = 0;
			orientational_grid.vertex_data[900*2+12*i+ 3] = -75;
			orientational_grid.vertex_data[900*2+12*i+ 4] = i-37;
			orientational_grid.vertex_data[900*2+12*i+ 5] = 0;
			orientational_grid.vertex_data[900*2+12*i+ 6] = i-37;
			orientational_grid.vertex_data[900*2+12*i+ 7] = 75;
			orientational_grid.vertex_data[900*2+12*i+ 8] = 0;
			orientational_grid.vertex_data[900*2+12*i+ 9] = i-37;
			orientational_grid.vertex_data[900*2+12*i+10] = -75;
			orientational_grid.vertex_data[900*2+12*i+11] = 0;
		}
		orientational_grid.colour_data = new float[3*75*2*2*3];
		for(int i = 0; i < 300; i++){
			orientational_grid.colour_data[900*0+3*i+0] = 1;
			orientational_grid.colour_data[900*0+3*i+1] = 0;
			orientational_grid.colour_data[900*0+3*i+2] = 0;
			
			orientational_grid.colour_data[900*1+3*i+0] = 0;
			orientational_grid.colour_data[900*1+3*i+1] = 1;
			orientational_grid.colour_data[900*1+3*i+2] = 0;
			
			orientational_grid.colour_data[900*2+3*i+0] = 0;
			orientational_grid.colour_data[900*2+3*i+1] = 0;
			orientational_grid.colour_data[900*2+3*i+2] = 1;
		}
		orientational_grid.index_data = new int[3*75*2*2];
		for(int i = 0; i < 900; i++)
			orientational_grid.index_data[i] = i;
		orientational_grid.draw_type = GL_LINES;
	}
	
	public void load(){
		vertex_buffer_ID = glGenBuffers();
		FloatBuffer vertex_data_buffer = BufferUtils.createFloatBuffer(vertex_data.length);
		vertex_data_buffer.put(vertex_data);
		vertex_data_buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_ID);
		glBufferData(GL_ARRAY_BUFFER, vertex_data_buffer, GL_STATIC_DRAW);
		
		colour_buffer_ID = glGenBuffers();
		FloatBuffer colour_data_buffer = BufferUtils.createFloatBuffer(colour_data.length);
		colour_data_buffer.put(colour_data);
		colour_data_buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, colour_buffer_ID);
		glBufferData(GL_ARRAY_BUFFER, colour_data_buffer, GL_STATIC_DRAW);
		
		index_buffer_ID = glGenBuffers();
		IntBuffer index_data_buffer = BufferUtils.createIntBuffer(index_data.length);
		index_data_buffer.put(index_data);
		index_data_buffer.flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, index_buffer_ID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, index_data_buffer, GL_STATIC_DRAW);
	}
	public void render(){
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_ID);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		glEnableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, colour_buffer_ID);
		glColorPointer(3, GL_FLOAT, 0, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, index_buffer_ID);
		glDrawElements(draw_type, index_data.length, GL_UNSIGNED_INT, 0);
	}
}
