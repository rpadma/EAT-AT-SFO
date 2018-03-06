package com.mandb.rohitpadma.eat_at_sfo.base;

/**
 * Created by rohitpadma on 3/6/18.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void dettach();
}
