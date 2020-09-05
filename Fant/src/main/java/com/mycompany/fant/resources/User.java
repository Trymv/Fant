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
    
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Email
    @NotEmpty
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @JsonbTransient
    private String password;
    
    @Column(name = "creation_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;
    
    @PrePersist
    protected void onCreate() {
        created = new Date();
    } 
    
    @ManyToMany
	@JoinTable(name = "users",
		joinColumns = @JoinColumn(
			name = "user_id",
			referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(
			name = "name",
			referencedColumnName = "name"))
	List<Group> groups;

	/**
	 * REFERENCING SIDE *
	 */
	@JsonbTransient
	@Getter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchases")
	private List<Purchase> donePurchases;

	/**
	 * REFERENCING SIDE *
	 */
	@JsonbTransient
	@Getter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
	private List<Item> ownedItems;

	public List<Group> getGroupMembership() {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		return groups;

	}
}
