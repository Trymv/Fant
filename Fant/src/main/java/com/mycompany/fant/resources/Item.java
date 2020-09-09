/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author 
 */
@Entity(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name = Item.FIND_ALL_ITEMS, query = "SELECT i FROM items i")})
public class Item implements Serializable {

	public final static String FIND_ALL_ITEMS = "findAllItems";

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id; //Item ID
        
	@NotEmpty
	@Column(name = "title")
	private String title;

	@NotEmpty
	@Column(name = "description")
	private String description;

	@Column(name = "created_time", nullable = false)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	@Positive
	@Column(name = "price_nok")
	private BigDecimal priceNok;

	/**
	 * OWNER SIDE *
         * Relation to see who owns the item.
	 */
	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "sller_user_id", referencedColumnName = "id",
		nullable = false)
	private User sellerUser;

	/**
	 * OWNER SIDE *
         * Relation to see who purchased an item.
	 */
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "purchase_id", referencedColumnName = "id")
	private Purchase purchase;
        
        public Item(User owner, String title, String description, BigDecimal priceNok) {
            this.sellerUser = owner;
            this.title = title;
            this.description = description;
            this.priceNok = priceNok;
        }
}
