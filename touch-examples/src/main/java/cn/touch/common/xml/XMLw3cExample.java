package cn.touch.common.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class XMLw3cExample {
    /*-
     *解析器读入整个文档，然后构建一个驻留内存的树结构，然后代码就可以使用 DOM 接口来操作这个树结构。
     * 优点：整个文档树在内存中，便于操作；支持删除、修改、重新排列等多种功能；
     * 缺点：将整个文档调入内存（包括无用的节点），浪费时间和空间；
     * 使用场合：一旦解析了文档还需多次访问这些数据；硬件资源充足（内存、CPU）
     */
    public static void main(String[] args) {

    }

    public void createXml(String fileName) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Element root = document.createElement("employees");
        document.appendChild(root);
        Element employee = document.createElement("employee");
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode("touchName"));
        employee.appendChild(name);
        Element sex = document.createElement("sex");
        sex.appendChild(document.createTextNode("m"));
        employee.appendChild(sex);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode("30"));
        employee.appendChild(age);
        root.appendChild(employee);
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void parserXml(String fileName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            
            //http://blog.csdn.net/u013224189/article/details/49759845
            
            //这是优先选择. 如果不允许DTDs (doctypes) ,几乎可以阻止所有的XML实体攻击
			String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			dbf.setFeature(FEATURE, true);
			
			/*-
			// 如果不能完全禁用DTDs，最少采取以下措施
		      FEATURE = "http://xml.org/sax/features/external-general-entities";
		      dbf.setFeature(FEATURE, false);

		      FEATURE = "http://xml.org/sax/features/external-parameter-entities";
		      dbf.setFeature(FEATURE, false);

		      // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks" (see reference below)
		      dbf.setXIncludeAware(false);
		      dbf.setExpandEntityReferences(false);
		      
		      */

			
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            Document document = db.parse(fileName);
            NodeList nodes = document.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node child = childNodes.item(j);
//                    child.getTextContent();
                    child.getNodeName();
                    child.getAttributes();
                }
            }
            System.out.println("解析完毕");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
