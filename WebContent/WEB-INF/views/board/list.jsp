<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${totalCount - (currentpage -1)*listSize - status.index }</td>
							<c:choose>
								<c:when test="${vo.depth >= 0 }">
									<td class="left" style="padding-left: ${vo.depth * 20}px">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png">
										<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }&p=${currentpage}">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td class="left">
										<a href = ""> ${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when  test="${not empty authUser && authUser.no == vo.users_no }">
									<td><a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }&p=${currentpage}" class="del">삭제</a></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<c:if test="${startpage ne '1' }">
							<li><a href="/mysite3/board?p=${startpage-1 }">◀</a></li>
						</c:if>
						<c:forEach begin="${startpage }"  end="${starpage+listSize }" var="npage">
						<c:choose>
							<c:when test="${currentpage == npage }">
								<li class="selected"><a href="/mysite3/board?p=${npage }">${npage }</a></li>
							</c:when>
							<c:otherwise>
								<li >
									<c:choose><c:when test ="${end >= npage }">
										<a href="${pageContext.request.contextPath }/board?p=${npage }">${npage }</a>
										</c:when>
									<c:otherwise>
										${npage }
									</c:otherwise>
									</c:choose>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${end > (starpage+listSize)}">
							<li><a href="${pageContext.request.contextPath }/board?p="${(starpage+listSize)+1 }">▶</a></li>
						</c:if>
					</ul>
				</div>				
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>