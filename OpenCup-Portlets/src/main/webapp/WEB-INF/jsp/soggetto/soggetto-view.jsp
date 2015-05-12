<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<style>
<!--
div.stripe{background: #fff;border-top:.5em solid #f0f0f0;}
-->
</style>

<portlet:defineObjects />

	<c:if test="${ config.mostraPulsanti }">
		<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center; background: #f0f0f0;">
			<div class="offset3 span2">
				<div class="btn-carica-distribuzione volume-color volume-color-pie sel-type-btn sel-type-btn-pie" data-distribuzione="VOLUME">
					<aui:a href="#" onClick="return false" cssClass="block">
						PROGETTI
					</aui:a>
				</div>
				<c:if test='${pattern eq "VOLUME"}'>
					<div class="arrow-down-volume arrow-down-volume-pie"></div>
				</c:if>
			</div>
			<div class="span2">	
				<div class="btn-carica-distribuzione costo-color costo-color-pie sel-type-btn sel-type-btn-pie" data-distribuzione="COSTO">
					<aui:a href="#" onClick="return false" cssClass="block">
						COSTO
					</aui:a>
				</div>
				<c:if test='${pattern eq "COSTO"}'>
					<div class="arrow-down-costo arrow-down-costo-pie"></div>
				</c:if>
			</div>
			<div class="span2">	
				<div class="btn-carica-distribuzione importo-color importo-color-pie sel-type-btn sel-type-btn-pie" data-distribuzione="IMPORTO">
					<aui:a href="#" onClick="return false" cssClass="block">
						IMPORTO
					</aui:a>
				</div>
				<c:if test='${pattern eq "IMPORTO"}'>
					<div class="arrow-down-importo arrow-down-importo-pie"></div>
				</c:if>
			</div>
			<div class="clear"></div>
		
		</div>	
	</c:if>
	
<div class="stripe">	
	<div id="container-soggetto-chart" style="padding-top: 30px; padding-bottom: 30px">
	</div>
</div>

<script type="text/javascript">
	var JsonClass4Soggetto = ${aggregati4Soggetto};
	var calculatedJsonClass4Soggetto = eval( JsonClass );
	
	console.log(JsonClass4Soggetto);
	
</script>
	

