package com.bhaskar.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bhaskar.beans.FormData;
import com.bhaskar.service.TrainNeuralNetwork;

@RestController
@RequestMapping("/api")
public class AppController {

	@Autowired
	private TrainNeuralNetwork service;

	@GetMapping("/corona_form")
	public ModelAndView createUserView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("form");
		mav.addObject("formData", new FormData());
		return mav;
	}

	@GetMapping("/add_to_dataset")
	public ModelAndView createData() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("add_dataset");
		mav.addObject("formData", new FormData());
		return mav;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bhaskar.controller.TrainNeuralNetwork#generateModel()
	 */
	@RequestMapping(value = "/train_model", method = RequestMethod.GET)
	public String generateModel() throws IOException, NoSuchFieldException, IllegalAccessException {
		return service.generateModel();
	}

	@RequestMapping(value = "/corona_test", method = RequestMethod.POST)
	public ModelAndView firstPage(@ModelAttribute FormData project, BindingResult result) throws IOException,
			NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		return service.firstPage(project, result);
	}

	@RequestMapping(value = "/add_to_database", method = RequestMethod.POST)
	public ModelAndView addTODatabase(@ModelAttribute FormData project, BindingResult result) throws IOException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {

		service.addObect(project);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("add_dataset");
		mav.addObject("formData", new FormData());
		return mav;
	}

}