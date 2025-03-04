package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class GenieDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static GenieDAO dao;
	
	public GenieDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (Exception ex) {
			// TODO: handle exception
		}
	}
	public static GenieDAO newInstance()
	{
		if(dao==null)
			dao=new GenieDAO();
		return dao;
	}
	
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch (Exception ex) {
			// TODO: handle exception
		}
	}
	public void disConnection()
	{
		try 
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}
	public List<GenieVO> genieListData(int page)
	{
		List<GenieVO> list=new ArrayList<GenieVO>();
		try
		{
			getConnection();
			String sql="SELECT mno,title,singer,poster,num "
					+"FROM (SELECT mno,title,singer,poster,rownum as num "
					+"FROM (SELECT /*+ INDEX_ASC(genie_music gm_mno_pk)*/mno,title,poster,singer "
					+"FROM genie_music)) "
					+"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GenieVO vo=new GenieVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSinger(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
		}catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	public int genieTotalPage()
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/12.0) "
					+ "FROM genie_music";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		}catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
	public GenieVO genieDetailData(int mno)
	{
		GenieVO vo=new GenieVO();
		try
		{
			getConnection();
			String sql="UPDATE genie_music SET "
						+"hit=hit+1 "
						+"WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
		  
			// mainpage => 공동작업 
			/*	
		   		private int fno,hit;
			  	private double score;
			  	private String name,type,phone,address,theme,poster,
	          	images,time,parking,content,price;
			 */
			sql="SELECT title,singer,album,poster,"
					+"idcrement,state,hit "
					+"FROM genie_music "
					+"WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			// MyBatis 
			vo.setTitle(rs.getString("title"));
			vo.setSinger(rs.getString("singer"));
			vo.setAlbum(rs.getString("album"));
			vo.setPoster(rs.getString("poster"));
			vo.setIdcrement(rs.getInt("idcrement"));
			vo.setState(rs.getString("state"));
			vo.setHit(rs.getInt("hit"));
		  	rs.close();
		  
	  	}
	  	catch(Exception ex)
	  	{
		  	ex.printStackTrace();
	  	}
	  	finally
	  	{
		  	disConnection();
	  	}
	  	return vo;
	}
	// cookie 데이터 
	public GenieVO genieCookieData(int mno)
	{
		GenieVO vo=new GenieVO();
		try
		{
			getConnection();
			String sql="SELECT mno,title,poster "
					+"FROM genie_music "
					+"WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setMno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setPoster(rs.getString(3));
			rs.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}	  
	// 음악 종류별 검색 
	public List<GenieVO> genieTypeFind(int page,int cno)
	{
		List<GenieVO> list=new ArrayList<GenieVO>();
		try
		{
			getConnection();	
			String sql="SELECT mno,title,singer,poster,num "
				+"FROM (SELECT mno,title,singer,poster,rownum as num "
				+"FROM (SELECT /*+ INDEX_ASC(genie_music gm_mno_pk)*/mno,title,singer,poster "
				+"FROM genie_music WHERE cno=?)) "
				+"WHERE num BETWEEN ? AND ?";
				  
			ps=conn.prepareStatement(sql);
			//4. ?에 값을 채운다 
			int rowSize=12;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setInt(1, cno);
			ps.setInt(2, start);
			ps.setInt(3, end);
			  
			// rownum은 1번부터 시작한다
		    // 자바 => 0 , 오라클 => 1
		    /*
		     *    'Hello Oracle'
		     *     123456....
		     */
		    //5. 실행 결과 읽기 
		    ResultSet rs=ps.executeQuery();
		    // => list에 저장 
		    while(rs.next())
		    {
		    	GenieVO vo=new GenieVO();
		    	vo.setMno(rs.getInt(1));
		    	vo.setTitle(rs.getString(2));
		    	vo.setSinger(rs.getString(3));
		    	vo.setPoster(rs.getString(4));
		    	list.add(vo);
		    }
	    	rs.close();
	 	} catch(Exception ex) {
	 		ex.printStackTrace();
	 	}
	 	finally
	 	{
	 		disConnection();
	 	}
		return list;
	}
	public int genieTypeTotalPage(int cno)
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/12.0) "
					+"FROM genie_music "
				    +"WHERE cno="+cno;
			  
			ps=conn.prepareStatement(sql);	
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();	  
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
	// ?page= &col= &fd=
	public List<GenieVO> genieFind(int page,String col,String fd)
	{
		List<GenieVO> list=new ArrayList<GenieVO>();
		try
		{
			getConnection();
			//SELECT fno,name,poster,address,type,num 
			String sql="SELECT mno,title,poster,singer,album,num "
					  +"FROM (SELECT mno,title,poster,singer,album,rownum as num "
					  +"FROM (SELECT mno,title,poster,singer,album "
					  +"FROM genie_music "
					  +"WHERE "+col+" LIKE '%'||?||'%')) "
					  +"WHERE num BETWEEN ? AND ?";
			// setString(1,col) => col=address
			// => WHERE 'address' 
			ps=conn.prepareStatement(sql);
			int rowSize=20;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			  
			ps.setString(1, fd);
			ps.setInt(2, start);
			ps.setInt(3, end);
			  
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GenieVO vo=new GenieVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				list.add(vo);
			}
			rs.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	public int genieFindTotalPage(String col,String fd)
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/20.0) "
					+"FROM genie_music "
					+"WHERE "+col+" LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
}
