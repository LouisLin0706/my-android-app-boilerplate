package com.boilerplate.base.mvp;


public interface IPresenter<T extends IView> {

    void attachView(T view);

    void detachView();
}
