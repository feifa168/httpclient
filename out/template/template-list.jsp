<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/commonTag.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath}" />
		<title><spring:message code="temp.templist"/></title>
		<%@include file="../common/head.html"%>
		<%@include file="../common/globalParam.jsp"%>
	</head>
<body class="skin-blue-light sidebar-mini fixed">
	<!-- Site wrapper -->
	<div class="wrapper">
		<%@ include file="../common/commonHeader.jsp"%>
		<%@ include file="../common/commonAside.jsp"%>
		<div class="content-wrapper">
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-widget">
							<div class="box-header">
								<h3 class="box-title"><spring:message code="temp.templist"/></h3>
								<shiro:hasPermission name="scan-template:create"> 
									<div class="box-tools pull-right">
										<a href="scan/template/config" class="btn btn-sm btn-primary"><spring:message code="action.new"/></a>
									</div>
								</shiro:hasPermission>
							</div>
							<div class="box-body">
								<table class="table table-bordered table-striped" id="templateList">
									<thead>
										<tr>
											<th><spring:message code="temp.tempid"/></th>
											<th><spring:message code="temp.name"/></th>
											<th><spring:message code="temp.description"/></th>
											<!-- <th>资产发现</th>
											<th>服务发现</th>
											<th>检查</th>
											<th>来源</th> -->
											<th><spring:message code="action.copy"/></th> 
											<th><spring:message code="action.edit"/></th>
											<th><spring:message code="action.remove"/></th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
		<%@ include file="../common/commonFooter.jsp"%>
	</div>
	<%@ include file="../common/lowVersion.jsp"%>
	<%@ include file="../common/commonJavaScript.jsp"%>
	<script type="text/javascript" src="resources/js/template/template-list.js"></script>
	<script type="text/javascript">
		function temp_copy_format(url){
			<shiro:hasPermission name="scan-template:copy">  
				return url;
			</shiro:hasPermission>
			return '';
		}
	
		function temp_edit_format(url, data){
			<shiro:hasPermission name="scan-template:edit">  
				return vsfront.edit(url, data);
			</shiro:hasPermission>
			return '';
		}
		
		function temp_delete_format(url, para, data){
			<shiro:hasPermission name="scan-template:delete">  
				return vsfront.remove(url, para, data);
			</shiro:hasPermission>
			return '';
		}
	</script>
</body>
</html>