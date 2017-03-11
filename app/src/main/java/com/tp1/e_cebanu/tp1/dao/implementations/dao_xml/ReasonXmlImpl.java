package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.ReasonDao;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.Reason;

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
 * Created by User on 09.03.2017.
 */

public class ReasonXmlImpl implements ReasonDao {

    public XMLParser xmlParser = null;
    public ReasonXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(Reason.FILENAME);
    }

    public Reason findById(int id) {
        Reason reason = new Reason();
        List<Reason> reasons = findAll();
        for (Reason item: reasons) {
            if (item.getId() == id) {
                reason = item;
            }
        }
        return reason;
    }

    @Override
    public List<Reason> findAll() {
        List<Reason> reasons = new ArrayList<>();
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Reason reason = new Reason();
                    reasons.add(reason.xmlToLocalMapper(nNode));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return reasons;
    }

    @Override
    public void delete(Reason reason) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == reason.getId()) {
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
}
