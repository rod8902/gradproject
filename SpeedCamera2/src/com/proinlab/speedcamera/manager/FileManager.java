package com.proinlab.speedcamera.manager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

public class FileManager {

	@SuppressLint("SimpleDateFormat")
	public static File getOutputMediaFile() {

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				"MyCameraApp");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "VID_" + timeStamp + ".mp4");

		return mediaFile;
	}

}
