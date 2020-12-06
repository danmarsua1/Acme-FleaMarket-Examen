<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<jstl:if test="${command != 'create'}">
		<acme:form-moment readonly="true" code="buyer.request.form.label.creation" path="creation"/>
		<acme:form-money readonly="true" code="buyer.request.form.label.totalPrice" path="totalPrice" />
	</jstl:if>
	<acme:form-textbox readonly="true" code="buyer.request.form.label.ticker" path="ticker"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-textbox code="buyer.request.form.label.item.title" path="item.title"/>
	</jstl:if>
	<acme:form-double code="buyer.request.form.label.quantity" path="quantity" placeholder=" "/>
	
	<acme:form-textarea code="buyer.request.form.label.notes" path="notes"/>
	
	<acme:form-submit test="${command == 'create' }"
		code="buyer.request.form.button.create" 
		action="/buyer/request-entity/create"/>
		
	<input id="item" name="item" value="${item}" type="hidden" />
	
	<acme:form-return code="buyer.request.form.button.return"/>
</acme:form>
