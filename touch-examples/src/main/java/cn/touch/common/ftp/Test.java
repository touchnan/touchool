/**
 *
 */
package cn.touch.common.ftp;

import sun.net.TelnetInputStream;
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
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 21);
            FtpClient client = FtpClient.create(addr);
//            client.connect(addr);
            client.login("name", "passwd".toCharArray());
            client.setBinaryType();

//            client.siteCmd("");
//            client.getStatus();

            /*- JDK1.6
            FtpClient client = new FtpClient();
            client.openServer("bekins.borland.com", 21);
//			FtpClient client = new FtpClient("bekins.borland.com", 21);
            client.login("ftestro", "webex123");
            client.binary();
            */

//			DataInputStream dis = new DataInputStream(client.list());
            DataInputStream dis = new DataInputStream(client.nameList(""));
            List<String> names = new ArrayList<String>();
            String s = "";
            while ((s = dis.readLine()) != null) {
                System.out.println(s);
                names.add(s);
            }

//            client.closeServer();//JDK1.6
            client.close();
        } catch (IOException|FtpProtocolException e) {
            e.printStackTrace();
        }
    }

    public long download(FtpClient client, String filename, String newfilename) throws Exception {
        long result = 0;
        InputStream is = null;
        FileOutputStream os = null;
        try {
//            is = client.get(filename);//JDK1.6
            is = client.getFileStream(filename);
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

}
