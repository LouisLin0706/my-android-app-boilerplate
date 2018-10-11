package com.boilerplate.lib_kpi

import com.boilerplate.lib_kpi.entity.KPIData

/**
 * Created by Louis on 2018/9/20.
 */
open class KPITrackingDataCreator : KPITDataCreator<KPIData> {

    override fun <T : KPIData> create(var1: Class<T>): T {
        return var1.newInstance()
    }

}