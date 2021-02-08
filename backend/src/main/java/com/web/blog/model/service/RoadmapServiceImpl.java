package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.repo.RoadmapRepo;

@Service
public class RoadmapServiceImpl implements RoadmapService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RoadmapRepo roadmaprepo;
	@Autowired
	CurriculumService curriservice;

	@Override
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public Object create(String nowuid, Roadmap map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uidnum = map.getUid();
			int nowuidnum = Integer.parseInt(nowuid);
			if (nowuidnum != uidnum)
				throw new RuntimeException("wrong user");
			
			JsonObject jsonObject = new JsonParser().parse(map.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = jsonObject.getAsJsonArray("nodeDataArray");
			jsonObject.remove("nodeDataArray");
			map.setTmp(jsonObject.toString());
			
			if (roadmaprepo.insert(map) != 1)
				throw new RuntimeException("Query wrong");
			int rmid = roadmaprepo.selectlastRmidByUid(nowuidnum);
			curriservice.create(rmid, nodeDataArray);
		} catch (Exception e) {
			logger.error("Service create : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public Object modify(String nowuid, Roadmap map) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int nowuidnum = Integer.parseInt(nowuid);
			if (nowuidnum != map.getUid())
				throw new RuntimeException("wrong user");

			JsonObject jsonObject = new JsonParser().parse(map.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = jsonObject.getAsJsonArray("nodeDataArray");
			jsonObject.remove("nodeDataArray");
			map.setTmp(jsonObject.toString());
			
			if (roadmaprepo.update(map) != 1)
				throw new RuntimeException("Query wrong");
			
			int rmid = roadmaprepo.selectlastRmidByUid(nowuidnum);
			curriservice.create(rmid, nodeDataArray);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("Service modify : Something wrong");
		}
		return result;
	}

	@Override
	public Object deleteRoadmap(String nowuid, String uid, String rmorder) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int nowuidnum = Integer.parseInt(nowuid);
			int uidnum = Integer.parseInt(uid);
			if (nowuidnum != uidnum)
				throw new RuntimeException("wrong user");

			int rmordernum = Integer.parseInt(rmorder);
			if (roadmaprepo.delete(rmordernum, uidnum) != 1)
				throw new RuntimeException("Query wrong");

		} catch (Exception e) {
			logger.error("Service deleteRoadmap : Something wrong");
		}
		return result;
	}

	@Override
	public Object getRoadmapListByUid(String nowuid, String uid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uidnum = Integer.parseInt(uid);
			int nowuidnum = Integer.parseInt(nowuid);

			if (nowuidnum == uidnum)
				result.put("roadmaps", roadmaprepo.selectMyRoadmapListByUid(uidnum));
			else
				result.put("roadmaps", roadmaprepo.selectOtherRoadmapListByUid(uidnum));

		} catch (Exception e) {
			logger.error("Service getRoadmapListByUid : Something wrong");
		}
		return result;
	}

	@Override
	public Object getRoadmapListByRmorder(String nowuid, String uid, String rmorder) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uidnum = Integer.parseInt(uid);
			int nowuidnum = Integer.parseInt(nowuid);
			int rmordernum = Integer.parseInt(rmorder);

			if (uidnum != nowuidnum)
				throw new RuntimeException("wrong user");

			result.put("roadmaps", roadmaprepo.selectRoadmapListByRmorder(rmordernum, uidnum));
		} catch (Exception e) {
			logger.error("Service getRoadmapListByRmorder : Something wrong");
		}
		
		return result;
	}

	@Override
	public Object getRoadmap(String nowuid, String rmid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int rmidnum = Integer.parseInt(rmid);
			int uidnum = roadmaprepo.selectUidByRmid(rmidnum);
			int nowuidnum = 0;
			if (uidnum < 0) {
				nowuidnum = uidnum;
			} else
				nowuidnum = Integer.parseInt(nowuid);
			Roadmap roadmap = null;
			if (nowuidnum == uidnum)
				roadmap = roadmaprepo.selectMyRoadmap(rmidnum);
			else
				roadmap = roadmaprepo.selectOtherRoadmap(rmidnum);

			if (roadmap == null)
				new RuntimeException("access denied");
			JsonObject jsonObject = new JsonParser().parse(roadmap.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = curriservice.getCurriculumByrmid(rmidnum);
			jsonObject.add("nodeDataArray", nodeDataArray);
			roadmap.setTmp(jsonObject.toString());
			result.put("roadmaps", roadmap);
		} catch (Exception e) {
			logger.error("Service getRoadmap : Something wrong");
		}
		return result;
	}

	@Override
	public Object getOfficialRoadmapList() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("roadmaps", roadmaprepo.selectOfficialListRoadmap());
		} catch (Exception e) {
			logger.error("Service getRoadmapListByUid : Something wrong");
		}
		return result;
	}

}
