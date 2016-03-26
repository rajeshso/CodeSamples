package com.rajesh.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rajesh.domain.Spittle;
import com.rajesh.domain.SpittleRepository;
import com.rajesh.domain.SpittleRepositoryImpl;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	private static final String MAX_LONG_AS_STRING = "283900L";
	//For running Junit test, use the following
	private SpittleRepository spittleRepository;
	//For deployment, use the following
	//private SpittleRepository spittleRepository = new SpittleRepositoryImpl();
	/**
	 * 
	 */
	public SpittleController() {
		super();
	}
	
	/**
	 * @param spittleRepository
	 */
	public SpittleController(SpittleRepository spittleRepository) {
		this.spittleRepository = spittleRepository;
	}
/**
 * This is a valid code too. This is for no param url. Example http://localhost:8080/mvc/spittles
	@RequestMapping(method=RequestMethod.GET)
	public String spittles(Model model) {
		model.addAttribute("spittleList",spittleRepository.findSpittles(Long.MAX_VALUE, 10));
		return "spittles";
	}
	**/
/**
 * This is a valid code too
	//Query Parameters such as http://localhost:8080/mvc/spittles?count=10&max=10
	@RequestMapping(method=RequestMethod.GET)
	public String spittles(
			Model model,
			@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max1,
			@RequestParam(value="count", defaultValue="10") int count1
			) {
		model.addAttribute("spittleList",spittleRepository.findSpittles(Long.MAX_VALUE, 10));
		return "spittles";
	}**/

	  @RequestMapping(method=RequestMethod.GET)
	  public List<Spittle> spittles(
	      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING,required=false) long max,
	      @RequestParam(value="count", defaultValue="20", required=false) int count) {
	    return spittleRepository.findSpittles(max, count);
	  }
	  

	  //Path Variable such as http://localhost:8080/mvc/spittles/12345
	  @RequestMapping(method=RequestMethod.GET, value="/{spittleId}")
	  public String spittle(Model model,
			  @PathVariable("spittleId") long spittleId
			  ) {
		  Spittle spittle = spittleRepository.findOneSpittle(spittleId);
		  model.addAttribute("spittle", spittle);
		  return "spittle";
	  }
}
