package by.blogobet.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.blogobet.entity.Prediction;
import by.blogobet.entity.manager.EntityService;

@RestController
@RequestMapping("/rest")
public class CollectorController {
	@Autowired
	EntityService entityService;
	
	 @RequestMapping(value = "/prediction/all", method = RequestMethod.GET)
	public List<Prediction> getAllPrediction(){
		return entityService.getAllPrediction();
	}

}
