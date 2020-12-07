<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
    <acme:form-textbox code="administrator.dashboard.form.label.numberNews" path="numberNews" />
    <acme:form-textbox code="administrator.dashboard.form.label.numberMaterialSheets" path="numberMaterialSheets" />
    <acme:form-textbox code="administrator.dashboard.form.label.numberToolSheets" path="numberToolSheets" />
    <acme:form-textbox code="administrator.dashboard.form.label.numberAdvertisement" path="numberAdvertisement" />
    <acme:form-textbox code="administrator.dashboard.form.label.numberSuggestions" path="numberSuggestions" />
    <acme:form-textbox code="administrator.dashboard.form.label.numberFigments" path="numberFigments" />
    
    <acme:form-textbox code="administrator.dashboard.form.label.minDiscountAdvertisements" path="minDiscount" />
    <acme:form-textbox code="administrator.dashboard.form.label.maxDiscountAdvertisements" path="maxDiscount" />
    <acme:form-textbox code="administrator.dashboard.form.label.averageSmallDiscountAdvertisements" path="averageSmallDiscountAdvertisements" />
    <acme:form-textbox code="administrator.dashboard.form.label.averageAverageDiscountAdvertisements" path="averageAverageDiscountAdvertisements" />
    <acme:form-textbox code="administrator.dashboard.form.label.averageLargeDiscountAdvertisements" path="averageLargeDiscountAdvertisements" />
    <acme:form-textbox code="administrator.dashboard.form.label.stddevDiscountSmallAdvertisements" path="stddevSDiscountAdvertisements" />
    <acme:form-textbox code="administrator.dashboard.form.label.stddevDiscountAverageAdvertisements" path="stddevADiscountAdvertisements" />
    <acme:form-textbox code="administrator.dashboard.form.label.stddevDiscountLargeAdvertisements" path="stddevLDiscountAdvertisements" />
    
    <acme:form-textbox code="administrator.dashboard.form.label.averageItemsPerSupplier" path="averageItemsPerSupplier" /> 
    <acme:form-textbox code="administrator.dashboard.form.label.averageRequestsPerSupplier" path="averageRequestsPerSupplier" />
    <acme:form-textbox code="administrator.dashboard.form.label.averageRequestsPerBuyer" path="averageRequestsPerBuyer" />
    
    <acme:form-return code="administrator.dashboard.form.button.return" />
</acme:form>