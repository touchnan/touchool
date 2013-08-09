/*
 * test.cn.touch.kit.MailTest.java
 * Aug 24, 2012 
 */
package test.cn.touch.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.junit.Before;
import org.junit.Test;

/**
 * Aug 24, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class MailTest {
    private Properties p;
    private String to = "";
    private String from = "";
    private String host = "";

    private String subject = "", cc = "", bcc = "", url = "";

    @Before
    public void before() {
        p = new Properties(); // Properties p = System.getProperties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.host", "smtp.163.com");
        p.put("mail.smtp.port", "25");
    }

    @Test
    public void sendSimple() {
        try {
            // 建立会话
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // 建立信息

            msg.setFrom(new InternetAddress("emailaccount@163.com")); // 发件人
            // msg.setRecipient(Message.RecipientType.TO, new
            // InternetAddress("mudenglong@163.com")); // 收件人
            msg.setRecipients(Message.RecipientType.TO, new InternetAddress[] {
                    new InternetAddress("11111111@163.com"), new InternetAddress("11111111@qq.com") });

            msg.setSentDate(new Date()); // 发送日期
            msg.setSubject("Oh 必胜法则"); // 主题
            msg.setText("话说、听说、据说、传说、"); // 内容
            // 邮件服务器进行验证
            Transport tran = session.getTransport("smtp");
            tran.connect("smtp.163.com", "emailaccount", "emailpasswd");
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送
            System.out.println("邮件发送成功");

        } catch (MessagingException mex) {
            System.out.println("\n--Exception handling in msgsendsample.java");

            mex.printStackTrace();
            System.out.println();
            Exception ex = mex;
            do {
                if (ex instanceof SendFailedException) {
                    SendFailedException sfex = (SendFailedException) ex;
                    Address[] invalid = sfex.getInvalidAddresses();
                    if (invalid != null) {
                        System.out.println("    ** Invalid Addresses");
                        for (int i = 0; i < invalid.length; i++)
                            System.out.println("         " + invalid[i]);
                    }
                    Address[] validUnsent = sfex.getValidUnsentAddresses();
                    if (validUnsent != null) {
                        System.out.println("    ** ValidUnsent Addresses");
                        for (int i = 0; i < validUnsent.length; i++)
                            System.out.println("         " + validUnsent[i]);
                    }
                    Address[] validSent = sfex.getValidSentAddresses();
                    if (validSent != null) {
                        System.out.println("    ** ValidSent Addresses");
                        for (int i = 0; i < validSent.length; i++)
                            System.out.println("         " + validSent[i]);
                    }
                }
                System.out.println();
                if (ex instanceof MessagingException)
                    ex = ((MessagingException) ex).getNextException();
                else
                    ex = null;
            } while (ex != null);
        }
    }

    @Test
    public void sendFile() {

        boolean debug = true;
        String msgText1 = "Sending a file.\n";
        String subject = "Sending a file";

        // create some properties and get the default Session
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);

        Session session = Session.getInstance(props, null);
        session.setDebug(debug);

        try {
            // create a message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = { new InternetAddress(to) };
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);

            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(msgText1);

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();

            // attach the file to the message
            String filename = "";
            mbp2.attachFile(filename);

            /*
             * Use the following approach instead of the above line if you want
             * to control the MIME type of the attached file. Normally you
             * should never need to do this.
             * 
             * FileDataSource fds = new FileDataSource(filename) { public String
             * getContentType() { return "application/octet-stream"; } };
             * mbp2.setDataHandler(new DataHandler(fds));
             * mbp2.setFileName(fds.getName());
             */

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            msg.setContent(mp);

            // set the Date: header
            msg.setSentDate(new Date());

            /*
             * If you want to control the Content-Transfer-Encoding of the
             * attached file, do the following. Normally you should never need
             * to do this.
             * 
             * msg.saveChanges(); mbp2.setHeader("Content-Transfer-Encoding",
             * "base64");
             */

            // send the message
            Transport.send(msg);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    @Test
    public void sendhtml() {

        String mailer = "sendhtml";
        String protocol = "", host = "", user = "", password = "";
        String record = ""; // name of folder in which to record mail
        boolean debug = false;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {

            // Get a Session object
            Session session = Session.getInstance(p, null);
            if (debug)
                session.setDebug(true);

            // construct the message
            Message msg = new MimeMessage(session);
            if (from != null)
                msg.setFrom(new InternetAddress(from));
            else
                msg.setFrom();

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            if (cc != null)
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
            if (bcc != null)
                msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));

            msg.setSubject(subject);

            collect(in, msg);

            msg.setHeader("X-Mailer", mailer);
            msg.setSentDate(new Date());

            // send the thing off
            Transport.send(msg);

            System.out.println("\nMail was sent successfully.");

            // Keep a copy, if requested.

            if (record != null) {
                // Get a Store object
                Store store = null;
                if (url != null) {
                    URLName urln = new URLName(url);
                    store = session.getStore(urln);
                    store.connect();
                } else {
                    if (protocol != null)
                        store = session.getStore(protocol);
                    else
                        store = session.getStore();

                    // Connect
                    if (host != null || user != null || password != null)
                        store.connect(host, user, password);
                    else
                        store.connect();
                }

                // Get record Folder. Create if it does not exist.
                Folder folder = store.getFolder(record);
                if (folder == null) {
                    System.err.println("Can't get record folder.");
                    System.exit(1);
                }
                if (!folder.exists())
                    folder.create(Folder.HOLDS_MESSAGES);

                Message[] msgs = new Message[1];
                msgs[0] = msg;
                folder.appendMessages(msgs);

                System.out.println("Mail was recorded successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collect(BufferedReader in, Message msg) throws MessagingException, IOException {
        String line;
        String subject = msg.getSubject();
        StringBuffer sb = new StringBuffer();
        sb.append("<HTML>\n");
        sb.append("<HEAD>\n");
        sb.append("<TITLE>\n");
        sb.append(subject + "\n");
        sb.append("</TITLE>\n");
        sb.append("</HEAD>\n");

        sb.append("<BODY>\n");
        sb.append("<H1>" + subject + "</H1>" + "\n");

        while ((line = in.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        sb.append("</BODY>\n");
        sb.append("</HTML>\n");

        msg.setDataHandler(new DataHandler(new ByteArrayDataSource(sb.toString(), "text/html")));
    }
}
