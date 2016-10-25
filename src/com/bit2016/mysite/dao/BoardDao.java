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
	
	public List<BoardVo> getList(String kwd, Integer page, Integer size){
		List<BoardVo>  list = new ArrayList<BoardVo>();
		Connection  conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			if(kwd ==""){
				String sql = " select no, title, hit, regdate, name, depth, user_no, group_no, order_no "
						+ "from ( select rownum as rn, no, title, hit, regdate, name, depth, user_no, group_no, order_no "
						+ "from ( select  a.no, a.title as title , a.hit as hit , to_char(a.REGDATE,'yyyy-mm-dd hh:mi:ss') as regdate, "
						+ "a.USER_NO as user_no, a.DEPTH as depth, b.NAME as name, a.group_no as group_no, a.order_no as order_no "
						+ "from board a, users b "
						+ "where a.user_no = b.no "
						+ "order by a.group_no desc, a.order_no asc)) "
						+ "where (?-1)*?+1 <= rn and rn <=?*? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, page);
				pstmt.setInt(2, size);
				pstmt.setInt(3, page);
				pstmt.setInt(4, size);	
			}else{
				String sql = " select no, title, hit, regdate, name, depth, user_no, group_no, order_no "
						+ "from ( select rownum as rn, no, title, hit, regdate, name, depth, user_no, group_no, order_no "
						+ "from ( select  a.no, a.title as title , a.hit as hit , to_char(a.REGDATE,'yyyy-mm-dd hh:mi:ss') as regdate, "
						+ "a.USER_NO as user_no, a.DEPTH as depth, b.NAME as name, a.group_no as group_no, a.order_no as order_no "
						+ "from board a, users b "
						+ "where a.user_no = b.no "
						+ "and (title like ? or content like ?) "
						+ "order by a.group_no desc, a.order_no asc)) "
						+ "where (?-1)*?+1 <= rn and rn <=?*? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, "%"+kwd+"%" );
				pstmt.setString(2, "%"+kwd+"%" );

				pstmt.setInt(3, page);
				pstmt.setInt(4, size);
				pstmt.setInt(5, page);
				pstmt.setInt(6, size);
			}
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
				bvo.setGroup_no(rs.getInt(8));
				bvo.setOrder_no(rs.getInt(9));
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

	public int geTotalCount(String kwd) {
		int totalpage = 0;
		Connection  conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		try {
			conn = getConnection();
			if(kwd==""){
				String sql = "select count(*) from board";
				pstmt =conn.prepareStatement(sql);
				
			}else{
				String sql = "select count(*) from board where title like ? or content like ? ";
				pstmt =conn.prepareStatement(sql);
				pstmt.setString(1, kwd);
				pstmt.setString(2, kwd);
				
			}
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

	public void insert(BoardVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			if(vo.getGroup_no() == null){
				/*새글입력*/
				String sql = "insert  into board values( board_seq.nextval, ? ,?, sysdate, 0, nvl((select max(group_no) from board),0) + 1, 1, 0, ? )";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUsers_no());
				
			}else{
			/*답글입력*/
				String sql = "insert  into board values( board_seq.nextval, ? ,?, sysdate, 0, ? , ?, ?, ? )";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setInt(3, vo.getGroup_no());
				pstmt.setInt(4, vo.getOrder_no());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUsers_no());
				
			}
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error :"+e);
		} finally {
			try {
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
	
	public BoardVo get (Long no){
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			conn = getConnection();
			String sql = "select no, title, content, hit, regdate, user_no, depth, group_no, order_no from board where no = ? ";
			pstmt =conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setHit(rs.getInt(4));
				vo.setRegDate(rs.getString(5));
				vo.setUsers_no(rs.getLong(6));
				vo.setDepth(rs.getInt(7));
				vo.setGroup_no(rs.getInt(8));
				vo.setOrder_no(rs.getInt(9));
			}
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
		
		try {
			conn = getConnection();
			String sql = "delete from board where no = ? and user_no = ?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);
			
			pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("error: "+ e);
		}  finally {
			try {
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

	public void modify(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		 try {
			conn = getConnection();
			String sql = "update board set title=?, content=? where no = ?";
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: "+ e);
		}  finally {
			try {
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

	public void updateHit(Long no) {
		Connection conn = null;
		PreparedStatement pstmt= null;
		try {
			conn = getConnection();
			String sql = "update board set hit=hit+1 where no =? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: "+ e);
		}  finally {
			try {
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
	
	public void increaseGroupOrder( Integer groupNo, Integer orderNo ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set order_no = order_no + 1 where group_no = ? and order_no > ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, groupNo );
			pstmt.setInt(2, orderNo );
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
	}
}
