package cn.touch.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class RegexExample {
    public static void main(String[] args) {
                /*-
         * regex: /(@(wifi|wlan).(\w){2}(.(chntel.com|cn))?)$/i
		 */
//		String reg = "/(@(wifi|wlan).\\w\\w(.(chntel.com|cn))?)$/i";
        String reg = "(@(wifi|wlan)\\.(\\w){2}(\\.(chntel.com|cn))?)$";
        Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher("www@wifi.zj.chntel.com");
        System.out.println(m.find());
        System.out.println(m.replaceAll("@wlan.huang.chntel.com"));
    }
}
