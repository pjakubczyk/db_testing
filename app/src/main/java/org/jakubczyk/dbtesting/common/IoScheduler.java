package org.jakubczyk.dbtesting.common;

import rx.Scheduler;

public class IoScheduler {
    protected Scheduler scheduler;

    public IoScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Scheduler get() {
        return scheduler;
    }
}