package com.wu.mybatis.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * XPath解析示例
 */
public class XPathParser {

    public static void main(String[] args) throws Exception {
        List<Students> list = new ArrayList<Students>();//解析出来的数据用Stundent对象存储，用集合存储该对象

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //设置一些配置参数
//        factory.setValidating(true);
//        factory.setNamespaceAware(false);
//        factory.setIgnoringComments(true);
//        factory.setIgnoringElementContentWhitespace(false);
//        factory.setCoalescing(false);
//        factory.setExpandEntityReferences(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("xpath/student.xml")));

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        NodeList nodes = (NodeList) xPath.evaluate("//student", doc, XPathConstants.NODESET);
//        XPathExpression compile = xPath.compile("//student");//选取student节点
//        NodeList nodes = (NodeList) compile.evaluate(doc, XPathConstants.NODESET);//选取student节点的所有节点
        for (int i = 0; i < nodes.getLength(); i++) {
            Students stu = new Students();
            NodeList childNodes = nodes.item(i).getChildNodes();//获取一个student节点所有的子节点，返回集合
            //遍历所有子节点，获取节点的名称与数据，将其存与Students对象的属性进行匹配并存入该对象
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node node = childNodes.item(j);
                if ("name".equals(node.getNodeName())) {
                    stu.setName(node.getTextContent());
                }
                if ("sex".equals(node.getNodeName())) {
                    stu.setSex(node.getTextContent());
                }
                if("id".equals(node.getNodeName())) {
                    stu.setId(Integer.parseInt(node.getTextContent()));
                }
                if("score".equals(node.getNodeName())) {
                    stu.setSocre(Integer.parseInt(node.getTextContent()));
                }
            }
            //获取 student节点的属性，将其存与Students对象的属性进行匹配并存入到该对象
            NamedNodeMap arr = nodes.item(i).getAttributes();
            for(int k=0;k<arr.getLength();k++) {
                Node ar = arr.item(k);
                if("name".equals(ar.getNodeName())) {
                    stu.setName(ar.getTextContent());
                }
                if("sex".equals(ar.getNodeName())) {
                    stu.setSex(ar.getTextContent());
                }
                if("id".equals(ar.getNodeName())) {
                    stu.setId(Integer.parseInt(ar.getTextContent()));
                }
                if("score".equals(ar.getNodeName())) {
                    stu.setSocre(Integer.parseInt(ar.getTextContent()));
                }
            }
            list.add(stu);
        }
        for (Students s: list) {
            System.out.println(s);
        }

    }

}
