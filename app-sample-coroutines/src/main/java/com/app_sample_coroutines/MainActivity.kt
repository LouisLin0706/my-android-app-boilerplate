package com.app_sample_coroutines

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.app_sample_coroutines.api.taipei.TaipeiAPIReop
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    val taipeiAPIReop = TaipeiAPIReop()
    var startTime: Long = 0
    var endTime: Long = 0
    private val e = Executors.newScheduledThreadPool(100).asCoroutineDispatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var job: Job? = null
        start.setOnClickListener {
            startActivity(Intent(this, MainActivityB::class.java))
//            Log.i("TAG", " Start Click ")
//            job = GlobalScope.launch(Dispatchers.IO) {
//                delay(5000).apply {
//                    Log.i("TAG", " A ")
//                }
//
//                delay(5000).apply {
//                    Log.i("TAG", " B ")
//                }
//
//                delay(5000).apply {
//                    Log.i("TAG", " C ")
//                }
//            }
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

    @Synchronized
    fun calcTime(tag: String) {
        endTime = System.currentTimeMillis()
        Log.i("TAG", " Tag = $tag Time = ${endTime - startTime}")
    }
}
