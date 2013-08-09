/*
 * cn.touch.kit.jvm.ThreadConsole.java
 * Jul 12, 2012 
 */
package cn.touch.kit.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Jul 12, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ThreadConsole {
    private ThreadMXBean tm;

    private ThreadConsole() {
        super();
        newThreadMxBean();
    }

    public ThreadMXBean newThreadMxBean() {
        this.tm = ManagementFactory.getThreadMXBean();
        this.tm.setThreadContentionMonitoringEnabled(true);
        return this.tm;
    }

    public ThreadInfo[] getAll() {
        return tm.getThreadInfo(tm.getAllThreadIds(), Integer.MAX_VALUE);
    }

    public String printPretty(ThreadInfo[] tia) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < tia.length; i++) {
            long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId()) / (1000 * 1000 * 1000);
            sbf.append("id:").append(tia[i].getThreadId()).append(" name :").append(tia[i].getThreadName())
                    .append(" statue:").append(tia[i].getThreadState()).append(" cuptime:").append(cpuTime)
                    .append(" stackTrace :");
            StackTraceElement[] ses = tia[i].getStackTrace();
            for (StackTraceElement se : ses) {
                sbf.append(se.getClassName() + " " + se.getMethodName() + " " + se.getLineNumber());
            }
        }
        return sbf.toString();
    }
    
    public static void main(String[] args) {
        ThreadConsole c = new ThreadConsole();
        System.out.println(c.printPretty(c.getAll()));
    }
}
