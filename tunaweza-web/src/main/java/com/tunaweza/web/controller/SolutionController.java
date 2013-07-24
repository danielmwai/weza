package com.tunaweza.web.controller;

import com.tunaweza.core.business.dao.exceptions.topic.TopicNotFoundExistException;
import com.tunaweza.core.business.dao.exercise.transaction.ExerciseTransactionTypeDao;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.file.File;
import com.tunaweza.core.business.model.status.Status;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.solution.SolutionService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.utils.PropertiesUtil;
import com.tunaweza.core.business.service.solution.PostSolutionBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.DOWNLOAD_SOLUTION;
import static com.tunaweza.web.views.Views.POST_SOLUTION;
import static com.tunaweza.web.views.Views.POST_SOLUTION_REPLY;
import static com.tunaweza.web.views.Views.READ_FEEDBACK;
import static com.tunaweza.web.views.Views.REPLACE_SOLUTION;
import static com.tunaweza.web.views.Views.RESUBMIT_SOLUTION;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author samuel Waweru
 * 
 */
@Controller
@RequestMapping(Views.SOLUTION)
public class SolutionController implements Views
{
	@Autowired
	private Validator validator;

	@Autowired
	private TopicService topicService;

	@Autowired
	private SolutionService solutionService;
	
	@Autowired
	private ExerciseTransactionTypeDao exerciseTransactionTypeDao;

	@Autowired
	private UserCastService userCastService;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@RequestMapping(value = POST_SOLUTION, method = RequestMethod.GET)
	public String postSolution(
			@RequestParam(value = "topicId", required = true) String id,
			Model model) throws TopicNotFoundExistException
	{

		Topic topic = topicService.getTopicById(Long.valueOf(id));
		PostSolutionBean postSolutionBean = new PostSolutionBean();
		postSolutionBean.setTopicId(id);
		model.addAttribute("postSolutionBean", postSolutionBean);
		model.addAttribute("TOPIC", topic);
		return POST_SOLUTION;

	}

	@RequestMapping(value = POST_SOLUTION, method = RequestMethod.POST)
	public String submitSolution(PostSolutionBean postSolutionBean, Model model)
			throws Exception
	{

		Set<ConstraintViolation<PostSolutionBean>> failures = validator
				.validate(postSolutionBean);
		if (!failures.isEmpty())
		{
			model.addAttribute("Message", submitValidationMessages(failures));
			return POST_SOLUTION_REPLY;
		}
		else
		{
			if (postSolutionBean.getSolution().getSize() == 0)
			{
				model.addAttribute("Message", "You must upload a solution");
				return POST_SOLUTION_REPLY;
			}

			LOGGER.info("\n SOLUTIONCONTROLLER: uploaded file mimetype -----"+postSolutionBean.getSolution().getContentType());
			if (!PropertiesUtil.isfileSupported("zip",postSolutionBean.getSolution().getContentType())) {
				
				model.addAttribute("Message", "Upload Only Supported Files ");
				return POST_SOLUTION_REPLY;
				
			}
			solutionService.createStudentExercise(userCastService.getUser(),
					postSolutionBean);
			model.addAttribute("Message", "Submitted Successfully");
			model.addAttribute("Topic", postSolutionBean.getTopicId());
			return POST_SOLUTION_REPLY;
		}

	}

	@RequestMapping(value = REPLACE_SOLUTION, method = RequestMethod.GET)
	public String replaceSolutionForm(
			@RequestParam(value = "topicId", required = true) String id,
			Model model) throws TopicNotFoundExistException
	{

		Topic topic = topicService.getTopicById(Long.valueOf(id));
		PostSolutionBean postSolutionBean = new PostSolutionBean();
		postSolutionBean.setTopicId(id);
		model.addAttribute("postSolutionBean", postSolutionBean);
		model.addAttribute("TOPIC", topic);
		return REPLACE_SOLUTION;

	}

	@RequestMapping(value = REPLACE_SOLUTION, method = RequestMethod.POST)
	public String replaceSolution(PostSolutionBean postSolutionBean, Model model)
			throws Exception
	{

		Set<ConstraintViolation<PostSolutionBean>> failures = validator
				.validate(postSolutionBean);
		if (!failures.isEmpty())
		{
			model.addAttribute("Message", submitValidationMessages(failures));
			return POST_SOLUTION_REPLY;
		}
		else
		{
			if (postSolutionBean.getSolution().getSize() == 0)
			{
				model.addAttribute("Message", "You must upload a solution");
				return POST_SOLUTION_REPLY;
			}

			LOGGER.info("\n SOLUTIONCONTROLLER: uploaded file mimetype -----"+postSolutionBean.getSolution().getContentType());
			if (!PropertiesUtil.isfileSupported("zip",postSolutionBean.getSolution().getContentType())) {
				model.addAttribute("Message", "Upload Only Supported Files ");

				return POST_SOLUTION_REPLY;
			}
			solutionService.replaceLastExerciseTransaction(
					userCastService.getUser(), postSolutionBean);
			model.addAttribute("Message", "Submitted Successfully");
			model.addAttribute("Topic", postSolutionBean.getTopicId());
			return POST_SOLUTION_REPLY;
		}

	}

	@RequestMapping(value = RESUBMIT_SOLUTION, method = RequestMethod.GET)
	public String resubmitSolutionForm(
			@RequestParam(value = "topicId", required = true) String id,
			Model model) throws TopicNotFoundExistException
	{

		Topic topic = topicService.getTopicById(Long.valueOf(id));
		PostSolutionBean postSolutionBean = new PostSolutionBean();
		postSolutionBean.setTopicId(id);
		model.addAttribute("postSolutionBean", postSolutionBean);
		model.addAttribute("TOPIC", topic);
		return RESUBMIT_SOLUTION;

	}

	@RequestMapping(value = RESUBMIT_SOLUTION, method = RequestMethod.POST)
	public String resubmitSolution(PostSolutionBean postSolutionBean,
			Model model) throws Exception
	{

		Set<ConstraintViolation<PostSolutionBean>> failures = validator
				.validate(postSolutionBean);
		if (!failures.isEmpty())
		{
			model.addAttribute("Message", submitValidationMessages(failures));
			return POST_SOLUTION_REPLY;
		}
		else
		{
			if (postSolutionBean.getSolution().getSize() == 0)
			{
				model.addAttribute("Message", "You must upload a solution");
				return POST_SOLUTION_REPLY;
			}

			LOGGER.info("\n SOLUTIONCONTROLLER: uploaded file mimetype -----"+postSolutionBean.getSolution().getContentType());
			if (!PropertiesUtil.isfileSupported("zip",postSolutionBean.getSolution().getContentType())) {
				model.addAttribute("Message", "Upload Only Supported Files ");

				return POST_SOLUTION_REPLY;
			}
			solutionService.resubmitExerciseTransaction(
					userCastService.getUser(), postSolutionBean);
			model.addAttribute("Message", "Submitted Successfully");
			model.addAttribute("Topic", postSolutionBean.getTopicId());
			return POST_SOLUTION_REPLY;
		}

	}

	@RequestMapping(value = DOWNLOAD_SOLUTION, method = RequestMethod.GET)
	public void downloadSolution(
			@RequestParam(value = "transactionId", required = true) String transactionId,
			HttpServletResponse response)
	{
		ExerciseTransaction exerciseTransaction = solutionService
				.getExerciseTransactionById(Long.valueOf(transactionId));

		File file = exerciseTransaction.getFile();
		

		try
		{
			int fileSize = (int)file.getFile().length();
			
			response.setBufferSize(fileSize);
			response.setContentType(file.getContentType());
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ file.getFileName() + "\"");
			response.setContentLength(fileSize);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(file.getFile().getBinaryStream(), out);
			out.flush();
			out.close();
			
		}
		catch (IOException e)
		{
			LOGGER.error(e);
		}
		catch (SQLException e)
		{
			LOGGER.error(e);
		}

	}

	private List<String> submitValidationMessages(
			Set<ConstraintViolation<PostSolutionBean>> failures)
	{
		List<String> failureMessages = new ArrayList<String>();
		for (ConstraintViolation<PostSolutionBean> failure : failures)
		{
			failureMessages.add(failure.getMessage());
		}
		return failureMessages;
	}

	@RequestMapping(value = READ_FEEDBACK, method = RequestMethod.GET)
	public String feadBack(
			@RequestParam(value = "topicId", required = true) String topicid,
			Model model) throws TopicNotFoundExistException
	{
		User user = userCastService.getUser();
		StudentExercise studentExercise = solutionService.getStudenExercise(
				user.getId().toString(), topicid);
		List<ExerciseTransaction> exerciseTransactions = solutionService
				.getExerciseTransactionByStudentExercise(studentExercise);
		ExerciseTransactionType type = exerciseTransactionTypeDao
				.getExerciseTransactionTypeByName("MentorToStudent");
		for (ExerciseTransaction exerciseTransaction : exerciseTransactions) {
			if (exerciseTransaction.getExerciseTransactionType() == type
					&& exerciseTransaction.getReadDate() == null) {
				exerciseTransaction.setReadDate(new Date());
				solutionService.saveExerciseTransaction(exerciseTransaction);
			}
		}
		Status status = topicService.getStatusOfExercise(user,
				topicService.getTopicById(Long.valueOf(topicid)));
		model.addAttribute("EXERCISE", studentExercise);
		model.addAttribute("EXERCISETRANSACTIONS", exerciseTransactions);
		model.addAttribute("EXERCISESTATUS", status);
		model.addAttribute("POSTS", exerciseTransactions.size());

		return READ_FEEDBACK;
	}

}
