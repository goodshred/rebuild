<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/_include/Head.jsp"%>
<title>部门管理</title>
<style type="text/css">
</style>
</head>
<body>
<div class="rb-wrapper rb-collapsible-sidebar">
	<jsp:include page="/_include/NavTop.jsp">
		<jsp:param value="部门管理" name="pageTitle"/>
	</jsp:include>
	<jsp:include page="/_include/NavLeftAdmin.jsp">
		<jsp:param value="users" name="activeNav"/>
	</jsp:include>
	<div class="rb-content">
		<div class="main-content container-fluid">
		</div>
	</div>
</div>

<%@ include file="/_include/Foot.jsp"%>
<script src="${baseUrl}/assets/js/rb-list.jsx" type="text/babel"></script>
<script src="${baseUrl}/assets/js/rb-forms.jsx" type="text/babel"></script>
<script src="${baseUrl}/assets/js/rb-advfilter.jsx" type="text/babel"></script>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
</body>
</html>
