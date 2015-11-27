package com.jc.update;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 解析xml代码的工具类(这个类主要将从服务器端下载的xml解析到uptInfo实体类中)
 * Created by bsty on 11/17/15.
 */
public class ParseXmlUtils {
    //解析xml文件
    public static UpdateInfo parseXml(InputStream in) throws ParserConfigurationException, IOException, SAXException {
        UpdateInfo updateInfo = new UpdateInfo();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        Document doc = null;
        doc = db.parse(in);
        org.w3c.dom.Element root = doc.getDocumentElement();
        NodeList resultNode = root.getElementsByTagName("info");
        for (int i = 0; i < resultNode.getLength(); i++) {
            org.w3c.dom.Element res = (org.w3c.dom.Element) resultNode.item(i);
            updateInfo.setVersion(res.getElementsByTagName("version").item(0).getFirstChild().getNodeValue());
            updateInfo.setUrl(res.getElementsByTagName("url").item(0).getFirstChild().getNodeValue());
            updateInfo.setDescription(res.getElementsByTagName("description").item(0).getFirstChild().getNodeValue());
        }
        return updateInfo;
    }
}
