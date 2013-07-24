package com.tunaweza.web.controller.link;

import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ANCHOR;
import static com.tunaweza.web.views.Views.EXTERNAL_LINK;
import static com.tunaweza.web.views.Views.INTERNAL_LINK;
import static com.tunaweza.web.views.Views.MEDIA;
import static com.tunaweza.web.views.Views.PLUGIN_ROOT;
import static com.tunaweza.web.views.Views.THEME_ROOT;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 
 * @author Samuel Waweru
 * 
 */

@Controller
@RequestMapping(Views.TINYMCE_ROOT)
public class LinkController implements Views {

	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private ModuleService moduleService;

	@RequestMapping(method = RequestMethod.GET, value = PLUGIN_ROOT+INTERNAL_LINK)
	public String internalLinkForm(HttpServletRequest request, Model model) throws Exception {
		HttpSession session =request.getSession(true);
		String moduleId = (String) session.getAttribute("Module");
		Module module = moduleService.getModuleById(Long.valueOf(moduleId));
		List<Topic> topics = topicService.getAllTopicsByModule(Long.valueOf(moduleId));
		model.addAttribute("MODULE", module.getName());
		model.addAttribute("TOPICS_LIST", topics);
		return INTERNAL_LINK;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = PLUGIN_ROOT+EXTERNAL_LINK)
	public String externalLinkForm() throws Exception {
		
		return EXTERNAL_LINK;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = PLUGIN_ROOT+MEDIA)
	public String mediaForm() throws Exception {
		
		return MEDIA;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = THEME_ROOT+ANCHOR)
	public String anchorForm() throws Exception {
		
		return ANCHOR;
	}
	

}
