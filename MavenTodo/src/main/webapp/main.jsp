<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"
 %>
 <%@ page import="java.util.*"
 %>
 <%@ page import="kr.or.maven.todo.*"
 %>       
<!DOCTYPE html>
<html lang="kr">
  <head>
    <meta charset="utf-8">
      <link rel="stylesheet" type="text/css" href="./css/style.css">
      <title>To Do</title>
  </head>
  <body>
    <h2 class="title">나의 해야할 일들</h3>
      <input id="newregist" type="button" onclick="location.href='/MavenTodo/TodoFormServlet'" value="새로운 TODO 등록">
    <section>
      <div class="nav-todo" id="todo">
          <li class="nav-state">TODO</li>
 <%

	List<TodoDto> list = (List<TodoDto>)request.getAttribute("Todolist");
	for(TodoDto dto : list) {
		//System.out.println(dto);
		
		String type=dto.getType();
		Long id=dto.getId();
		String title=dto.getTitle();
		String regdate=dto.getRegDate();
		String name=dto.getName();
		int prior=dto.getSequence();
	
		System.out.println(title+type);
		
			
%>
		<li class="nav-li <%=type %>" id="n<%=id %>">
		<%=title %><br>
		등록날짜:<%=regdate %>,<%=name %>,우선순위<%=prior %>
			<input class="event" type="button" onclick="changeState(<%=id %>,document.getElementById('n<%=id %>').className);" value="->">
		
		</li>
<% 
	}
%>

      </div>


      <div class="nav-doing" id="doing">
          <li class="nav-state">DOING</li>
 <%

	list = (List<TodoDto>)request.getAttribute("Doinglist");
	for(TodoDto dto : list) {
		//System.out.println(dto);
		
		String type=dto.getType();
		Long id=dto.getId();
		String title=dto.getTitle();
		String regdate=dto.getRegDate();
		String name=dto.getName();
		int prior=dto.getSequence();
	
		System.out.println(title+type);
		
			
%>
	   <li class="nav-li <%=type %>" id="n<%=id %>">
		<%=title %><br>
		등록날짜:<%=regdate %>,<%=name %>,우선순위:<%=prior %>
			<input class="event" type="button" onclick="changeState(<%=id %>,document.getElementById('n<%=id %>').className);" value="->">	
		</li>
<% 
	}
%>

      </div>

      <div class="nav-done" id="done">
          <li class="nav-state">DONE</li>
 <%

	list = (List<TodoDto>)request.getAttribute("Donelist");
	for(TodoDto dto : list) {
		//System.out.println(dto);
		
		String type=dto.getType();
		Long id=dto.getId();
		String title=dto.getTitle();
		String regdate=dto.getRegDate();
		String name=dto.getName();
		int prior=dto.getSequence();
	
		System.out.println(title+type);
		
			
%>
      <li class="nav-li" id="n<%=id %>">
		<%=title %><br>
		등록날짜:<%=regdate %>,<%=name %>,우선순위<%=prior %>
				
	  </li>
<% 
	}
%>

      </div>


    </section>
	<script type="text/javascript" src="./script/script.js"></script>
  </body>
  
</html>