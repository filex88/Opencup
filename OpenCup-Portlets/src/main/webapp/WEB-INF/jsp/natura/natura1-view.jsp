<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

	<div id="tooltip" class="hidden">
		<p><span id="label"></span></p>
		<p>&nbsp;</p>
		<p><strong><span id="labelvalue"></span>:&nbsp;</strong><span id="value"></span><span class="hidden" id="umvalue">&euro;</span></p>
	</div>

	<div id="pieChart" style="text-align: center"></div>

	<div class="btn-group btn-group-justified" role="group" aria-label="...">
	  <button type="button" class="btn btn-default" id="VOLUME">VOLUME</button>
	  <button type="button" class="btn btn-default" id="COSTO">COSTO</button>
	  <button type="button" class="btn btn-default" id="IMPORTO">IMPORTO</button>
	</div>