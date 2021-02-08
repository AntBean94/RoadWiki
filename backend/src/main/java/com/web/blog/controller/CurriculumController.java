package com.web.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.Curriculumtext;
import com.web.blog.model.service.CurriculumService;
import com.web.blog.model.service.LoginServiceImpl;

@RestController
@RequestMapping("/curriculum")
public class CurriculumController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";

	@Autowired
	CurriculumService Curriculumservice;

	@Autowired
	LoginServiceImpl loginServ;

	@PostMapping("/create")
	public Object createCurriculumtext(@RequestBody Curriculumtext curriculumtext, HttpServletRequest request) {
		logger.trace("createCurriculumtext start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;

		try {
			logger.info("roadmap : " + curriculumtext.toString());
			String nowuid = Integer.toString((int) loginServ.getData(request.getHeader("auth-token")).get("uid"));
			result = (Map<String, Object>) Curriculumservice.insertText(nowuid, curriculumtext);
			result.put("msg", SUCCESS);
			status = HttpStatus.OK;
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	@PostMapping("/delete")
	public Object deleteCurriculumtext(@RequestBody Curriculumtext curriculumtext, HttpServletRequest request) {
		logger.trace("deleteCurriculumtext start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		
		try {
			logger.info("roadmap : " + curriculumtext.toString());
			String nowuid = Integer.toString((int) loginServ.getData(request.getHeader("auth-token")).get("uid"));
			result = (Map<String, Object>) Curriculumservice.deleteText(nowuid, curriculumtext);
			result.put("msg", SUCCESS);
			status = HttpStatus.OK;
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	
	@GetMapping("/suggest")
	public Object suggest() {
		logger.trace("suggest start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		try {
			result = (Map<String, Object>) Curriculumservice.getSuggest();
			result.put("msg", SUCCESS);
			status = HttpStatus.OK;
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		} 
		return result;
	}
	
	@GetMapping("/suggest/{bdid}")
	public Object suggestByBdid(@PathVariable String bdid) {
		logger.trace("suggestByBdid start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		try {
			logger.info("bdid : " + bdid);
			result = (Map<String, Object>) Curriculumservice.getSuggestBybdid(bdid);
			result.put("msg", SUCCESS);
			status = HttpStatus.OK;
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		}
		return result;
	}
	
	@GetMapping("/suggest/{bdid}/{mdid}")
	public Object suggestByBdidMdid(@PathVariable String bdid,@PathVariable String mdid) {
		logger.trace("suggestByBdidMdid start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		try {
			logger.info("bdid : " + bdid);
			logger.info("mdid : " + mdid);
			result = (Map<String, Object>) Curriculumservice.getSuggestBybdidmdid(bdid,mdid);
			result.put("msg", SUCCESS);
			status = HttpStatus.OK;
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
			status = HttpStatus.NO_CONTENT;
		}
		return result;
	}

}