package com.badlogic.gamedev.tools;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

/**
 * The game activity implements the Activity Life Cycle of the
 * game. A {@link GameListener} can be attached to the activity and will
 * be called whenever a new frame has to be rendered or the
 * application needs to be setup.
 *  
 * @author mzechner
 *
 */
public class GameActivity extends Activity implements GLSurfaceView.Renderer
{	
	/** GLSurfaceView **/
	private GLSurfaceView glSurface;
	
	/** with and height of the viewport **/
	private int width, height;
	
	/** GameListener **/
	private GameListener listener = null;
	
	/** start time of last frame in nano seconds **/
	long lastFrameStart;
	/** delta time in seconds **/
	float deltaTime;		
	
	/**
	 * Called on creation of the Activity
	 */
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		glSurface = new GLSurfaceView( this );
		glSurface.setRenderer( this );
		this.setContentView( glSurface );
	}
	
	/**
	 * Called each Frame
	 */
	@Override
	public void onDrawFrame(GL10 gl) 
	{		
		long currentFrameStart = System.nanoTime();
		deltaTime = (currentFrameStart-lastFrameStart) / 1000000000.0f;
		lastFrameStart = currentFrameStart;
		
		if( listener != null )
			listener.mainLoopIteration( this, gl );
		
		Log.d( "GameActivity", "delta time: " + deltaTime );
	}

	/**
	 * Called when the surface size changed, e.g. due to tilting
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{	
		this.width = width;
		this.height = height;
	}

	/**
	 * Called when the GLSurfaceView has finished initialization
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{	
		if( listener != null )
			listener.setup( this, gl );
		
		lastFrameStart = System.nanoTime();
	}
	
	/**
	 * Called when the application is paused. We need to
	 * also pause the GLSurfaceView.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glSurface.onPause();		
	}

	/**
	 * Called when the application is resumed. We need to
	 * also resume the GLSurfaceView.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		glSurface.onResume();		
	}
	
	/**
	 * @return the viewport width in pixels
	 */
	public int getViewportWidth( )
	{
		return width;
	}
	
	/**
	 * @return the viewport height in pixels
	 */
	public int getViewportHeight( )
	{
		return height;
	}
	
	/**
	 * @return the delta time in seconds
	 */
	public float getDeltaTime( )
	{
		return deltaTime;
	}
}
