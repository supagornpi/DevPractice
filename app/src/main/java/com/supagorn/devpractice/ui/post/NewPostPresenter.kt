package com.supagorn.devpractice.ui.post

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.firebase.UserManager.STORAGE_PATH_PROFILE
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity
import com.supagorn.devpractice.utils.ValidatorUtils


class NewPostPresenter constructor(private var view: NewPostContract.View) : NewPostContract.Presenter {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDatabase = FirebaseDatabase.getInstance().reference
    val storageReference = FirebaseStorage.getInstance().reference

    override fun post(body: String?) {
        if (validate(body)) {
            view.showProgressDialog()

        }
    }


    private fun validate(body: String?): Boolean {
        var isValid = false
        if (body.isNullOrEmpty()) {
            //require first name
            view.requireField()
        } else {
            isValid = true
        }
        return isValid
    }

    private fun uploadFile(uri: Uri, username: String) {
        view.showProgressDialog()
        //getting the storage reference
        val sRef = storageReference.child(STORAGE_PATH_PROFILE + System.currentTimeMillis())
        //adding the file to reference
        sRef.putFile(uri)
                .addOnSuccessListener({ taskSnapshot ->
                    //dismissing the progress dialog
                    view.hideProgressDialog()

                    //creating the upload object to store uploaded image details
                    val upload = Upload(username, uri.lastPathSegment, taskSnapshot.downloadUrl.toString())

                    //adding an upload to firebase database
                    UserManager.updateUserImage(upload)

                    view.postSuccess()
                })
                .addOnFailureListener({ exception ->
                    view.hideProgressDialog()
                    Log.e("Failure", exception.message)

                })
                .addOnProgressListener { taskSnapshot ->
                    //displaying the upload progress
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    view.updateProgress("Uploaded ${progress.toInt()} %...")
                }
    }
}