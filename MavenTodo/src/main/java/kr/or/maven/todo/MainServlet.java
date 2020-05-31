package kr.or.maven.todo;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		TodoDao dao = new TodoDao();
		
		List<TodoDto> list = dao.getTodos();
		List<TodoDto> todo=new ArrayList<>();
		List<TodoDto> doing=new ArrayList<>();
		List<TodoDto> done=new ArrayList<>();

		for(TodoDto dto : list) {
			System.out.println(dto);
			if(dto.getType().equals("TODO")) {
				todo.add(dto);
			}
			else if(dto.getType().equals("DOING")) {
				doing.add(dto);
			}
			else {
				done.add(dto);
			}
				
		}
		//forwarding 하는부분.
		
		request.setAttribute("Todolist", todo); // keyname, value 
		request.setAttribute("Doinglist", doing);
		request.setAttribute("Donelist", done);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/main.jsp"); //forwarding 할 경로url 
		requestDispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
