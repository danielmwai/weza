/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.file;

/**
 *
 * @author Daniel Mwai
 */

import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Lob;
import java.sql.Blob;

@Entity
@Table(name = File.TABLE_NAME)
public class File extends AbstractPersistentEntity
{

	public static final String TABLE_NAME = "file";
	private static final long serialVersionUID = 1L;

	@Column(name = "file_name")
	private String fileName;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "file", length = 16777215)
	private Blob file;
	
	@Column(name="content_type")
	private String contentType;

	@OneToOne(mappedBy = "file")
	private Topic exercise;

	@OneToOne(mappedBy = "file")
	private ExerciseTransaction exerciseTransaction;

	@OneToOne(mappedBy = "file")
	private GoodAnswer goodAnswer;

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}	
	
	/**
	 * @return the contentType
	 */
	public String getContentType()
	{
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	/**
	 * @return the file
	 */
	public Blob getFile()
	{
		return file;
	}

	/**
	 * @param file
	 *            the path to file
	 */
	public void setFile(Blob file)
	{
		this.file = file;
	}

	/**
	 * @return the exercise
	 */
	public Topic getExercise()
	{
		return exercise;
	}

	/**
	 * @param exercise
	 *            the exercise to set
	 */
	public void setExercise(Topic exercise)
	{
		this.exercise = exercise;
	}

	/**
	 * @return the goodAnswer
	 */
	public GoodAnswer getGoodAnswer()
	{
		return goodAnswer;
	}

	/**
	 * @param goodAnswer
	 *            the goodAnswer to set
	 */
	public void setGoodAnswer(GoodAnswer goodAnswer)
	{
		this.goodAnswer = goodAnswer;
	}

	/**
	 * @param exerciseTransaction
	 *            the exerciseTransaction to set
	 */
	public void setExerciseTransaction(ExerciseTransaction exerciseTransaction)
	{
		this.exerciseTransaction = exerciseTransaction;
	}

	/**
	 * @return the exerciseTransaction
	 */
	public ExerciseTransaction getExerciseTransaction()
	{
		return exerciseTransaction;
	}

}