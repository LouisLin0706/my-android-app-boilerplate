package com.boilerplate.lib_kpi

import com.boilerplate.lib_kpi.entity.KPIData

/**
 * Created by Louis on 2018/9/20.
 */
interface KPITDataCreator<P : KPIData> {

    fun <T : P > create(var1: Class<T>): T
}