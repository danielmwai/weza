/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.solution;

import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseExistsException;
import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseNotFoundException;
import com.tunaweza.core.business.dao.exceptions.topic.TopicNotFoundExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.exercise.StudentExerciseDao;
import com.tunaweza.core.business.dao.exercise.transaction.ExerciseTransactionTypeDao;
import com.tunaweza.core.business.dao.file.FileDao;
import com.tunaweza.core.business.dao.topic.TopicDao;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.exercise.ExerciseTransactionDao;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.file.File;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.exercise.GradeExerciseBean;
import com.tunaweza.core.business.service.mentor.MentorTemplateService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserService;
import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author naistech
 */
@Service("solutionService")
@Transactional
public class SolutionServiceImpl implements SolutionService {
	@Autowired
	private FileDao fileDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private StudentExerciseDao studentExerciseDao;

	@Autowired
	private ExerciseTransactionDao exerciseTransactionDao;

	@Autowired
	private ExerciseTransactionTypeDao exerciseTransactionTypeDao;

	@Autowired
	private UserService userService;

	@Autowired
	private TopicService topicService;

	@Autowired
	private MentorTemplateService mentorTemplateService;

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public void createStudentExercise(User user, PostSolutionBean solutionBean)
			throws StudentExerciseExistsException {
		StudentExercise studentExercise = new StudentExercise();
		Long topicId = Long.valueOf(solutionBean.getTopicId());
		studentExercise.setExercise(topicDao.findById(topicId));
		studentExercise.setStudent(user.getStudent());
		studentExerciseDao.addStudentExercise(studentExercise);

		ExerciseTransaction transaction = new ExerciseTransaction();

		CommonsMultipartFile multipartFile = solutionBean.getSolution();

		File file = new File();
		file.setFile(convertFileToBytes(multipartFile));
		file.setContentType(multipartFile.getContentType());
		file.setFileName(multipartFile.getOriginalFilename());
		fileDao.saveFile(file);

		transaction.setFile(file);
		transaction.setStudentExercise(studentExercise);
		transaction.setComment(solutionBean.getMessage());
		transaction.setSubject(solutionBean.getSubject());

		// set transaction date to current date
		Date timestamp = Calendar.getInstance().getTime();
		transaction.setTransactionDate(timestamp);

		// set transaction type
		ExerciseTransactionType transactionType = exerciseTransactionTypeDao
				.getExerciseTransactionTypeByName("StudentToMentor");

		// If the transaction type does not exist create it
		if (transactionType == null) {
			ExerciseTransactionType exerciseTransactionType = new ExerciseTransactionType();
			exerciseTransactionType.setDescription("Student To Mentor");
			exerciseTransactionType.setName("StudentToMentor");

			// save the transaction
			exerciseTransactionTypeDao
					.saveExerciseTransactionType(exerciseTransactionType);
			transactionType = exerciseTransactionTypeDao
					.getExerciseTransactionTypeByName("StudentToMentor");
		}
		log.info("\n\n\n >>>>>>>>>>>>>> Solution service transactionType:"
				+ transactionType + "<<<<<<< \n\n\n");
		transaction.setExerciseTransactionType(transactionType);

		// persist transaction
		exerciseTransactionDao.saveExerciseTransaction(transaction);

	}

	@Override
	public void replaceLastExerciseTransaction(User user,
			PostSolutionBean solutionBean) {
		Long topicId = Long.valueOf(solutionBean.getTopicId());
		Topic exercise = topicDao.findById(topicId);
		StudentExercise studentExercise = studentExerciseDao
				.getStudentExerciseByExercise(user, exercise);

		CommonsMultipartFile multipartFile = solutionBean.getSolution();

		File file = new File();
		file.setFile(convertFileToBytes(multipartFile));
		file.setContentType(multipartFile.getContentType());
		file.setFileName(multipartFile.getOriginalFilename());
		fileDao.saveFile(file);

		ExerciseTransaction transaction = exerciseTransactionDao
				.getLastUserExerciseTransaction(studentExercise);
		transaction.setFile(file);
		transaction.setStudentExercise(studentExercise);
		transaction.setComment(solutionBean.getMessage());
		transaction.setSubject(solutionBean.getSubject());

		// set transaction date to current date
		Date timestamp = Calendar.getInstance().getTime();
		transaction.setTransactionDate(timestamp);

		// set transaction type
		ExerciseTransactionType transactionType = exerciseTransactionTypeDao
				.getExerciseTransactionTypeByName("StudentToMentor");

		// If the transaction type does not exist create it
		if (transactionType == null) {
			ExerciseTransactionType exerciseTransactionType = new ExerciseTransactionType();
			exerciseTransactionType.setDescription("StudentToMentor");
			exerciseTransactionType.setName("StudentToMentor");

			// save the transaction
			exerciseTransactionTypeDao
					.saveExerciseTransactionType(exerciseTransactionType);
			transactionType = exerciseTransactionTypeDao
					.getExerciseTransactionTypeByName("StudentToMentor");
		}

		transaction.setExerciseTransactionType(transactionType);

		// persist transaction
		exerciseTransactionDao.saveExerciseTransaction(transaction);

	}

	@Override
	public void resubmitExerciseTransaction(User user,
			PostSolutionBean solutionBean) {
		Long topicId = Long.valueOf(solutionBean.getTopicId());
		Topic exercise = topicDao.findById(topicId);
		StudentExercise studentExercise = studentExerciseDao
				.getStudentExerciseByExercise(user, exercise);
		ExerciseTransaction transaction = new ExerciseTransaction();

		// get a transaction type.
		ExerciseTransactionType transactionType = exerciseTransactionTypeDao
				.getExerciseTransactionTypeByName("StudentToMentor");

		// If the transaction type does not exist create it
		if (transactionType == null) {
			ExerciseTransactionType exerciseTransactionType = new ExerciseTransactionType();
			exerciseTransactionType.setDescription("StudentToMentor");
			exerciseTransactionType.setName("StudentToMentor");

			// save the transaction
			exerciseTransactionTypeDao
					.saveExerciseTransactionType(exerciseTransactionType);
			transactionType = exerciseTransactionTypeDao
					.getExerciseTransactionTypeByName("StudentToMentor");
		}

		// set transaction date to current date
		Date timestamp = Calendar.getInstance().getTime();

		CommonsMultipartFile multipartFile = solutionBean.getSolution();

		File file = new File();
		file.setFile(convertFileToBytes(multipartFile));
		file.setContentType(multipartFile.getContentType());
		file.setFileName(multipartFile.getOriginalFilename());
		fileDao.saveFile(file);

		transaction.setFile(file);
		transaction.setStudentExercise(studentExercise);
		transaction.setComment(solutionBean.getMessage());
		transaction.setSubject(solutionBean.getSubject());
		transaction.setTransactionDate(timestamp);
		transaction.setExerciseTransactionType(transactionType);

		exerciseTransactionDao.saveExerciseTransaction(transaction);
	}

	private Blob convertFileToBytes(CommonsMultipartFile file) {
		return NonContextualLobCreator.INSTANCE.createBlob(file.getBytes());

	}

	@Override
	public ExerciseTransaction getLastExerciseTransaction(
			StudentExercise studentExercise) {
		return exerciseTransactionDao
				.getLastUserExerciseTransaction(studentExercise);
	}

        @Override
	public ExerciseTransaction getExerciseTransactionById(long id) {
		return exerciseTransactionDao.getExerciseTransaction(id);
	}

	@Override
	public List<ExerciseTransaction> getExerciseTransactionByStudentExercise(
			StudentExercise studentExercise) {
		return exerciseTransactionDao
				.getExerciseTransactionByStudentExercise(studentExercise);
	}

	@Override
	public List<ExerciseTransaction> getExerciseTransactionByMentor(
			Mentor mentor) {
		return exerciseTransactionDao.getExerciseTransactionByMentor(mentor);
	}

	@Override
	public List<ExerciseTransaction> getExerciseTransactionByExerciseTransactionType(
			ExerciseTransactionType transactionType) {
		return exerciseTransactionDao
				.getExerciseTransactionByExerciseTransactionType(transactionType);
	}

	@Override
	public StudentExercise getStudentExerciseByStudentAndTopic(User user,
			Topic exercise) {
		return studentExerciseDao.getStudentExerciseByExercise(user, exercise);
	}

	@Override
	public StudentExercise getStudenExercise(String userid, String topicid) {

		try {

			User user = userService.getUserById(Long.valueOf(userid));

			Topic exercise = topicService.getTopicById(Long.valueOf(topicid));

			return studentExerciseDao.getStudentExerciseByExercise(user,
					exercise);

		} catch (NumberFormatException | UserDoesNotExistException | TopicNotFoundExistException e) {

		}

		return null;

	}

	@Override
	public ExerciseTransaction getExerciseLastTransaction(
			StudentExercise studentExercise) {

		return exerciseTransactionDao
				.getLastUserExerciseTransaction(studentExercise);

	}

	@Override
	public String getMentorName(ExerciseTransaction exerciseTransaction) {

		try {
			Mentor mentor = exerciseTransaction.getMentor();

			User user = mentor.getUser();

			return user.getFirstName() + " " + user.getLastName();

		} catch (Exception e) {

		}

		return null;

	}

	@Override
	public StudentExercise getStudentExerciseById(long exerciseId)
			throws StudentExerciseNotFoundException {
		return studentExerciseDao.findStudentExerciseById(exerciseId);

	}

	@Override
	public void addMentorExerciseTransaction(Mentor mentor,
			GradeExerciseBean gradedExercise) throws NumberFormatException,
			StudentExerciseNotFoundException {
		File file = new File();
		Calendar rightNow = Calendar.getInstance();
		if (gradedExercise.getAttachedFile() != null
				&& gradedExercise.getAttachedFile().getSize() > 0) {
			file.setFile(convertFileToBytes(gradedExercise.getAttachedFile()));
			file.setContentType(gradedExercise.getAttachedFile()
					.getContentType());
			file.setFileName(gradedExercise.getAttachedFile()
					.getOriginalFilename());
			file = fileDao.saveFile(file);
		}

		ExerciseTransactionType transactionType = exerciseTransactionTypeDao
				.getExerciseTransactionTypeByName("MentorToStudent");

		// If the transaction type does not exist create it
		if (transactionType == null) {
			ExerciseTransactionType exerciseTransactionType = new ExerciseTransactionType();
			exerciseTransactionType.setDescription("MentorToStudent");
			exerciseTransactionType.setName("MentorToStudent");

			// save the transaction
			exerciseTransactionTypeDao
					.saveExerciseTransactionType(exerciseTransactionType);
			transactionType = exerciseTransactionTypeDao
					.getExerciseTransactionTypeByName("MentorToStudent");
		}

		StudentExercise exercise = getStudentExerciseById(Long
				.valueOf(gradedExercise.getExerciseId()));
		ExerciseTransaction exerciseTransaction = new ExerciseTransaction();
		exerciseTransaction.setComment(gradedExercise.getMessage());
		exerciseTransaction.setMentor(mentor);
		exerciseTransaction.setSubject(gradedExercise.getSubject());
		exerciseTransaction.setTransactionDate(rightNow.getTime());
		if (gradedExercise.getAttachedFile() != null
				&& gradedExercise.getAttachedFile().getSize() > 0)
			exerciseTransaction.setFile(file);
		exerciseTransaction.setStudentExercise(exercise);
		exerciseTransaction.setExerciseTransactionType(transactionType);
		exerciseTransactionDao.saveExerciseTransaction(exerciseTransaction);

	}

	@Override
	public void completeStudentExercise(GradeExerciseBean gradedExercise)
			throws NumberFormatException, StudentExerciseNotFoundException {
		Calendar rightNow = Calendar.getInstance();
		StudentExercise exercise = getStudentExerciseById(Long
				.valueOf(gradedExercise.getExerciseId()));
		exercise.setComplete(true);
		exercise.setBeingGraded(false);
		exercise.setScore(Integer.valueOf(gradedExercise.getGrade()));
		exercise.setClosedDate(rightNow);
		studentExerciseDao.updateStudentExercise(exercise);
	}

	@Override
	public List<ExerciseTransaction> getModuleTransactionsInMentorTemplate(
			String mentorId, String moduleId) {

		return mentorTemplateService.getExerciseTransactions(
				Long.valueOf(moduleId), Long.valueOf(mentorId));

	}

	@Override
	public void saveExerciseTransaction(ExerciseTransaction exerciseTransaction) {
		exerciseTransactionDao.saveExerciseTransaction(exerciseTransaction);
	}

	@Override
	public int numberOfExercisesByMentorTemplate(long mentorId, long moduleId) {
		return mentorTemplateService.numberOfExercisesByMentorTemplate(
				mentorId, moduleId);
	}

	@Override
	public int countExerciseTransactions(long moduleId, long mentorId) {
		return mentorTemplateService.countExerciseTransactions(moduleId,
				mentorId);
	}

}

