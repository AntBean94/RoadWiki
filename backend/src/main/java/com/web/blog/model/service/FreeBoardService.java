package com.web.blog.model.service;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;

public interface FreeBoardService {
	public Object getPostingListAll(String page_s, String classifier, String...tags)  throws Exception;
	public Object getPostingListByName(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPostingListByTitle(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPostingListByContent(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPosting(String pid) throws Exception;
	public Object registPosting(Posting posting, String uid) throws Exception;
	public Object editPosting(Posting posting, String uid) throws Exception;
	public Object deletePosting(String pid, String uid) throws Exception;
	public Object registComment(Comment comment, String uid) throws Exception;
	public Object editComment(Comment comment, String uid) throws Exception;
	public Object deleteComment(String cid, String uid) throws Exception;

}
