package com.web.blog.model.service;

import java.util.List;

import com.web.blog.model.dto.RoadmapShare;

public interface RoadmapshareService {
	
	public Object selectRoadmapdataList(List<Integer> rmidlist);
	
	public Object selectAll();
	public Object selectByUserName(String name);
	public Object selectByTitle(String title);
	
	public Object insert(RoadmapShare roadmapshare,int nowuid);
	public Object delete(int uid, int pid);
	
	public Object selectlike(int uid,int pid);
	public Object like(int nowuid,int uid,int pid);
	public Object dislike(int nowuid,int uid,int pid);
	
}	
