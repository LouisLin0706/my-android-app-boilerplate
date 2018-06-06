package com.sample.app_player_sample

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boilerplate.lib_player.component.AutoSyncControllerComponent
import com.boilerplate.lib_player.core.HybridLifecyclePlayBack
import com.boilerplate.lib_player.core.HybridPlayBack
import com.boilerplate.lib_player.core.HybridPlayBack.TYPE_ANDROID_NATIVE_PLAYBACK
import com.boilerplate.lib_player.view.HybridPlayerView

class NormalActivity : AppCompatActivity() {

    private var hybridLifecyclePlayBack: HybridLifecyclePlayBack? = null
    private val sampleUrl: Uri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Google%2B/Google%2B_%20Instant%20Upload.mp4")

    companion object {
        fun getNormalActivityIntent(context: Context): Intent {
            return Intent(context, NormalActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)
        val hybridPlayerView = findViewById<HybridPlayerView>(R.id.hybridPlayerView)
        hybridLifecyclePlayBack = HybridPlayBack.createInstance(this, TYPE_ANDROID_NATIVE_PLAYBACK)
        hybridLifecyclePlayBack?.setHybridPlayerView(hybridPlayerView)
        hybridLifecyclePlayBack?.setAutoSyncControllerComponent(AutoSyncControllerComponent())
        play()
    }

    private fun play() {
        hybridLifecyclePlayBack?.preSetDataSource(sampleUrl)
    }

    override fun onPause() {
        super.onPause()
        hybridLifecyclePlayBack?.lifecycleIsPause()
    }

    override fun onResume() {
        super.onResume()
        hybridLifecyclePlayBack?.lifecycleIsResume()
    }

    override fun onStart() {
        super.onStart()
        hybridLifecyclePlayBack?.lifecycleIsStart()
    }

    override fun onStop() {
        super.onStop()
        hybridLifecyclePlayBack?.lifecycleIsStop()
    }
}