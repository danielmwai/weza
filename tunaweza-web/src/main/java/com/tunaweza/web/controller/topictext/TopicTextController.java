package com.tunaweza.web.controller.topictext;

import com.tunaweza.core.business.dao.exceptions.accessed.LastAccessedTopicException;
import com.tunaweza.core.business.dao.exceptions.topic.TopicNotFoundExistException;
import com.tunaweza.core.business.dao.exceptions.topic.TopicTextDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.question.Question;
import com.tunaweza.core.business.model.status.Status;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.topic.TopicText;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.answer.AnswerService;
import com.tunaweza.core.business.service.question.QuestionService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.topic.TopicTextService;
import com.tunaweza.core.business.service.topic.lastaccessed.LastAccessedTopicService;
import com.tunaweza.core.business.service.user.UserCastService;
import static com.tunaweza.core.business.utils.SessionHelper.evalSessionAttribRole;
import com.tunaweza.web.spring.configuration.topic.bean.AnswerBean;
import com.tunaweza.web.spring.configuration.topic.bean.QuizBean;
import com.tunaweza.web.spring.configuration.topic.bean.ResultsBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.QUIZ_FORM;
import static com.tunaweza.web.views.Views.QUIZ_NEXT;
import static com.tunaweza.web.views.Views.QUIZ_RESULTS;
import static com.tunaweza.web.views.Views.TOPICSTEXT_VIEW;
import static com.tunaweza.web.views.Views.TOPICTEXT;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * @author Samuel Waweru
 * @author Ephraim Muhia
 */
@Controller
@RequestMapping(Views.TOPICTEXT)
public class TopicTextController implements Views {
    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private TopicTextService topicTextService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserCastService userCastService;

    @Autowired
    private LastAccessedTopicService lastAccessedTopicService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    /**
     * @param model
     * @param response
     * @return
     * @throws LastAccessedTopicException
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, value = TOPICSTEXT_VIEW)
    public String listTopics(@RequestParam("topicId") String topic_Id,
                             Model model, HttpServletRequest request)
            throws TopicNotFoundExistException, TopicTextDoesNotExistException,
            UserDoesNotExistException, LastAccessedTopicException {
        User user = userCastService.getUser();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
                                                                                  .getAuthentication()
                                                                                  .getAuthorities();
        int topicId = Integer.parseInt(topic_Id);
        Topic topic = topicService.getTopicById(topicId);
        TopicText topictext = topicTextService.getTopicTextByTopic(topicId);
        model.addAttribute("TOPICTEXT", topictext);
        if (topic.isExercise()) {

            model.addAttribute("EXERCISE_STATUS",
                    topicService.getStatusOfExercise(user, topic));
        } else {
            for (GrantedAuthority auth : authorities)
                if (auth.getAuthority().equals("ROLE_STUDENT")
                        || auth.getAuthority().equals("ROLE_EVALUATOR")) {
                    evalSessionAttribRole(request, "STUDENT");
                    List<Topic> completedTopics = user.getStudent()
                                                      .getCompletedTopics();
                    if (completedTopics.contains(topic)) {
                        topic.setStatus(Status.STATUS_TOPIC_COMPLETED);
                    }
                    int maxQuestions = topicService.getNumberOfAllowedQuestions(topicId);
                    List<Question> questions = questionService.getQuestionsByTopic(Long.valueOf(topicId));
                    if (maxQuestions > 0
                            && questions.size() > 0
                            && topic.getStatus() != Status.STATUS_TOPIC_COMPLETED) {
                        topic.setStatus(Status.STATUS_TAKE_QUIZ);
                    }
                    break;
                }
        }
        try {
            if (topic.getFile().getFile().length() != 0) {
                model.addAttribute("FILE_ATTACHED", true);
                model.addAttribute("FILE_NAME", topic.getFile().getFileName());
                model.addAttribute("FILE_ID", topic.getFile().getId());

            } else {
                model.addAttribute("FILE_ATTACHED", false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            model.addAttribute("FILE_ATTACHED", false);
        }
        model.addAttribute("TOPIC", topic);
        lastAccessedTopicService.saveOrUpdateLastAccessedTopicInModule(user,
                topic);

        return TOPICSTEXT_VIEW + TOPICTEXT;

    }

    @RequestMapping(method = RequestMethod.GET, value = QUIZ_FORM)
    public String getQuiz(@RequestParam("topicId") String topic_Id,
                          Model model, HttpServletRequest request) {
        Long id = Long.valueOf(topic_Id);
        HttpSession session = request.getSession(true);
        session.setAttribute("TOPICID", topic_Id);
        if (session.getAttribute("Remaining") != null) {
            session.removeAttribute("Remaining");
        }
        int maxQuestions = topicService.getNumberOfAllowedQuestions(id);
        List<Question> questions = questionService.getQuestionsByTopic(Long.valueOf(id));
        if (questions.size() == 1 && questions.size() > 0) {
            Question question = questions.get(0);
            populateQuizBean(question, model);
            model.addAttribute("TOPICID", topic_Id);
            model.addAttribute("COMMAND", "Finish");

        } else {
            List<Question> pickedQuestions = new ArrayList<Question>();

            if (maxQuestions > questions.size()) {
                maxQuestions = questions.size();
            }

            Random r = new Random();
            int inputSize = questions.size();
            for (int i = 0; i < maxQuestions; i++) {
                int indexToSwap = i + r.nextInt(inputSize - i);
                Question temp = questions.get(i);
                questions.set(i, questions.get(indexToSwap));
                questions.set(indexToSwap, temp);
            }

            if (questions.size() > maxQuestions) {
                pickedQuestions = questions.subList(0, maxQuestions);
            } else {
                pickedQuestions = questions;
            }
            Question question = pickedQuestions.remove(0);
            populateQuizBean(question, model);
            model.addAttribute("COMMAND", "Next");
            session.setAttribute("Remaining", pickedQuestions);
        }
        return QUIZ_FORM;
    }

    private void populateQuizBean(Question question, Model model) {
        QuizBean quizBean = new QuizBean();
        quizBean.setTextin(question.getText());
        quizBean.setId(String.valueOf(question.getId()));
        List<Answer> answers = question.getAnswers();
        List<AnswerBean> answerBeans = new ArrayList<AnswerBean>();
        for (Answer answer : answers) {
            AnswerBean answerBean = new AnswerBean();
            answerBean.setText(answer.getText());
            answerBean.setId(String.valueOf(answer.getId()));
            answerBeans.add(answerBean);
        }
        model.addAttribute("quizBean", quizBean);
        model.addAttribute("ANSWERS", answerBeans);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(method = RequestMethod.POST, value = QUIZ_FORM)
    public String checkAnswer(@RequestBody QuizBean quizBean,
                              HttpServletRequest request,
                              HttpServletResponse response, Model model)
            throws Exception {
        HttpSession session = request.getSession(true);
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        // Set<ConstraintViolation<QuizBean>> failures = validator.validate(quizBean);
        // if (!failures.isEmpty()) {
        if (quizBean.getAnswer() == null) {
            Collections.emptyList();
            // return Collections.singletonMap("u", "Fill in required fields");
            jsonConverter.write(Collections.singletonMap("message",
                    "You must select Atleast One Answer"), jsonMimeType,
                    new ServletServerHttpResponse(response));
            return null;
        } else {
            String questionId = quizBean.getId();
            Question question = questionService.findQuestionById(Long.valueOf(questionId));
            String userAnswer = quizBean.getAnswer();
            List<Answer> answers = new ArrayList<Answer>();
            if (!userAnswer.startsWith("[")) {
                answers.add(answerService.findAnswerById(Long.valueOf(quizBean.getAnswer())));
            } else {
                JSONArray answersArray = new JSONArray(quizBean.getAnswer());
                for (int count = 0; count < answersArray.length(); count++) {
                    answers.add(answerService.findAnswerById(Long.valueOf(answersArray.getLong(count))));
                }
            }

            ResultsBean resultsBean = new ResultsBean();
            resultsBean.setQuestion(question.getText());
            for (Answer answer : answers) {
                if (!answer.getCorrect()) {
                    resultsBean.setResult("Wrong");
                    break;
                }
            }
            if (resultsBean.getResult() == null)
                resultsBean.setResult("Correct");
            List<ResultsBean> results = new ArrayList<ResultsBean>();

            if (session.getAttribute("Results") != null) {
                results = (List) session.getAttribute("Results");
            }
            results.add(resultsBean);
            session.setAttribute("Results", results);
            if (session.getAttribute("Remaining") != null) {
                jsonConverter.write(Collections.singletonMap("n", "Next"),
                        jsonMimeType, new ServletServerHttpResponse(response));
                return null;
            }
        }
        jsonConverter.write(Collections.singletonMap("u", "Saved"),
                jsonMimeType, new ServletServerHttpResponse(response));
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = QUIZ_NEXT)
    public String nextQuiz(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<Question> remainingQuestions = (List) session.getAttribute("Remaining");
        Question question1 = remainingQuestions.remove(0);
        try {
            question1 = questionService.findQuestion(question1);
        } catch (Exception e) {
        }
        populateQuizBean(question1, model);
        if (remainingQuestions.size() == 0) {
            model.addAttribute("COMMAND", "Finish");
            String topicId = (String) session.getAttribute("TOPICID");
            session.removeAttribute("TOPICID");
            session.setAttribute("Remaining", null);
            model.addAttribute("TOPICID", topicId);
        } else {
            model.addAttribute("COMMAND", "Next");
            session.setAttribute("Remaining", remainingQuestions);
        }
        return QUIZ_FORM;

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = QUIZ_RESULTS)
    public String showResult(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<ResultsBean> finalResults = (List) session.getAttribute("Results");
        int score = 0;
        for (ResultsBean resultBean : finalResults) {
            if (resultBean.getResult().equals("Correct")) {
                score++;
            }
        }
        model.addAttribute("SCORE", " " + score + " / " + finalResults.size());
        model.addAttribute("RESULTS", finalResults);
        session.removeAttribute("Results");
        return QUIZ_RESULTS;
    }
}