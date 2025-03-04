package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.vo.*;

public class GenieReplyDAO {
	// 연결 => 오라클 
	  private Connection conn;
	  // SQL=> 송신 , 결과값 => 수신 
	  private PreparedStatement ps;
	  // 연결 => URL 
	  private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	  // 한사람당 한개의 DAO사용 => 싱글턴 
	  private static GenieReplyDAO dao;
	  
	  // 1. 드라이버 등록 
	  public GenieReplyDAO()
	  {
		  try
		  {
			  Class.forName("oracle.jdbc.driver.OracleDriver");
		  }catch(Exception ex) {}
	  }
	  // 2. 싱글턴 => static으로 설정 (static 메모리 공간이 한개)
	  public static GenieReplyDAO newInstance()
	  {
		  if(dao==null)
			  dao=new GenieReplyDAO();
		  return dao;
	  }
	  // 3. 오라클 연결 
	  public void getConnection()
	  {
		  try
		  {
			  conn=DriverManager.getConnection(URL,"hr","happy");
			  // conn hr/happy
		  }catch(Exception ex) {}
	  }
	  // 4. 오라클 닫기 
	  public void disConnection()
	  {
		  try
		  {
			  if(ps!=null) ps.close();
			  if(conn!=null) conn.close();
		  }catch(Exception ex) {}
	  }
	  
	  // 기능 
	  // DML => SELECT / UPDATE / DELETE / INSERT
	  public void genieReplyInsert(GenieReplyVO vo)
	  {
		  try
		  {
			  getConnection();
			  String sql="INSERT INTO geniereply VALUES("
					    +"reply_rno_seq.nextval,?,?,?,?,SYSDATE)";
			  ps=conn.prepareStatement(sql);
			  ps.setInt(1,vo.getMno());
			  ps.setString(2, vo.getId());
			  ps.setString(3, vo.getName());
			  ps.setString(4, vo.getMsg());
			  ps.executeUpdate();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
	  }
	  public List<GenieReplyVO> genieReplyListData(int mno)
	  {
		  List<GenieReplyVO> list=new ArrayList<GenieReplyVO>();
		  try
		  {
			  getConnection();
			  String sql="SELECT rno,mno,id,name,msg,"
					    +"TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') "
					    +"FROM geniereply "
					    +"WHERE mno="+mno
					    +" ORDER BY rno DESC";
			  ps=conn.prepareStatement(sql);
			  ResultSet rs=ps.executeQuery();
			  while(rs.next())
			  {
				  GenieReplyVO vo=new GenieReplyVO();
				  vo.setRno(rs.getInt(1));
				  vo.setMno(rs.getInt(2));
				  vo.setId(rs.getString(3));
				  vo.setName(rs.getString(4));
				  vo.setMsg(rs.getString(5));
				  vo.setDbday(rs.getString(6));
				  list.add(vo);
			  }
			  rs.close();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
		  return list;
	  }
	  public void genieReplyDelete(int rno)
	  {
		  try
		  {
			  getConnection();
			  String sql="DELETE FROM geniereply "
					    +"WHERE rno="+rno;
			  ps=conn.prepareStatement(sql);
			  ps.executeUpdate();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
	  }
	  public void genieReplyUpdate(int rno,String msg)
	  {
		  try
		  {
			  getConnection();
			  String sql="UPDATE geniereply SET "
					    +"msg=? "
					    +"WHERE rno=?";
			  ps=conn.prepareStatement(sql);
			  ps.setString(1, msg);
			  ps.setInt(2, rno);
			  ps.executeUpdate();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
	  }
}
