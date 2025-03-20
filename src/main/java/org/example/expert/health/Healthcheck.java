package org.example.expert.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Healthcheck implements HealthIndicator {


    @Override
    public Health health() {
        int httpStatus = 200;

        if (httpStatus == HttpStatus.OK.value()) {
            return Health.up()
                    .withDetail("OtherServer", "Available")
                    .build();
        }

        return Health.down()
                .withDetail("OtherServer", "Not Available")
                .build();
    }
}
