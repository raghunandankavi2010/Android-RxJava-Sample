package com.raghunandan.mvpsample.events;

public class ErrorEvent {

    private Throwable e;
    public ErrorEvent(Throwable e) {
        this.e = e;
    }
}
