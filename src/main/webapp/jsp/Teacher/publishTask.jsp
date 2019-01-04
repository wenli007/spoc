<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<p> ${teacher.teacherName }</p>
<p>${readResult }</p>
    <form action="${pageContext.request.contextPath}/teacher/publishTask" enctype="multipart/form-data" method="post" >
       任务名字 <input id="taskTitle" name="taskTitle">
       任务细节<input id="taskDetail" name ="taskDetail">
       
        任务signIn类型<input id="taskType" name ="taskType">
        发布者 <input id="employeeNum" name="employeeNum" value="${teacher.employeeNum }">
        虚拟版号<input id="virtual_class_num" name ="virtual_class_num">
         第几章<input id="chapter" name="chapter">
         文件<input name="file" type="file" />
        状态 <input id="status" name ="status">
        
        <input type="submit" value="提交">
    </form>
    


</body>
</html>