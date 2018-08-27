package com.sample.app_player_sample

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.widget.TextView
import com.boilerplate.lib_player.component.AutoSyncControllerComponent
import com.boilerplate.lib_player.core.HybridLifecyclePlayBack
import com.boilerplate.lib_player.core.HybridPlayBack
import com.boilerplate.lib_player.core.HybridPlayBack.TYPE_ANDROID_NATIVE_PLAYBACK
import com.boilerplate.lib_player.core.HybridPlayBack.TYPE_EXO_PLAYBACK
import com.boilerplate.lib_player.view.HybridPlayerView

class NormalActivity : AppCompatActivity() {

    private var hybridLifecyclePlayBack: HybridLifecyclePlayBack? = null
    //https://d2dflkddhtkcl4.cloudfront.net/9774/1/v1/9774-eps-1_d_SD.m3u8
    //d2dflkddhtkcl4.cloudfront.net
    private val sampleUrl: Uri = Uri.parse("https://chococdn.chocolabs.com/9774/1/v1/9774-eps-1_d_SD.m3u8")

    companion object {
        fun getNormalActivityIntent(context: Context): Intent {
            return Intent(context, NormalActivity::class.java)
        }
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)
        val hybridPlayerView = findViewById<HybridPlayerView>(R.id.hybridPlayerView)
        hybridLifecyclePlayBack = HybridPlayBack.createInstance(this, TYPE_EXO_PLAYBACK)
        hybridLifecyclePlayBack?.setHybridPlayerView(hybridPlayerView)
        hybridLifecyclePlayBack?.autoSyncControllerComponent = AutoSyncControllerComponent()
        findViewById<SwitchCompat>(R.id.switchCompat_player).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val hybridPlayBack = HybridPlayBack.createInstance(this, TYPE_EXO_PLAYBACK)
                hybridLifecyclePlayBack = hybridPlayBack.transformCorePlayer(hybridLifecyclePlayBack)
                findViewById<TextView>(R.id.textView_current_player).text = "ExoPlayer"
            } else {
                val hybridPlayBack = HybridPlayBack.createInstance(this, TYPE_ANDROID_NATIVE_PLAYBACK)
                hybridLifecyclePlayBack = hybridPlayBack.transformCorePlayer(hybridLifecyclePlayBack)
                findViewById<TextView>(R.id.textView_current_player).text = "MediaPlayer"
            }
        }
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