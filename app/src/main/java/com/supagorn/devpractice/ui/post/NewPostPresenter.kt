package com.supagorn.devpractice.ui.post

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.firebase.UserManager.Companion.STORAGE_PATH_PROFILE
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.home.Post
import java.util.*


class NewPostPresenter constructor(private var view: NewPostContract.View) : NewPostContract.Presenter {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDatabase = FirebaseDatabase.getInstance().reference
    val storageReference = FirebaseStorage.getInstance().reference

    override fun submitPost(body: String) {
        val userId = UserManager.uid

        if (validate(body)) {
            view.hideProgressDialog()
            // Disable button so there are no multi-posts
            mDatabase.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    view.hideProgressDialog()
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user == null) {
                        view.postFailed()
                    } else {
                        writeNewPost(userId, user.username, body)
                    }
                    view.postSuccess()
//                    finish()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    view.hideProgressDialog()
                    view.postFailed()
                }
            })
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

    private fun writeNewPost(userId: String, username: String, body: String) {
        // Create new submitPost at /user-posts/$userid/$postid
        // and at /posts/$postid simultaneously
        val key = mDatabase.child("posts").push().key
        val post = Post(userId, username, body)
        val postValues = post.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/posts/$key"] = postValues
        childUpdates["/user-posts/$userId/$key"] = postValues

        mDatabase.updateChildren(childUpdates)
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