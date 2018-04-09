package com.boilerplate.base.mvp;

public interface ILoadingView extends IView{
    void showLoading();

    void completeLoading();

    void showMessage(String msg);
}
