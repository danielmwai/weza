///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tunaweza.core.business.model.persistence;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Version;
///**
// *
// * @author Daniel Mwai
// */
//@MappedSuperclass
//public class AbstractPersistentEntity implements PersistentEntity,
//		Serializable {
//
//	/**
//	 * Default version ID for serialization version management.
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//
//	@Version
//	@Column(name = "VERSION")
//	private int version=0;
//
//	public Long getId() {
//		return this.id;
//	}
//
//	/**
//	 * Sets the primary key identifier.
//	 * 
//	 * @param id
//	 *            Primary key identifier.
//	 */
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	/**
//	 * Returns the version number (used by Hibernate to manage optimistic
//	 * locking).
//	 * 
//	 * @return version Version number.
//	 */
//	public int getVersion() {
//		return this.version;
//	}
//
//	/**
//	 * <b>IMPORTANT NOTE:</b> The version property must <em>never</em> be
//	 * altered by the application in any way. To artificially increase the
//	 * version number see the LockMode.WRITE in the Hibernate reference manual.
//	 * 
//	 * @param version
//	 *            Version number.
//	 */
//	public void setVersion(final int version) {
//		this.version = version;
//	}
//}
