<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>我的申请查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <script type="text/javascript">
    $(function(){
        	$("#statusId").val('${status}');
    	});
    
    
    </script>
</head>
<body>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 我的申请查询
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="QueryArea">
	<div style="height: 30px">
		<form action="${pageContext.request.contextPath}/flowAction_myApplicationList.action" method="post">
		<table border=0 cellspacing=3 cellpadding=5>
			<tr>
				<td><select id="statusId" name="status" class="SelectStyle">
						<option value="">查看全部状态</option>
						<option value="审批中">审批中</option>
						<option value="未通过">未通过</option>
						<option value="已通过">已通过</option>
					</select>
				</td>
				<td><a href=""><input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/query.PNG"/></a></td>
			</tr>
		</table>
		</form>
	</div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
				<td width="250px">标题</td>
				<td width="115px">申请人</td>
				<td width="115px">申请日期</td>
				<td width="115px">当前状态</td>
				<td>相关操作</td>
			</tr>
		</thead>	
				
		<!--显示数据列表：被退回的我的表的单显示示例
		<tbody id="TableData" class="dataContainer" datakey="formList">
			-->
			<!-- 被退回的我的表的单显示示例 
			<tr class="TableDetail1 template">
				<td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">${form.title}</a></td>
				<td>${form.applicant.name}&nbsp;</td>
				<td>${form.applyTime}&nbsp;</td>
				<td>审批中&nbsp;</td>
				<td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">查看申请信息</a>
					<a href="${pageContext.request.contextPath}/Flow_Formflow/approvedHistory.html">查看流转记录</a>
					<a href="${pageContext.request.contextPath}/Flow_Formflow/editAndResubmitUI.html">修改后再次提交</a>
					<a href="#" onClick="return delConfirm()">删除</a>
				</td>
			</tr>
		</tbody>
		-->

		<!--显示数据列表：正在审批或审批完成的表单显示示例-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
        	<s:iterator value="applicationList">
			<!-- 正在审批或审批完成的表单显示示例 -->
			<tr align="CENTER" class="TableDetail1 template">
				<td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">${title}</a></td>
				<td>${applicant.name}&nbsp;</td>
				<td>${applyDate}&nbsp;</td>
				<td >
				<s:if test="status == '审批中'">
				<a href="javascript: window.open('${pageContext.request.contextPath}/flowAction_showPng.action?applicationId=${id}','newWindow','height=500, width=800, top=100, left=300,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')">${status}&nbsp;</a>	
				</s:if>
				<s:else>
					${status}&nbsp;
				</s:else>
				</td>
				<td><a href="#">查看申请信息</a>
					<a href="${pageContext.request.contextPath}/flowAction_approveInfoList.action?applicationId=${id}">查看流转记录</a>
				</td>
			</tr>		
			</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail"><div id="TableTail_inside"></div></div>
</div>
</body>
</html>

