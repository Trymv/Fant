/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mycompany.fant.resources.Sale;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author TrymV
 */
public class DatabaseClass {
    private static Map<Long, Sale> sales = new HashMap<>();
    
    public static Map<Long, Sale> getSales() {
        return sales;
    }
    
    public String dbTest() {
        StringBuilder result = new StringBuilder();
        
        return result.toString();
    }
}
