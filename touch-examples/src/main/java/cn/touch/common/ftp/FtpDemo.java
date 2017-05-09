/**
 *
 */
package cn.touch.common.ftp;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 */
public class FtpDemo {
    /**
     * FTP远程命令列表 <br>
     * USER PORT RETR ALLO DELE SITE XMKD CDUP FEAT <br>
     * PASS PASV STOR REST CWD STAT RMD XCUP OPTS <br>
     * ACCT TYPE APPE RNFR XCWD HELP XRMD STOU AUTH <br>
     * REIN STRU SMNT RNTO LIST NOOP PWD SIZE PBSZ <br>
     * QUIT MODE SYST ABOR NLST MKD XPWD MDTM PROT <br>
     * 在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上\r\n <br>
     * ftpclient.sendServer( "XMKD /test/bb\r\n "); //执行服务器上的FTP命令 <br>
     * ftpclient.readServerResponse一定要在sendServer后调用 <br>
     * nameList( "/test ")获取指目录下的文件列表 <br>
     * XMKD建立目录，当目录存在的情况下再次创建目录时报错 <br>
     * XRMD删除目录 <br>
     * DELE删除文件 <br>
     * <p>
     * Title: 使用JAVA操作FTP服务器(FTP客户端)
     * </p>
     * <p>
     * Description: 上传文件的类型及文件大小都放到调用此类的方法中去检测，比如放到前台JAVASCRIPT中去检测等
     * 针对FTP中的所有调用使用到文件名的地方请使用完整的路径名（绝对路径开始）。
     * </p>
     * <p>
     * Copyright: Copyright (c) 2005
     * </p>
     * <p>
     */

    private FtpClient ftpclient;

    private String _host;

    private int _port;

    private String _account;

    private String _passwd;

    /**
     * 构造函数
     *
     * @param host     String 机器地址
     * @param port     String 机器FTP端口号
     * @param userName String FTP用户名
     * @param passwd   String FTP密码
     * @throws Exception
     */
    public FtpDemo(String host, int port, String userName, String passwd)
            throws Exception {
        _host = new String(host);
        _port = port;
        ftpclient = FtpClient.create(new InetSocketAddress(host,_port));
        //ftpclient = new FtpClient(_host, _port);//JDK1.6
        // ftpclient = new FtpClient(ipAddress);
        _account = new String(userName);
        _passwd = new String(passwd);
    }

    /**
     * 构造函数
     *
     * @param host     String 机器地址，默认端口为21
     * @param userName String FTP用户名
     * @param passwd   String FTP密码
     * @throws Exception
     */
    public FtpDemo(String host, String userName, String passwd)
            throws Exception {
        _host = new String(host);
        _port = 21;
        ftpclient = FtpClient.create(new InetSocketAddress(host,_port));
//        ftpclient = new FtpClient(_host, _port);//JDK1.6
        // ftpclient = new FtpClient(ipAddress);
        _account = new String(userName);
        _passwd = new String(passwd);
    }

    /**
     * 登录FTP服务器
     *
     * @throws Exception
     */
    public void login() throws Exception {
        ftpclient.login(_account, _passwd.toCharArray());
    }

    /**
     * 退出FTP服务器
     *
     * @throws Exception
     */
    public void logout() throws Exception {
        // 用ftpclient.closeServer()断开FTP出错时用下面语句退出

      /*- JDK.16
        ftpclient.sendServer("QUIT\r\n ");
        int reply = ftpclient.readServerResponse(); // 取得服务器的返回信息
        */

        ftpclient.siteCmd("QUIT\r\n ");
        int reply = ftpclient.getLastReplyCode().getValue(); // 取得服务器的返回信息

    }

    /**
     * 在FTP服务器上建立指定的目录,当目录已经存在的情下不会影响目录下的文件,这样用以判断FTP 上传文件时保证目录的存在.目录格式必须以 "/
     * "根目录开头
     *
     * @param pathList String
     * @throws Exception
     */
    public void buildList(String pathList) throws Exception {
        ftpclient.setAsciiType();
        StringTokenizer s = new StringTokenizer(pathList, "/ "); // sign
        int count = s.countTokens();
        String pathName = " ";
        while (s.hasMoreElements()) {
            pathName = pathName + "/ " + (String) s.nextElement();
            try {
                //ftpclient.sendServer("XMKD   " + pathName + "\r\n ");//JDK1.6
                ftpclient.siteCmd("XMKD   " + pathName + "\r\n ");
            } catch (Exception e) {
                e = null;
            }

//            int reply = ftpclient.readServerResponse();JDK1.6
            int reply = ftpclient.getLastReplyCode().getValue();
        }
        ftpclient.setBinaryType();
    }

    /**
     * 取得指定目录下的所有文件名，不包括目录名称 分析nameList得到的输入流中的数，得到指定目录下的所有文件名
     *
     * @param fullPath String
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList fileNames(String fullPath) throws Exception {
        /*-JDK1.6
//        ftpclient.ascii(); // 注意，使用字符模式
//        TelnetInputStream list = ftpclient.nameList(fullPath);
        */

        ftpclient.setAsciiType(); // 注意，使用字符模式
        InputStream list = ftpclient.nameList(fullPath);

        byte[] names = new byte[2048];
        int bufsize = 0;
        bufsize = list.read(names, 0, names.length); // 从流中读取
        list.close();
        ArrayList namesList = new ArrayList();
        int i = 0;
        int j = 0;
        while (i < bufsize /* names.length */) {
            // char bc = (char) names;
            // System.out.println(i + " " + bc + " : " + (int) names);
            // i = i + 1;
            if (names[i] == 10) { // 字符模式为10，二进制模式为13
                // 文件名在数据中开始下标为j,i-j为文件名的长度,文件名在数据中的结束下标为i-1
                // System.out.write(names, j, i - j);
                // System.out.println(j + " " + i + " " + (i - j));
                String tempName = new String(names, j, i - j);
                namesList.add(tempName);
                // System.out.println(temp);
                // 处理代码处
                // j = i + 2; //上一次位置二进制模式
                j = i + 1; // 上一次位置字符模式
            }
            i = i + 1;
        }
        return namesList;
    }

    /**
     * 上传文件到FTP服务器,destination路径以FTP服务器的 "/ "开始，带文件名、
     * 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖
     *
     * @param source      String
     * @param destination String
     * @throws Exception
     */
    public void upFile(String source, String destination) throws Exception {
        buildList(destination.substring(0, destination.lastIndexOf("/ ")));

        /*-JDK1.6
        ftpclient.binary(); // 此行代码必须放在buildList之后
        TelnetOutputStream ftpOut = ftpclient.put(destination);
        */
        ftpclient.setBinaryType(); // 此行代码必须放在buildList之后
        OutputStream ftpOut = ftpclient.putFileStream(destination);

        TelnetInputStream ftpIn = new TelnetInputStream(new FileInputStream(
                source), true);
        byte[] buf = new byte[204800];
        int bufsize = 0;
        while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
            ftpOut.write(buf, 0, bufsize);
        }
        ftpIn.close();
        ftpOut.close();
    }

    /**
     * JSP中的流上传到FTP服务器, 上传文件只能使用二进制模式，当文件存在时再次上传则会覆盖 字节数组做为文件的输入流,此方法适用于JSP中通过
     * request输入流来直接上传文件在RequestUpload类中调用了此方法， destination路径以FTP服务器的 "/
     * "开始，带文件名
     *
     * @param sourceData  byte[]
     * @param destination String
     * @throws Exception
     */
    public void upFile(byte[] sourceData, String destination) throws Exception {
        buildList(destination.substring(0, destination.lastIndexOf("/ ")));
        /*- JDK1.8
        ftpclient.binary(); // 此行代码必须放在buildList之后
        TelnetOutputStream ftpOut = ftpclient.put(destination);
        */
        ftpclient.setBinaryType(); // 此行代码必须放在buildList之后
        OutputStream ftpOut = ftpclient.putFileStream(destination);
        ftpOut.write(sourceData, 0, sourceData.length);
        // ftpOut.flush();
        ftpOut.close();
    }

    /**
     * 从FTP文件服务器上下载文件SourceFileName，到本地destinationFileName 所有的文件名中都要求包括完整的路径名在内
     *
     * @param SourceFileName      String
     * @param destinationFileName String
     * @throws Exception
     */
    public void downFile(String SourceFileName, String destinationFileName)
            throws Exception {
//        ftpclient.binary(); // 一定要使用二进制模式 JDK1.6
//        TelnetInputStream ftpIn = ftpclient.get(SourceFileName);//JDK1.6

        ftpclient.setBinaryType(); // 一定要使用二进制模式
        InputStream ftpIn = ftpclient.getFileStream(SourceFileName);
        byte[] buf = new byte[204800];
        int bufsize = 0;
        FileOutputStream ftpOut = new FileOutputStream(destinationFileName);
        while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
            ftpOut.write(buf, 0, bufsize);
        }
        ftpOut.close();
        ftpIn.close();
    }

    /**
     * 从FTP文件服务器上下载文件，输出到字节数组中
     *
     * @param SourceFileName String
     * @return byte[]
     * @throws Exception
     */
    public byte[] downFile(String SourceFileName) throws Exception {

        //ftpclient.binary(); // 一定要使用二进制模式 JDK1.6
        //TelnetInputStream ftpIn = ftpclient.get(SourceFileName);//JDK1.6

        ftpclient.setBinaryType(); // 一定要使用二进制模式
        InputStream ftpIn = ftpclient.getFileStream(SourceFileName);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        byte[] buf = new byte[204800];
        int bufsize = 0;

        while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
            byteOut.write(buf, 0, bufsize);
        }
        byte[] return_arraybyte = byteOut.toByteArray();
        byteOut.close();
        ftpIn.close();
        return return_arraybyte;
    }

    public void delFilesFromFTP(String file) throws IOException, FtpProtocolException {
//        ftpclient.sendServer("DELE   " + file + "\r\n ");JDK1.6
        ftpclient.siteCmd("DELE   " + file + "\r\n ");
    }

    /**
     * 调用示例 FtpUpfile fUp = new FtpUpfile( "192.150.189.22 ", 21, "admin ",
     * "admin "); fUp.login(); fUp.buildList( "/adfadsg/sfsdfd/cc "); String
     * destination = "/test.zip "; fUp.upFile( "C:\\Documents and
     * Settings\\Administrator\\My Documents\\sample.zip ",destination);
     * ArrayList filename = fUp.fileNames( "/ "); for (int i = 0; i <
     * filename.size(); i++) { System.out.println(filename.get(i).toString()); }
     * fUp.logout();
     *
     * @param args String[]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String ip = "127.0.0.1";
        int port = 21;
        String userName = "name ";
        String password = "password ";
        String destinationFile = "c:/temp ";

        FtpDemo fUp = new FtpDemo(ip, port, userName, password);
        fUp.login();
        String remoteRoot = "/usr/ " + userName;
        List fileList = fUp.fileNames(remoteRoot);
        for (int i = 3; i < fileList.size(); i++) {
            String fileName = fileList.get(i).toString();
            System.out.println(fileName);

            String tempFile = destinationFile
                    + fileName.substring(fileName.lastIndexOf("/ "));
            // if (fileName.indexOf( "txt ") > 0) {
            fUp.downFile(fileName, tempFile);
            // }
        }

        fUp.logout();
    }
}
