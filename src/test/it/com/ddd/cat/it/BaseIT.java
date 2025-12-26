package com.ddd.cat.it;

import com.ddd.cat.RandomCatApplication;
import com.ddd.cat.it.config.ITConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = RandomCatApplication.class)
@ActiveProfiles("IT")
@Import(ITConfiguration.class)
abstract class BaseIT {

}
