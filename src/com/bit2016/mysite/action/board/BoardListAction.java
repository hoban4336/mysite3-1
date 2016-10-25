package com.bit2016.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class BoardListAction implements Action {
	private static final int LIST_SIZE = 5; // <1,2,3,4,5>
	private static final int PAGE_SIZE = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentpage = WebUtil.checkIntParam(request.getParameter("p"), 1);

		String keyword = WebUtil.checkNullParam(request.getParameter("kwd"), "");
		System.out.println("keyword: "+keyword);
		BoardDao dao = new BoardDao();

		int totalCount = dao.geTotalCount(keyword);
		int endpage = (int) Math.ceil ( (double) totalCount / PAGE_SIZE );
		if (currentpage > endpage) {
			currentpage = 1;
		}

		int startpoint = (int) Math.ceil ( (double) (currentpage / (LIST_SIZE + 1) ) ) * LIST_SIZE + 1;
		System.out.println( "startpoint: " + startpoint);

		List<BoardVo> list = dao.getList(keyword, currentpage, LIST_SIZE);
		request.setAttribute( "list", list);

		request.setAttribute( "totalCount", totalCount);
		request.setAttribute( "currentpage", currentpage);
		request.setAttribute( "listSize", LIST_SIZE);

		request.setAttribute( "startpage", startpoint);
		request.setAttribute( "end", endpage);
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
