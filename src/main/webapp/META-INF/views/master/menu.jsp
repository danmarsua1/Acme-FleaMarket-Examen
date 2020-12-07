<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-donaire" action="https://www.idealista.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-martinez" action="https://www.twitter.com/"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-donaire-bulletin" action="/anonymous/donaire-bulletin/list" />
			<acme:menu-suboption code="master.menu.anonymous.create-donaire-bulletin" action="/anonymous/donaire-bulletin/create" />	
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-martinez-bulletin" action="/anonymous/martinez-bulletin/list" />
			<acme:menu-suboption code="master.menu.anonymous.create-martinez-bulletin" action="/anonymous/martinez-bulletin/create" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-advertisement" action="/anonymous/advertisement/list"/>
      <acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-new" action="/anonymous/new/list" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-tool-sheet" action="/anonymous/tool-sheet/list" />
      <acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.list-material-sheet" action="/anonymous/material-sheet/list" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.list-suggestion" action="/authenticated/suggestion/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-figment" action="/authenticated/figment/list"/>
      		<acme:menu-suboption code="master.menu.authenticated.list-advertisement" action="/authenticated/advertisement/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-new" action="/authenticated/new/list"/>
      		<acme:menu-suboption code="master.menu.authenticated.list-material-sheet" action="/authenticated/material-sheet/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-tool-sheet" action="/authenticated/tool-sheet/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-items" action="/authenticated/item/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.auditor.list-requests" action="/administrator/auditor/list"/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.customisation" action="/administrator/customisation/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-new" action="/administrator/new/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-new" action="/administrator/new/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-material-sheet" action="/administrator/material-sheet/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-material-sheet" action="/administrator/material-sheet/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-tool-sheet" action="/administrator/tool-sheet/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-tool-sheet" action="/administrator/tool-sheet/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-suggestion" action="/administrator/suggestion/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-suggestion" action="/administrator/suggestion/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-figment" action="/administrator/figment/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-figment" action="/administrator/figment/create"/>
			<acme:menu-suboption code="master.menu.administrator.list-advertisement" action="/administrator/advertisement/list"/>
			<acme:menu-suboption code="master.menu.administrator.create-advertisement" action="/administrator/advertisement/create"/>
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.chart" action="/administrator/chart/show" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/dashboard/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.supplier" access="hasRole('Supplier')">
			<acme:menu-suboption code="master.menu.supplier.list-item" action="/supplier/item/list-mine"/>
			<acme:menu-suboption code="master.menu.supplier.create-item" action="/supplier/item/create"/>
			<acme:menu-suboption code="master.menu.supplier.list-mine-request" action="/supplier/request-entity/list-mine"/>
			<acme:menu-suboption code="master.menu.supplier.list-request" action="/supplier/request-entity/list"/>
		</acme:menu-option>

    <acme:menu-option code="master.menu.buyer" access="hasRole('Buyer')">
			<acme:menu-suboption code="master.menu.buyer.list-request" action="/buyer/request-entity/list-mine"/>
    </acme:menu-option>
    
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.list-item" action="/auditor/item/list"/>
			<acme:menu-suboption code="master.menu.auditor.list-my-item" action="/auditor/item/list-mine"/>
			<acme:menu-suboption code="master.menu.auditor.list-not-mine-audit-record" action="/auditor/item/list-not-mine"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.list-banner" action="/sponsor/banner/list-mine"/>
			<acme:menu-suboption code="master.menu.sponsor.create-banner" action="/sponsor/banner/create"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-supplier" action="/authenticated/supplier/create" access="!hasRole('Supplier')"/>
			<acme:menu-suboption code="master.menu.user-account.supplier" action="/authenticated/supplier/update" access="hasRole('Supplier')"/>
			<acme:menu-suboption code="master.menu.user-account.become-buyer" action="/authenticated/buyer/create" access="!hasRole('Buyer')"/>
			<acme:menu-suboption code="master.menu.user-account.buyer" action="/authenticated/buyer/update" access="hasRole('Buyer')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

