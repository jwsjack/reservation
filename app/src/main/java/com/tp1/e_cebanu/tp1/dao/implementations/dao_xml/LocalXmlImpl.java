package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.LocalDao;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by User on 08.03.2017.
 */

public class LocalXmlImpl implements LocalDao {


    public XMLParser xmlParser = null;
    public LocalXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(Local.FILENAME);
    }


    @Override
    public void create(Local local) {

    }

    @Override
    public void update(Local local) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            Node newNode = local.localToXmlMapper(doc);
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == local.getId()) {
                        doc.replaceChild(node, newNode);
//                        element.getElementsByTagName("name").item(0).setTextContent(user.getNom());
//                        element.getElementsByTagName("login").item(0).setTextContent(user.getLogin());
//                        element.getElementsByTagName("password").item(0).setTextContent(user.getPassword());
//                        element.getElementsByTagName("role").item(0).setTextContent(String.valueOf(user.getRole()));
                    }
                }
            }
            xmlParser.saveDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Local local) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == local.getId()) {
                        node.getParentNode().removeChild(node);
                    }
                }
            }
            xmlParser.saveDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Local find(Local local) {
        return null;
    }

    @Override
    public Local findByNombre(int nombre) {
        return null;
    }

    @Override
    public List<Local> findAll() {
        List<Local> locals = new ArrayList<>();
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Local local = new Local();
                    locals.add(local.xmlToLocalMapper(nNode));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return locals;
    }

    @Override
    public Local findById(int localId) {
        Local local = new Local();
        List<Local> locals = findAll();
        for (Local item: locals) {
            if (item.getId() == localId) {
                local = item;
            }
        }
        return local;
    }
}
