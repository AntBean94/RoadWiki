package com.web.blog.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.dto.RoadmapShare;
import com.web.blog.model.repo.RoadmapShareRepo;

@Service
public class RoadmapshareServiceImpl implements RoadmapshareService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RoadmapShareRepo roadmapshareRepo;
	
	@Autowired
	RoadmapService roadmapService;
	
	@Override
	public Object selectRoadmapdataList(List<Integer> rmidlist) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Roadmap> roadmaplist = new ArrayList<Roadmap>();
		try {
			for(int rmid : rmidlist) 
				roadmaplist.add((Roadmap)roadmapService.getRoadmap("0", Integer.toString(rmid)));
				
			result.put("roadmapdatalist", roadmaplist);
			
		} catch (Exception e) {
			logger.error("Service selectByUserName : Something wrong");
		}
		return result;
	}

	@Override
	public Object selectAll() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("roadmapshares", roadmapshareRepo.selectAll());
		} catch (Exception e) {
			logger.error("Service selectAll : Something wrong");
		}
		return result;
	}

	@Override
	public Object selectByUserName(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("roadmapshares", roadmapshareRepo.selectByUserName(name));
		} catch (Exception e) {
			logger.error("Service selectByUserName : Something wrong");
		}
		return result;
	}

	@Override
	public Object selectByTitle(String title) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("roadmapshares", roadmapshareRepo.selectByTitle(title));
		} catch (Exception e) {
			logger.error("Service selectByTitle : Something wrong");
		}
		return result;
	}

	@Override
	public Object insert(RoadmapShare roadmapshare,int nowuid) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(roadmapshare.getUid() != nowuid)
				throw new RuntimeException("wrong user");
			if(roadmapshareRepo.insert(roadmapshare) != 1)
				throw new RuntimeException("query wrong");
		} catch (Exception e) {
			logger.error("Service insert : Something wrong");
		}
		return result;
	}

	@Override
	public Object delete(int nowuid, int pid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(roadmapshareRepo.delete(nowuid,pid) != 1)
				throw new RuntimeException("not your roadmap");
		} catch (Exception e) {
			logger.error("Service delete : Something wrong");
		}
		return result;
	}

	@Override
	public Object selectlike(int uid, int pid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(roadmapshareRepo.selectlike(uid, pid) == 0)
				result.put("islike", false);
			else
				result.put("islike", true);
		} catch (Exception e) {
			logger.error("Service selectlike : Something wrong");
		}
		return result;
	}

	@Override
	@Transactional
	public Object like(int nowuid, int uid, int pid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(nowuid != uid)
				throw new RuntimeException("wrong user");
			if(roadmapshareRepo.updatelike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			if(roadmapshareRepo.insertlike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			
		} catch (Exception e) {
			logger.error("Service like : Something wrong");
		}
		return result;
	}

	@Override
	@Transactional
	public Object dislike(int nowuid, int uid, int pid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(nowuid != uid)
				throw new RuntimeException("wrong user");
			if(roadmapshareRepo.deletelike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			if(roadmapshareRepo.updatedislike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			
		} catch (Exception e) {
			logger.error("Service like : Something wrong");
		}
		return result;
	}

}
