/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mycompany.fant.DatasourceProducer;
import com.mycompany.fant.resources.Item;
import com.mycompany.fant.resources.Purchase;
import com.mycompany.fant.resources.User;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import lombok.extern.java.Log;

/**
 *
 * @author TrymV
 */
@Log
@Stateless
public class PurchaseBean {
    @Resource(lookup = DatasourceProducer.JNDI_NAME)
    DataSource dataSource;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    ItemBean itemBean;
    
    public Purchase purchaseItem(User buyer, String itemId) {
        Item itemToBuy = itemBean.getItem(itemId);
        
        if((buyer != null) && (itemToBuy != null)) {
            if(itemToBuy.getPurchase() == null) { //To see the item has not already been purchased.
                Purchase purchase = em.merge(new Purchase(buyer));
                em.flush();
                em.lock(itemToBuy, LockModeType.PESSIMISTIC_WRITE); //So the object can be edited in the database.
                itemToBuy.setPurchase(purchase);
                em.refresh(purchase);
                em.refresh(itemToBuy);
                
                return purchase;
            }
        }
        return null;
    }
}
