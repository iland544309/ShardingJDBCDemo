package com.jone.utils.properties;





import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * Created by iland on 2018/2/2.
 */
public class ReadDruidProperties {

    private static Properties properties = null;

    private static void init(){
        properties = new Properties();
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String readProperty2String(String prefix, String key){
        if(properties == null){
            init();
        }

        String str = "";
        String propertyName = prefix + "." + key;
        if(properties.containsKey(propertyName)){
            str = properties.getProperty(propertyName);
        }
        return str;
    }

    public static int readProperty2Int(String prefix, String key, int defaultVal){
        if(properties == null){
            init();
        }

        int val = defaultVal;
        String propertyName = prefix + "." + key;
        if(properties.containsKey(propertyName)){
            val = Integer.parseInt(properties.getProperty(propertyName));
        }
        return val;
    }

    public static boolean readProperty2Bool(String prefix, String key, boolean defaultVal){
        if(properties == null){
            init();
        }

        boolean val = defaultVal;
        String propertyName = prefix + "." + key;
        if(properties.containsKey(propertyName)){
            val = Boolean.parseBoolean(properties.getProperty(propertyName));
        }
        return val;
    }
}
