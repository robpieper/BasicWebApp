<%@include file="/WEB-INF/jsp/include.jsp" %>
<%--
	Cache killer.  For OUR style sheets and scripts, we don't want a user's browser to hang on to them
	longer than an hour.  Otherwise, we can't fix things without having the user have to clear their cache.
	For third party style sheets and javascripts (like jquery, etc.) we can allow them to be cached since we
	never modify them.
--%>
<jsp:useBean id="now" class="java.util.Date"/>
<%--
	Begin the layout definition.
--%>
<stripes:layout-definition>
	<%@ page language="java" contentType="text/html;charset=UTF-8" %>
	<f:message var="appVersion" bundle="${applicationResources}" key="app.version"/>
	<!DOCTYPE HTML>
	<html>
	<head>
		<stripes:layout-component name="html_head_meta"/>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="chrome=1"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<stripes:layout-component name="html_head_title">
			<title>Midwest Tape Administration</title>
		</stripes:layout-component>
		<link rel="shortcut icon" href="/img/bookmark.gif" type="image/gif">
			<%--
					Style Sheets
			--%>
		<link rel='stylesheet' type='text/css' href='/css/main.css?${appVersion}'/>
		<%@include file="/WEB-INF/jsp/includes/jqueryCss.jsp" %>
		<stripes:layout-component name="html_head_css"/>
			<%--
					Scripts
			--%>
		<%@include file="/WEB-INF/jsp/includes/jquery.jsp" %>
		<script type="text/javascript" src="/js/basic.js"></script>
		<stripes:layout-component name="html_head_js"/>
	</head>
	<body>
	<stripes:layout-component name="content"/>
	</body>
	</html>
</stripes:layout-definition>