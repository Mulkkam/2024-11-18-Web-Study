package com.sist.genie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.sist.dao.*;
import com.sist.vo.*;
@WebServlet("/GenieDetail")
public class GenieDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 // 브라우저로 전송 
		 PrintWriter out=response.getWriter();
		 
		 /*
		  *   request
		  *     => 클라이언트의 정보 (ip,port)
		  *     => 사용자 요청 정보 (사용자 보낸 값) 
		  *        = String getParameter() => 단일값
		  *        = String[] getParameterValues() => 다중값 
		  *          => checkbox
		  *        = encoding(byte[]) 전송 => decoding(원상 복귀)
		  *          => setCharacterEncoding("UTF-8");
		  *   https://www.google.com/search?q=%EC%9E%90%EB%B0%94&oq=%EC%9E%90%EB%B0%94&gs_lcrp=EgZjaHJvbWUyDwgAEEUYORiDARixAxiABDINCAEQABiDARixAxiABDINCAIQABiDARixAxiABDIHCAMQABiABDIKCAQQABixAxiABDIHCAUQABiABDIKCAYQABixAxiABDIHCAcQABiABDIHCAgQABiABDIHCAkQABiABNIBCTI0NDJqMGoxNagCCLACAQ&sourceid=chrome&ie=UTF-8
		  *   
		  *   
		  *   %EC%9E%90%EB%B0%94 ==> 자바
		  *   response 
		  *     => 서버에서 전송 정보 (HTML,Cookie)
		  *                       ----- ------- addCookie()
		  *                       setContentType() 
		  *     => 화면 이동 정보 
		  *        ----------- sendRedirect()
		  */
		 // 1. 사용자가 보낸 데이터 받기 
		 String mno=request.getParameter("mno");
		 // 2. 데이터베이스 연결 
		 GenieDAO dao=GenieDAO.newInstance();
		 GenieVO vo=dao.genieDetailData(Integer.parseInt(mno));
		 out.println("<html>");
		 out.println("<head>");
		 out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		 out.println("<link rel=stylesheet href=css/genie.css>");
		 out.println("</head>");
		 out.println("<body>");
		 out.println("<div class=container>");
		 out.println("<div class=row>");
		 out.println("<table class=table>");
		 out.println("<tr>");
		 out.println("<td width=30% class=text-center rowspan=8>");
		 out.println("<img src="+vo.getPoster()+" style=\"width:270px;height:300px\">");
		 out.println("</td>");
		 out.println("<td colspan=2>");
		 out.println("<h3>"+vo.getTitle()+"</h3>");
		 out.println("</td>");
		 out.println("</tr>");
		
		 out.println("<tr>");
		 out.println("<td width=10% style=\"color:gray\">가수</td>");
		 out.println("<td width=60%>"+vo.getSinger()+"</td>");
		 out.println("</tr>");
		
		 out.println("<tr>");
		 out.println("<td width=10% style=\"color:gray\">앨범</td>");
		 out.println("<td width=60%>"+vo.getAlbum()+"</td>");
		 out.println("</tr>");
		
		 out.println("<tr>");
		 out.println("<td width=10% style=\"color:gray\">순위</td>");
		 out.println("<td width=60%>"+vo.getMno());
		 if(vo.getState().equals("상승")) {
			 out.println("<h3 style=\"color:red\">"+vo.getIdcrement()+"&nbsp;<span style=\"color:red\">"+vo.getState()+"</span></h3>");
		 }
		 else if(vo.getState().equals("유지"))
		 {
			 out.println("<h3>"+vo.getIdcrement()+"&nbsp;<span>"+vo.getState()+"</span></h3>");
		 }
		 else if(vo.getState().equals("하강"))
		 {
			 out.println("<h3 style=\"color:orange\">"+vo.getIdcrement()+"&nbsp;<span style=\"color:orange\">"+vo.getState()+"</span></h3>");				
		 }
		 out.println("</td>");
		 out.println("</tr>");
		 
		 out.println("</table>");
		
		 out.println("<table class=table>");
		 out.println("<tr>");
		 out.println("<td class=text-right>");
		 out.println("<a href=# class=\"btn btn-xs btn-danger\">듣기</a>");
		 out.println("<a href=# class=\"btn btn-xs btn-success\">담기</a>");
		 out.println("<a href=# class=\"btn btn-xs btn-info\">다운로드</a>");
		 out.println("<a href=GenieList class=\"btn btn-xs btn-primary\">목록</a>");
		 out.println("</td>");
		 out.println("</tr>");
		 out.println("</table>");
		 // => 지도 
		 // => 댓글 
		 out.println("</div>");
		 out.println("</div>");
		 out.println("</body>");
		 out.println("</html>");
	}

}