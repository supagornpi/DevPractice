package com.supagorn.devpractice.firebase

import android.util.Log
import com.google.firebase.database.*
import com.supagorn.devpractice.model.home.Post
import java.util.HashMap

class PostManager {

    private object Holder {
        val INSTANCE = PostManager()
    }

    companion object {
        val instance: PostManager by lazy { Holder.INSTANCE }

        private val mDatabase = FirebaseDatabase.getInstance().reference

    }

    fun getGlobalPostRef(key: String): DatabaseReference {
        return mDatabase.child("posts").child(key)
    }

    fun getUserPostRef(uid: String, key: String): DatabaseReference {
        return mDatabase.child("user-posts").child(uid).child(key)
    }

    fun writeNewPost(userId: String, fullName: String, username: String, body: String) {
        // Create new submitPost at /user-posts/$userid/$postid
        // and at /posts/$postid simultaneously
        val key = mDatabase.child("posts").push().key
        val post = Post(key, userId, fullName, username, body)
        val postValues = post.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/posts/$key"] = postValues
        childUpdates["/user-posts/$userId/$key"] = postValues

        mDatabase.updateChildren(childUpdates)
    }

    fun removePost(uid: String, key: String) {
        //remove post
        getGlobalPostRef(key).also {
            it.removeValue()
        }
        getUserPostRef(uid, key).also {
            it.removeValue()
        }
    }

    fun onStarClicked(postRef: DatabaseReference) {
        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val p = mutableData.getValue(Post::class.java)
                        ?: return Transaction.success(mutableData)

                if (p.likes.containsKey(UserManager.uid)) {
                    // Unstar the post and remove self from likes
                    p.likeCount = p.likeCount - 1
                    p.likes.remove(UserManager.uid)
                } else {
                    // Star the post and add self to likes
                    p.likeCount = p.likeCount + 1
                    p.likes[UserManager.uid] = true
                }

                // Set value and report transaction success
                mutableData.value = p
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                Log.d("postTransaction", "onComplete:" + dataSnapshot?.key)
            }
        })
    }

    fun changeFullNameAllPost(newName: String) {
        val userPosts = mDatabase.child("user-posts").child(UserManager.uid)
        userPosts.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.children.toMutableList()
                data.let {
                    it.listIterator().forEach { item ->
                        //change full name at user-posts
                        changeFullName(newName, item.ref)
                        //change full name at posts
                        val globalPosts = mDatabase.child("posts").child(item.key!!)
                        changeFullName(newName, globalPosts.ref)
                    }
                }
            }
        })
    }

    fun getPost(key: String): DatabaseReference {
        return mDatabase.child("posts").child(key)
    }

    fun getAllPost(): DatabaseReference {
        return mDatabase.child("posts")
    }

    fun editPostAtAll(newMessage: String, postKey: String) {
        editPost(newMessage, getUserPostRef(UserManager.uid, postKey))
        editPost(newMessage, getGlobalPostRef(postKey))
    }

    private fun editPost(newMessage: String, postRef: DatabaseReference) {
        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val p = mutableData.getValue(Post::class.java)
                        ?: return Transaction.success(mutableData)
                p.body = newMessage
                // Set value and report transaction success
                mutableData.value = p
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                Log.d("postTransaction", "onComplete:" + dataSnapshot?.key)
            }
        })
    }

    private fun changeFullName(newName: String, postRef: DatabaseReference) {
        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val p = mutableData.getValue(Post::class.java)
                        ?: return Transaction.success(mutableData)
                p.fullName = newName
                // Set value and report transaction success
                mutableData.value = p
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                Log.d("postTransaction", "onComplete:" + dataSnapshot?.key)
            }
        })
    }
}