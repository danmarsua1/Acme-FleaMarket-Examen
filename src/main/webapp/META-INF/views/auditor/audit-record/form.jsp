<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
<jstl:if test="${command !='create' }">
	<acme:form-moment code="auditor.audit-record.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:form-textbox code="auditor.audit-record.form.label.title" path="title"/>
	<acme:form-textbox code="auditor.audit-record.form.label.body" path="body"/>
	<acme:form-textbox code="auditor.audit-record.form.label.status" path="status" readonly="true"/>
	<acme:form-select code="auditor.audit-record.form.label.finalMode" path="finalMode" >
		<acme:form-option code="auditor.audit-record.form.label.finalMode.final" value="true" />
		<acme:form-option code="auditor.audit-record.form.label.finalMode.draft" value="false" />
	</acme:form-select>
	
	<jstl:if test="${finalMode!='true'}">
	<acme:form-submit test="${idauditor==idprincipal}" code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update?item=${item}" />
	</jstl:if>
</jstl:if>
<jstl:if test="${command=='create' }">
	<acme:form-textbox code="auditor.audit-record.form.label.title" path="title"/>
	<acme:form-textbox code="auditor.audit-record.form.label.body" path="body"/>	
	<acme:form-submit  test="${command == 'create' }"  code="auditor.audit-record.form.button.create" action="/auditor/audit-record/create?item=${item}"/>
</jstl:if>	
	 
	  
	<acme:form-return code="auditor.audit-record.form.button.return"/>
</acme:form> 
