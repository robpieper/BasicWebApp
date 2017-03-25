<%@include file="/WEB-INF/jsp/include.jsp" %>
<%--@elvariable id="actionBean" type="co.ds.stripes.SubscriberAction"--%>
<stripes:layout-render name="/WEB-INF/jsp/layout/standard.jsp">
	<stripes:layout-component name="html_head_css">
		<style type="text/css">

			#name, #email {
				width: 360px;
			}

			DIV.form-wrapper {
				width: 720px;
				margin: 0 auto;
			}

			DIV.row {
				width: 720px;
			}

			DIV.label {
				padding: 10px 5px 0 0;
				width: 200px;
			}

			DIV.field {
				padding: 5px;
				width: 500px;
			}

		</style>
	</stripes:layout-component>
	<stripes:layout-component name="html_head_js">
		<script type="text/javascript">
			$(function () {
				$("#name").focus();
			});
		</script>
	</stripes:layout-component>
	<stripes:layout-component name="content">
		<stripes:form beanclass="co.ds.stripes.TopicAction">
			<stripes:hidden name="topic.id"/>
			<div class="banner">
				<div class="banner-title"><h1>Subscriber</h1></div>
			</div>
			<div class="clear"></div>
			<div class="button-bar">
				<div class="box-right">
					<stripes:submit name="save" value="Save" id="save-button"/>
					<stripes:submit name="cancelForm" value="Cancel" id="cancel-button"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-wrapper">
				<div class="row form-top-padding">
					<div class="label">Name:</div>
					<div class="field">
						<stripes:text name="Topic.name" id="name"/>
						<stripes:errors field="topic.name">
							<div class="form-error" id="recipient-error"><stripes:individual-error/></div>
						</stripes:errors>
					</div>
					<div class="clear"></div>
				</div>
				
					<div class="clear"></div>
				</div>
				<div class="row">
					<div class="label">Topics:</div>
					<div class="field">
						<c:forEach items="${actionBean.topics}" var="topic">
							<label for="topic_${topic.id}"><stripes:checkbox name="topic.topicIds" id="topic_${topic.id}" value="${topic.id}"/>${topic.name}</label><br/>
						</c:forEach>
					</div>
					<div class="clear"></div>
				</div>
			
		</stripes:form>
	</stripes:layout-component>
</stripes:layout-render>