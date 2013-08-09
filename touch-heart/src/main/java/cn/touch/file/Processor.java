/**
 * 
 */
package cn.touch.file;

import java.nio.charset.Charset;

/**
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 *
 */
public class Processor {
	public static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
	public static final Charset GBK_CHARSET = Charset.forName("GBK");
	public Charset fileCharset = DEFAULT_CHARSET;
	
	public enum LOOP {
		BREAK,
		CONTINUE
	}
}
