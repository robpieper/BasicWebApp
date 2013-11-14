<%@include file="/WEB-INF/jsp/include.jsp" %>
<%--@elvariable id="actionBean" type="co.ds.stripes.SubscriberAction"--%>
<stripes:layout-render name="/WEB-INF/jsp/layout/standard.jsp">
	<stripes:layout-component name="html_head_css">
		<style type="text/css">
			#edit-button {
				display: none;
			}
		</style>
	</stripes:layout-component>
	<stripes:layout-component name="html_head_js">
		<script type="text/javascript">
			$(function(){
				$("TR.subscriber-row").click(function(){
					$("#subscriber-id").val($(this).attr("data-id"));
					$("#edit-button").click();
				});
			});
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<stripes:form beanclass="co.ds.stripes.SubscriberAction">
			<div class="banner">
				<div class="banner-title"><h1>Subscribers</h1></div>
			</div>
			<div class="clear"></div>
			<div class="button-bar">
				<div class="box-right">
					<stripes:submit name="edit" value="Edit" id="edit-button"/>
					<stripes:hidden name="subscriber.id" value="" id="subscriber-id"/>
					<stripes:submit name="form" value="Add" id="add-button"/>
					<stripes:submit name="cancelList" value="Cancel" id="cancel-button"/>
				</div>
				<div class="clear"></div>
			</div>
			<table class="grid-list" cellspacing="0">
				<thead>
				<tr>
					<th><div>Name</div></th>
					<th><div>Email</div></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${actionBean.subscribers}" var="subscriber" varStatus="status">
					<c:set var="stateClass" value=""/>
					<tr data-id="${subscriber.id}" class="subscriber-row">
						<td class="${stateClass}">${subscriber.name}</td>
						<td class="center ${stateClass}">${subscriber.email}</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="2"><div>&nbsp;</div></td>
				</tr>
				</tfoot>
			</table>
		</stripes:form>
	</stripes:layout-component>
</stripes:layout-render>