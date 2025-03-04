package com.sist.genie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
@WebServlet("/GenieFind")
/*
 *   doGet()
 *   {
 *      화면 출력 
 *   }
 *   doPost()
 *   {
 *      검색어를 받는다 => 데이터연동 
 *   }
 */
public class GenieFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
    // GET / POST를 동시에 처리
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
	    
	    GenieDAO dao=GenieDAO.newInstance();
	    List<GenieVO> list=dao.genieFind(curpage, column, fd);
	    int totalpage=dao.genieFindTotalPage(column, fd);
	    
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
		out.println("<form method=post action=MainServlet?mode=8>");
		out.println("<select title=column class=input-sm>");
		out.println("<option value=title>곡명</option>");
		out.println("<option value=singer>가수명</option>");
		out.println("<option value=album>앨범명</option>");
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
			for(GenieVO vo:list)
			{
				out.println("<div class=\"col-md-3\">");
				out.println("<div class=\"thumbnail\">");
				out.println("<a href=\"GenieBeforeDetail?mno="+vo.getMno()+"\">");
				out.println("<img src="+vo.getPoster()+" style=\"width:230px;height:150px\">");
				out.println("<div class=\"caption\">");
				out.println("<p>"+vo.getTitle()+"</p>");
				out.println("<p>"+vo.getSinger()+"</p>");
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
			out.println("<li><a href=\"MainServlet?mode=8&column="+column+"&fd="+fd+"&page="+(startPage-1)+"\">&lt;</a></li>");
		}
		
		for(int i=startPage;i<=endPage;i++)
		{
			if(i==curpage)
				out.println("<li class=active><a href=\"MainServlet?mode=8&column="+column+"&fd="+fd+"&page="+i+"\">"+i+"</a></li>");
			else
				out.println("<li><a href=\"MainServlet?mode=8&column="+column+"&fd="+fd+"&page="+i+"\">"+i+"</a></li>");
		}
		
		if(endPage<totalpage)
		{
		 	out.println("<li><a href=\"MainServlet?mode=8&column="+column+"&fd="+fd+"&page="+(endPage+1)+"\">&gt;</a></li>");
		}
		out.println("</ul>");
	  
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}
}