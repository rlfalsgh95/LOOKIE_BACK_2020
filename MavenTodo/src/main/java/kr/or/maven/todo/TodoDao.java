package kr.or.maven.todo;

import java.sql.*;
import java.util.*;



public class TodoDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=Asia/Seoul&useSSL=false";
	private static String dbUser = "connectuser";
	private static String dbpasswd = "connect123!@#";
	public int addTodo(TodoDto dto) {
		int insertCount = 0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO todo (title, name, sequence) VALUES ( ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getName());
			ps.setInt(3, dto.getSequence());

			insertCount = ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return insertCount;
	}
	public List<TodoDto> getTodos() {
		List<TodoDto> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String sql = "SELECT id,title, regdate, name, type, sequence FROM todo order by regdate asc";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Long id = rs.getLong("id");
					String title = rs.getString(2);
					String regdate = rs.getString(3);
					String name = rs.getString(4);
					String type= rs.getString(5);					
					int sequence = rs.getInt("sequence");
					
					TodoDto dto= new TodoDto(id,name,regdate,sequence,title,type);
					list.add(dto); // list에 반복할때마다 Role인스턴스를 생성하여 list에 추가한다.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public int updateDto(String id,String type) {
		int updateCount = 0;
		System.out.println(id+type);
		int num=Integer.parseInt(id);
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName( "com.mysql.cj.jdbc.Driver" );
			
			conn = DriverManager.getConnection ( dburl, dbUser, dbpasswd );
			
			
			
			String sql = "update todo set type = ? where id = ?";
			ps = conn.prepareStatement(sql);
			if(type.equals("TODO")) {
				ps.setString(1, "DOING");
				ps.setInt(2,  num);
			}
			else if(type.equals("DOING")) {
				ps.setString(1, "DONE");
				ps.setInt(2,  num);
			}
	
			
			updateCount = ps.executeUpdate();

		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(ps != null) {
				try {
					ps.close();
				}catch(Exception ex) {}
			} // if
			
			if(conn != null) {
				try {
					conn.close();
				}catch(Exception ex) {}
			} // if
		} // finally
		
		return updateCount;
	}


}
