package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.ReservationDao;
import com.tp1.e_cebanu.tp1.models.Local;
import com.tp1.e_cebanu.tp1.models.Reservation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by User on 08.03.2017.
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

    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public Reservation find(Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> findByDate(Date date) {
        List<Reservation> reservations = findAll();
        List<Reservation> results = new ArrayList<>();
        for (Reservation reservation: reservations) {
            if (reservation.getDate().compareTo(date) == 0) {
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
