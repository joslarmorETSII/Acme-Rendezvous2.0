/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ConfigurationService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// services -------------------------------------------------
	@Autowired
	private ConfigurationService configurationService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		Configuration configuration = this.configurationService.findAll().iterator().next();
		SimpleDateFormat formatterEs;
		SimpleDateFormat formatterEn;
		String momentEs;
		String momentEn;

		formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		momentEs = formatterEs.format(new Date());
		formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		momentEn = formatterEn.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", configuration.getName());
		result.addObject("englishWelcome", configuration.getEnglishWelcome());
		result.addObject("spanishWelcome", configuration.getSpanishWelcome());
		result.addObject("configurationBanner", configuration.getBanner());
		result.addObject("momentEs", momentEs);
		result.addObject("momentEn", momentEn);

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {

		ModelAndView result;

		result = new ModelAndView("welcome/cookies");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}

}
