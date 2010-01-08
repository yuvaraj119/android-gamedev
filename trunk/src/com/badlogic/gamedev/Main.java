package com.badlogic.gamedev;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.badlogic.gamedev.samples.ColorSample;
import com.badlogic.gamedev.samples.ColorSample2;
import com.badlogic.gamedev.samples.InputSample;
import com.badlogic.gamedev.samples.MeshSample;
import com.badlogic.gamedev.samples.TextureSample;
import com.badlogic.gamedev.samples.TriangleSample;
import com.badlogic.gamedev.tools.GameActivity;

public class Main extends ListActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		String[] items = new String[]{"GameActivity", "Input Sample", "Triangle Sample",  "Color Sample",
									  "Color Sample 2","Texture Sample", "Mesh Sample"
									 };
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));        
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
	
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();

		Intent intent = null;
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
		
		startActivity( intent );
	}
}