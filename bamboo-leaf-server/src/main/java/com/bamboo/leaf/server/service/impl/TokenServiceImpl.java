package com.bamboo.leaf.server.service.impl;

import com.bamboo.leaf.server.dao.TokenDAO;
import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    TokenDAO tokenDAO;

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static Map<String, Set<String>> tokenMap = new HashMap<>();

    /**
     * 1分钟刷新一次token
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void refresh() {
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
                map.get(tokenDO.getToken()).add(tokenDO.getAppId());
            }
        }
        return map;
    }

    private List<TokenDO> queryAllToken() {
        return tokenDAO.selectAll();
    }

    @Override
    public boolean canVisit(String appId, String token) {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(token)) {
            return false;
        }
        Set<String> namespaceSet = tokenMap.get(token);
        boolean val=(namespaceSet != null && namespaceSet.contains(appId));
        return val;
    }

    @Override
    public int insertToken(TokenDO tokenDO) {
        return tokenDAO.insertToken(tokenDO);
    }

}
