package com.czstudio.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

public class MainActivity extends Activity 
{
	SurfaceView mSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mSurfaceView=(SurfaceView)findViewById(R.id.sv);
		new CzSys_Camera2(mSurfaceView,720,480);
    }
}
