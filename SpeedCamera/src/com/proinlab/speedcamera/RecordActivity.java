package com.proinlab.speedcamera;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.proinlab.speedcamera.manager.CameraManager;
import com.proinlab.speedcamera.manager.FileManager;
import com.proinlab.speedcamera.manager.MediaManager;

// add timer 
import java.util.Timer;
import java.util.TimerTask;

// start main 
public class RecordActivity extends Activity {

	private boolean isRecording = false;

	private Camera mCamera;
	private CameraPreview mPreview;
	private MediaRecorder mMediaRecorder;

	private ImageView captureButton;

	//start onCreate
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		mCamera = CameraManager.getCameraInstance(mCamera);

		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		captureButton = (ImageView) findViewById(R.id.record);

		final TimerTask mTask = new TimerTask(){
			public void run(){
				MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
				if(prepareVideoRecorder()) mMediaRecorder.start(); 
			}
		};
	

		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isRecording) {
					mMediaRecorder.stop();
					MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);

					mCamera.lock();

					captureButton
							.setImageResource(R.drawable.device_access_camera);

					isRecording = false;
				} else {
					if (prepareVideoRecorder()) {
						mMediaRecorder.start();
						captureButton.setImageResource(R.drawable.av_stop);

						// add Timer 
						Timer mtimer = new Timer();
						mtimer.schedule(mTask, 0, 3000);

						isRecording = true;
					} else {
						MediaManager.releaseMediaRecorder(mMediaRecorder,
								mCamera);
					}
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
		CameraManager.releaseCamera(mCamera);
	}

	private boolean prepareVideoRecorder() {

		CameraManager.releaseCamera(mCamera);

		mCamera = CameraManager.getCameraInstance(mCamera);
		mMediaRecorder = new MediaRecorder();

		mCamera.unlock();
		mMediaRecorder.setCamera(mCamera);

		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

		mMediaRecorder.setProfile(CamcorderProfile
				.get(CamcorderProfile.QUALITY_720P));

		mMediaRecorder.setOutputFile(FileManager.getOutputMediaFile()
				.toString());

		mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

		try {
			mMediaRecorder.prepare();
		} catch (IllegalStateException e) {
			MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
			return false;
		} catch (IOException e) {
			MediaManager.releaseMediaRecorder(mMediaRecorder, mCamera);
			return false;
		}
		return true;
	}

}
