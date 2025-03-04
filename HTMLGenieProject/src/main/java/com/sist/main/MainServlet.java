package com.sist.main;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

import com.sist.genie.GenieList;


@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 // 브라우저로 전송 
		 PrintWriter out=response.getWriter();
		 String mode=request.getParameter("mode");
		 if(mode==null)
			 mode="1";
		 String name="";
		 switch(Integer.parseInt(mode))
		 {
			 case 1:
				 name="GenieList";
				 break;
				
			 case 2:
				 name="GenieTypeFind";
				 break;
			 case 3:
				 name="GenieFind";
				 break;
		 } 
		 out.println("<html>");
		 out.println("<head>");
		 out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		 out.println("<link rel=stylesheet href=css/genie.css>");
		 out.println("</head>");
		 out.println("<body>");
		 String html="<nav class=\"navbar navbar-inverse\">"
				 + "  <div class=\"container-fluid\">"
				 + "    <div class=\"navbar-header\">"
				 + "      <a class=\"navbar-brand\" href=\"#\">Servlet Site</a>"
				 + "    </div>\r\n"
				 + "    <ul class=\"nav navbar-nav\">"
				 + "      <li class=\"active\"><a href=\"#\">Home</a></li>"
				 + "      <li><a href=\"MainServlet?mode=1\">인기곡</a></li>"
				 + "      <li><a href=\"MainServlet?mode=2\">장르검색</a></li>"
				 + "      <li><a href=\"MainServlet?mode=3\">음악검색</a></li>"
				 + "      <li><a href=\"#\">회원</a></li>"
				 + "    </ul>"
				 + "  </div>"
				 + "</nav>";
		 out.println(html);
		 RequestDispatcher rd=request.getRequestDispatcher(name);
		 rd.include(request, response);
		 out.println("</body>");
		 out.println("</html>");
	}

}
