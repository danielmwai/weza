/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.service.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.dao.mentor.MentorDao;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.exercise.ExerciseTransactionDao;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */


@Service("mentorService")
@Transactional
public class MentorServiceImpl implements MentorService{

	
	@Autowired
	MentorDao mentorDao;
	
	@Autowired
	ExerciseTransactionDao exerciseTransactionDAO;

	@Override
	public List<Mentor> getAllMentors() {
		return mentorDao.getAllMentors();
	}

	@Override
	public Mentor getMentorById(Long mentorId)
			throws MentorNotFoundException {
		return mentorDao.getMentorById(mentorId);
	}

	@Override
	public Mentor saveMentor(Mentor mentor) throws MentorExistsException {
		return mentorDao.saveMentor(mentor);
	}

	@Override
	public Mentor getMentorByUser(User user) throws MentorNotFoundException {
		return mentorDao.getMentorByUser(user);
	}

	@Override
	public List<MentorTemplate> getMentorTemplatesByMentor(long mentorId) {
		return mentorDao.getMentorTemplatesByMentor(mentorId);
	}

	@Override
	public void saveMentorTemplatesToMentor(Mentor mentor,
			List<MentorTemplate> mentorTemplates) {
		mentorDao.saveMentorTemplatesToMentor(mentor, mentorTemplates);

	}
	
	@Override
	public List<Integer> getTransactionLastThreeMonths(String mentorId){
		List<ExerciseTransaction> transactionList =
			exerciseTransactionDAO.getExerciseTransactions();
		
		List<Integer> listMonths = new ArrayList<Integer>();
		
		Calendar calToday = Calendar.getInstance();
		listMonths.add(calToday.get(Calendar.MONTH));
		
		Calendar calOneMonth = Calendar.getInstance();
		calOneMonth.add(Calendar.MONTH, -1);
		listMonths.add(calOneMonth.get(Calendar.MONTH));
		
		Calendar calTwoMonth = Calendar.getInstance();
		calTwoMonth.add(Calendar.MONTH, -2);
		listMonths.add(calTwoMonth.get(Calendar.MONTH));
		
		
		List<Integer> listTransactions = new ArrayList<Integer>();
		
		for (int i=0; i<listMonths.size(); i++){
			
			int count = 0;
			
			for(ExerciseTransaction et: transactionList){
				

			    try{

				 if (mentorId.equals(et.getMentor().getId()+"") ){	
					 
					 Calendar temp =Calendar.getInstance();
					 temp.setTime(et.getTransanctionDate());
					 
					 int transactionMonth = temp.get(Calendar.MONTH);
					 
					 if (transactionMonth == listMonths.get(i).intValue()){
						 
						 count++;
					 }
					 
				 }

			   }catch(Exception e){

			   }
			    
			}
			
			listTransactions.add(count);
			
		}
		
		return listTransactions;
			
	}
	
	@Override
	public List<String> getNameLastMonths(){
		
		List<String> listNameMonths = new ArrayList<String>();
		
		
		Calendar calToday = Calendar.getInstance();
		Date d1 = new Date(calToday.getTimeInMillis());
		listNameMonths.add(new SimpleDateFormat("MMMM").format(d1).toString());
		
		
		Calendar calOneMonth = Calendar.getInstance();
		calOneMonth.add(Calendar.MONTH, -1);
		Date d2 = new Date(calOneMonth.getTimeInMillis());
		listNameMonths.add(new SimpleDateFormat("MMMM").format(d2).toString());
		
		Calendar calTwoMonth = Calendar.getInstance();
		calTwoMonth.add(Calendar.MONTH, -2);
		Date d3 = new Date(calTwoMonth.getTimeInMillis());
		listNameMonths.add(new SimpleDateFormat("MMMM").format(d3).toString());
		
		return listNameMonths;
		
	}
	
	
	@Override
	public List<String> getAvergeTimeToRead(String mentorId){
		
		List<Integer> listMonths = new ArrayList<Integer>();
		
		Calendar calToday = Calendar.getInstance();
		listMonths.add(calToday.get(Calendar.MONTH));
		
		Calendar calOneMonth = Calendar.getInstance();
		calOneMonth.add(Calendar.MONTH, -1);
		listMonths.add(calOneMonth.get(Calendar.MONTH));
		
		Calendar calTwoMonth = Calendar.getInstance();
		calTwoMonth.add(Calendar.MONTH, -2);
		listMonths.add(calTwoMonth.get(Calendar.MONTH));
		
		List<ExerciseTransaction> transactionList =
			exerciseTransactionDAO.getExerciseTransactions();
		
		List<StudentExercise> studentExerciseList = new ArrayList<StudentExercise>();
		
		List<String> listAverage  = new ArrayList<String>();
		
		for (int i=0; i<listMonths.size(); i++){
			
			List<Long> listDiff  = new ArrayList<Long>();
		
			for (ExerciseTransaction exerciseTMentor:transactionList){
				
				Calendar temp =Calendar.getInstance();
				temp.setTime(exerciseTMentor.getTransanctionDate());
				 
				int transactionMonth = temp.get(Calendar.MONTH);
				
				if (transactionMonth == listMonths.get(i).intValue()){
				
					try{
						
							if (mentorId.equals(exerciseTMentor.getMentor().getId()+"") ){
									
									studentExerciseList.add(exerciseTMentor.getStudentExercise());
									StudentExercise se = exerciseTMentor.getStudentExercise();
									
									List<ExerciseTransaction> trasactionsExerciseList = 
										se.getExerciseTransaction();
									
									int index = trasactionsExerciseList.indexOf(exerciseTMentor)-1;
									
									ExerciseTransaction exerciseTStudent = trasactionsExerciseList.get(index);
										
									
									long diff = exerciseTMentor.getReadDate().getTime() - 
										exerciseTStudent.getTransanctionDate().getTime();
                                    String estimatedTime;
									long totalAverageTime = diff/(1000 * 60 * 60);

									listDiff.add(totalAverageTime);
									
								
							}
							
							
						}catch(Exception e){
							
						}
				}
			}
			
			listAverage.add(getAvergeofList(listDiff));
		}	
		
		return listAverage;		
		
	}
	
	
	@Override
	public List<String> getAvergeTimeToResponse(String mentorId){
		
		List<Integer> listMonths = new ArrayList<Integer>();
		
		Calendar calToday = Calendar.getInstance();
		listMonths.add(calToday.get(Calendar.MONTH));
		
		Calendar calOneMonth = Calendar.getInstance();
		calOneMonth.add(Calendar.MONTH, -1);
		listMonths.add(calOneMonth.get(Calendar.MONTH));
		
		Calendar calTwoMonth = Calendar.getInstance();
		calTwoMonth.add(Calendar.MONTH, -2);
		listMonths.add(calTwoMonth.get(Calendar.MONTH));
		
		List<ExerciseTransaction> transactionList =
			exerciseTransactionDAO.getExerciseTransactions();
		
		List<StudentExercise> studentExerciseList = new ArrayList<StudentExercise>();
		
		List<String> listAverage  = new ArrayList<String>();
		
		for (int i=0; i<listMonths.size(); i++){
			
			List<Long> listDiff  = new ArrayList<Long>();
		
			for (ExerciseTransaction exerciseTMentor:transactionList){
				
				Calendar temp =Calendar.getInstance();
				temp.setTime(exerciseTMentor.getTransanctionDate());
				 
				int transactionMonth = temp.get(Calendar.MONTH);
				
				if (transactionMonth == listMonths.get(i).intValue()){
				
					try{
						
							if (mentorId.equals(exerciseTMentor.getMentor().getId()+"") ){
									
									studentExerciseList.add(exerciseTMentor.getStudentExercise());
									StudentExercise se = exerciseTMentor.getStudentExercise();
									
									List<ExerciseTransaction> trasactionsExerciseList = 
										se.getExerciseTransaction();
									
									int index = trasactionsExerciseList.indexOf(exerciseTMentor)-1;
									
									ExerciseTransaction exerciseTStudent = trasactionsExerciseList.get(index);
										
									
									long diff = exerciseTMentor.getTransanctionDate().getTime() - 
										exerciseTStudent.getTransanctionDate().getTime();

                                    String estimatedTime;
									long totalAverageTime = diff/(1000 * 60 * 60);

									listDiff.add(totalAverageTime);

							}


						}catch(Exception e){

						}
				}
			}
			
			listAverage.add(getAvergeofList(listDiff));
		}	
		
		return listAverage;
	}
	
	
	@Override
	public String getAvergeofList(List<Long> listofDiff){
		
		long diff = 0;
		String estimatedTime;
		for (Long temp:listofDiff){
			
			diff = diff + temp.longValue();
			
		}

       if (!(listofDiff.size() == 0)){
		   long totalAverageTime = diff/listofDiff.size();
           if(totalAverageTime>24)
                                    {
                                       totalAverageTime =  totalAverageTime/30;
                                       if(totalAverageTime>30)
                                       {
                                          totalAverageTime = totalAverageTime/30;
                                          estimatedTime = totalAverageTime+" Month";
                                        if(totalAverageTime>1)
                                       {
                                              estimatedTime +="s";
                                       }
                                       }
                                       else
                                       {
                                           estimatedTime = totalAverageTime+" Day";
                                            if(totalAverageTime>1)
                                       {
                                              estimatedTime +="s";
                                       }
                                       }

                                    }
                                    else
                                    {
                                       estimatedTime = "" + totalAverageTime+" Hr";
                                       if(totalAverageTime>1)
                                       {
                                              estimatedTime +="s";
                                       }

                                    }

		
	    return (estimatedTime);
		
		}
       else{
		
			return "-";
		
		}	
			
	}
}
