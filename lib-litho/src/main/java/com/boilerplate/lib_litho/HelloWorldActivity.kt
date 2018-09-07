package com.boilerplate.lib_litho

import android.app.Activity
import android.os.Bundle
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.Text

/**
 * Created by Louis on 2018/9/7.
 */
class HelloWorldActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val context = ComponentContext(this);
        val component =
                Text.create(context)
                        .text("test_louis")
                        .textSizeDip(50f)
                        .build()
        setContentView(LithoView.create(context, component));
    }
}