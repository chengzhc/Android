package com.czstudio.myapp;
import android.graphics.*;
import android.hardware.*;
import android.util.*;
import android.view.*;
import android.view.SurfaceHolder.*;
import java.io.*;

import java.io.ByteArrayOutputStream;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;


import android.hardware.Camera;



public class CzSys_Camera2
{
	Camera mCamera;
	int mWidth,mHeight;
	SurfaceView mSurfaceView;
	SurfaceHolder mSurfaceHolder;
	
	public boolean isPreview=false;
	
	public CzSys_Camera2(SurfaceView surfaceView,int width,int height){
		mCamera=Camera.open();
		mWidth = width;
		mHeight = height;
		mSurfaceView=surfaceView;
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(new Callback() {
				@Override
				public void surfaceChanged(SurfaceHolder holder, int format,
										   int width, int height) {
				}

				@Override
				public void surfaceCreated(SurfaceHolder holder) {
					initCamera(); 
				}

				@Override
				public void surfaceDestroyed(SurfaceHolder holder) {
					
					if (mCamera != null) {
						if (isPreview)
							mCamera.stopPreview();
						mCamera.release();
						mCamera = null;
					}
					System.exit(0);
				}
			});
		
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	
	}
	
	void initCamera() {
		if (mCamera != null && !isPreview) {
			try {
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setPreviewSize(mWidth, mHeight); // ����Ԥ����Ƭ�Ĵ�С
				parameters.setPreviewFpsRange(20, 30); // ÿ����ʾ20~30֡
				parameters.setPictureFormat(ImageFormat.NV21); // ����ͼƬ��ʽ
				parameters.setPictureSize(mWidth, mHeight); // ������Ƭ�Ĵ�С
				// camera.setParameters(parameters); // android2.3.3�Ժ���Ҫ���д���
				mCamera.setPreviewDisplay(mSurfaceHolder); // ͨ��SurfaceView��ʾȡ������
				mCamera.setPreviewCallback(new Camera.PreviewCallback(){
						@Override
						public void onPreviewFrame(byte[] data, Camera camera) {
							Size size = camera.getParameters().getPreviewSize();
							try {
								// ����image.compressToJpeg������YUV��ʽͼ�����dataתΪjpg��ʽ
								YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width,
															  size.height, null);
								if (image != null) {
									ByteArrayOutputStream outstream = new ByteArrayOutputStream();
									image.compressToJpeg(new Rect(0, 0, size.width, size.height),
														 20, outstream);

									
									outstream.flush();
								}
							} catch (Exception ex) {
								Log.e("Sys", "Error:" + ex.getMessage());
							}
						}
				}); // ���ûص����
				mCamera.startPreview(); // ��ʼԤ��
				mCamera.autoFocus(null); // �Զ��Խ�
			} catch (Exception e) {
				e.printStackTrace();
			}
			isPreview = true;
		}
	}
	
	
}
