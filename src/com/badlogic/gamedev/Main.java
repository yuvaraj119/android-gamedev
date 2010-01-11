package com.badlogic.gamedev;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.badlogic.gamedev.samples.CameraSample;
import com.badlogic.gamedev.samples.ColorSample;
import com.badlogic.gamedev.samples.ColorSample2;
import com.badlogic.gamedev.samples.InputSample;
import com.badlogic.gamedev.samples.LightSample;
import com.badlogic.gamedev.samples.MeshSample;
import com.badlogic.gamedev.samples.MultipleObjectsSample;
import com.badlogic.gamedev.samples.MusicSample;
import com.badlogic.gamedev.samples.ObjSample;
import com.badlogic.gamedev.samples.OrthoSample;
import com.badlogic.gamedev.samples.PerspectiveSample;
import com.badlogic.gamedev.samples.SoundSample;
import com.badlogic.gamedev.samples.TextureMeshSample;
import com.badlogic.gamedev.samples.TextureSample;
import com.badlogic.gamedev.samples.TransformationSample;
import com.badlogic.gamedev.samples.TriangleSample;
import com.badlogic.gamedev.samples.ZBufferSample;
import com.badlogic.gamedev.spaceinvaders.SpaceInvaders;
import com.badlogic.gamedev.tools.GameActivity;

public class Main extends ListActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		String[] items = new String[]{"Space Invaders", "GameActivity", "Input Sample", "Triangle Sample",  "Color Sample",
									  "Color Sample 2","Texture Sample", "Mesh Sample", "Texture & Mesh Sample",
									  "Ortho Sample", "Camera Sample", "Z-Buffer Sample", "Light Sample",
									  "Transformation Sample", "Obj Sample", "Multiple Objects Sample", "Sound Sample", "Music Sample"
									 };
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));        
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
	
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();

		Intent intent = null;
		if( keyword.equals( "Space Invaders" ) )
			intent = new Intent( this, SpaceInvaders.class );
		if( keyword.equals( "GameActivity" ) )
			intent = new Intent( this, GameActivity.class );
		if( keyword.equals( "Input Sample" ) )
			intent = new Intent( this, InputSample.class );
		if( keyword.equals( "Triangle Sample" ) )
			intent = new Intent( this, TriangleSample.class );
		if( keyword.equals( "Color Sample" ) )
			intent = new Intent( this, ColorSample.class );
		if( keyword.equals( "Color Sample 2" ) )
			intent = new Intent( this, ColorSample2.class );
		if( keyword.equals( "Texture Sample" ) )
			intent = new Intent( this, TextureSample.class );
		if( keyword.equals( "Mesh Sample" ) )
			intent = new Intent( this, MeshSample.class );
		if( keyword.equals( "Texture & Mesh Sample" ) )
			intent = new Intent( this, TextureMeshSample.class );
		if( keyword.equals( "Ortho Sample" ) )
			intent = new Intent( this, OrthoSample.class );
		if( keyword.equals( "Perspective Sample" ) )
			intent = new Intent( this, PerspectiveSample.class );
		if( keyword.equals( "Camera Sample" ) )
			intent = new Intent( this, CameraSample.class );
		if( keyword.equals( "Z-Buffer Sample" ) )
			intent = new Intent( this, ZBufferSample.class );
		if( keyword.equals( "Light Sample" ) )
			intent = new Intent( this, LightSample.class );
		if( keyword.equals( "Transformation Sample" ) )
			intent = new Intent( this, TransformationSample.class );
		if( keyword.equals( "Obj Sample" ) )
			intent = new Intent( this, ObjSample.class );
		if( keyword.equals( "Multiple Objects Sample" ) )
			intent = new Intent( this, MultipleObjectsSample.class );
		if( keyword.equals( "Sound Sample" ) )
			intent = new Intent( this, SoundSample.class );
		if( keyword.equals( "Music Sample" ) )
			intent = new Intent( this, MusicSample.class );
			
		startActivity( intent );
	}
}