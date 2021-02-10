package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.PostingLikeUser;
import com.web.blog.model.repo.PostingLikeUserRepo;

@Service
public class BoardLikeServieImpl implements BoardLikeService {
	
	@Autowired
	PostingLikeUserRepo postinglikeuserRepo;

	@Override
	public Object getPostingLikeUser(PostingLikeUser postingLikeUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PostingLikeUser postLike = postinglikeuserRepo.select(postingLikeUser);
			result.put("msg", "success");
			result.put("like", postLike);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object registPostingLike(PostingLikeUser postingLikeUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			postinglikeuserRepo.insert(postingLikeUser);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}


	@Override
	public Object deletePostingLike(PostingLikeUser postingLikeUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			postinglikeuserRepo.delete(postingLikeUser);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
}
