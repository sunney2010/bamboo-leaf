package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ParamConstant;
import com.bamboo.leaf.client.enums.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.factory.AbstractSegmentGeneratorFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.impl.CachedSegmentGenerator;
import com.bamboo.leaf.core.service.SegmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

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
     * 前中后缀最大长度6
     */
    private static final Integer FIX_MAX_LENGTH = 6;

    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("segmentService")
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
        Long maxValue = 0L;
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
        return Long.valueOf(this.dateSegmentId(namespace, paramMap));
    }

    @Override
    public String dateSegmentId(String namespace, Map<String, Object> paramMap) {
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
            //方法级最大值,范围【8~10位】
            if (mvTemp >= LeafConstant.SEGMENT_8_MAXVALUE && mvTemp <= LeafConstant.SEGMENT_10_MAXVALUE) {
                maxValue = mvTemp;
            } else {
                logger.warn("dateSegmentId的maxValue:范围是【8~10位】,当前值maxValue：{}，maxValue Reset:{}", mvTemp, maxValue);
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("segmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String sequence = generator.nextSegmentIdFixed(maxValue);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String fixed = formatter.format(dt);
        return this.buildSegmentId(fixed, sequence, paramMap);
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
        return Long.valueOf(this.shortDateSegmentId(namespace, paramMap));

    }

    @Override
    public String shortDateSegmentId(String namespace, Map<String, Object> paramMap) {
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
            } else {
                logger.warn("shortDateSegmentId的maxValue:范围是【8~10位】,当前值maxValue：{}，maxValue Reset:{}", mvTemp, maxValue);
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("shortDateSegmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String sequence = generator.nextSegmentIdFixed(maxValue);

        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        String fixed = formatter.format(dt);
        return this.buildSegmentId(fixed, sequence, paramMap);
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
        return Long.valueOf(this.timeSegmentId(namespace, paramMap));

    }

    @Override
    public String timeSegmentId(String namespace, Map<String, Object> paramMap) {
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
            } else {
                logger.warn("timeSegmentId的maxValue:范围是【5~7位】,当前值maxValue：{}，maxValue Reset:{}", mvTemp, maxValue);
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("shortDateSegmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String sequence = generator.nextSegmentIdFixed(maxValue);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String fixed = formatter.format(dt);
        return this.buildSegmentId(fixed, sequence, paramMap);
    }


    @Override
    public String autoDateSegmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        // 设置默认模式
        paramMap.put(ParamConstant.PARAM_MODE_KEY, ClientConfig.getInstance().getMode());
        // 设置默认最大值
        paramMap.put(ParamConstant.PARAM_MAX_VALUE_KEY, LeafConstant.SEGMENT_10_MAXVALUE);
        return this.autoDateSegmentId(namespace, paramMap);
    }

    @Override
    public String autoDateSegmentId(String namespace, Map<String, Object> paramMap) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        String fixed = formatter.format(dt);
        // namespace=namespace_yyMMdd
        namespace = namespace + "_" + fixed;
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
            } else {
                logger.warn("autoDateSegmentId的maxValue:范围是【8~10位】,当前值maxValue：{}，maxValue Reset:{}", mvTemp, maxValue);
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("segmentId param is error,msg:", e);
            }
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, maxValue, mode);
        String sequence = generator.nextSegmentIdFixed(maxValue);
        return buildSegmentId(fixed, sequence, paramMap);
    }

    /**
     * 序列构建
     * 默认格式：fixed+sequence
     * 自定义格式：[prefix]+fixed+[infix]+sequence+[suffix]
     *
     * @param fixed    固定位，如yyyyMMdd
     * @param sequence 序列值
     * @param paramMap 参数
     * @return
     */
    private String buildSegmentId(String fixed, String sequence, Map<String, Object> paramMap) {
        // 前缀
        String prefix = (String) paramMap.get(ParamConstant.PARAM_PREFIX);
        //前缀不为空，最大长度大于6时，获取前6位。
        if (StringUtils.isNotBlank(prefix) && prefix.length() > FIX_MAX_LENGTH) {
            prefix = prefix.substring(0, 5);
            logger.warn("prefix前缀最大长度大于:{},截取前:{}位！", FIX_MAX_LENGTH, FIX_MAX_LENGTH);
        }
        // 中缀
        String infix = (String) paramMap.get(ParamConstant.PARAM_INFIX);
        //中缀不为空，最大长度大于6时，获取前6位。
        if (StringUtils.isNotBlank(infix) && infix.length() > FIX_MAX_LENGTH) {
            infix = infix.substring(0, 5);
            logger.warn("infix中缀最大长度大于:{},截取前:{}位！", FIX_MAX_LENGTH, FIX_MAX_LENGTH);
        }
        // 后缀
        String suffix = (String) paramMap.get(ParamConstant.PARAM_SUFFIX);
        //后缀不为空，最大长度大于6时，获取前6位。
        if (StringUtils.isNotBlank(suffix) && suffix.length() > FIX_MAX_LENGTH) {
            suffix = suffix.substring(0, 5);
            logger.warn("suffix后缀最大长度大于:{},截取前:{}位！", FIX_MAX_LENGTH, FIX_MAX_LENGTH);
        }
        StringBuilder segmentId = new StringBuilder(64);
        segmentId.append(prefix).append(fixed).append(infix).append(sequence).append(suffix);
        return segmentId.toString();
    }


    @Override
    protected SegmentGenerator createSegmentGenerator(String namespace, long maxValue, String mode) {
        SegmentGenerator segmentGenerator = null;
        //判断当前模式不能为空
        if (null == mode || mode.trim().length() == 0) {
            if (logger.isWarnEnabled()) {
                logger.warn("bamboo.leaf.client.mode is  null,default set mode=Local");
            }
            // 默认为本地模式
            mode = ModeEnum.Local.name();
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
