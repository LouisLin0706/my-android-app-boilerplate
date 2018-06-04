package com.sample.app_player_sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boilerplate.lib_player.core.HybridLifecyclePlayBack
import com.boilerplate.lib_player.core.HybridPlayBack

class RecyclerActivity : AppCompatActivity() {

    private var hybridLifecyclePlayBack: HybridLifecyclePlayBack? = null

    companion object {
        fun getRecyclerIntent(context: Context): Intent {
            return Intent(context, RecyclerActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        hybridLifecyclePlayBack = HybridPlayBack.createInstance(this)
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