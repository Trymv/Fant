/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import beans.UserBean;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import no.ntnu.tollefsen.auth.AuthenticationService;
import no.ntnu.tollefsen.auth.Group;
import org.eclipse.microprofile.jwt.JsonWebToken;

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
    JsonWebToken principal;
    
    @Inject
    PasswordHash hasher;
    
    @Inject
    IdentityStoreHandler identityStoreHandler;
    
    @Inject
    UserBean userBean;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") String userId) {
        User userToFind = null;
        List<User> allUsers = userBean.getAllUsers();
        for(User user:allUsers) {
            if(user.getUserId().equals(userId)) {
                userToFind = user;
            }
        }
        return userToFind;
    }
    
    @GET
    @Path("/currentuser")
    @RolesAllowed(value = {Group.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public User getCurrentUser() {
        System.out.println("Current user was called!");
        return em.find(User.class, principal.getName());
    }

    @POST
    @Path("/createUser")
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(
            @QueryParam("FirstName") @NotBlank String firstName,
            @QueryParam("LastName") @NotBlank String lastName,
            @QueryParam("email") @NotBlank String email, 
            @QueryParam("pwd") @NotBlank String pwd,
            @QueryParam("phoneNumber") @NotBlank String phoneNumber,
            @Context HttpServletRequest request) {
        System.out.println("\n TEST \n" + " " + pwd + " "
                + firstName + " " + lastName + " "
                + email + " " + phoneNumber + "\n");
        return userBean.createUser(pwd, firstName, lastName, email, phoneNumber);
    }
    
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @QueryParam("email") @NotBlank String email, //@FormParam
            @QueryParam("pwd") @NotBlank String pwd,
            @Context HttpServletRequest request) {
        return authenticationService.loginV2(email, pwd, request);
    }
    
    @PUT
    @Path("/changePassword")
    @RolesAllowed(value = {Group.USER})
    public Response changePassword(
            @FormParam("email") String email,
            @FormParam("currentPwd") String currentPwd,
            @FormParam("newPwd") String newPwd,
            @Context SecurityContext sc) {
        User userToChangePwd = userBean.findUserByEmail(email);
        if(userToChangePwd == null) {
            System.out.println("\nWrong password or no user with the email: " + email + ".\n");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        CredentialValidationResult result = identityStoreHandler.validate(
                    new UsernamePasswordCredential(userToChangePwd.getUserId(), currentPwd));
        if(result.getStatus() != CredentialValidationResult.Status.VALID) {
            System.out.println("\nWrong password or no user with the email: " + email + ".\n");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if(newPwd == null || newPwd.length() < 6) {
            System.out.println("\nNew password has to be longer than 6.\n");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            userToChangePwd.setPassword(hasher.generate(newPwd.toCharArray()));
            em.merge(userToChangePwd);
            System.out.println("Password was successfully changed!");
            return Response.ok().build();
        }
        
    }
}
