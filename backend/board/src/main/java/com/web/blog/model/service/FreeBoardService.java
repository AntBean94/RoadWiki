package com.web.blog.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.PostingLikeUser;
import com.web.blog.model.dto.Recomment;

public interface FreeBoardService {
	public Object getPostingListAll(String page_s, String classifier, String...tags) throws Exception;
	public Object getPostingListByName(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPostingListByTitle(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPostingListByContent(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPosting(String pid) throws Exception;
	public Object registPosting(Posting posting, int uid) throws Exception;
	public Object editPosting(Posting posting, int uid) throws Exception;
	public Object deletePosting(String pid, int uid) throws Exception;
	public Object registComment(Comment comment, int uid) throws Exception;
	public Object editComment(Comment comment, int uid) throws Exception;
	public Object deleteComment(String cid, int uid) throws Exception;
	public Object registRecomment(Recomment recomment) throws Exception;
	public Object editRecomment(Recomment recomment) throws Exception;
	public Object deleteRecomment(String rcid) throws Exception;
	public Object totalCount() throws Exception;
}
