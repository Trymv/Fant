/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import no.ntnu.tollefsen.auth.AuthenticationService;

/**
 *
 * @author TrymV
 */
@Path("fant")
public class FantResource {

    AuthenticationService authenticationService = new AuthenticationService();
    @PersistenceContext
    EntityManager em;
    
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") long userId) {
        System.out.println("\ngetUser!\n");
        User user = em.find(User.class, userId);
        return user;
    }
    
    @POST
    @Path("/createUser/{psw}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUSer(User userToBeMade, @PathParam("psw") String psw) {
        System.out.println("\n TEST \n" + userToBeMade.getUserId() + " " + psw + " " +
                userToBeMade.getFirstName() + " " + userToBeMade.getLastName() + " " + 
                userToBeMade.getEmail()+ " " + userToBeMade.getPhoneNumber() + "\n");
        return authenticationService.createUserV2(userToBeMade.getUserId(), psw, 
                userToBeMade.getFirstName(), userToBeMade.getLastName(), userToBeMade.getEmail(), userToBeMade.getPhoneNumber());
    }
}
