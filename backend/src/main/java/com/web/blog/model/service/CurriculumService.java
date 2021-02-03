package com.web.blog.model.service;

import java.util.List;

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.dto.Curriculumtext;

public interface CurriculumService {
	
	public Object insertText(Curriculumtext curriculumtext);
	public Object deleteText(Curriculumtext curriculumtext);
	public Object create(List<Curriculum> curriculumlist);
	public Object getCurriculumByrmid(int rmid);
	
}	
