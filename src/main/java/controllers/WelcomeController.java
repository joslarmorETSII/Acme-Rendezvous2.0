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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;

		SimpleDateFormat formatterEs;
		SimpleDateFormat formatterEn;
		String momentEs;
		String momentEn;

		formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		momentEs = formatterEs.format(new Date());
		formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		momentEn = formatterEn.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("englishWelcome", "to our web of rendezvous");
		result.addObject("spanishWelcome", "de nuestra web de citas");
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
