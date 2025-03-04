package com.sist.food;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;

@WebServlet("/FoodFind")
public class FoodFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// JSP 파일 코딩 
		// 1. 브라우저로 전송 => HTML을 전송한다 
		// HTML,XML,JSON => response(HTML,Cookie전송이 가능)
		response.setContentType("text/html;charset=UTF-8");
		// 2. 브라우저 연결 
	    PrintWriter out=response.getWriter();
	    
	    // 사용자가 보내준 값을 받는다 
	    String strPage=request.getParameter("page");
	    if(strPage==null)
	    	strPage="1";
	    int curpage=Integer.parseInt(strPage);
	    
	    String column=request.getParameter("column");
	    String fd=request.getParameter("fd");
	    
	    FoodDAO dao=FoodDAO.newInstance();
	    List<FoodVO> list=dao.foodFind(curpage, column, fd);
	    int totalpage=dao.foodFindTotalPage(column, fd);
	    
	    final int BLOCK=10;
	    int startPage=((curpage-1)/BLOCK*BLOCK)+1;
	    int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	    
	    if(endPage>totalpage)
	    	endPage=totalpage;
	    // => DAO에서 결과값을 받는다 
	    out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<link rel=stylesheet href=css/food.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=\"row\">");
		out.println("<form method=post action=MainServlet?mode=4>");
		out.println("<select name=column class=input-sm>");
		out.println("<option value=name>업체명</option>");
		out.println("<option value=type>음식종류</option>");
		out.println("<option value=address>주소</option>");
		out.println("</select>");
		out.println("<input type=text name=fd size=15 class=input-sm>");
		out.println("<button class=\"btn-sm btn-danger\">검색</button>");
		out.println("</form>");
		out.println("</div>");
		out.println("<div class=row style=\"margin-top:20px\">");
		/*
		 *   response는 기능을 한개 수행이 가능 
		 *   ----------------------------
		 *   1. 쿠키 전송 => Detail이동 => HTML전송 
		 *   2. HTML전송 
		 *   
		 */
		
		if(list!=null)
		{
			for(FoodVO vo:list)
			{
				out.println("<div class=\"col-md-3\">");
				out.println("<div class=\"thumbnail\">");
				out.println("<a href=\"FoodBeforeDetail?fno="+vo.getFno()+"\">");
				out.println("<img src="+vo.getPoster()+" style=\"width:230px;height:150px\">");
				out.println("<div class=\"caption\">");
				out.println("<p>"+vo.getName()+"</p>");
				out.println("</div>");
				out.println("</a>");
				out.println("</div>");
				out.println("</div>");
			}
		}
		out.println("</div>");
		out.println("<div class=\"row text-center\">");
		out.println("<ul class=\"pagination\">");
		// startPage = 1 , 11 , 21
		if(startPage>1)
		{
		  out.println("<li><a href=\"MainServlet?mode=4&column="+column+"&fd="+fd+"&page="+(startPage-1)+"\">&lt;</a></li>");
		}
		
		for(int i=startPage;i<=endPage;i++)
		{
		 if(i==curpage)
		  out.println("<li class=active><a href=\"MainServlet?mode=4&column="+column+"&fd="+fd+"&page="+i+"\">"+i+"</a></li>");
		 else
		  out.println("<li><a href=\"MainServlet?mode=4&column="+column+"&fd="+fd+"&page="+i+"\">"+i+"</a></li>");
		}
		
		if(endPage<totalpage)
		{
		  out.println("<li><a href=\"MainServlet?mode=4&column="+column+"&fd="+fd+"&page="+(endPage+1)+"\">&gt;</a></li>");
		}
		out.println("</ul>");
	  
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}





