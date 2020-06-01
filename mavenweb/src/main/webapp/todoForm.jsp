<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:8080/mavenweb/add" accept-charset= "UTF-8" method="post">
        어떤일인가요?<br>
       	<input type="text" name="title" style="ime-mode:auto"><br>
        누가 할 일인가요?<br>
        <input type="text" name="name" style="ime-mode:auto"><br>
 	우선순위를 선택하세요<br>
            <input type="checkbox" name="sequence" value="1">1순위
            <input type="checkbox" name="sequence" value="2">2순위
            <input type="checkbox" name="sequence" value="3">3순위
            <br>
        <input type="submit" value="제출">
    </form>


</body>
</html>

