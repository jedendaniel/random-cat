package com.ddd.cat.service;

import com.ddd.cat.domain.RandomCatService;
import com.ddd.cat.domain.S3Service;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RandomCatResourceServiceTest {
    public static final String BASE_CAT_1 = "baseCat1";
    public static final String BASE_CAT_2 = "baseCat2";
    public static final String PREMIUM_CAT_1 = "premiumCat1";
    public static final String PREMIUM_CAT_2 = "premiumCat2";

    private final S3Service s3Service = mock(S3Service.class);
    private final Random random = mock(Random.class);
    private final Integer catsHistorySize = 2;
    private final RandomCatService randomCatService =
            new RandomCatService(s3Service, random, catsHistorySize);

    @Test
    void shouldInitializeAndRefreshCats() {
        when(s3Service.listCatKeys()).thenReturn(
                List.of(BASE_CAT_1, PREMIUM_CAT_1, BASE_CAT_2, PREMIUM_CAT_2));
        byte[] baseCatPic1 = {1, 11};
        byte[] baseCatPic2 = {1, 12};
        byte[] premiumCatPic1 = {2, 21};
        byte[] premiumCatPic2 = {2, 22};
        when(s3Service.getCatPicAsByteArray(BASE_CAT_1)).thenReturn(baseCatPic1);
        when(s3Service.getCatPicAsByteArray(PREMIUM_CAT_1)).thenReturn(premiumCatPic1);
        when(s3Service.getCatPicAsByteArray(BASE_CAT_2)).thenReturn(baseCatPic2);
        when(s3Service.getCatPicAsByteArray(PREMIUM_CAT_2)).thenReturn(premiumCatPic2);

        when(random.nextInt(2))
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0)
                .thenReturn(1);

        randomCatService.initializeCats();
        assertEquals(baseCatPic1, randomCatService.getBaseCatPic());
        assertEquals(premiumCatPic1, randomCatService.getPremiumCatPic());

        randomCatService.refreshCatResources();
        assertEquals(baseCatPic2, randomCatService.getBaseCatPic());
        assertEquals(premiumCatPic2, randomCatService.getPremiumCatPic());

        verify(s3Service, times(2)).listCatKeys();
        verify(s3Service, times(4)).getCatPicAsByteArray(any());
    }
}