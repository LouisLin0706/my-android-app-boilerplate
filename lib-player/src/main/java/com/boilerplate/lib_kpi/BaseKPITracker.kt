package com.boilerplate.lib_kpi

import com.boilerplate.lib_kpi.entity.*

/**
 * Created by Louis on 2018/9/18.
 */
abstract class BaseKPITracker<T : KPIData>(var1: Class<T>) : KPITracker {
    private var kpiData: KPIData? = null
    init {
        var1.newInstance()
    }

    override fun <T : KPIData> create(var1: Class<T>) {
        kpiData = var1.newInstance()
    }

    private var cSBufferTime = 0L

    abstract fun getDuration(): Long

    abstract fun getEffectiveTimeMillis(): Long

    override fun sendEvent(@KPIEvent event: Long) {
        when (event) {

            SET_SOURCE -> {

            }

            IS_BUFFER_END -> {
                addStallTime()
            }

            IS_BUFFER_START -> {
                addBufferCount()
                cSBufferTime = System.currentTimeMillis()
            }

            IS_SOURCE_READY -> {
                kpiData?.videoDuration = getDuration()
            }

            IS_ERROR -> {
                addErrorCount()
            }

            else -> {

            }

        }
    }

    private fun addStallTime() {
        kpiData?.videoStallTotalTime += System.currentTimeMillis() - cSBufferTime
    }

    private fun addErrorCount() {
        kpiData?.videoErrorCount += 1
    }

    private fun addBufferCount() {
        kpiData?.videoStallCount += 1
    }
}