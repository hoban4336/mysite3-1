package com.bit2016.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class BoardWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(session == null){
			WebUtil.redirect(request, response, "/mysite3/main");
			return;	
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null){
			WebUtil.redirect(request, response, "/mysite3/board");
			return;
		}
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		vo.setUsers_no(authUser.getNo());
		
		if(request.getParameter("gno")!=null){
			int group_no = WebUtil.checkIntParam(request.getParameter("gno"), 0);
			int order_no = WebUtil.checkIntParam(request.getParameter("ono"), 0);
			int depth = WebUtil.checkIntParam(request.getParameter("d"), 0);
			
			dao.increaseGroupOrder(group_no,order_no);
			
			vo.setGroup_no(group_no);
			vo.setOrder_no(order_no+1);
			vo.setDepth(depth+1);
		}
		
			dao.insert(vo);
		
		WebUtil.redirect(request, response, "/mysite3/board");

	}

}
