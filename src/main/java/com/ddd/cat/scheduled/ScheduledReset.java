package com.ddd.cat.scheduled;

import com.ddd.cat.service.RandomCatService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledReset {
    private final RandomCatService randomCatService;

    public ScheduledReset(RandomCatService randomCatService) {
        this.randomCatService = randomCatService;
    }

    @Scheduled(cron = "${scheduled.reset:0 0 0 * * *}")
    public void resetCatPic() {
        randomCatService.reset();
    }
}
