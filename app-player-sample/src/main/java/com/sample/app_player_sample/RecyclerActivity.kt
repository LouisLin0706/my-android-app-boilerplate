package com.sample.app_player_sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boilerplate.lib_player.core.HybridLifecyclePlayBack

class RecyclerActivity : AppCompatActivity() {

    private val hybridLifecyclePlayBack: HybridLifecyclePlayBack? = null

    companion object {
        fun getRecyclerIntent(context: Context): Intent {
            return Intent(context, RecyclerActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
    }
}