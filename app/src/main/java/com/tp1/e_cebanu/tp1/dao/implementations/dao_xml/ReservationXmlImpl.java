package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.ReservationDao;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.Reservation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Java# version 1.8.0
 *
 * @class UserXmlImpl
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Mise en œuvre de lecture / écriture XML pour RESERVATIONS
 */
public class ReservationXmlImpl implements ReservationDao {

    public XMLParser xmlParser = null;
    public ReservationXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(Reservation.FILENAME);
    }
    @Override
    public void create(Reservation reservation) {
        Document doc = null;
        try {
            doc = xmlParser.getNodeListFromResources();
            Node parent = doc.getFirstChild();
            Node child = reservation.reservationToXmlMapper(doc);
            parent.appendChild((Node) child);

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
    public void update(Reservation reservation) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == reservation.getId()) {

                        Node user = element.getElementsByTagName("user").item(0).getFirstChild();
                        user.setNodeValue(String.valueOf(reservation.getUser().getId()));

                        Node local = element.getElementsByTagName("local").item(0).getFirstChild();
                        local.setNodeValue(String.valueOf(reservation.getLocal().getId()));

                        Node reason = element.getElementsByTagName("reason").item(0).getFirstChild();
                        reason.setNodeValue(String.valueOf(reservation.getReason().getId()));

                        Node additionalReason = element.getElementsByTagName("additional").item(0).getFirstChild();
                        additionalReason.setNodeValue(reservation.getAdditionalReason());

                        Node course = element.getElementsByTagName("course").item(0).getFirstChild();
                        course.setNodeValue(reservation.getCourse());

                        Node date = element.getElementsByTagName("date").item(0).getFirstChild();
                        date.setNodeValue(reservation.getDate().toString());
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
    public void delete(Reservation reservation) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == reservation.getId()) {
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
    public Reservation find(Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> findByDate(Calendar date) {
        List<Reservation> reservations = findAll();
        List<Reservation> results = new ArrayList<>();
        for (Reservation reservation: reservations) {
            if (((int) reservation.getDate().get(Calendar.YEAR)) == date.get(Calendar.YEAR)
                    && ((int) reservation.getDate().get(Calendar.MONTH)) == date.get(Calendar.MONTH)
                    && ((int) reservation.getDate().get(Calendar.DAY_OF_MONTH)) == date.get(Calendar.DAY_OF_MONTH)) {
                results.add(reservation);
            }
        }
        return results;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Reservation reservation = new Reservation();
                    reservations.add(reservation.xmlToReservationMapper(nNode));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
