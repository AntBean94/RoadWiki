package com.web.blog.model.service;

import com.google.gson.JsonArray;
import com.web.blog.model.dto.Curriculumtext;

public interface CurriculumService {
	
	public Object insertText(String uid,Curriculumtext curriculumtext);
	public Object deleteText(String uid,Curriculumtext curriculumtext);
	public Object getSuggest();
	public Object getSuggestBybdid(String bdid);
	public Object getSuggestBybdidmdid(String bdid, String mdid);

	
	public void create(int rmid,JsonArray nodeDataArray);
	public JsonArray getCurriculumByrmid(int rmid);
	public Object getSuggestBySearch(String text);
}	
