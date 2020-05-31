<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>할일등록</title>
</head>
<body>
<form action="/MavenTodo/TodoAddServlet" method="post" name="AddTodo">
  <div class="form-group">
        <label for="form-title">어떤일 인가요?</label><br>
        <input type="text" class="form-control" name="title" id="form-title" placeholder="swift 공부하기(24자까지)" required>
      </div>
  <div class="form-group">
        <label for="form-author">누가 할일 인가요?</label><br>
        <input type="text" class="form-control" name="name" id="form-author" placeholder="홍길동" required>
  </div>

  <div class="form-group">
        <label for="form-description">우선순위를 선택하세요.</label><br>
        <input type="radio" name="priority" value="1" required/> 1순위
        <input type="radio" name="priority" value="2" /> 2순위
        <input type="radio" name="priority" value="3" /> 3순위

  </div>
      <button type="button" onclick="location.href='/MavenTodo/MainServlet'">이전으로</button>
      <input type="submit" name="submit" value="제출">
      <input type="reset" value="내용지우기">
</form>

<script type="text/javascript" src="./script/script.js"></script>
</body>
</html>