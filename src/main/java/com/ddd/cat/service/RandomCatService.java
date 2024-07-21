package com.ddd.cat.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomCatService {
    private final S3Service s3Service;
    private byte[] currentCatPic = new byte[0];

    public RandomCatService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public byte[] getRandomCatPic() {
        if (currentCatPic.length == 0) {
            List<String> catKeys = s3Service.listCatKeys();
            String randomCatKey = catKeys.get(new Random().nextInt(catKeys.size()));
            currentCatPic = s3Service.getCatPicAsByteArray(randomCatKey);
        }
        return currentCatPic;
    }

    public void reset() {
        currentCatPic = new byte[0];
    }
}
