/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import beans.ItemBean;
import beans.UserBean;
import com.mycompany.fant.DatasourceProducer;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.sql.DataSource;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;
import no.ntnu.tollefsen.auth.Group;
import no.ntnu.tollefsen.auth.KeyService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author TrymV
 */
@Path("/fantmarket")
@Stateless
@Log
public class FantMarket {
    @Inject
    KeyService keyService;

    @Inject
    IdentityStoreHandler identityStoreHandler;

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "issuer")
    String issuer;

    /** 
     * The application server will inject a DataSource as a way to communicate 
     * with the database.
     */
    @Resource(lookup = DatasourceProducer.JNDI_NAME)
    DataSource dataSource;
    
    /** 
     * The application server will inject a EntityManager as a way to communicate 
     * with the database via JPA.
     */
    @PersistenceContext
    EntityManager em;

    @Inject
    PasswordHash hasher;

    @Inject
    JsonWebToken principal;
    
    @Inject
    UserBean userBean;
    
    @Inject
    ItemBean itemBean;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed(value = {Group.USER})
    public List<Item> showAllItems() {
        return itemBean.getAllItems();
    }
    
    @POST
    @Path("/newSale")
    @RolesAllowed(value = {Group.USER})
    public Response addItem(
    @FormParam("title") String title,
    @FormParam("description") String description,
    @FormParam("priceNok") BigDecimal priceNok) {
        System.out.println("Trying to sell item: " + title + "\nDescription: " + description + "\nPrice: " + priceNok);
        Item itemToSell = itemBean.addItem(em.find(User.class, principal.getName()), title, description, priceNok);
        if(itemToSell != null) {
            return Response.ok(itemToSell).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
