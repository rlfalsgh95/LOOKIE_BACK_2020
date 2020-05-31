package kr.or.connect.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.or.connect.todo.dto.TodoDto;

public class TodoDao {
    private static String dburl = "jdbc:mysql://localhost:3306/todo?serverTimezone=Asia/Seoul&useSSL=false";
    private static String dbUser = "root";
    private static String dbpasswd = "1234";

    public int addTodo(TodoDto todo) {
        int addCount = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String sql = "INSERT INTO todo (title, name, sequence) VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getName());
            ps.setInt(3, todo.getSequence());

            addCount = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return addCount;
    }

    public List<TodoDto> getTodos() {
        List<TodoDto> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "SELECT id, title, name, sequence, type, regdate FROM todo ORDER BY regdate ASC";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String regdate = rs.getString("regdate");
                    int sequence = rs.getInt("sequence");
                    String title = rs.getString("title");
                    String type = rs.getString("type");
                    TodoDto todo = new TodoDto(id, name, regdate, sequence, title, type);
                    list.add(todo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int updateTodo(TodoDto todo) {
        int updateCount = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = null;
        if (todo.getType().equals("TODO")) {
            sql = "UPDATE todo SET type = 'DOING' WHERE id = ?";
        } else { //"DOING"
            sql = "UPDATE todo SET type = 'DONE' WHERE id = ?";
        }

        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, todo.getId());
            updateCount = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return updateCount;
    }

}
