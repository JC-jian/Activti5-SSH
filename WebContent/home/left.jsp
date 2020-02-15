<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>导航菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/script/menu.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/menu.css" />
</head>
<body style="margin: 0">
<div id="Menu">
    <ul id="MenuUl">
        <li class="level1">
            <div onClick="menuClick(this);" class="level1Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/FUNC20057.gif" class="Icon" /> 审批流转</div>
            <ul style="display: hidden;" class="MenuLevel2">
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/processDefintionAction_list.action">审批流程管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/templateAction_list.action">表单模板管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
			href="${pageContext.request.contextPath}/flowAction_templateList.action">起草申请</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/flowAction_myTaskList.action"> 待我审批</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
			href="${pageContext.request.contextPath}/flowAction_myApplicationList.action">我的申请查询</a></div>
                </li>
                <!--
		<li class="level2">
			<div class="level2Style"><img
                  src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a  target="right" href="Flow_FormQuery/myApprovedList.html">经我审批</a> </div>
		</li>
		-->
            </ul>
        </li>
        
    </ul>
</div>
</body>
</html>
