package com.tunaweza.web.controller.topic;

import java.util.List;
import java.util.Map;


public class TopicBean {

	private long id;
	
	private String data;
			
	private List<TopicBean> children;
	
	private Map<String,Object> attr;
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String name) {
		this.data = name;
	}
	
	public Map<String, Object> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, Object> attr) {
		this.attr = attr;
	}

	public List<TopicBean> getChildren() {
		return children;
	}

	public void setChildren(List<TopicBean> children) {
		this.children = children;
	}
	
}
