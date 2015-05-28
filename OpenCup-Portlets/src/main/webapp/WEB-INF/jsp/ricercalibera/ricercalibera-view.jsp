<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<portlet:defineObjects />

<div class="portlet-body">
	<aui:form action="${ricercaLiberaURL }" method="POST">
		<div class="input-append">
			<aui:input type="text" cssClass="span3" label="" bean="ricerca"  name="cercaPerKeyword" inlineField="true"  ></aui:input>	
			<aui:button type="submit" cssClass="btn" value="<i class='icon-search'></i>" ></aui:button>	
		</div>
	</aui:form>
</div>
