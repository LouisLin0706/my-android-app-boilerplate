package com.boilerplate.lib_player.extension;

/**
 * Created by Louis on 2018/5/24.
 */

public abstract class OverrideExtensionAdapter implements OverrideExtensionListener {

    @Override
    public String getAuthorizationHeader() {
        return "Authorization";
    }

    @Override
    public String getAuthorizationToken() {
        return "";
    }

    @Override
    public String getOverrideSourceType() {
        return "";
    }
}
