/*
 * cn.touch.kit.env.CommandExec.java
 * Sep 6, 2012 
 */
package cn.touch.kit.env;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.nutz.lang.Encoding;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import cn.touch.kit.MethodResult;

/**
 * Sep 6, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class CommandExec {
    private static Log log = Logs.getLog(CommandExec.class);

    private static String encode = System.getProperty("sun.jnu.encoding", Encoding.UTF8);
    
    /*-
     * *************************************************************************
     * 运行一句系统命令, 系统命令的返回结果保存在command_return_value中
     * @param command 命令
     * @param workdir 工作目录
     * @return 运行成功返回true , 否则返回false
     *************************************************************************
     */
    public static MethodResult runASystemCommand(boolean sh, String command, String workdir) {
        File f_workdir = Strings.isBlank(workdir)? null : new File(workdir);
        try {
            Process tprocess;
            if (log.isDebugEnabled()) {
                log.debug("command:" + command);
            }
            if (sh) {
                tprocess = Runtime.getRuntime().exec(new String[] { "sh", "-c", command }, null, f_workdir);
            } else {
                tprocess = Runtime.getRuntime().exec(command, null, f_workdir);
            }

            String content = IOUtils.toString(tprocess.getErrorStream(), encode);
            if (log.isDebugEnabled()) {
                log.debug("getErrorStream: " + content);
            }
            if (!Strings.isBlank(content)) {
                return new MethodResult(content);
            }

            content = IOUtils.toString(tprocess.getInputStream(), encode);
            if (log.isDebugEnabled()) {
                log.debug("getInputStream: " + content);
            }
            return new MethodResult(true, content);
        } catch (IOException e) {
            log.error(e);
            return new MethodResult(e);
        }
    }
    
}
