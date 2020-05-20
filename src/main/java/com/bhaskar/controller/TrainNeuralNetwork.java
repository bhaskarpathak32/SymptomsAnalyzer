package com.bhaskar.controller;

import java.io.IOException;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.bhaskar.beans.FormData;

public interface TrainNeuralNetwork {

	public String generateModel() throws IOException, NoSuchFieldException, IllegalAccessException;

	public ModelAndView firstPage(FormData project, BindingResult result) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException ;

	public void addObect(FormData project);
		

}