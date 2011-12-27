<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>

	<%@ page import="com.talkweb.ei.login.log.*"%>
	<%@ page import="com.talkweb.ei.util.log.*"%>
	<%@ page import="com.talkweb.ei.common.*"%>

	<%
	ClsGlobalVar.init();
	
		LogerOperationImpl.test();
		
		String path = request.getContextPath();
		System.out.println("path:" + path);

		path = application.getRealPath("");
		System.out.println("application path" + path);

		path = this.getClass().getResource("/").getPath();
		System.out.println("path:" + path);
		FileLog.writeLog(LogConstant.LogFile_Info,
				LogConstant.LOGFILE_LOADCONFIGFILE, "mytest log");
	%>
	<body>
		hello world!
	</body>
</html>
