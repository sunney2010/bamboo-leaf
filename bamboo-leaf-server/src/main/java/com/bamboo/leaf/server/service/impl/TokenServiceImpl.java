package com.bamboo.leaf.server.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bamboo.leaf.server.service.TokenService;
import com.xiaoju.uemc.tinyid.server.dao.entity.TinyIdToken;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
@Component
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static Map<String, Set<String>> tokenMap = new HashMap<>();

    /**
     * 1分钟刷新一次token
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void refresh() {
        logger.info("refresh token begin");
        init();
    }

    @PostConstruct
    private synchronized void init() {
        logger.info("bamboo-leaf token init begin");
        List<TinyIdToken> list = queryAllToken();
        Map<String, Set<String>> map = converToMap(list);
        tokenMap = map;
        logger.info("bamboo-leaf token init success, token size:{}", list == null ? 0 : list.size());
    }

    public Map<String, Set<String>> converToMap(List<TinyIdToken> list) {
        Map<String, Set<String>> map = new HashMap<>(64);
        if (list != null) {
            for (TinyIdToken tinyIdToken : list) {
                if (!map.containsKey(tinyIdToken.getToken())) {
                    map.put(tinyIdToken.getToken(), new HashSet<String>());
                }
                map.get(tinyIdToken.getToken()).add(tinyIdToken.getBizType());
            }
        }
        return map;
    }

    private List<TinyIdToken> queryAllToken() {
        return null;
    }

    @Override
    public boolean canVisit(String namespace, String token) {
        if (StringUtils.isEmpty(namespace) || StringUtils.isEmpty(namespace)) {
            return false;
        }
        Set<String> namespaceSet = tokenMap.get(token);
        return (namespaceSet != null && namespaceSet.contains(namespace));
    }

}
