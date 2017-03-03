package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.util.Log;

import com.tp1.e_cebanu.tp1.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.R.attr.path;

/**
 * Java# version 1.8.0
 *
 * @class XMLParser
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description lecture / écriture XML
 */

public class XMLParser {

    private Context context;
    private Resources res;


    // Constructeur vide
    public XMLParser(Context current) {
        this.context = current;
    }


    private int getStringResourceByName(String aString) {
        String packageName = context.getPackageName();
        res = context.getResources();
        int resId = res.getIdentifier(aString, "xml", packageName);
        return resId;
    }


    public String getXmlFromResources(String fileName) throws XmlPullParserException, IOException {
        Integer xmlID = this.getStringResourceByName(fileName);
        StringBuffer stringBuffer = new StringBuffer();
        XmlResourceParser xpp = res.getXml(xmlID);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("item")){
                    stringBuffer.append(xpp.getAttributeValue(null, "ItemName") + "\n");
                }
            }
            eventType = xpp.next();
        }
        return stringBuffer.toString();
    }


    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

//
//        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            dbf.setNamespaceAware(false);
//            dbf.setValidating(false);
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            return db.parse(xml);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }

        try {
            DocumentBuilder e = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = e.parse(xml);
            return doc;
        } catch (ParserConfigurationException var6) {
            Log.e("Error: ", var6.getMessage());
            return null;
        } catch (SAXException var7) {
            Log.e("Error: ", var7.getMessage());
            return null;
        } catch (IOException var8) {
            Log.e("Error: ", var8.getMessage());
            return null;
        }
    }

    public final String getElementValue(Node elem) {
        if(elem != null && elem.hasChildNodes()) {
            for(Node child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                if(child.getNodeType() == 3) {
                    return child.getNodeValue();
                }
            }
        }

        return "";
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }


}
