package com.boilerplate.lib_kpi.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Louis on 2018/9/18.
 *
 * spec
dramaId:9904,
Device:iphone 6,
Version:8.25.0,
App:"iOS",
Location:"",
Carrier:"",
user_network_type:"3G/Wifi/4G",
IP:???,
user_network_speed: 230000,#not sure
video_duration:21600,
video_startup_time:0.4,
video_stall_count:0.04,
video_stall_duration:0.6,
video_stream_quality:2
video_error_count:4
 */
open class KPIData {
    @SerializedName("dramaId")
    public var dramaId: Int = 0

    @SerializedName("Device")
    public var device: String = ""

    @SerializedName("Version")
    public var version: String = ""

    @SerializedName("App")
    public var app: String = ""

    @SerializedName("Location")
    public var location: String = ""

    @SerializedName("Carrier")
    public var carrier: String = ""

    @SerializedName("user_network_type")
    public var userNewWorkType: String = ""

    @SerializedName("user_network_speed")
    public var userNewWorkSpeed: Long = 0

    @SerializedName("video_duration")
    public var videoDuration: Long = 0

    @SerializedName("video_startup_time")
    public var videoStartUpTime: Double = 0.0

    @SerializedName("video_stall_count")
    public var videoStallCount: Double = 0.0

    @SerializedName("video_stall_duration")
    public var videoStallDuration: Double = 0.0

    @SerializedName("video_stream_quality")
    public var videoSteamQuality: Long = 0

    @SerializedName("video_error_count")
    public var videoErrorCount: Int = 0

    @Expose
    public var videoStallTotalTime: Long = 0
}