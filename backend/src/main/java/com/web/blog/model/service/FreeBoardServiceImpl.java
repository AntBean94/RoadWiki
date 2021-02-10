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
import com.web.blog.model.dto.PostingLikeUser;
import com.web.blog.model.dto.Recomment;
import com.web.blog.model.repo.CommentRepo;
import com.web.blog.model.repo.PostingLikeUserRepo;
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

	final static String[] TAG = new String[] { "tag" };
	final static int[] PAGESIZE = new int[] { 10 };

	@Override
	public Object getPostingListAll(String page_s, String classifier, String... tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = (Integer.parseInt(page_s) - 1) * cnt;

			if (tags.length == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("cnt", cnt);
				result.put("postings", postingRepo.selectListAll(map));
			} else {
				result.put("postings", postingRepo.selectListAllTag(page, cnt, classifier, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			for (int i = 0; i < Postings.length; i++)
				names[i] = userRepo.getName(Postings[i].getUid());
			result.put("names", names);
			result.put("msg", "success");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListByName(String page_s, String classifier, String word, String... tags) throws Exception {
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
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListByTitle(String page_s, String classifier, String word, String... tags)
			throws Exception {
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
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListByContent(String page_s, String classifier, String word, String... tags)
			throws Exception {
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
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPosting(String pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Posting posting = postingRepo.select(Integer.parseInt(pid));
			if (posting == null)
				throw new RuntimeException("cannot fine posting");
			result.put("posting", posting);

			List<Comment> comments = new ArrayList<>();
			for (Comment c : commentRepo.selectListPid(Integer.parseInt(pid)))
				comments.add(c);
			result.put("comments", comments);
			List<Recomment>[] recomments = new ArrayList[comments.size()];
			for (int i = 0; i < recomments.length; i++) {
				recomments[i] = new ArrayList<Recomment>();
			}
//			for(Comment c : comments) 
//				for(Recomment rc : recommentRepo.selectListCid(c.getCid()))
//					recomments.add(rc);

			for (int i = 0; i < recomments.length; i++) {
				for (Recomment rc : recommentRepo.selectListCid(comments.get(i).getCid())) {
					recomments[i].add(rc);
				}
			}
			result.put("recomments", recomments);

			result.put("name", userRepo.getName(posting.getUid()));
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object registPosting(Posting posting, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (posting.getUid() != uid)
				throw new RuntimeException("wrong user");
			if (postingRepo.insert(posting) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			e.printStackTrace();

			throw e;
		}
		return result;
	}

	@Override
	public Object editPosting(Posting posting, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (postingRepo.select(posting.getPid()).getUid() != uid)
				throw new RuntimeException("wrong user");
			if (postingRepo.update(posting) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object deletePosting(String pid_s, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int pid = Integer.parseInt(pid_s);
			if (postingRepo.select(pid).getUid() != uid)
				throw new RuntimeException("wrong user");
			if (postingRepo.delete(pid) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object registComment(Comment comment, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
//			if(commentRepo.select(comment.getUid() != uid) throw new RuntimeException("wrong user");
			if (commentRepo.insert(comment) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object editComment(Comment comment, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
//			if(commentRepo.select(comment.getCid()).getUid() != uid) throw new RuntimeException("wrong user");
			if (commentRepo.update(comment) == 1) {
				result.put("msg", "success");
			}

			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object deleteComment(String cid, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (commentRepo.select(Integer.parseInt(cid)).getUid() != uid)
				throw new RuntimeException("wrong user");
			if (commentRepo.delete(Integer.parseInt(cid)) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object registRecomment(Recomment recomment) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (recommentRepo.insert(recomment) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public Object editRecomment(Recomment recomment) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (recommentRepo.update(recomment) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public Object deleteRecomment(String rcid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
//			if (recommentRepo.select(Integer.parseInt(rcid)).getUid() != uid)
//				throw new RuntimeException("wrong user");
			if (recommentRepo.delete(Integer.parseInt(rcid)) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
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

}
