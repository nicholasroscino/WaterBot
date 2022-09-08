package com.ncoding.infrastructure.wisp;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.cron.CronSchedule;
import com.ncoding.core.ports.JobScheduler;

public class WispSchedulerWrapper implements JobScheduler {

    private final Scheduler scheduler;

    public WispSchedulerWrapper() {
        this.scheduler = new Scheduler();
    }

    @Override
    public void scheduleEveryHour(Runnable runnable) {
        //scheduler.schedule(runnable, CronSchedule.parseQuartzCron("0 0 0/1 1/1 * ? *"));
        scheduler.schedule(runnable, CronSchedule.parseQuartzCron("* * * * * ? *")); //each second test
    }
}




