/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TrymV
 */
@XmlRootElement
public class Sale {
    private String email;
    private String name;
    private String saleDescription;
    private Date created;
    private int price;
    private long orderId;
    
    public Sale(String email, String name, String saleDescription, int price, long orderId) {
        this.email = email;
        this.name = name;
        this.saleDescription = saleDescription;
        this.created = new Date();
        this.price = price;
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaleDescription() {
        return saleDescription;
    }

    public void setSaleDescription(String saleDescription) {
        this.saleDescription = saleDescription;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
