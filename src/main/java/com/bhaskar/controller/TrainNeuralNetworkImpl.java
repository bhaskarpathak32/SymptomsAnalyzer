package com.bhaskar.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.bhaskar.beans.FormData;
import com.bhaskar.beans.IndexMapping;
import com.bhaskar.db.DataRepository;

@Service
public class TrainNeuralNetworkImpl implements TrainNeuralNetwork {
	final String HEADER="bleeding_gum,gum_pain,nasal_congestion,clogged_ear,throat_pain,ear_pain,wheezing,rash,nausea,age,cough,fever,headache,chest_pain,runny_nose,sneeze,breath_difficulty";
	@Autowired
	private IndexMapping mapping;
	
	@Autowired
	private DataRepository repo;
	
	@Override
	public String generateModel() throws IOException, NoSuchFieldException, IllegalAccessException {

		List<FormData> objects= (List<FormData>) repo.findAll();
		appendInQueryCSV( objects);
		
		repo.deleteAll();
		
		String path =getClass().getClassLoader().getResource("python/script.py").getPath().substring(1); 
		Process p = Runtime.getRuntime().exec("python "+ path );
		  BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
	            String s = null;

	            // read the output from the command
	            System.out.println("Here is the standard output of the command:\n");
	            while ((s = stdInput.readLine()) != null) {
	                System.out.println(s);
	            }
	            
	            // read any errors from the attempted command
	            System.out.println("Here is the standard error of the command (if any):\n");
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	            }
	            
		return "Model has been trained successfully";
		
	
	}
	
	public ModelAndView  firstPage(FormData project, BindingResult result) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		ModelAndView mav = new ModelAndView();
		
		Date date= new Date();
		String timeStamp= date.getYear()+"_"+date.getMonth()+"_"+date.getDay()+"_"+date.getDay()+"_"+date.getHours()+
				"_"+date.getMinutes()+"_"+date.getSeconds();
		
		generateQueryCSV(project, timeStamp);
		
		String path =getClass().getClassLoader().getResource("python/predict.py").getPath().substring(1); 
		Process p = Runtime.getRuntime().exec("python "+ path +" "+timeStamp);
		  BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));
	
	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
	            String s = null;
	
	            // read the output from the command
	            System.out.println("Here is the standard output of the command:\n");
	            while ((s = stdInput.readLine()) != null) {
	                System.out.println(s);
	            }
	            
	            // read any errors from the attempted command
	            System.out.println("Here is the standard error of the command (if any):\n");
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	            }
	            BufferedReader readResult = new BufferedReader(new 
		                 InputStreamReader(new FileInputStream(new File(timeStamp+".dat"))));
	            
	            String resultVal= "";
	            String str=null;
	            while((  str=readResult.readLine())!=null) {
	            	resultVal+=str;
	            }
	            readResult.close();
	            String[] vals=resultVal.split(",");
	            Arrays.stream(vals).forEach(System.out::println);
	            Double[] doubleVals= Arrays.stream(vals).map(ss -> new Double(ss)).toArray(Double[]::new);
	            double maxProb=0d;
	            int maxIndex=0;
	            for(int i=0;i<doubleVals.length;i++) {
	            	if(maxProb < doubleVals[i] ) {
	            		maxProb=doubleVals[i];
	            		maxIndex=i;
	            	}
	            }
	            
	            project.setStatus(maxIndex);
	            project.setDescription(maxProb+"% probability of contracting "+mapDisease(maxIndex)+" infection");
	     mav.setViewName("result");
	    mav.addObject("formData", project);
	    return mav;
	}
	
	void generateQueryCSV(FormData project, String timeStamp)
			throws FileNotFoundException, IOException, NoSuchFieldException, IllegalAccessException {
		BufferedWriter wr= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(timeStamp+"_query.csv"))));
		
		wr.write(HEADER);
		wr.newLine();
		wr.write(formQuery(project));
		wr.newLine();
		wr.close();
	}
	
	void appendInQueryCSV(List<FormData> objects)
			throws FileNotFoundException, IOException, NoSuchFieldException, IllegalAccessException {
		FileWriter fw = new FileWriter(new File("dataset.csv"),true);
		BufferedWriter wr=null;
		if(!CollectionUtils.isEmpty(objects)) {
			wr=new BufferedWriter(fw);
		}
		
		for(FormData data:objects) {
		wr.append(formQuery(data)+","+data.getStatus());
		wr.newLine();
		}
		if(!CollectionUtils.isEmpty(objects)) {
			wr.close();
		}
	}

	private String formQuery(FormData project) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String[] list= HEADER.split(",");
		String queryString="";
		for(String str:list) {
		Field[] f= project.getClass().getDeclaredFields();
		Field field= Arrays.stream(f).filter(s->s.getName().equalsIgnoreCase(str)).findAny().get(); 
		long val=field.getLong(project);
		queryString+=","+val; 
		}
		return queryString.substring(1);
	}

	String mapDisease(int maxIndex) {
		
		 return mapping.getConfig().get(maxIndex+"");
	}

	@Override
	public void addObect(FormData project) {
		
		repo.save(project);
		 
	}
}
