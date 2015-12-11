package com.anupcowkur.mvpsample.events;

import com.anupcowkur.mvpsample.model.pojo.Post;

import java.util.List;

public class ErrorEvent {

    private Throwable e;
    public ErrorEvent(Throwable e) {
        this.e = e;
    }
}
