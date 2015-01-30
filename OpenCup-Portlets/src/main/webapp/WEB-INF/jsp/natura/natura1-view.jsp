<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<portlet:defineObjects />

<div id="tooltip" class="hidden">
	<p><span id="label"></span></p>
	<p>&nbsp;</p>
	<p><strong><span id="labelvalue"></span>:&nbsp;</strong><span id="value"></span><span class="hidden" id="umvalue">&euro;</span></p>
</div>

<div id="pieChart" style="text-align: center"></div>

<aui:button-row cssClass="btn-group btn-group-justified">
	<aui:button cssClass="natura-sel-btn btn btn-default" data-natura="VOLUME" value="VOLUME" />
	<aui:button cssClass="natura-sel-btn btn btn-default" data-natura="COSTO" value="COSTO" />
	<aui:button cssClass="natura-sel-btn btn btn-default" data-natura="IMPORTO" value="IMPORTO" />
</aui:button-row>