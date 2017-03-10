package com.tp1.e_cebanu.tp1.models;

import android.app.Application;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Java# version 1.8.0
 *
 * @class Reservation
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des reservations
 */

public class Reservation {
    private int id;
    private User user;
    private Local local;
    private Reason reason;
    private String additionalReason, course;
    private Date date;
    public static final String FILENAME = "reservations.xml";

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getAdditionalReason() {
        return additionalReason;
    }

    public void setAdditionalReason(String additionalReason) {
        this.additionalReason = additionalReason;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Reservation xmlToReservationMapper(Node node) {
        Reservation reservation = new Reservation();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            int userId = Integer.parseInt(eElement.getElementsByTagName("user").item(0).getTextContent());
            int localId = Integer.parseInt(eElement.getElementsByTagName("local").item(0).getTextContent());
            int reasonId = Integer.parseInt(eElement.getElementsByTagName("local").item(0).getTextContent());

            User user = AppService.getUsersService().findById(userId);
            Local local = AppService.getLocalsService().findById(localId);
            Reason reason = AppService.getReasonsService().findById(reasonId);

            DateFormat df = DateFormat.getDateInstance();
            Date date = null;
            try {
                date = df.parse(eElement.getElementsByTagName("date").item(0).getTextContent());
            } catch (ParseException e) {
                date = new Date();
            }
            reservation.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            reservation.setLocal(local);
            reservation.setUser(user);
            reservation.setReason(reason);
            reservation.setAdditionalReason(eElement.getElementsByTagName("autreRaison").item(0).getTextContent());
            reservation.setCourse(eElement.getElementsByTagName("cours").item(0).getTextContent());
            reservation.setDate(date);
        }
        return reservation;
    }

    public Node reservationToXmlMapper(Document doc) throws ParserConfigurationException {
        Element item = doc.createElement("item");
        Element id = doc.createElement("id");
        Element user = doc.createElement("user");
        Element local = doc.createElement("local");
        Element date = doc.createElement("date");
        Element reason = doc.createElement("reason");
        Element course = doc.createElement("course");
        Element additionalReason = doc.createElement("additional");

        id.setTextContent(String.valueOf(getId()));
        user.setTextContent(String.valueOf(getUser().getId()));
        local.setTextContent(String.valueOf(getLocal().getId()));
        reason.setTextContent(String.valueOf(getReason().getId()));
        course.setTextContent(getCourse());
        additionalReason.setTextContent(getAdditionalReason());
        date.setTextContent(getDate().toString());

        item.appendChild(id);
        item.appendChild(user);
        item.appendChild(local);
        item.appendChild(reason);
        item.appendChild(course);
        item.appendChild(additionalReason);
        item.appendChild(date);
        return item;
    }
}
