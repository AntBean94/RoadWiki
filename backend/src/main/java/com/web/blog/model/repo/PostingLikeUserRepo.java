package com.web.blog.model.repo;

import java.sql.SQLException;

import com.web.blog.model.dto.PostingLikeUser;

public interface PostingLikeUserRepo {
	public PostingLikeUser select(PostingLikeUser postingLikeUser) throws SQLException;
	public int insert(PostingLikeUser postingLikeUser) throws SQLException;
	public int delete(PostingLikeUser postingLikeUser) throws SQLException;
}
