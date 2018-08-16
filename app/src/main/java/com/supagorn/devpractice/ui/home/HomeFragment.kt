package com.supagorn.devpractice.ui.home

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.customs.adapter.PostViewHolder
import com.supagorn.devpractice.customs.view.PostView
import com.supagorn.devpractice.firebase.UserManager
import com.supagorn.devpractice.model.Upload
import com.supagorn.devpractice.model.account.User
import com.supagorn.devpractice.model.home.Post
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.ui.post.NewPostActivity
import com.supagorn.devpractice.ui.sidebar.SidebarContract
import com.supagorn.devpractice.ui.sidebar.SidebarPresenter
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_recyclerview_with_progress.*

/**
 * Created by apple on 2/18/2018 AD.
 */

class HomeFragment : AbstractFragment(), SidebarContract.View{

    private val mDatabase = FirebaseDatabase.getInstance().reference
    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<Post, PostViewHolder>
    private val presenter: SidebarContract.Presenter = SidebarPresenter(this)

    override fun setLayoutView(): Int {
        return R.layout.fragment_home
    }

    override fun setupView() {
        AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_HOME)
        bindUI()
        setTitle(R.string.title_home)
        showUserToggleWithAction()
    }

    private fun bindUI() {
        bindAction()
        initRecyclerView()

        presenter.fetchUserImage()

    }

    private fun bindAction() {
        btnNewPost.setOnClickListener {
            NewPostActivity.start()
        }
    }

    private fun initRecyclerView() {
        val postsQuery = mDatabase.child("posts")

        // Set up Layout Manager, reverse layout
        val mManager = LinearLayoutManager(context)
        mManager.reverseLayout = true
        mManager.stackFromEnd = true
        recyclerView.layoutManager = mManager

        val options = FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(postsQuery, Post::class.java)
                .build()

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int, model: Post) {
                val postRef = getRef(position)
                viewHolder.itemView as PostView

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.itemView.bind(model, View.OnClickListener {
                    // Need to write to both places the post is stored
                    val globalPostRef = mDatabase.child("posts").child(postRef.key)
                    val userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.key)

                    // Run two transactions
                    onStarClicked(globalPostRef)
                    onStarClicked(userPostRef)
                })
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostViewHolder {
                return PostViewHolder(PostView(viewGroup.context))
            }

            override fun onDataChanged() {
                super.onDataChanged()
            }
        }
        recyclerView.adapter = mFirebaseAdapter
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()
    }

    override fun bindUserProfile(user: User) {

    }

    override fun bindUserImage(upload: Upload?) {
        //load image profile in circle
        GlideLoader.loadImageCircle(
                activity!!.applicationContext,
                upload?.url, ivProfile)
    }

    private fun onStarClicked(postRef: DatabaseReference) {
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

//    private fun setSampleViewPager() {
//        val samplePagerView = SamplePagerView(activity!!)
//        //get json from asset
//        val jsonString = JsonUtils().loadJSONFromAsset(activity, "json/banner.json")
//        val samplePagerEntities = Gson().fromJson<ArrayList<SamplePagerEntity>>(jsonString, object : TypeToken<ArrayList<SamplePagerEntity>>() {
//
//        }.type)
//        //set fragmentNavigation
//        samplePagerView.setNavigation(fragmentNavigation!!)
//        samplePagerView.setSampleEntity(samplePagerEntities)
//        //set banner Height
//        layoutPagerView.layoutParams.height = ResolutionUtils.getBannerHeightFromRatio(activity)
//        layoutPagerView.addView(samplePagerView)
//    }
}
