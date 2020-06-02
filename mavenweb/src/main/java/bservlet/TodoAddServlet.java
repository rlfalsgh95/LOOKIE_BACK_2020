package bservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lookie.project_b.dao.TodoDao;
import lookie.project_b.dto.TodoDto;

/**
 * Servlet implementation class TodoAddServlet
 */
@WebServlet("/add")
public class TodoAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TodoDao dao= new TodoDao();
		String title=new String(request.getParameter("title").getBytes("ISO-8859-1"), "UTF-8");
		String name=new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
		int sequence= Integer.parseInt(request.getParameter("sequence"));
		
		TodoDto todo = new TodoDto(title,name,sequence);
		dao.addTodo(todo);
		response.sendRedirect("http://localhost:8080/mavenweb/main");
	}

}
