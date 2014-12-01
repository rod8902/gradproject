package com.proinlab.speedcamera.manager;

import android.hardware.Camera;
import android.media.MediaRecorder;

public class MediaManager {

	public static void releaseMediaRecorder(MediaRecorder mMediaRecorder,
			Camera c) {
		if (mMediaRecorder != null) {
			mMediaRecorder.reset();
			mMediaRecorder.release();
			mMediaRecorder = null;
			c.lock();
		}
	}

}
