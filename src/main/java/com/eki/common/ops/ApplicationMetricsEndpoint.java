package com.eki.common.ops;

import org.json.simple.JSONObject;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.JSONPObject;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
@Endpoint(id = "service.count")
public class ApplicationMetricsEndpoint {

    /** The counter. */

    private Counter counter;


    /**

     * Instantiates a new application metrics endpoint.

     * @param registry the registry

     */

    public ApplicationMetricsEndpoint(MeterRegistry registry) {

        this.counter = registry.counter("service.count");

    }


    /**

     * Count service.

     *

     * @return the string

     * @throws JSONException

     */

    @ReadOperation

    public String countService()  {

        counter.increment();

  JSONObject metricData = new JSONObject();

        metricData.put("TOTAL_COUNT", counter.count());

        return metricData.toString();

    }

     


}
