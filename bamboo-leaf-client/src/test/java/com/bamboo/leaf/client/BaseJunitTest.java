package com.bamboo.leaf.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseJunitTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
