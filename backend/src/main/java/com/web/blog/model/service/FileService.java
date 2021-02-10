package com.web.blog.model.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public Object uploadImg(MultipartFile file, HttpServletRequest request, int uid) throws Exception;
	public byte[] showImg(String uid, HttpServletRequest request) throws Exception;
	public Object uploadbgImg(MultipartFile file, HttpServletRequest request, int uid) throws Exception;
	public byte[] showbgImg(String uid, HttpServletRequest request) throws Exception;
	
}
