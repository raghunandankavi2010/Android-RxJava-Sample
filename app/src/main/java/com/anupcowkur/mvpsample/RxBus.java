package com.anupcowkur.mvpsample;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Raghunandan on 12-12-2015.
 */
public class RxBus {

    private static RxBus bus = new RxBus( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private RxBus(){ }

    /* Static 'instance' method */
    public static RxBus getInstance( ) {
        return bus;
    }

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }

}