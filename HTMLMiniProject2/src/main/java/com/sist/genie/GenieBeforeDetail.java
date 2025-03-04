package com.sist.genie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GenieBeforeDetail")
public class GenieBeforeDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mno=request.getParameter("mno");
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			for(int i=0;i<cookies.length;i++)
			{
				if(cookies[i].getName().equals("genie_"+mno))
				{
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
		}
		Cookie cookie=new Cookie("genie_"+mno, mno);		
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24); // 저장기간 => 1일
		response.addCookie(cookie);
		response.sendRedirect("MainServlet?mode=6&mno="+mno);
	}

}