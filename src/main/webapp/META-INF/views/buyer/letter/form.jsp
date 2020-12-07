<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
		<acme:form-textarea code="buyer.letter.form.label.description" path="description"/>
		<acme:form-textbox code="buyer.letter.form.label.link" path="link"/>
		<acme:form-textbox code="buyer.letter.form.label.password" path="password"/>

	<acme:form-submit test="${command == 'create' }"
		code="buyer.letter.form.button.create" 
		action="/buyer/letter/create?req=${req}"/>
				
	<input id="req" name="req" value="${req}" type="hidden" />
	
	<acme:form-return code="buyer.letter.form.button.return"/>
</acme:form>
