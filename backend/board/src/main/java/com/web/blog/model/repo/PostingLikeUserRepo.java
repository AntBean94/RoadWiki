package com.web.blog.model.repo;

import java.sql.SQLException;

import com.web.blog.model.dto.PostingHateUser;
import com.web.blog.model.dto.PostingLikeUser;

public interface PostingLikeUserRepo {
	public PostingLikeUser select(PostingLikeUser postingLikeUser) throws SQLException;
	public int totallike(int pid) throws SQLException;
	public int insert(PostingLikeUser postingLikeUser) throws SQLException;
	public int delete(PostingLikeUser postingLikeUser) throws SQLException;
	public PostingHateUser selectHate(PostingHateUser postingHateUser) throws SQLException;
	public int totalhate(int pid) throws SQLException;
	public int insertHate(PostingHateUser postingHateUser) throws SQLException;
	public int deleteHate(PostingHateUser postingHateUser) throws SQLException;
}
