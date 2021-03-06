/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import no.ntnu.tollefsen.auth.Group;

/**
 *
 * @author TrymV
 */
@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name = User.FIND_ALL_USERS,
		query = "SELECT u FROM users u ORDER BY u.firstName"),
	@NamedQuery(name = User.FIND_USER_BY_EMAIL,
		query = "SELECT u FROM users u WHERE u.email LIKE :email")
})
public class User implements Serializable {
    public final static String FIND_ALL_USERS = "User.findAllUsers";
    public final static String FIND_USER_BY_EMAIL = "User.findUserByEmail";
    
    public enum State {
        ACTIVE, INACTIVE
    }
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Email
    @NotEmpty
    private String email;
    
    @Column(name = "phone_number")
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+")
    private String phoneNumber;
    
    @JsonbTransient
    @Size(min = 6)
    private String password;
    
    @Column(name = "creation_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;
    
    @PrePersist
    protected void onCreate() {
        created = new Date();
    } 
    
    @Enumerated(EnumType.STRING)
    State currentState = State.ACTIVE;
    
    @ManyToMany
	@JoinTable(name = "AUSERGROUP",
		joinColumns = @JoinColumn(
			name = "id",
			referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(
			name = "name",
			referencedColumnName = "name"))
	List<Group> groups;

	/**
	 * REFERENCING SIDE *
         * To see the purchases a user has made.
	 */
	@JsonbTransient
	@Getter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyerUser")
	private List<Purchase> donePurchases;

	/**
	 * REFERENCING SIDE *
         * To see what items a user is or has sold.
	 */
	@JsonbTransient
	@Getter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sellerUser")
	private List<Item> ownedItems;

	public List<Group> getGroups() {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		return groups;

	}
}
