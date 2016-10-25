package com.bit2016.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = WebUtil.checkLongParam(request.getParameter("no"), 0L);
		int page = WebUtil.checkIntParam(request.getParameter("p"),1);
		
		BoardDao dao = new BoardDao();
		BoardVo vo = dao.get(no);
		
		if(vo  == null){
			WebUtil.redirect(request, response, "/mysite3/board");
			return;
		}
		
		dao.updateHit(no);
		
		request.setAttribute("page", page);
		request.setAttribute("vo", vo);
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
		
	}

}
