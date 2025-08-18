package com.ddd.cat.domain;

import com.ddd.cat.infra.FifoFixedSizedQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Queue;
import java.util.Random;

@Service
public class RandomCatService {
    private final S3Service s3Service;
    private final Random random;
    private final Queue<String> catsHistory;
    private RandomCatResource baseCatResource;
    private RandomCatResource premiumCatResource;

    public RandomCatService(S3Service s3Service, Random random,
                            @Value("${randomCat.historySize}") Integer catsHistorySize) {
        this.s3Service = s3Service;
        this.random = random;
        this.catsHistory = new FifoFixedSizedQueue<>(catsHistorySize);
    }

    @PostConstruct
    public void initializeCats() {
        refreshCatResources();
    }

    public String getBaseCatPic() {
        return baseCatResource.currentPic;
    }
    public RandomCatResource getBaseCatPicTest() {
        return baseCatResource;
    }

    public String getPremiumCatPic() {
        return premiumCatResource.currentPic;
    }

    public void refreshCatResources() {
        List<String> catKeys = new ArrayList<>(s3Service.listCatKeys());
        catKeys.removeAll(catsHistory);
        String baseCatKey = catKeys.remove(random.nextInt(catKeys.size()));
        catsHistory.offer(baseCatKey);
        baseCatResource = new RandomCatResource(baseCatKey, Base64.getEncoder().encodeToString(s3Service.getCatPicAsByteArray(baseCatKey)));
        String premiumCatKey = catKeys.remove(random.nextInt(catKeys.size()));
        premiumCatResource = new RandomCatResource(premiumCatKey, Base64.getEncoder().encodeToString((s3Service.getCatPicAsByteArray(premiumCatKey))));
        catsHistory.offer(premiumCatKey);
    }

    public record RandomCatResource(String s3Key, String currentPic) {}
}
