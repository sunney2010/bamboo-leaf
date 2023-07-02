package com.bamboo.leaf.plugin.mybatis;

import com.bamboo.leaf.client.annotation.DateSegmentId;
import com.bamboo.leaf.client.annotation.SegmentId;
import com.bamboo.leaf.client.constant.ParamConstant;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(
        type = Executor.class,
        method = "insert",
        args = {MappedStatement.class, Object.class})})
public class BambooLeafPlugin implements Interceptor {

    public static final String DEFAULT_LIST_KEY = "list";

    @Autowired
    BambooLeafSegmentClient bambooLeafSegmentClient;

    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        //不是插入操作直接返回
        if (!SqlCommandType.INSERT.equals(statement.getSqlCommandType())) {
            return invocation.proceed();
        }
        Object parameter = args[1];
        if (!(parameter instanceof Map)) {
            //cosIdSupport.ensureId(parameter);
            return invocation.proceed();
        }
        Collection entityList = (Collection) ((Map) parameter).get(DEFAULT_LIST_KEY);
        for (Object entity : entityList) {
            //cosIdSupport.ensureId(entity);
            sequenceField(entity);
        }
        return invocation.proceed();
    }

    /**
     * @param object
     * @throws IllegalAccessException
     */
    private void sequenceField(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            SegmentId segmentId = field.getAnnotation(SegmentId.class);
            if (segmentId != null) {
                String mode = segmentId.mode();
                String namespace = segmentId.namespace();
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(ParamConstant.PARAM_MODE_KEY, mode);
                Object val = bambooLeafSegmentClient.segmentId(namespace, paramMap);
                field.set(object, val);
            }
            DateSegmentId dateSegmentId = field.getAnnotation(DateSegmentId.class);
            if (dateSegmentId != null) {
                String mode = dateSegmentId.mode();
                String namespace = dateSegmentId.namespace();
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(ParamConstant.PARAM_MODE_KEY, mode);
                Object val = bambooLeafSegmentClient.dateSegmentId(namespace, paramMap);
                field.set(object, val);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
