<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

<jstl:if test="${command !='create' }">
	<acme:form-textbox code="supplier.item.form.label.newItem" path="newItem" readonly="true"/>
	<acme:form-moment code="supplier.item.form.label.creationMoment" path="creationMoment" readonly="true" />
	<acme:form-textbox code="supplier.item.form.label.ticker" path="ticker" readonly="true" />
	<acme:form-textbox code="supplier.item.form.label.title" path="title"/>
	<acme:form-textbox code="supplier.item.form.label.description" path="description"/>
	<acme:form-url code="supplier.item.form.label.photo" path="photo"/>
	<acme:form-textbox code="supplier.item.form.label.itemCategory" path="itemCategory"/>
	<acme:form-money code="supplier.item.form.label.price" path="price"/>
	<acme:form-url code="supplier.item.form.label.link" path="link"/>
	<acme:form-textbox code="supplier.item.form.label.status" path="status" readonly="true"/>
	<acme:form-select code="supplier.item.form.label.finalMode" path="finalMode" >
		<acme:form-option code="supplier.item.form.label.finalMode.final" value="true" />
		<acme:form-option code="supplier.item.form.label.finalMode.draft" value="false" />
	</acme:form-select>
	
	<jstl:if test="${hasCoupon==false}">
		<acme:form-submit method="get" code="supplier.item.form.button.createCoupon" action="/supplier/coupon/create?item=${id}" />
	</jstl:if>
	<jstl:if test="${hasCoupon==true}">
		
		<fieldset>
			<legend><acme:message code="supplier.coupon.form.legend.coupon"/></legend>
		</fieldset>
	
		<acme:form-textbox code="supplier.coupon.form.label.code" path="coupon.code" readonly="true" />
		<acme:form-textarea code="supplier.coupon.form.label.description" path="coupon.description" readonly="true" />
		<acme:form-money code="supplier.coupon.form.label.minMoney" path="coupon.minMoney" readonly="true" />
		<acme:form-money code="supplier.coupon.form.label.maxMoney" path="coupon.maxMoney" readonly="true" />
		<acme:form-submit code="supplier.coupon.form.button.deleteCoupon" action="/supplier/coupon/delete?item=${id}" />
	</jstl:if>

	<jstl:if test="${!finalMode}">
	<acme:form-submit test="${idsupplier==idprincipal}" code="supplier.item.form.button.update" action="/supplier/item/update?id=${id}" />	
	</jstl:if>
</jstl:if>
<jstl:if test="${!hasRequests}">
	<acme:form-submit test="${command == 'show' }" code="supplier.item.form.button.delete" action="/supplier/item/delete"/>
	<acme:form-submit test="${command == 'delete' }" code="supplier.item.form.button.delete" action="/supplier/item/delete"/>
</jstl:if>
<jstl:if test="${command=='create' }">
	<acme:form-textbox code="supplier.item.form.label.title" path="title"/>
	<acme:form-textbox code="supplier.item.form.label.description" path="description"/>
	<acme:form-url code="supplier.item.form.label.photo" path="photo"/>
	<acme:form-textbox code="supplier.item.form.label.itemCategory" path="itemCategory"/>
	<acme:form-money code="supplier.item.form.label.price" path="price"/>
	<acme:form-url code="supplier.item.form.label.link" path="link"/>
		
	<acme:form-submit code="supplier.item.form.button.create" action="/supplier/item/create"/>
</jstl:if>


	<acme:form-return code="supplier.item.form.button.return"/>
</acme:form>
