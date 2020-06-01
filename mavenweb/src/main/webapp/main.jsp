<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>TODO</h3>
<c:forEach items="${list }" var="item">
	<c:if test="${item.getType() == 'TODO'}">
	${item }
	<input type="button" value = "->" onclick = "ajax(${item.id},'${item.type}')">
	<br>
	</c:if>
</c:forEach>
<span id="todo"></span>

<h3>DOING</h3>
<c:forEach items="${list }" var="item">
	<c:if test="${item.getType() == 'DOING'}">
	${item } 
	<input type="button" value = "->" onclick = "ajax(${item.id},'${item.type}')">
	<br>
	</c:if>
</c:forEach>
<span id="doing"></span>

<h3>DONE</h3>
<c:forEach items="${list }" var="item">
	<c:if test="${item.getType() == 'DONE'}">
	${item } 
	<input type="button" value = "->" onclick = "ajax(${item.id},'${item.type}')">
	<br>
	</c:if>
</c:forEach>
<span id="done"></span>
	
<input type="button" value = "새로운 todo 등록" onclick = "location.href= 'http://localhost:8080/mavenweb/form'">
</body>

<script type = "text/javascript">
function ajax(id,type) {
	 var oReq = new XMLHttpRequest();
	 var id_type="id="+id+"&type="+type; 
	 
	 oReq.onreadystatechange = function(){
	       location.reload();
	    }   
	 
	 oReq.open("POST", "http://localhost:8080/mavenweb/Type",true);
	 oReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	 oReq.send(id_type);
	      
}
</script>
</html>

