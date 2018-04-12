/*
 *  Copyright (C) 2018 Tse Ju Lin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.boilerplate.mvvm_sample.ui.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2018/4/9.
 */
@Module
public class MainActivityModel {

    @Provides
    MainViewModel provideMainViewModel() {
        return new MainViewModel();
    }

//    @Provides
//    MainPagerAdapter provideFeedPagerAdapter(MainActivity activity) {
//        return new MainPagerAdapter(activity.getSupportFragmentManager());
//    }
}
