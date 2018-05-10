package com.boilerplate.lib_player.entity;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by Louis on 2018/4/16.
 */
@AutoValue
public abstract class PlayInfo {

    abstract Uri url();

    abstract String overrideExtension();

    abstract int currentPosition();

    @NonNull
    public static Builder builder() {
        return new AutoValue_PlayInfo.Builder();
    }

    public static TypeAdapter<PlayInfo> typeAdapter(@NonNull final Gson gson) {
        return new AutoValue_PlayInfo.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    interface Builder {

        Builder url(final Uri url);

        Builder overrideExtension(final String overrideExtension);

        Builder currentPosition(final int currentPosition);

        PlayInfo build();
    }
}
