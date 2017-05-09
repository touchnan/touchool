package cn.touch.common;

import java.util.Enumeration;
import java.util.Properties;


public class SysProperties {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Properties prop = System.getProperties();
        Enumeration<Object> enume = prop.keys();
        while (enume.hasMoreElements()) {
            String key = enume.nextElement().toString();
            System.out.println(key + " <==>   " + prop.getProperty(key));
        }
    }

}
