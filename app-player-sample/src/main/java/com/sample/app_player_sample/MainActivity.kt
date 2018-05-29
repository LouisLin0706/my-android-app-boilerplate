package com.sample.app_player_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boilerplate.lib_player.core.HybridLifecyclePlayBack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val hybridLifecyclePlayBack: HybridLifecyclePlayBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appCompatButton_normal.setOnClickListener({
            // open normal
            startActivity(NormalActivity.getNormalActivityIntent(this))

        })
        appCompatButton_recyclerView.setOnClickListener({
            // open recyclerView
            startActivity(RecyclerActivity.getRecyclerIntent(this))
        })
    }
}
