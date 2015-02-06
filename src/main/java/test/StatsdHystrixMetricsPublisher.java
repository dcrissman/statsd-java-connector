/*
 * Copyright 2015 Red Hat, Inc.
 * Author: Dennis Crissman
 *
 * Licensed under the GNU Lesser General Public License, version 3 or
 * any later version.
 */

package test;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherCommand;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherThreadPool;

public class StatsdHystrixMetricsPublisher extends HystrixMetricsPublisher{

    @Override
    public HystrixMetricsPublisherCommand getMetricsPublisherForCommand(final HystrixCommandKey commandKey, final HystrixCommandGroupKey commandGroupKey, final HystrixCommandMetrics metrics, final HystrixCircuitBreaker circuitBreaker, final HystrixCommandProperties properties) {
        return new HystrixMetricsPublisherCommand(){

            @Override
            public void initialize() {
                System.out.println("HystrixMetricsPublisherCommand");
            }

        };
    }

    @Override
    public HystrixMetricsPublisherThreadPool getMetricsPublisherForThreadPool(final HystrixThreadPoolKey threadPoolKey, final HystrixThreadPoolMetrics metrics, final HystrixThreadPoolProperties properties) {
        return new HystrixMetricsPublisherThreadPool(){

            @Override
            public void initialize() {
                System.out.println("HystrixMetricsPublisherThreadPool");
            }

        };
    }
}
