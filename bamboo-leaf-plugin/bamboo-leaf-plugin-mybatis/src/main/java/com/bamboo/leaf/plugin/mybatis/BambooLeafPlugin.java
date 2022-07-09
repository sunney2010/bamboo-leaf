package com.bamboo.leaf.plugin.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
public class BambooLeafPlugin implements Interceptor {

    public static final String DEFAULT_LIST_KEY = "list";
    //private final CosIdAnnotationSupport cosIdSupport;

//    public CosIdPlugin(CosIdAnnotationSupport cosIdSupport) {
//        this.cosIdSupport = cosIdSupport;
//    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
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
        }
        return invocation.proceed();
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
