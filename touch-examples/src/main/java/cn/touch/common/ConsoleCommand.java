package cn.touch.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class ConsoleCommand {
    public static void main(String[] args) {
//        format();
//        scan();
        exec();
    }

    public static void format() {
        System.out.println(String.format("%.2f", 2.2323));
    }

    public static void exec() {
        String cmd = "ping 127.0.0.1";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
//            process.waitFor();//等待运行完，一次性全部读取信息，否则运行一行读取一行信息
            readConsoleShell(process);
//        } catch (IOException|InterruptedException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*-
        String cpuid="dmidecode -t processor | grep 'ID' | head -1";
        Process p = Runtime.getRuntime().exec(cpuid);//失败 原因：不会被再次解析，管道符失效
        //正确的办法：
        String[] command = { "/bin/sh", "-c", cpuid };//linux下
        String[] command = { "cmd", "/c", cpuid };//windows下
        Process ps = Runtime.getRuntime().exec(command );
        */
    }

    /**
     * 读取输出流数据
     *
     * @param process 进程
     * @return 从输出流中读取的数据
     * @throws IOException
     */
    public static final String readConsoleShell(Process process) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
             BufferedReader br = new BufferedReader(isr)) {
            //String LINE_SEPARATOR = System.getenv("line.separator");os.name
            String LINE_SEPARATOR = "\n";

            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append(LINE_SEPARATOR);// 追加换行符
            }
            return sb.toString();
        } finally {
            process.destroy();
        }
    }

    public static void scan() {
        Scanner scan = new Scanner(System.in);
        String cmd = null;

        /*-
        while (!"exit".equalsIgnoreCase(cmd = scan.next())) {
            System.out.println(cmd);
        }*/

        while (!"exit".equalsIgnoreCase(cmd = scan.nextLine())) {
            System.out.println("cmd:" + cmd);
            StringTokenizer st = new StringTokenizer(cmd);
            while (st.hasMoreTokens()) {
                System.out.println("token:" + st.nextToken());
            }
        }
        /*-
       int count = scan.nextInt();
        int i=0;
        while (i<count) {
            System.out.println(scan.next());
            i++;
        }*/
    }

}
