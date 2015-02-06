/*
 * Copyright 2015 Red Hat, Inc.
 * Author: Dennis Crissman
 *
 * Licensed under the GNU Lesser General Public License, version 3 or
 * any later version.
 */

package test;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.JvmMetricPoller;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;

public class Test {

    public static final void main(String[] args) {
        HystrixPlugins.getInstance().registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());

        MetricObserver observer = new StatsdMetricObserver("dennis.test", "localhost", 8125);

        PollScheduler.getInstance().start();

        PollRunnable registeryTask = new PollRunnable(new MonitorRegistryMetricPoller(), BasicMetricFilter.MATCH_ALL, observer);
        PollScheduler.getInstance().addPoller(registeryTask, 5, TimeUnit.SECONDS);

        PollRunnable jvmTask = new PollRunnable(new JvmMetricPoller(), BasicMetricFilter.MATCH_ALL, observer);
        PollScheduler.getInstance().addPoller(jvmTask, 5, TimeUnit.SECONDS);

        new FakeHystrixCommand().execute();

        /*        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        PollScheduler.getInstance().stop();
        System.exit(0);*/
    }

}
