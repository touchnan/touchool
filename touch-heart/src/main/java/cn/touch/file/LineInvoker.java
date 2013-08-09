/**
 * 
 */
package cn.touch.file;

import java.io.IOException;
import java.io.Reader;

import cn.touch.file.Processor.LOOP;



/**
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 *
 */
public interface LineInvoker {
	void before(Reader reader) throws IOException;
	LOOP each(Reader reader, String line) throws IOException;
	void after(Reader reader) throws IOException;
}
