package test;

import java.util.List;

import com.netflix.servo.Metric;
import com.netflix.servo.publish.BaseMetricObserver;
import com.netflix.servo.publish.graphite.BasicGraphiteNamingConvention;
import com.netflix.servo.publish.graphite.GraphiteNamingConvention;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.StatsDClientErrorHandler;

public class StatsdMetricObserver extends BaseMetricObserver{

    private final String prefix;
    private final String host;
    private final int port;
    private final GraphiteNamingConvention namingConvention;

    public StatsdMetricObserver(String prefix, String host, int port){
        this(prefix, host, port, new BasicGraphiteNamingConvention());
    }

    public StatsdMetricObserver(String prefix, String host, int port, GraphiteNamingConvention namingConvention) {
        super("StatsdMetricObserver." + prefix);
        this.prefix = prefix;
        this.host = host;
        this.port = port;
        this.namingConvention = namingConvention;
    }

    @Override
    public void updateImpl(List<Metric> metrics) {
        StatsDClient statsd = new NonBlockingStatsDClient(prefix, host, port, new ErrorHandler());
        System.out.println("send data");

        for (Metric metric : metrics) {
            String aspect = namingConvention.getName(metric);

            if(metric.hasNumberValue()){
                statsd.recordGaugeValue(aspect, metric.getNumberValue().longValue());
            }
            else {
                statsd.recordSetEvent(aspect, metric.getValue().toString());
            }

            statsd.recordExecutionTime(aspect, metric.getTimestamp() / 1000);
        }

        statsd.stop();
    }

    private static class ErrorHandler implements StatsDClientErrorHandler{

        @Override
        public void handle(Exception exception) {
            exception.printStackTrace();
        }

    }

}
