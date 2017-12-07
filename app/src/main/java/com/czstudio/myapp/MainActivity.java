package com.czstudio.myapp;

import android.app.*;
import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.view.*;
import android.view.SurfaceHolder.*;

import android.hardware.Camera;

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
