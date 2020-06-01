<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.connect.todo.dto.TodoDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODO 리스트</title>
<% String path = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%=path%>/main.css"/>
<link rel="shortcut icon" href="#"/>
</head>
<body>
	<p class="title">나의 해야할 일 들</p>
    <p class="new"><a href="<%=path%>/form">새로운 TODO 등록</a></p>
    <div class="flex-container">
        <div class="list" id="TODO" align="center">
            <p>TODO</p>
            <c:forEach var="item" items="${todo }">
            	<c:if test="${item.getType() == 'TODO'}">
	            	<p>
	                    <span class= "item-title">${item.getTitle() }</span> <br>
	                    <fmt:parseDate var="dateTempParse" value="${item.getRegdate()}" pattern="yyyy-MM-dd HH:mm:ss.S"/>
	                    	등록날짜: <fmt:formatDate value="${parsedDate}" pattern="yyyy. MM. dd"/>, 
	                    	${item.getName() }, 
	                    	우선순위 ${item.getSequence()}
	                    <span class='button' id='${item.getType() }#${item.getId()}'>-></span>
	                </p>
	            </c:if>
           	</c:forEach>
        </div>
        
        <div class="list" id="DOING" align="center">
            <p>DOING</p>
            <c:forEach var="item" items="${todo }">
            	<c:if test="${item.getType() == 'DOING'}">
	            	<p>
	                    <span class= "item-title">${item.getTitle() }</span> <br>
	                    <fmt:parseDate value="${item.getRegdate()}" var="parsedDate" pattern="yyyy-MM-dd HH:mm:ss.S"/>
	                    	등록날짜: <fmt:formatDate value="${parsedDate}" pattern="yyyy. MM. dd"/>, 
	                    	${item.getName() }, 
	                    	우선순위 ${item.getSequence()}
	                    <span class='button' id="${item.getType()}#${item.getId()}">-></span>
	                </p>
	            </c:if>
           	</c:forEach>
        </div> 
        
        <div class="list" id="DONE" align="center">
            <p>DONE</p>
            <c:forEach var="item" items="${todo }">
            	<c:if test="${item.getType() == 'DONE'}">
	            	<p>
	                    <span class= "item-title">${item.getTitle() }</span> <br>
	                    <fmt:parseDate value="${item.getRegdate()}" var="parsedDate" pattern="yyyy-MM-dd HH:mm:ss.S"/>
	                    	등록날짜: <fmt:formatDate value="${parsedDate}" pattern="yyyy. MM. dd"/>, 
	                    	${item.getName() }, 
	                    	우선순위 ${item.getSequence()}
	                </p>
	            </c:if>
           	</c:forEach>
        </div>
    </div>
	<script type="text/javascript" src="<%=path%>/main.js"></script>
</body>
</html>