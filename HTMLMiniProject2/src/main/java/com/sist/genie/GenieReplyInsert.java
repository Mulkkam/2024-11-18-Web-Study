package com.sist.genie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.sist.vo.*;
import com.sist.dao.*;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;


@WebServlet("/GenieReplyInsert")
public class GenieReplyInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mno=request.getParameter("mno");
		String rno=request.getParameter("rno");

		GenieReplyDAO dao=GenieReplyDAO.newInstance();
		dao.genieReplyDelete(Integer.parseInt(rno));
	
		response.sendRedirect("MainServlet?mode=6&mno="+mno);	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mno=request.getParameter("mno");
		String msg=request.getParameter("msg");
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		GenieReplyVO vo=new GenieReplyVO();
		vo.setMno(Integer.parseInt(mno));
		vo.setMsg(msg);
		vo.setId(id);
		vo.setName(name);
		
		GenieReplyDAO dao=GenieReplyDAO.newInstance();
		dao.genieReplyInsert(vo);
		
		response.sendRedirect("MainServlet?mode=6&mno="+mno);
		
	}

}
