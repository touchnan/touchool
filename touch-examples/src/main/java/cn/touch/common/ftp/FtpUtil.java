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
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:touchnan@gmail.com">hancq</a>
 */
public class FtpUtil {
    private FtpClient ftpClient;

    /**
     * connectServer 连接ftp服务器
     *
     * @param path     文件夹，空代表根目录
     * @param password 密码
     * @param user     登陆用户
     * @param serverIp   服务器地址
     * @param port  端口
     * @throws IOException
     */
    public void connectServer(String serverIp, int port, String user, String password,
                              String path) throws IOException, FtpProtocolException {
        /*-  jdk 1.6
        // server：FTP服务器的IP地址；user:登录FTP服务器的用户名
        // password：登录FTP服务器的用户名的口令；path：FTP服务器上的路径
        ftpClient = new FtpClient();
        ftpClient.openServer(serverIp,port);
        ftpClient.login(user, password);
        // path是ftp服务下主目录的子目录
        if (path.length() != 0)
            ftpClient.cd(path);
        // 用2进制上传、下载
        ftpClient.binary();

        */


        InetSocketAddress addr= new InetSocketAddress(serverIp,port);
        ftpClient = FtpClient.create(addr);
        //ftpClient.connect(addr);
        ftpClient.login(user,password.toCharArray());
        // path是ftp服务下主目录的子目录
        if (path.length() != 0) {
            ftpClient.changeDirectory(path);
        }
        // 用2进制上传、下载
        ftpClient.setBinaryType();

    }

    /**
     * upload 上传文件
     *
     * @param newname  上传后的新文件名
     * @param filename 上传的文件
     * @return -1 文件不存在 -2 文件内容为空 >0 成功上传，返回文件的大小
     * @throws Exception
     */
    public long upload(String filename, String newname) throws Exception {
//        FileInputStream is = null;
//        ftpClient.putFile(filename,is);

        long result = 0;
//        TelnetOutputStream os = null;//JDK1.6
        OutputStream os = null;
        FileInputStream is = null;
        try {
            java.io.File file_in = new java.io.File(filename);
            if (!file_in.exists())
                return -1;
            if (file_in.length() == 0)
                return -2;
//            os = ftpClient.put(newname);//JDK1.6
            os = ftpClient.putFileStream(newname);
            result = file_in.length();
            is = new FileInputStream(file_in);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return result;
    }

    /**
     * upload
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public long upload(String filename) throws Exception {
        String newname = "";
        if (filename.indexOf("/") > -1) {
            newname = filename.substring(filename.lastIndexOf("/") + 1);
        } else {
            newname = filename;
        }
        return upload(filename, newname);
    }

    /**
     * download 从ftp下载文件到本地
     *
     * @param newfilename 本地生成的文件名
     * @param filename    服务器上的文件名
     * @return
     * @throws Exception
     */
    public long download(String filename, String newfilename) throws Exception {
        long result = 0;
//        TelnetInputStream is = null;//JDK1.6
        InputStream is = null;
        FileOutputStream os = null;
        try {

//            is = ftpClient.get(filename);//JDK1.6
            is = ftpClient.getFileStream(filename);
            java.io.File outfile = new java.io.File(newfilename);
            os = new FileOutputStream(outfile);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
                result = result + c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return result;
    }

    /**
     * 取得某个目录下的所有文件列表
     */
    public List getFileList(String path) {
        List list = new ArrayList();
        try {
            DataInputStream dis = new DataInputStream(ftpClient.nameList(path));
            String filename = "";
            while ((filename = dis.readLine()) != null) {
                list.add(filename);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * closeServer 断开与ftp服务器的链接
     *
     * @throws IOException
     */
    public void closeServer() throws IOException {
        try {
            if (ftpClient != null) {
//                ftpClient.closeServer();//JDK1.6
                ftpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        FtpUtil ftp = new FtpUtil();
        try {
            // 连接ftp服务器
            ftp.connectServer("10.163.7.15",21, "cxl", "1", "info2");
            /** 上传文件到 info2 文件夹下 */
            System.out.println("filesize:"
                    + ftp.upload("f:/download/Install.exe") + "字节");
            /** 取得info2文件夹下的所有文件列表,并下载到 E盘下 */
            List list = ftp.getFileList(".");
            for (int i = 0; i < list.size(); i++) {
                String filename = (String) list.get(i);
                System.out.println(filename);
                ftp.download(filename, "E:/" + filename);
            }
        } catch (Exception e) {
            // /
        } finally {
            ftp.closeServer();
        }
    }
}
