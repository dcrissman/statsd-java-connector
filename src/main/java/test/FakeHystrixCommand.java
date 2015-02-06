/*
 * Copyright 2015 Red Hat, Inc.
 * Author: Dennis Crissman
 *
 * Licensed under the GNU Lesser General Public License, version 3 or
 * any later version.
 */

package test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class FakeHystrixCommand extends HystrixCommand<String> {

    public static final String GROUPKEY = "fake";

    public FakeHystrixCommand() {
        this("fakeCommand");
    }

    public FakeHystrixCommand(String commandKey) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(GROUPKEY)).
                andCommandKey(HystrixCommandKey.Factory.asKey(GROUPKEY + ":" + commandKey)));
    }

    @Override
    protected String run() throws Exception {
        return "hello";
    }

}
