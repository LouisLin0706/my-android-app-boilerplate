package com.sample.app_player_sample.arch.event

/**
 * Created by Louis on 2018/10/29.
 */
sealed class UserInteractionEvent {
    object IntentTapRetry : UserInteractionEvent()
}