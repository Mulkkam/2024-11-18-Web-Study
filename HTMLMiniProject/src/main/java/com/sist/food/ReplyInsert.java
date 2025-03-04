package com.sist.food;

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


@WebServlet("/ReplyInsert")
public class ReplyInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fno=request.getParameter("fno");
		String rno=request.getParameter("rno");

		ReplyDAO dao=ReplyDAO.newInstance();
		dao.replyDelete(Integer.parseInt(rno));
	
		response.sendRedirect("MainServlet?mode=2&fno="+fno);	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fno=request.getParameter("fno");
		String msg=request.getParameter("msg");
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		ReplyVO vo=new ReplyVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setMsg(msg);
		vo.setId(id);
		vo.setName(name);
		
		ReplyDAO dao=ReplyDAO.newInstance();
		dao.replyInsert(vo);
		
		response.sendRedirect("MainServlet?mode=2&fno="+fno);
		
	}

}
