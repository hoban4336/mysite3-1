package com.bit2016.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bit2016.mysite.vo.BoardVo;

public class BoardDao {

	public Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 :" + e);
		}
		return conn;
		
	}
	
	public List<BoardVo> getList(Integer page, Integer size){
		List<BoardVo>  list = new ArrayList<BoardVo>();
		Connection  conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = " select no, title, hit, reg_date, name, depth,user_no "
					+ "from ( select rownum as rn, no, title, hit, reg_date, name, depth, user_no "
					+ "from ( select  a.no, a.title as title , a.hit as hit , to_char(a.REG_DATE,'yyyy-mm-dd hh:mi:ss') as reg_date, a.USERS_NO as user_no, a.DEPTH as depth, b.NAME as name "
					+ "from board a, users b "
					+ "where a.users_no = b.no "
					+ "order by a.group_no desc, a.ORDERNO asc)) "
					+ "where (?-1)*?+1 <= rn and rn <=?*? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, page);
			pstmt.setInt(2, size);
			pstmt.setInt(3, page);
			pstmt.setInt(4, size);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardVo bvo = new BoardVo();
				bvo.setNo(rs.getLong(1));
				bvo.setTitle(rs.getString(2));
				bvo.setHit(rs.getInt(3));
				bvo.setRegDate(rs.getString(4));
				bvo.setName(rs.getString(5));
				bvo.setDepth(rs.getInt(6));
				bvo.setUsers_no(rs.getLong(7));
				System.out.println(bvo);
				list.add(bvo);
			}
		} catch (SQLException e) {
			System.out.println("error :"+e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		
		return list;
	}

	public int geTotalCount() {
		int totalpage = 0;
		Connection  conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		try {
			conn = getConnection();
			String sql = "select count(*) from board";
			pstmt =conn.prepareStatement(sql);
			rs= pstmt.executeQuery();
			if(rs.next()){
				totalpage = rs.getInt(1);
			}
		}  catch (SQLException e) {
			System.out.println("error :"+e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		return totalpage;
		
	}

	public BoardVo get (Long no){
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			conn = getConnection();
			String sql = "";
			conn.prepareStatement(sql);
			
		} catch (SQLException e) {
			System.out.println("error: "+ e);
		}  finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		return vo;
		
	}

	public void delete(Long no, Long userNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		try {
			conn = getConnection();
			String sql = "delete from board where no = ? and users_no = ?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);
			
			pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("error: "+ e);
		}  finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		
	}
}
