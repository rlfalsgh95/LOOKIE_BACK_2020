package lookie.project_b.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lookie.project_b.dto.TodoDto;






public class TodoDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=Asia/Seoul&useSSL=false";
	private static String dbUser = "connectuser";
	private static String dbpasswd = "connect123!@#";


	//id로 원하는 칼럼 삭제
	public int delTodo(TodoDto todo) {
		int delCount=0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "DELETE FROM todo WHERE id = ?;";
		
		try (Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setLong(1, todo.getId());
			
			delCount=ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return delCount;

	}
	//칼럼 삽입
	public int addTodo(TodoDto todo) {
		int insertCount=0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "insert into todo(title, name, sequence) values(?, ?, ?);";
		
		try (Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1,todo.getTitle());
			ps.setString(2, todo.getName());
			ps.setInt(3, todo.getSequence());
			
			insertCount=ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return insertCount;
	}
	
	//아이디 검색(아이디는 중복 x)으로 타입 수정
	public int updateTodo(int id,String type) {
		int updateCount=0;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql="update todo set type = ? where id = ?;";
		
		try (Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, type);
			ps.setLong(2, id);
			
			updateCount=ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return updateCount;
	}
	//타입 조건 조회하기
	public List<TodoDto> getTodos(TodoDto todo) {
		List<TodoDto> list = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "select id, title, name, sequence, type, regdate from todo where type = ? order by regdate desc;";
		
		try (Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, todo.getType());
			try(ResultSet re = ps.executeQuery()) {
				while(re.next()) {
					long id = re.getLong(1);
					String name = re.getString(3);
					String regDate = re.getString(6);
					int sequence = re.getInt(4);
					String title = re.getString(2);
					String type = re.getString(5);
					
					TodoDto todoDto = new TodoDto(id,name,regDate,sequence,title,type);
					list.add(todoDto);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;	
		
	}
	//모든 테이블 조회하기
	public List<TodoDto> getTodoDtos(){
		List<TodoDto> list = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "select * from todo order by regdate desc";
		
		try (Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)){
		try(ResultSet re = ps.executeQuery()){
			while(re.next()) {
				long id = re.getLong(1);
				String name = re.getString(3);
				String regDate = re.getString(6);
				int sequence = re.getInt(4);
				String title = re.getString(2);
				String type = re.getString(5);
				
				TodoDto todoDto = new TodoDto(id,name,regDate,sequence,title,type);
				list.add(todoDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
				
	}
}
