package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ParamConstant;
import com.bamboo.leaf.client.enums.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.AbstractSegmentGeneratorFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.impl.CachedSegmentGenerator;
import com.bamboo.leaf.core.service.SegmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 客户端实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:04
 */
@Service
public class BambooLeafSegmentClientImpl extends AbstractSegmentGeneratorFactory implements BambooLeafSegmentClient, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 前缀最大长度
     */
    private static final Integer PREFIX_MAX_LENGTH = 10;

    private ApplicationContext applicationContext;

    @Resource(name = "segmentService")
    SegmentService localSegmentService;


    @Override
    public Long segmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        // 设置默认模式
        paramMap.put(ParamConstant.PARAM_MODE_KEY, ClientConfig.getInstance().getMode());
        return this.segmentId(namespace, paramMap);
    }

    @Override
    public Long segmentId(String namespace, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        // 默认模式
        String mode = ClientConfig.getInstance().getMode();
        //最大值默认为0
        long maxValue = 0l;
        try {
            // 获取方法级模式，方法级优先。
            String modeTemp = (String) paramMap.get(ParamConstant.PARAM_MODE_KEY);
            if (StringUtils.isNotBlank(modeTemp)) {
                mode = modeTemp;
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("segmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        return generator.nextSegmentId();
    }

    @Override
    public Long dateSegmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        // 设置默认模式
        paramMap.put(ParamConstant.PARAM_MODE_KEY, ClientConfig.getInstance().getMode());
        // 设置默认最大值
        paramMap.put(ParamConstant.PARAM_MAX_VALUE_KEY, LeafConstant.SEGMENT_10_MAXVALUE);
        return this.dateSegmentId(namespace, paramMap);
    }

    @Override
    public String dateSegmentId(String namespace, String prefix) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.dateSegmentId(namespace);
    }

    @Override
    public Long dateSegmentId(String namespace, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        // 默认模式
        String mode = ClientConfig.getInstance().getMode();
        //最大值默认为10位
        long maxValue = LeafConstant.SEGMENT_10_MAXVALUE;
        try {
            // 获取方法级模式，方法级优先。
            String modeTemp = (String) paramMap.get(ParamConstant.PARAM_MODE_KEY);
            if (StringUtils.isNotBlank(modeTemp)) {
                mode = modeTemp;
            }
            //获取方法级最大值
            Long mvTemp = (Long) paramMap.get(ParamConstant.PARAM_MAX_VALUE_KEY);
            //方法级最大值,范围【8~11位】
            if (mvTemp >= LeafConstant.SEGMENT_8_MAXVALUE && mvTemp <= LeafConstant.SEGMENT_10_MAXVALUE) {
                maxValue = mvTemp;
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("segmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String val = generator.nextSegmentIdFixed(maxValue);
        StringBuilder id = new StringBuilder(20);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        id.append(formatter.format(dt));
        id.append(val);
        return Long.valueOf(id.toString());
    }

    @Override
    public String dateSegmentId(String namespace, String prefix, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.dateSegmentId(namespace, paramMap);
    }

    @Override
    public Long shortDateSegmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        // 设置默认模式
        paramMap.put(ParamConstant.PARAM_MODE_KEY, ClientConfig.getInstance().getMode());
        // 设置默认最大值
        paramMap.put(ParamConstant.PARAM_MAX_VALUE_KEY, LeafConstant.SEGMENT_10_MAXVALUE);
        return this.shortDateSegmentId(namespace, paramMap);

    }

    @Override
    public Long shortDateSegmentId(String namespace, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        // 默认模式
        String mode = ClientConfig.getInstance().getMode();
        //最大值默认为10位
        long maxValue = LeafConstant.SEGMENT_10_MAXVALUE;
        try {
            // 获取方法级模式，方法级优先。
            String modeTemp = (String) paramMap.get(ParamConstant.PARAM_MODE_KEY);
            if (StringUtils.isNotBlank(modeTemp)) {
                mode = modeTemp;
            }
            //获取方法级最大值
            Long mvTemp = (Long) paramMap.get(ParamConstant.PARAM_MAX_VALUE_KEY);
            //方法级最大值,范围【8~11位】
            if (mvTemp >= LeafConstant.SEGMENT_8_MAXVALUE && mvTemp <= LeafConstant.SEGMENT_10_MAXVALUE) {
                maxValue = mvTemp;
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("shortDateSegmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String val = generator.nextSegmentIdFixed(maxValue);
        StringBuilder id = new StringBuilder(20);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        id.append(formatter.format(dt));
        id.append(val);
        return Long.valueOf(id.toString());
    }

    @Override
    public String shortDateSegmentId(String namespace, String prefix) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.shortDateSegmentId(namespace);
    }

    @Override
    public String shortDateSegmentId(String namespace, String prefix, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.shortDateSegmentId(namespace, paramMap);
    }


    @Override
    public Long timeSegmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        // 设置默认模式
        paramMap.put(ParamConstant.PARAM_MODE_KEY, ClientConfig.getInstance().getMode());
        // 设置默认最大值
        paramMap.put(ParamConstant.PARAM_MAX_VALUE_KEY, LeafConstant.SEGMENT_7_MAXVALUE);
        return this.timeSegmentId(namespace, paramMap);

    }

    @Override
    public Long timeSegmentId(String namespace, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        // 默认模式
        String mode = ClientConfig.getInstance().getMode();
        //最大值默认为10位
        long maxValue = LeafConstant.SEGMENT_7_MAXVALUE;
        try {
            // 获取方法级模式，方法级优先。
            String modeTemp = (String) paramMap.get(ParamConstant.PARAM_MODE_KEY);
            if (StringUtils.isNotBlank(modeTemp)) {
                mode = modeTemp;
            }
            //获取方法级最大值
            Long mvTemp = (Long) paramMap.get(ParamConstant.PARAM_MAX_VALUE_KEY);
            //方法级最大值,范围【5~7位】
            if (mvTemp >= LeafConstant.SEGMENT_5_MAXVALUE && mvTemp <= LeafConstant.SEGMENT_7_MAXVALUE) {
                maxValue = mvTemp;
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("shortDateSegmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String val = generator.nextSegmentIdFixed(maxValue);

        StringBuilder id = new StringBuilder(20);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        id.append(formatter.format(dt));
        id.append(val);
        return Long.valueOf(id.toString());
    }

    @Override
    public String timeSegmentId(String namespace, String prefix) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.timeSegmentId(namespace);
    }

    @Override
    public String timeSegmentId(String namespace, String prefix, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.timeSegmentId(namespace, paramMap);
    }


    @Override
    protected SegmentGenerator createSegmentGenerator(String namespace, long maxValue, String mode) {
        SegmentGenerator segmentGenerator = null;
        //判断当前模式不能为空
        if (null == mode || mode.trim().length() == 0) {
            throw new BambooLeafException("bamboo.leaf.client.mode is not null");
        }
        //判断配置的模式
        if (mode.equalsIgnoreCase(ModeEnum.Remote.name())) {
            // 远程模式
            segmentGenerator = new CachedSegmentGenerator(namespace, maxValue, new RemoteSegmentServiceImpl());
        } else if (mode.equalsIgnoreCase(ModeEnum.Local.name())) {
            // 本地模式
            segmentGenerator = new CachedSegmentGenerator(namespace, maxValue, localSegmentService);
        }
        return segmentGenerator;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
