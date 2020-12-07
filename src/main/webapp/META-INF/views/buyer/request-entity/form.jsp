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
		<acme:form-textbox code="buyer.request.form.label.item.title" path="item.title" readonly="true" />
		<acme:form-double code="buyer.request.form.label.quantity" path="quantity" readonly="true"  placeholder=" "/>
		<acme:form-textarea code="buyer.request.form.label.notes" path="notes" readonly="true" />
	</jstl:if>
	
	<jstl:if test="${command == 'create' }">
		<acme:form-double code="buyer.request.form.label.quantity" path="quantity"  placeholder=" "/>
		<acme:form-textarea code="buyer.request.form.label.notes" path="notes" />
		<acme:form-submit 
		code="buyer.request.form.button.create" 
		action="/buyer/request-entity/create"/>
	</jstl:if>
	<jstl:if test="${hasLetter==false && hasCoupon==true}">	
	<acme:form-submit test="${command != 'create'}"
		 method="get" code="buyer.request.form.button.createLetter" action="/buyer/letter/create?req=${id}"/>
	</jstl:if>
	<jstl:if test="${hasLetter==true && hasCoupon==true}">	
	<fieldset>
			<legend><acme:message code="buyer.letter.form.legend.letter"/></legend>
		</fieldset>
		<acme:form-textarea code="buyer.letter.form.label.description" path="letter.description" readonly="true"/>
		<acme:form-url code="buyer.letter.form.label.link" path="letter.link" readonly="true"/>
		<acme:form-textbox code="buyer.letter.form.label.password" path="letter.password" readonly="true"/>
		<acme:form-textbox code="buyer.letter.form.label.status" path="letter.status" readonly="true"/>
	<jstl:if test="${isAccepted==false}">	
	<acme:form-submit code="buyer.request.form.button.deleteLetter" action="/buyer/letter/delete?req=${id}" />
	</jstl:if>
	</jstl:if>
	<input id="item" name="item" value="${item}" type="hidden" />
	
	<acme:form-return code="buyer.request.form.button.return"/>
</acme:form>
