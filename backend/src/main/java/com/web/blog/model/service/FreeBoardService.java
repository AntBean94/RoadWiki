package com.web.blog.model.service;

import java.util.Map;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;

public interface FreeBoardService {
	public Object getPostingListAll(String page_s, String classifier, String...tags);
	public Object getPostingListByName(String page_s, String classifier, String word, String...tags);
	public Object getPostingListByTitle(String page_s, String classifier, String word, String...tags);
	public Object getPostingListByContent(String page_s, String classifier, String word, String...tags);
	public Object getPosting(String pid);
	public Object registPosting(Posting posting, int uid);
	public Object editPosting(Posting posting, int uid);
	public Object deletePosting(String pid, int uid);
	public Object registComment(Comment comment, int uid);
	public Object editComment(Comment comment, int uid);
	public Object deleteComment(String cid, int uid);
	public Object totalCount();

}
