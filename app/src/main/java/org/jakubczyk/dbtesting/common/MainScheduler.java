package org.jakubczyk.dbtesting.common;

import rx.Scheduler;

public class MainScheduler {

    protected Scheduler scheduler;

    public MainScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Scheduler get() {
        return scheduler;
    }
}