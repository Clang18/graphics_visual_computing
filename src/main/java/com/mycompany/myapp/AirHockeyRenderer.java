package com.mycompany.myapp;

import android.content.*;
import android.opengl.*;
import com.mycompany.myapp.util.*;
import java.nio.*;
import javax.microedition.khronos.opengles.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import android.opengl.GLSurfaceView.Renderer;


public class AirHockeyRenderer implements Renderer
{
	private static final int POSITION_COMPONENT_COUNT=2;
	private static final int BYTES_PER_FLOAT=4;
	private final FloatBuffer vertexData;
	private final Context context;
	private int program;
	private static final String U_COLOR="u_Color";
	private int uColorLocation;
	private static final String A_POSITION="a_Position";
	private int aPositionLocation;
	
	public AirHockeyRenderer(Context context)
	{
		this.context=context;
		
		float[] tableVerticesWithTriangles=
		{
			
			//Triangle 1
		     0.0f, 0.90f,
		    -0.40f, 0.40f,
		    0.40f, 0.40f,
			
			//Triangle 2
			-0.40f, 0.40f,
			0.90f, 0.0f,
			-0.40f, -0.40f,
			
			//Triangle 3
			-0.40f, -0.40f,
			0.0f, -0.90f,
			0.40f, -0.40f,
			
			//Triangle 4
			0.40f, 0.40f,
			0.90f, 0.0f,
			0.40f, -0.40f,
			
			//1of1
			0.0f, 0.85f,
			-0.35f, 0.35f,
			0.35f, 0.35f,
			
			//1of2
			-0.85f, 0.0f,
			-0.35f, 0.35f,
			-0.35f, -0.35f,
			
			//1of3
			0.0f, -0.85f,
			0.35f, -0.35f,
			-0.35f, -0.35f,
			
			//1of4
			0.85f, 0.0f,
			0.35f, 0.35f,
			0.35f, -0.35f,
			
			//2of1
			0.0f, 0.80f,
			-0.30f, 0.30f,
			0.30f, 0.30f,
			
			//2of3
			0.0f, -0.80f,
			0.30f, -0.30f,
			-0.30f, -0.30f,
			
			//2of4
			0.80f, 0.0f,
			0.30f, -0.30f,
			0.30f, 0.30f,
			
			//3of1
			0.0f, 0.75f,
			-0.25f, 0.25f,
			0.25f, 0.25f,
			
			//3of2
			-0.75f, 0.0f,
			-0.25f, 0.25f,
			-0.25f, -0.25f,
			
			//3of3
			0.0f, -0.75f,
			0.25f, -0.25f,
			-0.25f, -0.25f,
			
			//3of4
			0.75f, 0.0f,
			0.25f, -0.25f,
			0.25f, 0.25f,
			
			//4of1
			0.0f, 0.70f,
			-0.20f, 0.20f,
			0.20f, 0.20f,
			
			//4of2
			-0.70f, 0.0f,
			-0.20f, 0.20f,
			-0.20f, -0.20f,
			
			//4of3
			0.0f, -0.70f,
			0.20f, -0.20f,
			-0.20f, -0.20f,
			
			//4of4
			0.70f, 0.0f,
			0.20f, -0.20f,
			0.20f, 0.20f,
			
			//5of1
			0.0f, 0.65f,
			-0.15f, 0.15f,
			0.15f, 0.15f,
			
			//5of2
			-0.65f, 0.0f,
			-0.15f, 0.15f,
			-0.15f, -0.15f,
			
			//5of3
			0.0f, -0.65f,
			0.15f, -0.15f,
			-0.15f, -0.15f,
			
			//5of4
			0.65f, 0.0f,
			0.15f, -0.15f,
			0.15f, 0.15f,
			
			//6of1
			0.0f, 0.60f,
			-0.10f, 0.10f,
			-0.10f, -0.10f,
			
			//6of2
			-0.60f, 0.0f,
			-0.10f, 0.10f,
			-0.10f, -0.10f,
			
			//6of3
			0.0f, -0.60f,
			0.10f, -0.10f,
			-0.10f, -0.10f,
			
			//6of4
			0.60f, 0.0f,
			0.10f, -0.10f,
			0.10f, 0.10f,
			
			//7of1
			0.0f, 0.55f,
			-0.05f, 0.05f,
			0.05f, 0.05f,
			
			//7of2
			-0.55f,0.0f,
			-0.5f,0.5f,
			-0.05f,-0.05f,
			
			//7of3
			0.0f,-0.55f,
			0.05f,-0.05f,
			-0.05f,-0.05f,
			
			//7of4
			0.55f,0.0f,
			0.05f,-0.05f,
			0.05f,0.05f,
			
			//8of1
			0.0f,0.50f,
			-0.01f,0.01f,
			0.01f,0.01f,
			
			//8of2
			-0.50f,0.0f
			-0.01f,0.01f,
			-0.01f,-0.01f,
			
			//8of3
			0.0f,-0.50f,
			0.01f,-0.01f,
			-0.01f,-0.01f,
			
			//8of4
			0.50f, 0.0f,
			0.01f, -0.01f,
			0.01f, 0.01f,
			
			
		};
		vertexData=ByteBuffer
	    .allocateDirect(tableVerticesWithTriangles.length*BYTES_PER_FLOAT)
	    .order(ByteOrder.nativeOrder())
	    .asFloatBuffer();
	    vertexData.put(tableVerticesWithTriangles);
	}
		
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to red. The first component is
        // red, the second is green, the third is blue, and the last
        // component is alpha, which we don't use in this lesson.
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		String vertexShaderSource= TextResourceReader
		.readTextFileFromResource(context,R.raw.simple_vertex_shader);
		
		String fragmentShaderSource = TextResourceReader
		.readTextFileFromResource(context,R.raw.simple_fragment_shader);
		
		int vertexShader=ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader=ShaderHelper.compileFragmentShader(fragmentShaderSource);
		
		program=ShaderHelper.linkProgram(vertexShader,fragmentShader);
		
		if(LoggerConfig.ON)
		{
			ShaderHelper.validateProgram(program);
		}
		
		glUseProgram(program);
		
		uColorLocation=glGetUniformLocation(program,U_COLOR);
		
		aPositionLocation=glGetAttribLocation(program,A_POSITION);
		
		vertexData.position(0);
		
		glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,GL_FLOAT,false,0,vertexData);
		
		glEnableVertexAttribArray(aPositionLocation);
		
    }

    /**
     * onSurfaceChanged is called whenever the surface has changed. This is
     * called at least once when the surface is initialized. Keep in mind that
     * Android normally restarts an Activity on rotation, and in that case, the
     * renderer will be destroyed and a new one createdk.
     * 
     * @param width
     *            The new width, in pixels.
     * @param height
     *            The new height, in pixels.
     */
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);
    }

    /**
     * OnDrawFrame is called whenever a new frame needs to be drawn. Normally,
     * this is done at the refresh rate of the screen.
     */
    
	@Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.
		
        glClear(GL_COLOR_BUFFER_BIT);
		
		glUniform4f(uColorLocation,1.0f,1.0f,0.0f,1.0f);
		glDrawArrays(GL_TRIANGLES,0,12);
		
		glUniform4f(uColorLocation,0.0f,1.1f,1.0f,0.0f);
		glDrawArrays(GL_TRIANGLES,12,12);
		
		glUniform4f(uColorLocation,1.0f,0.1f,0.0f,1.0f);
		glDrawArrays(GL_TRIANGLES,24,12);
		
		glUniform4f(uColorLocation,1.0f,1.0f,0.0f,1.0f);
		glDrawArrays(GL_TRIANGLES,36,12);
		
		glUniform4f(uColorLocation,0.5f,0.0f,1.0f,1.0f);
		glDrawArrays(GL_TRIANGLES,48,12);
		
		glUniform4f(uColorLocation,1.1f,0.0f,0.1f,0.5f);
		glDrawArrays(GL_TRIANGLES,60,12);
		
		glUniform4f(uColorLocation,1.0f,0.0f,1.0f,0.5f);
		glDrawArrays(GL_TRIANGLES,72,12);
		
		glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
		glDrawArrays(GL_TRIANGLES,84,12);
    }
}
