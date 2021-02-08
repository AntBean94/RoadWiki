package com.web.blog.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.Recomment;
import com.web.blog.model.repo.CommentRepo;
import com.web.blog.model.repo.PostingRepo;
import com.web.blog.model.repo.RecommentRepo;
import com.web.blog.model.repo.UserRepo;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	PostingRepo postingRepo;
	
	@Autowired
	CommentRepo commentRepo;

	@Autowired
	RecommentRepo recommentRepo;
	
	@Autowired
	UserRepo userRepo;
	
	final static String[] TAG = new String[] {"tag"};
	final static int[] PAGESIZE = new int[]{10};
	
	public Object getPostingListAll(String page_s, String classifier, String...tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListAll(page, cnt, classifier));
			} else {
				result.put("postings", postingRepo.selectListAllTag(page, cnt, classifier, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			for (int i = 0; i < Postings.length; i++)
				names[i] = userRepo.getName(Postings[i].getUid());
			result.put("names", names);
			result.put("msg", "success");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object getPostingListByName(String page_s, String classifier, String word, String...tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = 1 + (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListName(page, cnt, classifier, word));
			} else {
				result.put("postings", postingRepo.selectListNameTag(page, cnt, classifier, word, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			for (int i = 0; i < Postings.length; i++)
				names[i] = userRepo.getName(Postings[i].getUid());
			result.put("names", names);
			result.put("msg", "success");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object getPostingListByTitle(String page_s, String classifier, String word, String...tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = 1 + (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListTitle(page, cnt, classifier, word));
			} else {
				result.put("postings", postingRepo.selectListTitleTag(page, cnt, classifier, word, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			for (int i = 0; i < Postings.length; i++)
				names[i] = userRepo.getName(Postings[i].getUid());
			result.put("names", names);
			result.put("msg", "success");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object getPostingListByContent(String page_s, String classifier, String word, String...tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = 1 + (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListContent(page, cnt, classifier, word));
			} else {
				result.put("postings", postingRepo.selectListContentTag(page, cnt, classifier, word, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			for (int i = 0; i < Postings.length; i++)
				names[i] = userRepo.getName(Postings[i].getUid());
			result.put("names", names);
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object getPosting(String pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Posting posting = postingRepo.select(Integer.parseInt(pid));
			if(posting == null) throw new RuntimeException("cannot fine posting");
			result.put("posting", posting);
			
			List<Comment> comments = new ArrayList<>();
			for(Comment c : commentRepo.selectListPid(Integer.parseInt(pid))) 
				comments.add(c);
			result.put("comments", comments);
			
			List<Recomment> recomments = new ArrayList<>();
			for(Comment c : comments) 
				for(Recomment rc : recommentRepo.selectListCid(c.getCid()))
					recomments.add(rc);
			result.put("recomments", recomments);

			result.put("name", userRepo.getName(posting.getUid()));
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object registPosting(Posting posting, String uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(postingRepo.select(posting.getPid()).getUid() != Integer.parseInt(uid)) throw new RuntimeException("wrong user");
			if(postingRepo.insert(posting) == 1) result.put("msg", "success");
			else result.put("msg", "fail");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object editPosting(Posting posting, String uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(postingRepo.select(posting.getPid()).getUid() != Integer.parseInt(uid)) throw new RuntimeException("wrong user");
			if(postingRepo.update(posting) == 1) result.put("msg", "success");
			else result.put("msg", "fail");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object deletePosting(String pid_s, String uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int pid = Integer.parseInt(pid_s);
			if(postingRepo.select(pid).getUid() != Integer.parseInt(uid)) throw new RuntimeException("wrong user");
			if(postingRepo.delete(pid) == 1) result.put("msg", "success");
			else result.put("msg", "fail");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object registComment(Comment comment, String uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(commentRepo.select(comment.getCid()).getUid() != Integer.parseInt(uid)) throw new RuntimeException("wrong user");
			if(commentRepo.insert(comment) == 1) result.put("msg", "success");
			else result.put("msg", "fail");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object editComment(Comment comment, String uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(commentRepo.select(comment.getCid()).getUid() != Integer.parseInt(uid)) throw new RuntimeException("wrong user");
			if(commentRepo.update(comment) == 1) result.put("msg", "success");
			else result.put("msg", "fail");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object deleteComment(String cid, String uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(commentRepo.select(Integer.parseInt(cid)).getUid() != Integer.parseInt(uid)) throw new RuntimeException("wrong user");
			if(commentRepo.delete(Integer.parseInt(cid)) == 1) result.put("msg", "success");
			else result.put("msg", "fail");
		} catch(NumberFormatException e){
			throw new RuntimeException("input data type error");
		} catch(Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object totalCount() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int total = postingRepo.totalCount();
			result.put("total", total);
			result.put("msg", "success");
		} catch (SQLException e) {
			logger.error("sql error");
			result.put("msg", "fail");
		}
		return result;
	}

	@Override
	public Object registPosting(Posting posting, int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object editPosting(Posting posting, int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deletePosting(String pid, int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object registComment(Comment comment, int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object editComment(Comment comment, int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteComment(String cid, int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
