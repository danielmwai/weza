/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.licence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author naistech
 */
 
@Entity
@Table(name=License.TABLE_NAME)
public class License {
 @Id
 @GeneratedValue
 public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME="license_types";
	
	@Column(name="max_users",nullable=false)
	private Long max_users;
	
	@Column(name="cost",nullable=false)
	private Float cost;
	
	@Column(name="name",nullable=false,columnDefinition="varchar(250) default 'name'")
	private String name;
	
	@Column(name="is_associated")
	private boolean associated = false;
	
	

	public boolean isAssociated() {
		return associated;
	}

	public void setAssociated(boolean associated) {
		this.associated = associated;
	}

	/**
	 * gets the name of the license
	 * 
	 * @return the name of the license
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the license 
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the maximum number of users that the system caters for
	 * 
	 * @return max_users
	 */
	public Long getMax_users() {
		return max_users;
	}

	/**
	 * sets the maximum number of users that the license caters for
	 * 
	 * @param max_users
	 */
	public void setMax_users(Long max_users) {
		this.max_users = max_users;
	}
	/**
	 * gets the cost of that license
	 * 
	 * @return
	 */
	public Float getCost() {
		return cost;
	}

	/**
	 * sets the cost of that license
	 * 
	 * @param cost
	 */
	public void setCost(Float cost) {
		this.cost = cost;
	}
	

}
