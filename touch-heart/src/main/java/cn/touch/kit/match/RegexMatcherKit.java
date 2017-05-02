/*
 * cn.touch.kit.match.RegexMatcherKit.java
 * Aug 22, 2012 
 */
package cn.touch.kit.match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Logs;

/**
 * Aug 22, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class RegexMatcherKit implements MatcherKit {

    private String propFileName;
    private List<Pattern> patterns;

    public RegexMatcherKit(String propFileName) {
        super();
        this.propFileName = propFileName;
    }

    public void init() {
        if (!Strings.isBlank(propFileName)) {
            try {
                Properties variables = new Properties();
                variables.load(Files.findFileAsStream(propFileName));
                if (!variables.isEmpty()) {
                    patterns = new ArrayList<Pattern>(variables.size());
                    for (Entry<Object, Object> e : variables.entrySet()) {
                        patterns.add(Pattern.compile(e.getValue().toString()));
                    }
                }
            } catch (IOException e) {
                Logs.getLog(RegexMatcherKit.class).error("init RegexPatterns error", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.kit.match.MatcherKit#isMatched(java.lang.Object)
     */
    @Override
    public boolean isMatched(Object value) {
        if (value instanceof CharSequence) {
            if (!patterns.isEmpty()) {
                for (Pattern p : patterns) {
                    Matcher m = p.matcher((CharSequence) value);
                    if (m.matches()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
