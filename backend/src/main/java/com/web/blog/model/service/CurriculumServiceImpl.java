package com.web.blog.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.dto.Curriculumtext;

@Service
public class CurriculumServiceImpl implements CurriculumService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	//@Autowired
	//RoadmapRepo roadmaprepo;

	@Override
	public Object insertText(Curriculumtext curriculumtext) {
		//sdid , mdid, bdid 순으로 검사하면서 있으면 그 위치에 넣는 repo 실행
		
		
		return null;
	}

	@Override
	public Object deleteText(Curriculumtext curriculumtext) {
		//sdid,mdid,bdid 순으로 검사하면서 있으면 그 위치에 꺼 삭제하는 repo 실행
		
		return null;
	}

	@Override
	public Object create(List<Curriculum> curriculumlist) {
		//small, middle, big 나누어서 따로 list 만들어 저장하고 각각 repo실행
		
		
		return null;
	}

	@Override
	public Object getCurriculumByrmid(int rmid) {
		//small, middle, big 에서 rmid로 싹 긁어오고 json으로 만들어서 보내주기
		
		return null;
	}


	

}
