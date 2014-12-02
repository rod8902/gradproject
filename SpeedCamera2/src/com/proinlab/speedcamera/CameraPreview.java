package com.proinlab.speedcamera;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;

	@SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera) {
		super(context);
		this.mCamera = camera;
		this.mHolder = this.getHolder();
		this.mHolder.addCallback(this);
		this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// add 
		mCamera.stopPreview();
        mCamera.release();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		if (mHolder.getSurface() == null) {
			return;
		}

		try {
			mCamera.stopPreview();
		} catch (Exception e) {
		}

		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e) {
			Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());

		}
	}
}