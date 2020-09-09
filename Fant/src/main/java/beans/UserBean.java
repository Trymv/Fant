/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mycompany.fant.DatasourceProducer;
import com.mycompany.fant.resources.User;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.sql.DataSource;
import lombok.extern.java.Log;
import no.ntnu.tollefsen.auth.Group;

/**
 *
 * @author TrymV
 */
@Log
@Stateless
public class UserBean {
    
    @Resource(lookup = DatasourceProducer.JNDI_NAME)
    DataSource dataSource;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    PasswordHash hasher;
    
    /**
     * Create a new user and adds it to the database.
     * @param pwd of the user to be made.
     * @param firstName of the user to be made.
     * @param lastName of the user to be made.
     * @param email of the user to be made.
     * @param phoneNumber of the user to be made.
     * @return the new user.
     */
    public User createUser(String pwd, String firstName, String lastName, String email, String phoneNumber) {
        System.out.println("\nTrying to create user using email: " + email + "\n");
        User user;
        
        if(email != null) {
            Query query = em.createNamedQuery(User.FIND_USER_BY_EMAIL);
            query.setParameter("email", email);
            List<User> foundUsers = query.getResultList();
            if(foundUsers.isEmpty()) {
                System.out.println("Creating user with email: " + email);
                user = new User();
                user.setPassword(hasher.generate(pwd.toCharArray()));
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                Group usergroup = em.find(Group.class, Group.USER);
                user.getGroups().add(usergroup);
                return em.merge(user);
            } else {
                System.out.println("Already user with email: " + email);
                log.log(Level.INFO, "user already exists {0}", email);
                throw new IllegalArgumentException("User with email " + email + " already exists");
            }
        } else {
            throw new IllegalArgumentException("Email was null");
        }        
    }
    
    /**
     * Find a user from the database with an email.
     * @param email to find in user database.
     * @return found user or null if no user was found with matching email.
     */
    public User findUserByEmail(String email) {
        User userToFind = null;
        Query query = em.createNamedQuery(User.FIND_USER_BY_EMAIL);
        if(email != null) {
            query.setParameter("email", email);
            List<User> foundUsers = query.getResultList();
            if(foundUsers.size() == 1) {
                System.out.println("Found user by email.");
                userToFind = foundUsers.get(0);
            }
        }
        return userToFind;
    }
    
    /**
     * Return a list of all users.
     * @return list of all user.
     */
    public List<User> getAllUsers() {
        Query query = em.createNamedQuery(User.FIND_ALL_USERS);
        return query.getResultList();
    }
}
