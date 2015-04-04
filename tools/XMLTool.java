package com.rstyle.qa.utils.matchers.tools;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anton.Tatarinov
 * Date: 09.02.15
 * Time: 9:23
 */
public class XMLTool {
    private List<String> list = new ArrayList();

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public XMLTool() {}

    public XMLTool(File file, String xpathExpression) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputSource inputSource = new InputSource(new FileInputStream(file));


        NodeList nodes = (NodeList) xpath
                .evaluate(xpathExpression, inputSource, XPathConstants.NODESET);

        int j = nodes.getLength();

        for (int i = 0; i < j; i++) {

            list.add(nodes.item(i).getTextContent());
        }

    }

    public List findNodes(File file, String xpathExpression) throws Exception  {
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputSource inputSource = new InputSource(new FileInputStream(file));


        NodeList nodes = (NodeList) xpath
                .evaluate(xpathExpression, inputSource, XPathConstants.NODESET);

        int j = nodes.getLength();

        for (int i = 0; i < j; i++) {

            list.add(nodes.item(i).getTextContent());
        }
        return getList();
    }



    public boolean matchCode(String... codes) {
        boolean b = false;
        for(String code : codes) {
            if (getList().contains(code)) {
                b=true;
            }else {
                b=false;
            }
        }
        return b;

    };


}
