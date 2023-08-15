package com.vtsoft;

import com.vtsoft.service.CollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {VtSoftApplication.class})
class VtsoftApplicationTests {

    @Autowired
    private CollectionService collectionService;

    @Test
    void contextLoads() {
//        collectionService.collectionVideoMessages("garden2023");
    }

}
