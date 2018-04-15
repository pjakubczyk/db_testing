package org.jakubczyk.dbtesting

import org.jakubczyk.dbtesting.common.IoScheduler
import org.jakubczyk.dbtesting.common.MainScheduler
import rx.Scheduler
import rx.schedulers.TestScheduler
import spock.lang.Specification

class MySpecification extends Specification {

    def testIoScheduler = new TestIoScheduler(new TestScheduler());
    def testMainScheduler = new TestMainScheduler(new TestScheduler());

    def triggerScheduler() {
        testIoScheduler.get().triggerActions()
        testMainScheduler.get().triggerActions()
    }

    static class TestIoScheduler extends IoScheduler {
        TestIoScheduler(Scheduler scheduler) {
            super(scheduler)
        }

        public TestScheduler get() {
            return scheduler;
        }
    }

    static class TestMainScheduler extends MainScheduler {
        TestMainScheduler(Scheduler scheduler) {
            super(scheduler)
        }

        public TestScheduler get() {
            return scheduler;
        }
    }
}