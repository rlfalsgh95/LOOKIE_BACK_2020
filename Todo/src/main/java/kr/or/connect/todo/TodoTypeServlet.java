package kr.or.connect.todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.todo.dao.TodoDao;
import kr.or.connect.todo.dto.TodoDto;

@WebServlet("/type")
public class TodoTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TodoTypeServlet() {
        super();
    }

	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/x-www-form-urlencoded");

		String id = (String)request.getParameter("id");
		String type = (String)request.getParameter("type");
		
		TodoDao dao = new TodoDao();
		TodoDto todo = new TodoDto();
		todo.setId(Long.valueOf(id));
		todo.setType(type);

		dao.updateTodo(todo);

		List<TodoDto> list = dao.getTodos();
		int len = list.size();
		TodoDto target = null;
		for (int i = 0; i < len; i++) {
			target = list.get(i);
			if(target.getId().equals(Long.valueOf(id)))	break;				
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(target);		

		PrintWriter out = response.getWriter();
		out.println(json);
		out.close();
		
        String path = request.getContextPath();
        response.sendRedirect(path + "/main");
	}
}
