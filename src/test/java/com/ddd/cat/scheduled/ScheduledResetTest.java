package com.ddd.cat.scheduled;

import com.ddd.cat.service.RandomCatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ScheduledResetTest {
    @Mock
    private RandomCatService randomCatService;
    @InjectMocks
    private ScheduledReset scheduledReset;

    @Test
    void shouldResetCurrentCat() {
        scheduledReset.resetCatPic();
        verify(randomCatService).reset();
    }
}