package com.web.blog.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.std.FileSerializer;
import com.web.blog.model.dto.User;
import com.web.blog.model.service.FileService;
import com.web.blog.model.service.LoginServiceImpl;
import com.web.blog.model.service.UserService;

import io.jsonwebtoken.lang.Assert;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";

	@Autowired
	UserService userServ;

	@Autowired
	FileService fileService;

	@Autowired
	LoginServiceImpl loginServ;

	@PostMapping("/login")
	public Object login(@RequestBody User user, HttpServletResponse response) {
		logger.trace("login");
//		System.out.println("로그인" + user);
		try {
			logger.info(user.toString());
			Map<String, Object> result = (Map<String, Object>) userServ.login(user);
			response.setHeader("authorizationToken", (String) result.get("token"));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/info/{uid}")
	public Object getInfo(@PathVariable int uid, HttpServletRequest request) {
		logger.trace("getInfo");
		try {
//			String email = (String) loginServ.getData(request.getHeader("auth-token")).get("email");
			int loginUid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			logger.info(Integer.toString(loginUid));
			Map<String, Object> result = (Map<String, Object>) userServ.getInfo(uid, loginUid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/join")
	public Object join(@RequestBody User user) {
		logger.trace("join");
		try {
			logger.info(user.toString());
			Map<String, Object> result = (Map<String, Object>) userServ.join(user);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/existname/{name}")
	public Object isexistName(@PathVariable String name) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (userServ.checkName(name) == null) {
			result.put("msg", "success");
		} else {
			result.put("msg", "fail");
		}
		return result;
	}
	

	@GetMapping("/canjoin/{email}")
	public Object canJoin(@PathVariable String email) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (userServ.checkUser(email) == null) {
			result.put("msg", "success");
		} else {
			result.put("msg", "fail");
		}
		return result;
	}

	@PutMapping("/modify")
	public Object modify(@RequestBody User user, HttpServletRequest request) {
		logger.trace("modify");
		try {
			String email = (String) loginServ.getData(request.getHeader("auth-token")).get("email");
			user.setEmail(email);
			logger.info(user.toString());
			Map<String, Object> result = (Map<String, Object>) userServ.modify(user);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/withdraw")
	public Object withdraw(HttpServletRequest request) {
		logger.trace("withdraw");
//		System.out.println("회원탈퇴" + request.getHeader("auth-token"));
		try {
			String email = (String) loginServ.getData(request.getHeader("auth-token")).get("email");
			logger.info(email);
			Map<String, Object> result = (Map<String, Object>) userServ.withdraw(email);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/pic")
	public Object getImg(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletRequest request)
			throws Exception {
		int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
		// System.out.println("uploaduid : " + uid);
		Map<String, Object> result = (Map<String, Object>) fileService.uploadImg(file, request, uid);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/image")
	public Object getImage(HttpServletRequest request) throws Exception {
		int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
		Map<String, Object> result = (Map<String, Object>) fileService.showImage(uid, request);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
//	@GetMapping(value = "/image/{uid}")
//	public @ResponseBody byte[] getImage(@PathVariable String uid, HttpServletRequest request) throws Exception {
//		//System.out.println("image uid : " + uid);
//		return fileService.showImg(uid, request);
//	}

	@PostMapping("/bgpic")
	public Object getbgImg(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletRequest request)
			throws Exception {
		int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
		// System.out.println("uploaduid : " + uid);
		Map<String, Object> result = (Map<String, Object>) fileService.uploadbgImg(file, request, uid);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/bgimage/{uid}")
	public @ResponseBody byte[] getbgImage(@PathVariable String uid, HttpServletRequest request) throws Exception {
		// System.out.println("image uid : " + uid);
		return fileService.showbgImg(uid, request);
	}

	@GetMapping("/name/{uid}")
	public Object getName(@PathVariable String uid) {
		logger.trace("getName");
		try {
			Map<String, Object> result = (Map<String, Object>) userServ.getName(uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.NO_CONTENT);
		}
	}
}
