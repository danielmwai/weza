package com.tunaweza.web.spring.configuration.topic.bean;


import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import java.util.List;


/**
 * @author Jose Marcucci
 */
public class FeedBackBean 
{
	
	private String text;

	private String mentorName;

	private List<ExerciseTransaction> exerciseTransactions;

	
	/*
	 *
         * @param text
         * 
	 */
	public void setText(String text){

		this.text = text;

	}
	
	/*
	 *
         * @return text
         * 
	 */
	public String getText(){

		return text;

	}

	/*
	 *
         * @param mentorName
         * 
	 */
	public void setMentorName(String mentorName){

		this.mentorName = mentorName;

	}
	
	/*
	 *
         * @return mentorName
         * 
	 */
	public String getMentorName(){

		return mentorName;

	}


	/*
	 *
         * @param exerciseTransactions
         * 
	 */
	public void setExerciseTransactions(
		List<ExerciseTransaction> exerciseTransactions ){

		this.exerciseTransactions = exerciseTransactions;

	}
	
	/*
	 *
         * @return exerciseTransactions
         * 
	 */
	public List<ExerciseTransaction> getExerciseTransactions(){

		return exerciseTransactions;

	}

		
}
