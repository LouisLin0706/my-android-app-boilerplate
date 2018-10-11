package com.boilerplate.lib_kpi

import com.boilerplate.lib_kpi.entity.KPIData
import com.boilerplate.lib_kpi.entity.KPIEvent

/**
 * Created by Louis on 2018/9/18.
 */
interface KPITracker {

    fun sendEvent(@KPIEvent event: Long)

    fun <T : KPIData> getKPIData(): T


    fun <T : KPIData> create(var1: Class<T>)
}