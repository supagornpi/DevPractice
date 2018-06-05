package com.supagorn.devpractice.ui.video

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.google.android.youtube.player.YouTubePlayer
import com.supagorn.devpractice.BuildConfig
import com.supagorn.devpractice.R
import com.supagorn.devpractice.constants.AppEventsConstants
import com.supagorn.devpractice.customs.YouTubeFailureRecoveryActivity
import com.supagorn.devpractice.singleton.AppEventLogger
import com.supagorn.devpractice.utils.ResolutionUtils
import com.supagorn.devpractice.utils.YoutubeLinkUtils
import kotlinx.android.synthetic.main.activity_video_full_screen.*
import kotlinx.android.synthetic.main.layout_action_bar.*

class VideoFullScreenActivity : YouTubeFailureRecoveryActivity(), YouTubePlayer.OnFullscreenListener {

    private var fullscreen = false
    private var player: YouTubePlayer? = null
    private var isPlaying = false

    companion object {
        private const val VIDEO_LINK = "https://youtu.be/YE7VzlLtp-4"

        fun start(context: Context) {
            AppEventLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEW_VIDEO)
            val intent = Intent(context, VideoFullScreenActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        setContentView(R.layout.activity_video_full_screen)
        setupView()
    }

    private fun setupView() {
        setTitle("Video")
        showBackButton()
        youtubePlayer.layoutParams.height = ResolutionUtils.getBannerHeightFromRatio(applicationContext)
        youtubePlayer.initialize(BuildConfig.GOOGLE_API_KEY, this)
        doLayout()
    }

    private fun setTitle(title: String) {
        tvTitle.text = title
    }

    private fun showBackButton() {
        if (btnIconLeft != null) {
            btnIconLeft.setImageResource(R.drawable.ic_previous)
            btnIconLeft.visibility = View.VISIBLE
            btnIconLeft.setOnClickListener({
                onBackPressed()
            })
        }
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean) {
        this.player = player
        this.player?.setOnFullscreenListener(this)

        setFullScreenFlag()

        if (!wasRestored) {
            player.cueVideo(YoutubeLinkUtils.getYoutubeId(VIDEO_LINK))
            // Hiding player controls
//            player?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
            isPlaying = true
        }
    }

    override fun onFullscreen(isFullscreen: Boolean) {
        fullscreen = isFullscreen
        doLayout()
    }

    override fun getYouTubePlayerProvider(): YouTubePlayer.Provider = youtubePlayer

    private fun setFullScreenFlag() {
        // Specify that we want to handle fullscreen behavior ourselves.
        this.player?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)

        //set full screen flag
        var controlFlags = this.player?.fullscreenControlFlags
        controlFlags = controlFlags?.or(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE)
        this.player?.fullscreenControlFlags = controlFlags!!
    }

    private fun doLayout() {
        val playerParams = youtubePlayer.layoutParams as LinearLayout.LayoutParams
        if (fullscreen) {
            // When in fullscreen, the visibility of all other views than the player should be set to
            // GONE and the player should be laid out across the whole screen.
            playerParams.width = LinearLayout.LayoutParams.MATCH_PARENT
            playerParams.height = LinearLayout.LayoutParams.MATCH_PARENT

            otherViews.visibility = View.GONE
            actionbar.visibility = View.GONE
        } else {
            // This layout is up to you - this is just a simple example (vertically stacked boxes in
            // portrait, horizontally stacked in landscape).
            otherViews.visibility = View.VISIBLE
            actionbar.visibility = View.VISIBLE
            val otherViewsParams = otherViews.layoutParams
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                otherViewsParams.width = 0
                playerParams.width = otherViewsParams.width
                playerParams.height = WRAP_CONTENT
                otherViewsParams.height = MATCH_PARENT
                playerParams.weight = 1f
                baseLayout.orientation = LinearLayout.HORIZONTAL
            } else {
                otherViewsParams.width = MATCH_PARENT
                playerParams.width = otherViewsParams.width
                playerParams.height = WRAP_CONTENT
                playerParams.weight = 0f
                otherViewsParams.height = 0
                baseLayout.orientation = LinearLayout.VERTICAL
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        doLayout()
    }
}
