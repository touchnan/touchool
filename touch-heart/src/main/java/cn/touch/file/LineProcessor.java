/**
 * 
 */
package cn.touch.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import cn.touch.file.chardet.FileCharsetDetector;


/**
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 *
 */
public class LineProcessor extends Processor {
	protected File file;
	
	public LineProcessor(File file)throws IOException {
		super();
		init(file);
	}
	
	private void init(File file) throws IOException {
		this.fileCharset = Charset.forName(new FileCharsetDetector().guestFileEncoding(file));
		this.file = file;
	}
	
	public Processor eachLine(LineInvoker invoker) throws IOException {
		BufferedReader reader = null;
		try {
			invoker.before(reader);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),fileCharset.name()));
			String line = null;
			for ( ;(line = reader.readLine()) != null; ) {
				LOOP status = invoker.each(reader, line);
				if (LOOP.BREAK == status) {
					break;
				}
			}
			invoker.after(reader);
		} finally {
			if (reader!=null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
		return this;
	}	
}
