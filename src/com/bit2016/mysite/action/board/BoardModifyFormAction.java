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

public class BoardModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		long no = WebUtil.checkLongParam(request.getParameter("no"), 0L);
		BoardVo vo = new BoardDao().get(no);
		
		request.setAttribute("vo", vo);
		WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
	}

}
