/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import beans.UserBean;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import no.ntnu.tollefsen.auth.AuthenticationService;

/**
 *
 * @author TrymV
 */
@Path("fant")
@Stateless
public class FantResource {

    @Inject
    AuthenticationService authenticationService;

    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserBean userBean;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") String userId) {
        User userToFind = null;
        List<User> allUsers = userBean.getAllUsers();;
        for(User user:allUsers) {
            if(user.getUserId().equals(userId)) {
                userToFind = user;
            }
        }
        return userToFind;
    }

    @POST
    @Path("/createUser/{psw}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUSer(User userToBeMade, @PathParam("psw") String psw) {
        System.out.println("\n TEST \n" + userToBeMade.getUserId() + " " + psw + " "
                + userToBeMade.getFirstName() + " " + userToBeMade.getLastName() + " "
                + userToBeMade.getEmail() + " " + userToBeMade.getPhoneNumber() + "\n");
        return userBean.createUser(psw, userToBeMade.getFirstName(), userToBeMade.getLastName(), userToBeMade.getEmail(), userToBeMade.getPhoneNumber());
    }
    
    @POST
    @Path("/login")
    public Response login(
            @FormParam("email") @NotBlank String email, //@FormParam
            @FormParam("pwd") @NotBlank String pwd,
            @Context HttpServletRequest request) {
        return authenticationService.loginV2(email, pwd, request);
    }
}
