/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import database.DatabaseClass;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TrymV
 */
public class SaleService {
    private DatabaseClass saleDatabase = new DatabaseClass();
    private Map<Long, Sale> sales = saleDatabase.getSales();
    
//    public SaleService() {
//        sales.put(1L, new Sale(1L, "Kim", "A nice stone chair", 1000));
//        sales.put(2L, new Sale(2L, "Henrik", "A nice hug", 50));
//        sales.put(3L, new Sale(3L, "Aleksander", "WTS dragon scim 5m!", 50000));
//    }
//    
//    public List<Sale> getAllSales() {
//        return new ArrayList<Sale>(sales.values());
//    }
//    
//    public String testingDb() throws SQLException, ClassNotFoundException {
//        return saleDatabase.dbTest();
//    }
//    
//    public Sale getSale(long saleId) {
//        return sales.get(saleId);
//    }
//    
//    public Sale addSale(Sale sale) {
//        sale.setId(sales.size()+1);
//        sales.put(sale.getId(), sale);
//        return sale;
//    }
//    
//    public Sale updateSale(Sale sale) {
//        if(sale.getId() <= 0) {
//            return null;
//        }
//        sales.put(sale.getId(), sale);
//        return sale;
//    }
//    
//    public Sale removeSale(long saleId) {
//        return sales.remove(saleId);
//    }
}
