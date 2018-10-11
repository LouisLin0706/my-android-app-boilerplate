package com.boilerplate.lib_kpi

import com.boilerplate.lib_kpi.entity.KPIData
import com.boilerplate.lib_kpi.entity.KPIEvent

/**
 * Created by Louis on 2018/9/18.
 * 提供此KPI必須提供的資訊
 * 其餘客製化資訊由外部自己處理
 */
interface OnKPITrackerParameterListener<T : KPIData> {

    public fun getDuration()

    public fun getEffectiveTimeMillis()

    public fun OnStateChange(data: T, @KPIEvent event: Long)

}