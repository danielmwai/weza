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

package com.tunaweza.core.business.service.exercise.impl;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("exerciseService")
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

	@Autowired
	private StudentExerciseDAO studentExerciseDao;
	
	@Autowired
	private ExerciseTransactionDAO exerciseTransactionDAO;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private ModuleDAO moduleDao;
	
	@Autowired
	private TopicService topicService;
	
	@Override
	public List<ExerciseBean> getStudentExerciseByModule(String userId,
			String moduleId) throws StudentExerciseNotFoundException, NumberFormatException, UserDoesNotExistException, ModuleDoesNotExistException {
		
		User user = userDao.findUserById(Long.valueOf(userId));
		Module module = moduleDao.findModuleById(Long.valueOf(moduleId));
		List<StudentExercise> studentExerciseList = studentExerciseDao.getAllStudentExerciseByModule(user, module).get(0);
		List<Topic> topics = studentExerciseDao.getAllStudentExerciseByModule(user, module).get(1);
		List<Topic> attemptedTopics = new ArrayList<Topic>();
		List<ExerciseBean> exerciseBeanList = new ArrayList<ExerciseBean>();
		if(studentExerciseList!=null)
		{
			for(StudentExercise studentExercise : studentExerciseList){
			if(studentExercise.getExercise().isExercise())
			{
			ExerciseBean exerciseBean = new ExerciseBean();
			exerciseBean.setExerciseModule(studentExercise.getExercise().getModule().getName());
			exerciseBean.setExerciseTitle(
					StringEscapeUtils.unescapeHtml(studentExercise.getExercise().getName()));
			exerciseBean.setExerciseStatus(topicService.getStatusOfExercise(user, studentExercise.getExercise()));
			exerciseBean.setLastSubmitDate(exerciseTransactionDAO.getLastUserExerciseTransactionByType(studentExercise,"StudentToMentor").getTransanctionDate().toString());
			exerciseBean.setLastTransactionDate(exerciseTransactionDAO.getLastUserExerciseTransaction(studentExercise).getTransanctionDate().toString());
			exerciseBeanList.add(exerciseBean);
			}
		}
	
		}
		for(Topic topic :topics){
				ExerciseBean exerciseBean = new ExerciseBean();
				exerciseBean.setExerciseModule(topic.getModule().getName());
				exerciseBean.setExerciseTitle(StringEscapeUtils.unescapeHtml(topic.getName()));
				exerciseBean.setLastSubmitDate("Not Started");
				exerciseBean.setLastTransactionDate("Not Started");
				exerciseBeanList.add(exerciseBean);
		}
		return exerciseBeanList;
	}	
}
