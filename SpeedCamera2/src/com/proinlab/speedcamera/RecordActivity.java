package com.proinlab.speedcamera;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.proinlab.speedcamera.manager.CameraManager;
import com.proinlab.speedcamera.manager.FileManager;
import com.proinlab.speedcamera.manager.MediaManager;

import java.text.SimpleDateFormat;
import java.util.Date;
// import timer 
import java.util.Timer;
import java.util.TimerTask;
import android.hardware.Camera.PictureCallback;
// import java.io
import java.io.*;

public class RecordActivity extends Activity {

	// private boolean isRecording = false;

	private Camera mCamera;
	private CameraPreview mPreview;
	private MediaRecorder mMediaRecorder;

	private ImageView captureButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		mCamera = CameraManager.getCameraInstance(mCamera);
		// mCamera = CameraManager.getCameraInstance(mCamera);

		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		captureButton = (ImageView) findViewById(R.id.record);

		/* add timer task */
		final TimerTask myTask = new TimerTask() {
			public void run() {
				
				
				mCamera.takePicture(null, null, mPicture);
				
				
			}
		};

		// MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
		//
		// if (prepareVideoRecorder())
		// mMediaRecorder.start();
		//
		// // Timer timer1 = new Timer();
		// // timer1.schedule(myTask2,3000 , 5000);
		//
		// }
		// };

		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (isRecording) {
				// mMediaRecorder.stop();
				// MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);

				// mCamera.lock();

				// captureButton.setImageResource(R.drawable.device_access_camera);

				// isRecording = false;
				// } else {
				// if (prepareVideoRecorder()) {
				// mMediaRecorder.start();
				// captureButton.setImageResource(R.drawable.av_stop);
				//
				/* add Timer */
				Timer timer = new Timer();
				timer.schedule(myTask, 0, 2000);
				//
				// isRecording = true;
				//
				// } else {
				// MediaManager.releaseMediaRecorder(mMediaRecorder,
				// mCamera);
				// }
				// }

			}
		});
	}

	// private Camera getCameraInstance() {
	// Camera camera = null;
	// try {
	// camera = Camera.open( );
	// } catch (Exception e) {
	// // cannot get camera or does not exist
	// }
	// return camera;
	// }

	PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			File pictureFile = getOutputMediaFile();
			if (pictureFile == null) {
				return;
			}
			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {
			}
			
		}
		
	};

	private static File getOutputMediaFile() {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		// File mediaFile;
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

	@Override
	protected void onPause() {
		super.onPause();
		// MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
		CameraManager.releaseCamera(mCamera);
	}

	// private boolean prepareVideoRecorder() {
	//
	// CameraManager.releaseCamera(mCamera);
	//
	// mCamera = CameraManager.getCameraInstance(mCamera);
	// mMediaRecorder = new MediaRecorder();
	//
	// mCamera.unlock();
	// mMediaRecorder.setCamera(mCamera);
	//
	// mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
	// mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
	//
	// mMediaRecorder.setProfile(CamcorderProfile
	// .get(CamcorderProfile.QUALITY_720P));
	//
	// mMediaRecorder.setOutputFile(FileManager.getOutputMediaFile()
	// .toString());
	//
	// mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());
	//
	// try {
	// mMediaRecorder.prepare();
	// } catch (IllegalStateException e) {
	// MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
	// return false;
	// } catch (IOException e) {
	// MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
	// return false;
	// }
	// return true;
	// }

}
