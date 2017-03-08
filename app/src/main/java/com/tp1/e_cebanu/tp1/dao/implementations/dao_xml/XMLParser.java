package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import android.content.Context;
import android.content.res.Resources;

import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Java# version 1.8.0
 *
 * @author EUGENIU CEBANU / matricule: 20025851
 * @author jwsjack3@gmail.com
 * @version 1
 * @class XMLParser
 * @package TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @date 2017-02-20
 * @description lecture / écriture XML
 */

public class XMLParser {

//    private Context context;
//    private Resources res;
//
//
//    // Constructeur vide
//    public XMLParser(Context current) {
//        this.context = current;
//    }
//
//
//    private int getStringResourceByName(String aString) {
//        String packageName = context.getPackageName();
//        res = context.getResources();
//        int resId = res.getIdentifier(aString, "xml", packageName);
//        return resId;
//    }
//
//
//    public String getXmlFromResources(String fileName) throws XmlPullParserException, IOException {
//        Integer xmlID = this.getStringResourceByName(fileName);
//        StringBuffer stringBuffer = new StringBuffer();
//        XmlResourceParser xpp = res.getXml(xmlID);
//        xpp.next();
//        int eventType = xpp.getEventType();
//        while (eventType != XmlPullParser.END_DOCUMENT) {
//            if (eventType == XmlPullParser.START_TAG) {
//                if (xpp.getName().equals("item")){
//                    stringBuffer.append(xpp.getAttributeValue(null, "ItemName") + "\n");
//                }
//            }
//            eventType = xpp.next();
//        }
//        return stringBuffer.toString();
//    }
//
//
//    public Document getDomElement(String xml) {
//        Document doc = null;
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
////
////        try {
////            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
////            dbf.setNamespaceAware(false);
////            dbf.setValidating(false);
////            DocumentBuilder db = dbf.newDocumentBuilder();
////            return db.parse(xml);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
//
//        try {
//            DocumentBuilder e = dbf.newDocumentBuilder();
//            InputSource is = new InputSource();
//            is.setCharacterStream(new StringReader(xml));
//            doc = e.parse(xml);
//            return doc;
//        } catch (ParserConfigurationException var6) {
//            Log.e("Error: ", var6.getMessage());
//            return null;
//        } catch (SAXException var7) {
//            Log.e("Error: ", var7.getMessage());
//            return null;
//        } catch (IOException var8) {
//            Log.e("Error: ", var8.getMessage());
//            return null;
//        }
//    }
//
//    public final String getElementValue(Node elem) {
//        if(elem != null && elem.hasChildNodes()) {
//            for(Node child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
//                if(child.getNodeType() == 3) {
//                    return child.getNodeValue();
//                }
//            }
//        }
//
//        return "";
//    }
//
//    public String getValue(Element item, String str) {
//        NodeList n = item.getElementsByTagName(str);
//        return this.getElementValue(n.item(0));
//    }


    private Context context;

    private Resources res;

    private String fileName;
    // Constructeur vide

    public XMLParser() {
        this.context = MyApplication.getAppContext();
    }

    public XMLParser(String fileName) {
        this.fileName = fileName;
    }

    // Setters/Getters
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Resources getRes() {
        return res;
    }

    public void setRes(Resources res) {
        this.res = res;
    }


    //public functions

    /**
     * Retrieve le content d'un fichier et lui transforme dans un DOCUMENT element pour faciliter le lire
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public Document getNodeListFromResources() throws IOException, ParserConfigurationException {
        File fXmlFile = new File(getContext().getFilesDir() + "/" + fileName + ".xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
//            Log.e("Error: ", e.getMessage());

        } catch (IOException e) {
            System.out.println(e.getMessage());
//            Log.e("Error: ", e.getMessage());

        }

//        doc.getDocumentElement().normalize();
        return doc;
    }


    /**
     * Retrieve l'identifier d'un fichier par ce nom
     *
     * @param aString
     * @param type
     * @return
     */
    private int getStringResourceByName(String aString, String type) {
        if (type.isEmpty()) {
            type = "xml";
        }
        String packageName = context.getPackageName();
        res = context.getResources();
        int resId = res.getIdentifier(aString, type, packageName);
        return resId;
    }

    public void saveDocument(Document doc) throws IOException, TransformerException {
//        DOMSource source = new DOMSource(doc);
//
//
//        FileOutputStream _stream = getContext().openFileOutput(fileName + ".xml", getContext().MODE_APPEND);
//
//        Integer xmlID = this.getStringResourceByName(fileName, UIUtils.FILE_STORAGE_FOLDER);
////        String path = res.getResourceName(xmlID);
////        System.out.print("PATH:::: " + path);
////        FileWriter writer = new FileWriter(path);
//        StreamResult result = new StreamResult(_stream);
//
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.transform(source, result);
////        writer.notifyAll();
////        writer.flush();




        try {
            DOMSource source = new DOMSource(doc);
            FileOutputStream fOut = getContext().openFileOutput(fileName+".xml", getContext().MODE_PRIVATE);
            StreamResult result = new StreamResult(fOut);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);
//            String fileContent = "<item>JACK</item>"; //build file content
//            osw.write(fileContent );
//            osw.flush();
//            osw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }

}
