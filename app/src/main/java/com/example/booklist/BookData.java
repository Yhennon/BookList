package com.example.booklist;

import java.util.Date;

public class BookData {

    /* Declaring all the variables of the BookData Class, in accordance with the textViews that are in the booklist_layout.xml */

    String bookName;
    String deliveryAddress;
    String bookAuthor;
    String contactName;
    Date deliveryDeadline;

    /* Constructor */

    public BookData(String bookName, String deliveryAddress, String bookAuthor, String contactName, Date deliveryDeadline) {
        this.bookName = bookName;
        this.deliveryAddress = deliveryAddress;
        this.bookAuthor = bookAuthor;
        this.contactName = contactName;
        this.deliveryDeadline = deliveryDeadline;
    }

    /* Getters and Setters */

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Date getDeliveryDeadline() {
        return deliveryDeadline;
    }

    public void setDeliveryDeadline(Date deliveryDeadline) {
        this.deliveryDeadline = deliveryDeadline;
    }
}
