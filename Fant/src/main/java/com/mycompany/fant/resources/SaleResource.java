/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author TrymV
 */
@Path("sales")
public class SaleResource {
    SaleService saleService = new SaleService();
    
    /**
     *
     * @return
     */
//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public List<Sale> getSales() {
//        return saleService.getAllSales();
//    }
//    
//    @GET
//    @Path("/{saleId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Sale getSale(@PathParam("saleId") long saleId) {
//        System.out.print("getSale has beeen called.");
//        return saleService.getSale(saleId);
//    }
//    
//    @GET
//    @Path("/dbTest")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String dbTest() {
//        String test = "Nothing";
//        try {
//            test = saleService.testingDb();
//            test = "try";
//        } catch (SQLException ex) {
//            Logger.getLogger(SaleResource.class.getName()).log(Level.SEVERE, null, ex);
//            test = "SQLException";
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(SaleResource.class.getName()).log(Level.SEVERE, null, ex);
//            test = "ClassNotFoundException";
//        }
//        return test;
//    }
}
