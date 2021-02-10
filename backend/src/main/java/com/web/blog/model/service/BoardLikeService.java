package com.web.blog.model.service;

import com.web.blog.model.dto.PostingLikeUser;

public interface BoardLikeService {
	public Object getPostingLikeUser(PostingLikeUser postingLikeUser) throws Exception;
	public Object registPostingLike(PostingLikeUser postingLikeUser) throws Exception;
	public Object deletePostingLike(PostingLikeUser postingLikeUser) throws Exception;
}
