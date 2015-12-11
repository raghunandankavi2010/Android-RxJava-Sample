package com.anupcowkur.mvpsample.ui.presenters;

import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Raghunandan on 08-11-2015.
 */
public  class OnSubscribeRefreshingCache<T> implements Observable.OnSubscribe<T> {

    private final AtomicBoolean refresh = new AtomicBoolean(true);
    private final Observable<T> source;
    private volatile Observable<T> current;

    public OnSubscribeRefreshingCache(Observable<T> source) {
        this.source = source;
        this.current = source;
    }

    public void reset() {
        refresh.set(true);
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        if (refresh.compareAndSet(true, false)) {
            current = source.cache();
        }
        current.unsafeSubscribe(subscriber);
    }

}
