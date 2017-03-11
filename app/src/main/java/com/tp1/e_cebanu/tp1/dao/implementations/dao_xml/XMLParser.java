package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.tp1.e_cebanu.tp1.models.MyApplication;
import com.tp1.e_cebanu.tp1.util.UIUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


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
     * Si le fichier est introuvable, on copier lui de la répertoire RAW
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public Document getNodeListFromResources() throws IOException, ParserConfigurationException {
        File fXmlFile = new File(getContext().getFilesDir() + "/" + fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = null;

        if (!fXmlFile.exists()) {
            fXmlFile.createNewFile();
            Document docFailOver = getDocumentFromRaw();
            try {
                saveDocument(docFailOver);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            fXmlFile = new File(getContext().getFilesDir() + "/" + fileName);
        }
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
        }

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
        //retirer la termination xml
        String[] filenameParts = aString.split("\\.");
        String fileNameWithoutExtention = filenameParts[0];
        if (type.isEmpty()) {
            type = "xml";
        }
        String packageName = context.getPackageName();
        res = context.getResources();
        int resId = res.getIdentifier(fileNameWithoutExtention, type, packageName);
        return resId;
    }

    private Document getDocumentFromRaw() throws ParserConfigurationException, FileNotFoundException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = null;

        //File file = getContext().getFileStreamPath(fileName);
        // trouver le fichier par son nom dans le repertoire RAW
        Integer xmlID = this.getStringResourceByName(fileName, UIUtils.FILE_STORAGE_FOLDER);
        InputStream fXmlFile = res.openRawResource(xmlID);

        try {
            doc = dBuilder.parse(fXmlFile);
            Log.i(UIUtils.TAG,"Parsing file  : " +fileName+ " successfully executed!");
        } catch (SAXException e) {
            e.printStackTrace();
            Log.i(UIUtils.TAG,"Parsing file  : " +fileName+ " hasn't been executed!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(UIUtils.TAG,"Parsing file  : " +fileName+ " hasn't been executed!");
        }
        return doc;
    }

    public void saveDocument(Document doc) throws IOException, TransformerException {
        DOMSource source = new DOMSource(doc);

        try {
            FileOutputStream fOut = getContext().openFileOutput(fileName, getContext().MODE_PRIVATE);
            StreamResult result = new StreamResult(fOut);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
