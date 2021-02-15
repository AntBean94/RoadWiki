package com.web.blog.model.repo;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Follow;

@Repository
public interface FollowRepo {
	public int[] selectFollowers(int toUid) throws SQLException;
	public int[] selectFollowings(int fromUid) throws SQLException;
	public int insert(Follow follow) throws SQLException;
	public int delete(Follow follow) throws SQLException;
}
