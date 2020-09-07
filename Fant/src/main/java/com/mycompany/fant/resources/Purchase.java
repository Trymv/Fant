/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fant.resources;

import java.io.Serializable;
import java.util.Date;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author TrymV
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "purchases")
public class Purchase implements Serializable {
	@Id
	@Column(name = "buyer_id")
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long buyerId;
        
        @Column(name = "item_id")
        private long itemId;
	
	@NotEmpty
	@Column(name = "purchase_date")
	private Date purchaseDate;
	
	@PrePersist
	protected void onCreate() {
		this.purchaseDate = new Date();
	}
	
	/** OWNING SIDE **/
        @NotEmpty
	@ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id", referencedColumnName = "buyer_id",
		nullable = false)
	private User buyerUser;
	
	
	/** REFERNENCING SIDE **/
	@JsonbTransient
	@Getter
	@OneToOne(mappedBy = "purchase")
	private Item item;	
    
}
