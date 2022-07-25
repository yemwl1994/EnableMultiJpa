package com.andy.commons.api;


import com.andy.commons.api.response.HealthResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apimg")
public class HealthController {

    @RequestMapping("/health")
    public HealthResponse health() {

        HealthResponse response = new HealthResponse();
        response.setStatusDescription("Done");
        response.setStatus("200");

        return response;

    }

}
