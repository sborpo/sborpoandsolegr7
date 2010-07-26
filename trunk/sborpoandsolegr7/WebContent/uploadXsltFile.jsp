<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<br/>
<br/>
<br/>
<div class="centeredBox">
<form action="UploadXSLT" enctype="multipart/form-data" method="post">
Upload your XSLT file : <br/><input type="file" id ="xsltFile" name="xsltFileN" />
<br/>
<br/>
<input value="Send" type="submit"/>
</form>
</div>
</body>
</html>