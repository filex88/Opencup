<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<portlet:defineObjects />

<!-- attenzione: ho utilizzato requires-namespaced-parameters = false in liferay-portlet.xml -->

<liferay-ui:success key="config-salvata" message="config-salvata-1" />

<springform:form action="${saveURL}" method="POST" modelAttribute="config" cssClass="form-horizontal" >
	
	<fieldset>
	   	<legend>Configurazione</legend>
		
		<div class="control-group">
			<span class="control-label">Mostra pulsanti</span>
			<div class="controls">
				<springform:checkbox path="mostraPulsanti" />
			</div>
		</div>
	
		<div class="control-group">
			<span class="control-label">Selezionabile</span>
			<div class="controls">
				<springform:checkbox path="selezionabile" />
			</div>
		</div>
		
		<div class="control-group">
			<span class="control-label">Portlet Principale</span>
			<div class="controls">
				<springform:checkbox path="portletPrincipale" />
			</div>
		</div>
		
		<div class="control-group">
			<div class="controls">
				<button type="submit" class="btn btn-primary">Salva &nbsp;<i class="icon-save"></i></button>
			</div>
		</div>
	
	</fieldset>	
</springform:form>