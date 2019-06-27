package com.supagorn.devpractice.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.customs.adapter.kotlin.FlexibleAdapter
import com.supagorn.devpractice.customs.view.PostView
import com.supagorn.devpractice.firebase.PostManager
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
import java.util.HashMap
import kotlin.collections.ArrayList

/**
 * Created by apple on 2/18/2018 AD.
 */

class HomeFragment : AbstractFragment(), SidebarContract.View {

    //    private val mDatabase = FirebaseDatabase.getInstance().reference
//    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<Post, PostViewHolder>
    private val presenter: SidebarContract.Presenter = SidebarPresenter(this)

    private lateinit var adapter: FlexibleAdapter<Post>

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
//        initRecyclerView()
        initPostRecyclerView()
        initPullToRefresh()

        presenter.fetchUserImage()

        getPosts()

    }

    private fun bindAction() {
        btnNewPost.setOnClickListener {
            NewPostActivity.start()
        }
    }

//    private fun initRecyclerView() {
//        val postsQuery = mDatabase.child("posts")
//
//        // Set up Layout Manager, reverse layout
//        val mManager = LinearLayoutManager(context)
//        mManager.reverseLayout = true
//        mManager.stackFromEnd = true
//        recyclerView.layoutManager = mManager
//
//        val options = FirebaseRecyclerOptions.Builder<Post>()
//                .setQuery(postsQuery, Post::class.java)
//                .build()
//
//        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
//            override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int, model: Post) {
//                val postRef = getRef(position)
//                viewHolder.itemView as PostView
//
//                // Bind Post to ViewHolder, setting OnClickListener for the star button
//                viewHolder.itemView.bind(model, postRef)
//            }
//
//            override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostViewHolder {
//                return PostViewHolder(PostView(viewGroup.context))
//            }
//
//            override fun onDataChanged() {
//                super.onDataChanged()
//            }
//        }
////        recyclerView.adapter = mFirebaseAdapter
//    }

    private fun initPostRecyclerView() {
        val mManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mManager

        adapter = FlexibleAdapter(object : FlexibleAdapter.OnBindViewListener {
            override fun <T> onBindViewHolder(item: T, itemView: View, viewType: Int, position: Int) {
                itemView as PostView

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                itemView.bind(item as Post, null)
            }

            override fun onCreateView(parent: ViewGroup, viewType: Int): View {
                return PostView(context!!)
            }
        }, object : FlexibleAdapter.OnDiffCallback {
            override fun <T> areItemsTheSame(oldItem: T, newItem: T): Boolean {
                oldItem as Post
                newItem as Post
                return oldItem.id == newItem.id
            }

            override fun <T> areContentsTheSame(oldItem: T, newItem: T): Boolean {
                oldItem as Post
                newItem as Post
                return oldItem.body == newItem.body && oldItem.timestamp == newItem.timestamp
            }
        })

        recyclerView.adapter = adapter
    }

    private fun initPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            getPosts()
            //mFirebaseAdapter.onNewData(DataGenerator.getUpdatedData())
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getPosts() {
        PostManager.instance.getAllPost().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val objectsGTypeInd = object : GenericTypeIndicator<HashMap<String, Post>>() {

                }
                val objectHashMap = dataSnapshot.getValue<HashMap<String, Post>>(objectsGTypeInd)
                        ?: return
                val posts = ArrayList<Post>(objectHashMap.values)
                adapter.submitList(posts)
            }
        })
    }

//    override fun onStart() {
//        super.onStart()
//        mFirebaseAdapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mFirebaseAdapter.stopListening()
//    }

    override fun bindUserProfile(user: User) {

    }

    override fun bindUserImage(upload: Upload?) {
        //load image profile in circle
        GlideLoader.loadImageCircle(
                activity!!.applicationContext,
                upload?.url, ivProfile)
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
