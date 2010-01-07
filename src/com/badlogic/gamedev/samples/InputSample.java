package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class InputSample extends GameActivity implements GameListener
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setGameListener( this );
	}	

	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) {
		Log.d( "InputSample", "touch: " + activity.getTouchX() + ", " + activity.getTouchY() + ", " + activity.isTouched() );
		Log.d( "InputSample", "accelerometer: " + activity.getAccelerationOnXAxis() + ", " + activity.getAccelerationOnYAxis() + ", " + activity.getAccelerationOnZAxis() );
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) {
		// TODO Auto-generated method stub
		
	}

}
