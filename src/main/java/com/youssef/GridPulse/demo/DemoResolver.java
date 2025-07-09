package com.youssef.GridPulse.demo;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DemoResolver {

    @QueryMapping(name = "me")
    public String me() {
        return "Hello, GraphQL! API is running.";
    }
}
