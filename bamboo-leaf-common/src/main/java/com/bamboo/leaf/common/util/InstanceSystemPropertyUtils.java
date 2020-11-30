package com.bamboo.leaf.common.util;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/29 下午11:53
 */
public class InstanceSystemPropertyUtils {
    public static final String KEY_INSTANCE_IP = "pafa.instance.ip";

    public static final String KEY_INSTANCE_NAME = "pafa.instance.name";

    private static final String KEY_INSTANCE_ID = "pafa.instance.id";

    /*static{
        getInstanceName();
        getInstanceID();
        getInstanceIP();
    }*/

    private static String instanceName;

    private static String instanceIp;

    private static Integer instanceId;

    public synchronized static String getInstanceName() {
        if (instanceName == null) {
            String name = System.getProperty(KEY_INSTANCE_NAME);
            if (name != null && (name = name.trim()).length() > 0) {
                instanceName = name;
            } else {
                name = System.getProperty("serverName");
                if (name != null && (name = name.trim()).length() > 0) {
                    instanceName = name.trim();
                } else {
                    name = System.getProperty("instance.name");
                    if (name != null && (name = name.trim()).length() > 0) {
                        instanceName = name.trim();
                    }
                }
            }
            if (instanceName == null) {
                instanceName = "";
            }
        }
        return instanceName.length() == 0 ? null : instanceName;
    }

    public synchronized static Integer getInstanceID() {
        if (instanceId != null) {
            return instanceId;
        }
        String name = getInstanceName();
        if (name != null) {
            StringBuilder sb = null;
            for (int i = name.length() - 1; i > 0; i--) {
                char ch = name.charAt(i);
                if (Character.isDigit(ch)) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(ch);
                } else {
                    break;
                }
            }
            if (sb != null) {
                instanceId = Integer.valueOf(sb.toString());
            }
        }
        if (instanceId == null) {
            instanceId = 0;
        }
        System.setProperty(KEY_INSTANCE_ID, String.valueOf(instanceId));
        return instanceId;
    }

    public synchronized static String getInstanceIP() {
        if (instanceIp == null) {
            String ip = System.getProperty(KEY_INSTANCE_IP);
            if (ip == null || (ip = ip.trim()).length() == 0) {
                ip = System.getProperty("JVM_DEFAULT_BIND_IP");
                if (ip != null && (ip = ip.trim()).length() > 0) {
                    if (!PNetUtils.isValidIP(ip)) {
                        ip = null;
                    }
                }
            } else {
                if (!PNetUtils.isValidIP(ip)) {
                    throw new java.lang.IllegalArgumentException(
                            "System property " + KEY_INSTANCE_IP + "=" + ip + " error,not be valid ipaddress.");
                }
            }
            if (ip == null) {
                instanceIp = "";
            } else {
                instanceIp = ip;
            }
        }
        return instanceIp.length() == 0 ? null : instanceIp;
    }
}
