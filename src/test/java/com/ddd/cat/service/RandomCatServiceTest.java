package com.ddd.cat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomCatServiceTest {
    @Mock
    private S3Service s3Service;
    @InjectMocks
    private RandomCatService randomCatService;

    @Test
    void shouldGenerateRandomCatOnceWhenThereIsNoReset() {
        when(s3Service.listCatKeys()).thenReturn(List.of("catKey"));
        byte[] mockedCatPic = {1, 2, 3};
        when(s3Service.getCatPicAsByteArray("catKey")).thenReturn(mockedCatPic);

        assertEquals(mockedCatPic, randomCatService.getRandomCatPic());
        randomCatService.getRandomCatPic();

        verify(s3Service, times(1)).listCatKeys();
        verify(s3Service, times(1)).getCatPicAsByteArray(any());
    }

    @Test
    void shouldGenerateRandomCatTwiceWhenResetHappened() {
        when(s3Service.listCatKeys()).thenReturn(List.of("catKey"));
        byte[] mockedCatPic = {1, 2, 3};
        when(s3Service.getCatPicAsByteArray("catKey")).thenReturn(mockedCatPic);

        assertEquals(mockedCatPic, randomCatService.getRandomCatPic());
        randomCatService.reset();
        randomCatService.getRandomCatPic();

        verify(s3Service, times(2)).listCatKeys();
        verify(s3Service, times(2)).getCatPicAsByteArray(any());
    }
}