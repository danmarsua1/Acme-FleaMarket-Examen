<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<acme:form-textarea code="supplier.coupon.form.label.description" path="description" />
	<acme:form-money code="supplier.coupon.form.label.minMoney" path="minMoney"/>
	<acme:form-money code="supplier.coupon.form.label.maxMoney" path="maxMoney"/>
		
	<acme:form-submit code="supplier.item.form.button.create" action="/supplier/coupon/create?item=${item}"/>

	<input id="item" name="item" value="${item}" type="hidden" />

	<acme:form-return code="supplier.item.form.button.return"/>
</acme:form>
