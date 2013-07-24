package com.tunaweza.web.controller.referer;

import org.springframework.stereotype.Service;

@Service
public class RefererHome implements Referer{

	private String Referer;

	@Override
	public String getReferer() {
		return Referer;
	}
	@Override
	public void setReferer(String referer) {
		Referer = referer;
	}
	
	
}
