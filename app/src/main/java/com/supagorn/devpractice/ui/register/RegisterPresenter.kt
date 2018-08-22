package com.supagorn.devpractice.ui.register

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.supagorn.devpractice.enums.RequireField
import com.supagorn.devpractice.firebase.PostManager
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.firebase.UserManager.Companion.STORAGE_PATH_PROFILE
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.register.RegisterEntity
import com.supagorn.devpractice.utils.ValidatorUtils


class RegisterPresenter constructor(private var view: RegisterContract.View) : RegisterContract.Presenter {

    private var isEditMode = false
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDatabase = FirebaseDatabase.getInstance().reference
    val storageReference = FirebaseStorage.getInstance().reference

    override fun register(entity: RegisterEntity) {
        isEditMode = false
        if (validate(entity)) {
            view.showProgressDialog()
            mAuth.createUserWithEmailAndPassword(entity.email, entity.password).addOnCompleteListener({ task ->
                view.hideProgressDialog()
                if (task.isSuccessful) {
                    val firebaseUser = task.result.user

                    UserManager.updateUserData(firebaseUser.uid, entity)
                    if (entity.imageUri != null) {
                        val username = UserManager.createUsernameWithEmail(entity.email)
                        uploadFile(entity.imageUri, username)
                    } else {
                        view.registerSuccess()
                    }
                } else {
                    view.registerFailed()
                }
            })
        }
    }

    override fun fetchUserProfile() {
        mDatabase.child("users").child(UserManager.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                view.bindUserProfile(user!!)
            }
        })
    }

    override fun fetchUserImage() {
        mDatabase.child("user-images").child(UserManager.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val userImage = dataSnapshot?.getValue(Upload::class.java)
                if (userImage?.url != null) {
                    view.bindUserImage(userImage)
                }
            }
        })
    }

    override fun editProfile(entity: RegisterEntity) {
        isEditMode = true
        if (validate(entity)) {
            UserManager.updateUserData(UserManager.uid, entity)
            PostManager.instance.changeFullNameAllPost("${entity.firstName} ${entity.lastName}")

            if (entity.imageUri != null) {
                val username = UserManager.createUsernameWithEmail(entity.email)
                uploadFile(entity.imageUri, username)
            } else {
                view.updateProfileSuccess()
            }
        }
    }

    private fun validate(entity: RegisterEntity): Boolean {
        var isValid = false
        if (entity.firstName.isNullOrEmpty()) {
            //require first name
            view.requireField(RequireField.FirstName)
        } else if (entity.lastName.isNullOrEmpty()) {
            //require last name
            view.requireField(RequireField.LastName)
        } else if (entity.email.isNullOrEmpty()) {
            //require email
            view.requireField(RequireField.Email)
        } else if (ValidatorUtils.isInValidEmail(entity.email)) {
            //email is invalid
            view.showEmailInvalid()
        } else if (!entity.mobile.isNullOrEmpty() && ValidatorUtils.isInValidMobile(entity.mobile)) {
            //mobile is invalid
            view.showMobileInvalid()
        } else if (!isEditMode) {
            if (entity.password.isNullOrEmpty()) {
                //require password
                view.requireField(RequireField.Password)
            } else if (ValidatorUtils.isInValidPassword(entity.password)) {
                //password is invalid
                view.showPasswordInvalid()
            } else if (entity.confirmPassword.isNullOrEmpty()) {
                //require confirm password
                view.requireField(RequireField.ConfirmPassword)
            } else if (entity.password != entity.confirmPassword) {
                //confirm password not match
                view.showConfirmPasswordNotMatch()
            } else {
                isValid = true
            }
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

                    if (isEditMode) {
                        view.updateProfileSuccess()
                    } else {
                        view.registerSuccess()
                    }
                })
                .addOnFailureListener({ exception ->
                    view.hideProgressDialog()
                    Log.e("Failure", exception.message)

                })
                .addOnProgressListener { taskSnapshot ->
                    //displaying the upload progress
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    view.updateProgress("Uploading ${progress.toInt()} %...")
                }
    }
}