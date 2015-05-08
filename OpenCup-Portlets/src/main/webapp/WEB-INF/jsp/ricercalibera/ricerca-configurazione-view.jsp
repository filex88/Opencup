<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<portlet:defineObjects />

<liferay-ui:error key="config-mancante" message="config-mancante-1" />
<liferay-ui:success key="indicizzazione-avviata" message="indicizzazione-avviata-1" />

<portlet:actionURL var="actionURL">
	<portlet:param name="action" value="avviaIndicizzazione"/>
</portlet:actionURL>

<aui:form action="${actionURL}" method="POST">
	
	
	<div class="control-group" >
		<label class="control-label" for="stato">Schedulazione cron</label>
		<div class="controls">
			<aui:input type="text" value="${cronExp}" cssClass="input-xlarge" label="" name="cronExp" ></aui:input>
		</div>
	</div>

	<div class="control-group">
		<div class="pull-right">
			<aui:button type="submit" cssClass="btn"
				value="Schedula indicizzazione"></aui:button>
		</div>
	</div>
</aui:form>