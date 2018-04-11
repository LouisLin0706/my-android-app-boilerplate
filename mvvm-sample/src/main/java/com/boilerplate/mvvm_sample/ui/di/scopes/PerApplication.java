package com.boilerplate.mvvm_sample.ui.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Louis on 2018/1/21.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {}