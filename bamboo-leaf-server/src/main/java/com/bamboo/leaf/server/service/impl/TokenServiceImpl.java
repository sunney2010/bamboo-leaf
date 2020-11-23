package com.bamboo.leaf.server.service.impl;

import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

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
        List<TokenDO> list = queryAllToken();
        Map<String, Set<String>> map = converToMap(list);
        tokenMap = map;
        logger.info("bamboo-leaf token init success, token size:{}", list == null ? 0 : list.size());
    }

    public Map<String, Set<String>> converToMap(List<TokenDO> list) {
        Map<String, Set<String>> map = new HashMap<>(64);
        if (list != null) {
            for (TokenDO tokenDO : list) {
                if (!map.containsKey(tokenDO.getToken())) {
                    map.put(tokenDO.getToken(), new HashSet<String>());
                }
                map.get(tokenDO.getToken()).add(tokenDO.getNamespace());
            }
        }
        return map;
    }

    private List<TokenDO> queryAllToken() {
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
