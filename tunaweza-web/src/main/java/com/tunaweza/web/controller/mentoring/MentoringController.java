package com.tunaweza.web.controller.mentoring;

import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseNotFoundException;
import com.tunaweza.core.business.dao.exceptions.topic.TopicNotFoundExistException;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.status.Status;
import com.tunaweza.core.business.service.exercise.GradeExerciseBean;
import com.tunaweza.core.business.service.mentor.MentorService;
import com.tunaweza.core.business.service.solution.SolutionService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.utils.PropertiesUtil;
import com.tunaweza.web.spring.configuration.mentoring.bean.StatisticsMentorBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.CHECK_IS_BEING_GRADED;
import static com.tunaweza.web.views.Views.GRADE_EXERCISE;
import static com.tunaweza.web.views.Views.GRADE_EXERCISE_REPLY;
import static com.tunaweza.web.views.Views.LIST_ALL_MENTORS;
import static com.tunaweza.web.views.Views.MENTOR_STATISTICS;
import static com.tunaweza.web.views.Views.MENTOR_VIEW_FEEDBACK;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author samuel Waweru.
 * 
 */
@Controller
@RequestMapping(Views.MENTORING)
public class MentoringController implements Views {

	@Autowired
	private Validator validator;

	@Autowired
	private TopicService topicService;

	@Autowired
	private SolutionService solutionService;

	@Autowired
	private UserCastService userCastService;

	@Autowired
	MentorService mentorService;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@RequestMapping(value = GRADE_EXERCISE, method = RequestMethod.GET)
	public String gradeExexercice(
			@RequestParam(value = "exerciseId", required = true) String id,
			Model model) throws TopicNotFoundExistException,
			NumberFormatException, StudentExerciseNotFoundException {

		StudentExercise exercise = solutionService.getStudentExerciseById((Long
				.valueOf(id)));
		GradeExerciseBean gradeExerciseBean = new GradeExerciseBean();
		gradeExerciseBean.setExerciseId(id);
		model.addAttribute("gradeExerciseBean", gradeExerciseBean);
		model.addAttribute("LAST_TRANSACTION",
				solutionService.getLastExerciseTransaction(exercise));
		return GRADE_EXERCISE;
	}

	@RequestMapping(value = GRADE_EXERCISE, method = RequestMethod.POST)
	public String submitSolution(GradeExerciseBean gradedExercise, Model model)
			throws Exception {

		Set<ConstraintViolation<GradeExerciseBean>> failures = validator
				.validate(gradedExercise);
		if (!failures.isEmpty()) {
			model.addAttribute("Message", submitValidationMessages(failures));
			model.addAttribute("success", false);
			return GRADE_EXERCISE_REPLY;
		} else {
			if (gradedExercise.getAttachedFile().getSize() > 0) {
				LOGGER.info("\n MENTORINGCONTROLLER: uploaded file mimetype -----"
						+ gradedExercise.getAttachedFile().getContentType());
				if (!PropertiesUtil.isfileSupported("zip", gradedExercise
						.getAttachedFile().getContentType())) {
					model.addAttribute("Message",
							"Upload Only Supported Files ");
					model.addAttribute("success", false);
					return GRADE_EXERCISE_REPLY;
				}
			}
			Mentor mentor = userCastService.getUser().getMentor();
			// exercise is completed
			if (Integer.valueOf(gradedExercise.getStatus()) == 1) {
				solutionService.completeStudentExercise(gradedExercise);
			}
			solutionService
					.addMentorExerciseTransaction(mentor, gradedExercise);
			StudentExercise exercise = solutionService
					.getStudentExerciseById(Long.valueOf(gradedExercise
							.getExerciseId()));
			Long moduleId = exercise.getExercise().getModule().getId();
			Long mentorId = userCastService.getUser().getMentor().getId();
			model.addAttribute("moduleId", moduleId);
			model.addAttribute("mentorId", mentorId);
			model.addAttribute("success", true);
			model.addAttribute("Message", "Grading Submitted Successfully");

			return GRADE_EXERCISE_REPLY;
		}

	}

	@RequestMapping(value = MENTOR_VIEW_FEEDBACK, method = RequestMethod.GET)
	public String readFeedBack(
			@RequestParam(value = "exerciseId", required = true) String exerciseId,
			Model model) throws NumberFormatException,
			StudentExerciseNotFoundException {
		StudentExercise studentExercise = solutionService
				.getStudentExerciseById(Long.valueOf(exerciseId));
		List<ExerciseTransaction> exerciseTransactions = solutionService
				.getExerciseTransactionByStudentExercise(studentExercise);
		ExerciseTransaction lastTransaction = solutionService
				.getLastExerciseTransaction(studentExercise);
		lastTransaction.setReadDate(new Date());
		lastTransaction.setMentor(userCastService.getUser().getMentor());
		solutionService.saveExerciseTransaction(lastTransaction);
		studentExercise.getStudent().getUser();
		Status status = topicService.getStatusOfExercise(studentExercise
				.getStudent().getUser(), studentExercise.getExercise());
		model.addAttribute("EXERCISE", studentExercise);
		model.addAttribute("EXERCISETRANSACTIONS", exerciseTransactions);
		model.addAttribute("EXERCISESTATUS", status);
		model.addAttribute("POSTS", exerciseTransactions.size());
		return MENTOR_VIEW_FEEDBACK;
	}

	@RequestMapping(value = CHECK_IS_BEING_GRADED, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> checkExerciseGrading(
			@RequestParam(value = "exerciseId", required = true) String exerciseId)
			throws Exception {

		StudentExercise studentExercise = solutionService
				.getStudentExerciseById(Long.valueOf(exerciseId));
		ExerciseTransaction lastTransaction = solutionService
				.getLastExerciseTransaction(studentExercise);
		Date lastReadDate = lastTransaction.getReadDate();

		String mentorName = "null";
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("isBeingGraded", "false");

		if (lastTransaction.getExerciseTransactionType().getName()
				.equals("MentorToStudent")) {
			mentorName = lastTransaction.getMentor().getUser().getFirstName();
			String mentorId = lastTransaction.getMentor().getId().toString();
			String moduleId = studentExercise.getExercise().getModule().getId()
					.toString();
			responseMap.put("mentorId", mentorId);
			responseMap.put("moduleId", moduleId);
			responseMap.put("isGraded", "true");
		} else {
			if (lastReadDate != null) {
				Date currentTime = new Date();
				long timeDiff = currentTime.getTime() - lastReadDate.getTime();
				Mentor currentMentor = userCastService.getUser().getMentor();
				Mentor lastMentor = lastTransaction.getMentor();
				if (lastMentor != null && currentMentor != lastMentor
						&& timeDiff < 1200000) {
					mentorName = lastTransaction.getMentor().getUser()
							.getFirstName();
					mentorName += " "
							+ lastTransaction.getMentor().getUser()
									.getLastName();
					responseMap.put("isBeingGraded", "true");
					responseMap.put("mentorName", mentorName);
				}
			}
		}
		return responseMap;
	}

	private List<String> submitValidationMessages(
			Set<ConstraintViolation<GradeExerciseBean>> failures) {
		List<String> failureMessages = new ArrayList<String>();
		for (ConstraintViolation<GradeExerciseBean> failure : failures) {
			failureMessages.add(failure.getMessage());
		}
		return failureMessages;
	}

	@RequestMapping(value = LIST_ALL_MENTORS, method = RequestMethod.GET)
	public String listAllMentors(Model model) throws Exception {

		List<Mentor> mentors = mentorService.getAllMentors();

		model.addAttribute("MENTORLIST", mentors);

		return LIST_ALL_MENTORS;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = MENTOR_STATISTICS)
	public String mentorStatistics(Model model,
			@RequestParam(value = "mentorId", required = true) String id) {
		List<String> monthsNameList = null;
		List<Integer> transactionsMonthList = null;
		List<String> responseAverageTimeList = null;
		List<String> responseAverageTimeReadList = null;
		List<StatisticsMentorBean> dataList = new ArrayList<StatisticsMentorBean>();

		try {
			monthsNameList = mentorService.getNameLastMonths();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (monthsNameList != null) {

			try {
				transactionsMonthList = mentorService
						.getTransactionLastThreeMonths(id);
				responseAverageTimeList = mentorService
						.getAvergeTimeToResponse(id);
				responseAverageTimeReadList = mentorService
						.getAvergeTimeToRead(id);

				for (int i = 0; i < monthsNameList.size(); i++) {

					StatisticsMentorBean SMB = new StatisticsMentorBean();
					SMB.setMonth(monthsNameList.get(i));
					SMB.setAverageTimeToResponse(responseAverageTimeList.get(i));
					SMB.setAverageTimeToRead(responseAverageTimeReadList.get(i));

					if (transactionsMonthList.get(i) != 0) {

						SMB.setTotalTransactions(transactionsMonthList.get(i)
								+ "");

					} else {

						SMB.setTotalTransactions("No transactions");

					}

					dataList.add(SMB);

				}
			}

			catch (NullPointerException e) {

				e.printStackTrace();
			}
		}

		model.addAttribute("DATALIST", dataList);

		return MENTOR_STATISTICS;
	}

}
