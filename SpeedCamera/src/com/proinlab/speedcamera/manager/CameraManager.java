package com.proinlab.speedcamera.manager;

import android.hardware.Camera;

public class CameraManager {

	public static Camera getCameraInstance(Camera c) {
		c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
		}
		return c;
	}
	
	public static void releaseCamera(Camera c) {
		if (c != null) {
			c.release();
			c = null;
		}
	}
	
}
