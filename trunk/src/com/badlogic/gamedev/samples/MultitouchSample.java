package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;

public class MultitouchSample extends GameActivity implements GameListener
{
	Mesh rect;
	int[] touchX = new int[10];
	int[] touchY = new int[10];		
	boolean[] touched = new boolean[10];
	
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setGameListener( this );
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{			
		int action = event.getAction();
	    int ptrId = event.getPointerId(0);
	    if(event.getPointerCount() > 1)
	        ptrId = (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
	    action = action & MotionEvent.ACTION_MASK;
	    if(action < 7 && action > 4)
	        action = action - 5;	    		   
		
		if( action == MotionEvent.ACTION_DOWN )
		{
			for( int i = 0; i < event.getPointerCount(); i++ )
			{
				float x = event.getX(i);
			    float y = event.getY(i);
			    
				touchX[event.getPointerId(i)] = (int)x;
				touchY[event.getPointerId(i)] = (int)y;
			}			

			touched[ptrId] = true;
			Log.d( "Multitouch", "down, ptr: " + ptrId );
		}
		if( action == MotionEvent.ACTION_MOVE )
		{							
			for( int i = 0; i < event.getPointerCount(); i++ )
			{
				float x = event.getX(i);
			    float y = event.getY(i);
			    
				touchX[event.getPointerId(i)] = (int)x;
				touchY[event.getPointerId(i)] = (int)y;				
			}											
		}
		if( action == MotionEvent.ACTION_UP )
		{
			touched[ptrId] = false;
			
			if( event.getPointerCount() == 1 )
				for( int i = 0; i < 10; i++ )
					touched[i] = false;
			Log.d( "Multitouch", "up, ptr: " + ptrId );
		}
		if( action == MotionEvent.ACTION_CANCEL )
		{
			touched[ptrId] = false;
			if( event.getPointerCount() == 1 )
			for( int i = 0; i < 10; i++ )
				touched[i] = false;
		}
		
		return true;
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, activity.getViewportWidth(), 0, activity.getViewportHeight() );
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		
		for( int i = 0; i < touched.length; i++ )
		{
			if( touched[i] )
			{
				gl.glPushMatrix();
				gl.glTranslatef( touchX[i], activity.getViewportHeight() - touchY[i], 0 );
				rect.render(PrimitiveType.TriangleFan);
				gl.glPopMatrix();
			}
		}			
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		rect = new Mesh( gl, 4, false, false, false );
		rect.vertex( -80, -80, 0 );
		rect.vertex( -80, 80, 0 );
		rect.vertex( 80, 80, 0 );
		rect.vertex( 80, -80, 0 );
		
	}
}
