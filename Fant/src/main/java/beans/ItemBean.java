/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mycompany.fant.DatasourceProducer;
import com.mycompany.fant.resources.Item;
import com.mycompany.fant.resources.User;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import lombok.extern.java.Log;

/**
 *
 * @author TrymV
 */
@Log
@Stateless
public class ItemBean {
    
    @Resource(lookup = DatasourceProducer.JNDI_NAME)
    DataSource dataSource;
    
    @PersistenceContext
    EntityManager em;
    
    /**
     * Returns a list of all items in the database.
     * @return list of all items in the database.
     */
    public List<Item> getAllItems() {
        Query query = em.createNamedQuery(Item.FIND_ALL_ITEMS);
        return query.getResultList();
    }
    
    public Item addItem(User seller, String title, String description, BigDecimal priceNok) {
        return em.merge(new Item(seller, title, description, priceNok));
    }
}
