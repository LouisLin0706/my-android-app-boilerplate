package com.boilerplate.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<T extends IView> implements IPresenter<T> {


    protected T view;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void detachView() {
        this.view = null;
        unSubscribe();
    }
}
