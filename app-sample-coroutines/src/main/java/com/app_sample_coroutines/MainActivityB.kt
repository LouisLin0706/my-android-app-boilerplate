package com.app_sample_coroutines

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*

class MainActivityB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var job: Job? = null
        start.setOnClickListener {
            Log.i("TAG", " Start Click ")
            job = GlobalScope.launch(Dispatchers.IO) {
                delay(5000).apply {
                    Log.i("TAG", " A ")
                    withContext(Dispatchers.Main){
                        Log.i("TAG" , " ===== TimmyDev ===== " + " Main " )
                        start.text = "ABC"
                    }
                }

                delay(5000).apply {
                    Log.i("TAG", " B ")
                }

                delay(5000).apply {
                    Log.i("TAG", " C ")
                }
            }
        }

        cancel.setOnClickListener {
            Log.i("TAG", " Cancel Click ")
            job?.cancel()
        }
//        start.setOnClickListener {
//            repeat(100) {
//                startTime = System.currentTimeMillis()
//                GlobalScope.launch(e) {
//                    val jobs = async {
//                        taipeiAPIReop.fetchCarPark()
//                    }
//                    jobs.await()
//                    Log.i("TAG", " Thread Nmae ${Thread.currentThread().name}")
//                    calcTime(it.toString())
//                }
//            }
//        }
    }
}
