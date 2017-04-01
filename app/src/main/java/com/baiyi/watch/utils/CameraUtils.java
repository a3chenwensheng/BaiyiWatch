package com.baiyi.watch.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class CameraUtils {

	public static final String IMAGE_UNSPECIFIED = "image/*";

	public static void startPhotoZoom(Activity activity, Uri uri, int requestCode) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 5);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, requestCode);
	}

	
	public static void openCamera(Activity activity, File file, int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, requestCode);
	}

	public static void openGallery(Activity activity, int requestCode) {
		// 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        activity.startActivityForResult(intent, requestCode);
		
	}
}
