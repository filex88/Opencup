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
	<aui:form action="${ ricercaLiberaURL }" method="POST" cssClass="form-ricerca-opencup">
		<div class="input-append">
			<aui:input type="text" cssClass="span3" label="" bean="ricerca"  name="cercaPerKeyword" inlineField="true"  ></aui:input>	
			<aui:button type="submit" cssClass="btn" value="<i class='icon-search'></i>" ></aui:button>	
			<aui:button type="submit" cssClass="btn btn-ricerca-avanzata" value="Avanzata" ></aui:button>	
		</div>
	</aui:form>
</div>
<%-- 
<aui:form action="#" method="POST" id="form-ricerca-opencup">
	<aui:input type="hidden" bean="ricerca"  name="cercaPerKeyword" cssClass="cercaPerKeyword"></aui:input>	
</aui:form>

<div class="input-append">
  <input class="span3 txt-ricerca-libera" id="txt-ricerca-libera" type="text">
  <button class="btn ricerca-libera-sub" id="ricerca-libera-sub" type="button"><i class="icon-search"></i></button>
  <button class="btn ricerca-avanzata-sub" id="ricerca-avanzata-sub" type="button">Avanzata</button>
</div>

--%>
<script type="text/javascript">
	var ricercaLiberaURL = "${ ricercaLiberaURL }";
	var ricercaAvanzataURL = "${ ricercaAvanzataURL }";
	

	AUI().use('get', function(A){
		A.Get.script('${jsFolder}/jquery-1.11.0.min.js', {
			onSuccess: function(){
		    	A.Get.script('${jsFolder}/bootstrap.min.js', {
		       		onSuccess: function(){	
/*
		       			$( ".ricerca-libera-sub" ).click(function() {
		       				$( ".cercaPerKeyword" ).val( $(".txt-ricerca-libera").val() );
			       			$( ".form-ricerca-opencup" ).attr("action", ricercaLiberaURL);
			       			$( ".naviga-form-soggetto" ).submit();
		       			});	
*/		       			
		       			$( ".btn-ricerca-avanzata" ).click(function() {
			       			$( ".form-ricerca-opencup" ).attr("action", ricercaAvanzataURL);
			       			$( ".form-ricerca-opencup" ).submit();
		       			});	
		      		
		       		}
			 	});
		    }
		});
	});
		
</script>

