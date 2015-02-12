<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<portlet:defineObjects />

<div id="tooltip-natura-portlet1" class="tooltip-natura-portlet1 hidden">
	<p><span id="label-tooltip-natura-portlet1"></span></p>
	<p>&nbsp;</p>
	<p><strong><span id="labelvalue-tooltip-natura-portlet1"></span>:&nbsp;</strong><span id="value-tooltip-natura-portlet1"></span><span class="hidden" id="umvalue-tooltip-natura-portlet1">&euro;</span></p>
</div>

<a name="natura-portlet1"></a>

<div id="pieChart" style="text-align: center">
</div>

<div class="alert alert-info pieChartEmpty" id="pieChartEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>

<div style="text-align: center" class="pieChartToolBar" id="pieChartToolBar">
	<aui:button-row cssClass="btn-group btn-group-justified">
		<aui:button cssClass="natura-sel-btn btn btn-default" data-natura="VOLUME" value="VOLUME" />
		<aui:button cssClass="natura-sel-btn btn btn-default" data-natura="COSTO" value="COSTO" />
		<aui:button cssClass="natura-sel-btn btn btn-default" data-natura="IMPORTO" value="IMPORTO" />
	</aui:button-row>
</div>

<form class="formEmptyNatura1" method="POST" action="#">
</form>

<script type="text/javascript">

var namespace = "<portlet:namespace/>";
namespace = namespace.substring(1, namespace.length - 1);

</script>

<%-- 
naturaportlet1_WAR_OpenCupPortletsportlet
<br>
<portlet:namespace />
<script type="text/javascript">
	
	var obj = null;
	var namespace = "<portlet:namespace />";

	AUI().use(
			'liferay-portlet-url', 
			'aui-base', 
			'aui-io-deprecated',
			'datatype-number', 
			function(A) {
				obj = new NaturaPortlet1("naturaportlet1_WAR_OpenCupPortletsportlet", A);
				obj.loadPie("VOLUME", A.one('.natura-sel-btn'));
				obj.drawPie();
				//d3.select("#pieChart").on("mouseover", function(d) {
				//	var tooltip = d3.select("#tooltip-natura-portlet1");
				//	tooltip.style("left", d3.event.pageX + "px");
				//	tooltip.style("top", d3.event.pageY + "px");
				//});
			}
	);
</script>
--%>