package com.supagorn.devpractice.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat

import com.supagorn.devpractice.MyApplication

import java.util.ArrayList

object IntentUtils {

    fun startIntentGallery(activity: Activity, requestCode: Int) {
        if (PermissionUtils.isRequestPermissionReadExternalStorage(activity)) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            activity.startActivityForResult(Intent.createChooser(intent, "Select your image"), requestCode)
        }
    }
}
