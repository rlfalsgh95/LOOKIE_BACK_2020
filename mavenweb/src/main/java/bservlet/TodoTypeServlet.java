package bservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lookie.project_b.dao.TodoDao;

/**
 * Servlet implementation class TodoTypeServlet
 */
@WebServlet("/Type")
public class TodoTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoTypeServlet() {
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
		request.setCharacterEncoding("utf-8");

		int id=Integer.parseInt(request.getParameter("id"));
		String type=request.getParameter("type");
		
		TodoDao dao = new TodoDao();
		if(type.equals("TODO")) {
		dao.updateTodo(id, "DOING");
		}else if(type.equals("DOING")) {
			dao.updateTodo(id, "DONE");
		}else {
			dao.updateTodo(id, "TODO");
		}
		
	}

}
