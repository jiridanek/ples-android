package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.item_bar_zbozi);
		setContentView(R.layout.activity_main);
		
		GridView mGridView = (GridView) findViewById(R.id.gridview);
//		TextView whatsonView = (TextView) findViewById(R.id.whatson);
//		if() {
//			whatsonView.setText()
//		}
		//mGridView.setAdapter(new MyAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void sendMessage (View view) {
		
	}
}
