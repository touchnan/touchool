package cn.touch.common.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class XMLSaxExample {
    /*-
     * SAX ，事件驱动。
     * 当解析器发现元素开始、元素结束、文本、文档的开始或结束等时，发送事件，程序员编写响应这些事件的代码，保存数据。
     * 优点：不用事先调入整个文档，占用资源少；SAX解析器代码比DOM解析器代码小，适于Applet，下载。
     * 缺点：不是持久的；事件过后，若没保存数据，那么数据就丢了；无状态性；从事件中只能得到文本，但不知该文本属于哪个元素；
     * 使用场合：Applet;只需XML文档的少量内容，很少回头访问；机器内存少
     */
    public static void main(String[] args) {

    }


    public void parserXml(String fileName) {
        SAXParserFactory saxfac = SAXParserFactory.newInstance();
		try {
			//这是优先选择. 如果不允许DTDs (doctypes) ,几乎可以阻止所有的XML实体攻击
			String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			saxfac.setFeature(FEATURE, true);
			
			/*-
			// 如果不能完全禁用DTDs，最少采取以下措施
		      FEATURE = "http://xml.org/sax/features/external-general-entities";
		      saxfac.setFeature(FEATURE, false);

		      FEATURE = "http://xml.org/sax/features/external-parameter-entities";
		      saxfac.setFeature(FEATURE, false);

		      // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks" (see reference below)
		      dbf.setXIncludeAware(false);
		      saxfac.setExpandEntityReferences(false);
		      
		      */
		} catch (SAXNotRecognizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
            SAXParser saxparser = saxfac.newSAXParser();
            InputStream is = new FileInputStream(fileName);
            saxparser.parse(is, new DefaultHandler() {
                @Override
                public void startDocument() throws SAXException {
                    System.out.println("start document!");
                }

                @Override
                public void endDocument() throws SAXException {
                    System.out.println("end document!");
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println("start element!");
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    System.out.println("end element!");
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    super.characters(ch, start, length);
                }
            });
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

	/*-
	*
http://blog.csdn.net/luka2008/article/details/8203915

用SAX解析XML.
String vendorParserClass= "org.apache.xerces.parsers.SAXParser";
XMLReader reader = XMLReaderFactory.createXMLReader(vendorParserClass);
抛出异常：java.lang.ClassNotFoundException:org.apache.xerces.parsers.SAXParser
原因：未在classpath中加入xercesImpl.jar.
但如果改为：XMLReader reader = XMLReaderFactory.createXMLReader();
程序能正常运行。此时程序创建的是一个默认的XMLReader。

如果系统的org.xml.sax.driver i没有被指定为特定的service API：org.xml.sax.driver , org.xml.sax.helpers.此时XMLReaderFactory.createXMLReader 将会使用由SAX Parser指定的默认的XMLReader class （在SUN JDK5, 默认的类是com.sun.org.apache.xerces.internal.parsers.SAXParser.）因此，如果应用程序改变了系统的 org.xml.sax.driver 属性，指向了org.apache.xerces.parsers.SAXParser, 将会产生ClassNotFoundException.
如果不添加xercesImpl.jar的解决方法是：在程序中如果要获取 XMLReader，则不要设定系统的org.xml.sax.driver 属性，或者使用如下的回退机制：
catch (Exception e) { 
                  try { 
                       // If unable to create an instance, let's try to use 
                       // the XMLReader from JAXP 
                       if (m_parserFactory == null) { 
                           m_parserFactory = SAXParserFactory.newInstance(); 
                           m_parserFactory.setNamespaceAware(true); 
                       }
                       reader = m_parserFactory.newSAXParser().getXMLReader();

Note ：that it is generally not a good idea to hard code a reference com.sun.org.apache.xerces.internal.parsers.SAXParser in your application, because the class might not be available when JDK upgrades or in other distributions of JDK .

	*/
}
