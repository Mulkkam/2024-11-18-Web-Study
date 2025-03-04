package com.sist.genie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.GenieVO;
/*
 *   1. 사용자의 전송방식 
 *      GET / POST 
 *      | <a> , sendRedirect() => URL?데이터전송 
 *      | <form> , ajax , axios.post()
 *      
 *      검색어 입력 / 찾는 컬럼 => 두개를 동시에 전송 
 *      -------------------------------------<form> =>POST
 *      처음 출력은 => GET
 *      
 *      Get => doGet() ==> @GetMapping()
 *      Post => doPost() ==> @PostMapping()
 *      Get/Post => service() @RequestMapping()
 *                            => Spring 6.0 : 권장
 *                            
 *      JSP => doGet/doPost가 없다 
 *             _jspService()
 */
@WebServlet("/GenieTypeFind")
public class GenieTypeFind extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 브라우저로 전송 => HTML을 전송한다 
		// HTML,XML,JSON => response(HTML,Cookie전송이 가능)
		response.setContentType("text/html;charset=UTF-8");
		// 2. 브라우저 연결 
	    PrintWriter out=response.getWriter();
	    
	    GenieDAO dao=GenieDAO.newInstance();
	    // 사용자가 보내준 값을 받는다 
	    String page=request.getParameter("page");
	    if(page==null)
	    	page="1";
	    int curpage=Integer.parseInt(page);
	    
	    String cno=request.getParameter("cno");
	    if(cno==null)
	    	cno="1";

	    List<GenieVO> list=dao.genieTypeFind(curpage,Integer.parseInt(cno));
	    
	    int totalpage=dao.genieTypeTotalPage(Integer.parseInt(cno));
	    
	    final int BLOCK=10;
	    int startPage=((curpage-1)/BLOCK*BLOCK)+1;
	    int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	    
	    if(endPage>totalpage)
	    	endPage=totalpage;
	    // => DAO에서 결과값을 받는다 
	    
	    out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<link rel=stylesheet href=css/genie.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=\"row text-center\">");
		out.println("<a href=GenieTypeFind?cno=1 class=\"btn btn-sm btn-danger\">Top50</a>");
		out.println("<a href=GenieTypeFind?cno=2 class=\"btn btn-sm btn-success\">가요</a>");
		out.println("<a href=GenieTypeFind?cno=3 class=\"btn btn-sm btn-info\">POP</a>");
		out.println("<a href=GenieTypeFind?cno=4 class=\"btn btn-sm btn-primary\">OST</a>");
		out.println("<a href=GenieTypeFind?cno=5 class=\"btn btn-sm btn-warning\">트로트</a>");
		out.println("<a href=GenieTypeFind?cno=6 class=\"btn btn-sm btn-default\">JAZZ</a>");
		out.println("<a href=GenieTypeFind?cno=7 class=\"btn btn-sm btn-link\">CLASSIC</a>");
		out.println("</div>");
		out.println("<div class=row style=\"margin-top:20px\">");
		/*
		 *   response는 기능을 한개 수행이 가능 
		 *   ----------------------------
		 *   1. 쿠키 전송 => Detail이동 => HTML전송 
		 *   2. HTML전송 
		 *   
		 */
		
		for(GenieVO vo:list)
		{
			out.println("<div class=\"col-md-3\">");
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#\">");
			out.println("<img src="+vo.getPoster()+" style=\"width:230px;height:150px\">");
			out.println("<div class=\"caption\">");
			out.println("<p>"+vo.getTitle()+"</p>");
			out.println("<p>"+vo.getSinger()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");
		out.println("<div class=\"row text-center\">");
		out.println("<ul class=\"pagination\">");
		// startPage = 1 , 11 , 21
		if(startPage>1)
		{
			out.println("<li><a href=\"GenieTypeFind?cno="+cno+"&page="+(startPage-1)+"\">&lt;</a></li>");
		}
		
		for(int i=startPage;i<=endPage;i++)
		{
			if(i==curpage)
				out.println("<li class=active><a href=\"GenieTypeFind?cno="+cno+"&page="+i+"\">"+i+"</a></li>");
			else
				out.println("<li><a href=\"GenieTypeFind?cno="+cno+"&page="+i+"\">"+i+"</a></li>");
		}
		
		if(endPage<totalpage)
		{
			out.println("<li><a href=\"GenieTypeFind?cno="+cno+"&page="+(endPage+1)+"\">&gt;</a></li>");
		}
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}