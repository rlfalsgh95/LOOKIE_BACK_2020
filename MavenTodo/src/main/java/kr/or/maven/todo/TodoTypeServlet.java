package kr.or.maven.todo;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class TodoTypeServlet
 */
@WebServlet("/TodoTypeServlet")
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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");

			String id=request.getParameter("id");
			String type=request.getParameter("type");
			System.out.println(id+type);
			
			TodoDao dao = new TodoDao();
			int insertCount = dao.updateDto(id, type);

			System.out.println(insertCount);
			response.sendRedirect("/MavenTodo/MainServlet");
//			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/main.jsp"); //forwarding 할 경로url 
//			requestDispatcher.forward(request, response);

			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
