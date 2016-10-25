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

public class BoardModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = WebUtil.checkLongParam(request.getParameter("no"), 0L);
		
		HttpSession session = request.getSession();
		if(session == null){
			System.out.println("no session ");
			WebUtil.redirect(request, response, "/mysite3/board" );
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null){
			System.out.println("no authUser ");
			WebUtil.redirect(request, response, "/mysite3/board" );
			return;
		}
		
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		
		new BoardDao().modify(vo);
		
		WebUtil.redirect(request, response, "/mysite3/board?a=view&no="+no+"&update=success");

	}

}
