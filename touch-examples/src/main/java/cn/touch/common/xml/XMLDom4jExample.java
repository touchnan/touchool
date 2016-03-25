package cn.touch.common.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class XMLDom4jExample {
    /*-
     * dom4j  性能优异、功能强大
     */
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        String nasXml = "<NAS><Id>BAS-SE800</Id><IPAddress>192.168.1.120</IPAddress><SNMPCommunity>npm_community</SNMPCommunity><SoftwareVersion>5.0.7.2p4</SoftwareVersion><Secret>npmtest</Secret><Type>SER</Type></NAS>";
        StringBuffer xmlBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuffer.append(nasXml);

        StringReader stringReader = new StringReader(xmlBuffer.toString());
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            document = saxReader.read(stringReader);
            List elements = document.getRootElement().elements("IPAddress");
            if (elements != null) {
                for (Iterator iter = elements.iterator(); iter.hasNext(); ) {
                    Element data = (Element) iter.next();
                    System.out.println(data.getStringValue().toLowerCase().trim());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void createXml(String fileName) {
        Document document = DocumentHelper.createDocument();
        Element employees = document.addElement("employees");
        Element employee = employees.addElement("employee");
        Element name = employee.addElement("name");
        name.setText("ddvip");
        Element sex = employee.addElement("sex");
        sex.setText("m");
        Element age = employee.addElement("age");
        age.setText("29");
        try {
            Writer fileWriter = new FileWriter(fileName);
            XMLWriter xmlWriter = new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parserXml(InputStream is) {
        SAXReader saxReader = new SAXReader();
        try  {
            Document document = saxReader.read(is);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                for (Iterator j = element.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
