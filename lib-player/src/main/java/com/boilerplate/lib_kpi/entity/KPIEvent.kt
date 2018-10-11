package com.boilerplate.lib_kpi.entity

import android.support.annotation.IntDef

/**
 * Created by Louis on 2018/9/19.
 */
@IntDef(SET_SOURCE, IS_SOURCE_READY, IS_BUFFER_START, IS_BUFFER_END, IS_ERROR, PREPARE_DATA)
@Retention(AnnotationRetention.SOURCE)
annotation class KPIEvent


const val PREPARE_DATA = 6L
const val SET_SOURCE = 0L
const val IS_SOURCE_READY = 1L
const val IS_BUFFER_START = 2L
const val IS_BUFFER_END = 3L
const val IS_ERROR = 4L