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
<liferay-ui:success key="indicizzazione-schedulata" message="indicizzazione-schedulata-1" />
<liferay-ui:success key="indicizzazione-non-schedulata" message="indicizzazione-non-schedulata-1" />

<portlet:actionURL var="actionURL">
	<portlet:param name="action" value="azioneDaAvviare"/> 
</portlet:actionURL>

<portlet:resourceURL var="statoIndicizzazioneURL" id="loadSchedulazione" escapeXml="false"   />

<script>
function submitTheForm(action){
	
	var actionURL = "${actionURL}";
		
    document.forms['<portlet:namespace/>form'].action = actionURL.replace('azioneDaAvviare', action);
    document.forms['<portlet:namespace/>form'].submit();
}

var Aui = null;
var progressBar = null;

function checkJob() {
	var resourceURL = "${statoIndicizzazioneURL}";
	Aui.io.request( resourceURL, {
			method: 'GET',
			dataType: 'json',
			on: {
	   			success: function(event, id, obj) {
	   				var jobInd = this.get('responseData').jobInd;
	   				
	   				
	   				
	   				if (jobInd.stato == 'ESECUZIONE') {
	   					Aui.one('#statoIndicizzazione').set('text', 'IN ESECUZIONE');
	   					Aui.one('#statoIndicizzazione').setStyle('color', 'green');
	   					Aui.one('#statoIndicizzazione').setStyle('font-weight', 'bold');
	   					Aui.one('#boxAvanzamento').show();
	   					Aui.one('#boxProssimaEsecuzione').hide();
	   					
	   					if (progressBar == null) {
	   						progressBar = new Aui.ProgressBar(
	  	   					      {
	  	   					        boundingBox: '#avanzamento',
	  	   					        label: '0%',
	  	   					        max: 100,
	  	   					        min: 0,
	  	   					        on: {
	  	   					          complete: function(e) {
	  	   					            this.set('label', 'Terminato!');
	  	   					          },
	  	   					          valueChange: function(e) {
	  	   					            this.set('label', e.newVal + '%');
	  	   					          }
	  	   					        },
	  	   					        value: 0,
	  	   					        width: 900
	  	   					      }
	  	   					    )
	   					};
						console.log("step = " + jobInd.step + ", totale = " + jobInd.totale + ", value = " + (jobInd.step / jobInd.totale) * 100);
	   					progressBar.set("value", (jobInd.step / jobInd.totale) * 100 );
	   					progressBar.render();
	   					
	   				}
	   				
	   				if (jobInd.stato == 'SCHEDULATO') {
	   					Aui.one('#statoIndicizzazione').set('text', 'SCHEDULATO');
	   					Aui.one('#statoIndicizzazione').setStyle('color', '#DDE432');
	   					Aui.one('#statoIndicizzazione').setStyle('font-weight', 'bold');
	   					Aui.one('#boxAvanzamento').hide();
	   					Aui.one('#boxProssimaEsecuzione').show();
	   					Aui.one('#prossimaEsecuzione').set('text', jobInd.prossimaEsecuzione );
	   				}
	   				
	   				if (jobInd.stato == 'ASSENTE') {
	   					Aui.one('#statoIndicizzazione').set('text', 'NON SCHEDULATO');
	   					Aui.one('#statoIndicizzazione').setStyle('color', 'red');
	   					Aui.one('#statoIndicizzazione').setStyle('font-weight', 'bold');
	   					Aui.one('#boxAvanzamento').hide();
	   					Aui.one('#boxProssimaEsecuzione').hide();
	   				}
	   				
	   				
	   			},
	   			failure: function (e) {
	   				Aui.one('#statoIndicizzazione').set('text', 'IMPOSSIBILE CONTATTARE IL SERVER');
   					Aui.one('#statoIndicizzazione').setStyle('color', 'red');
   					Aui.one('#statoIndicizzazione').setStyle('font-weight', 'bold');
	   				
	   			}
			}
		});
}


AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated',
		'aui-progressbar',
		function( A ) {
			
			Aui = A;
			
			checkJob();
			
			setInterval(checkJob, 5000);
		}
);


</script>


<aui:form action="${actionURL}" method="POST" cssClass="form-horizontal" name="form" >
	
	<div class="control-group" >
		<label class="control-label" for="stato">Batch di indicizzazione</label>
		<div class="controls">
			<span id="statoIndicizzazione"></span>
		</div>
	</div>
	
	<div class="control-group" id="boxAvanzamento" style="display: none;">
		<label class="control-label" for="stato">Avanzamento</label>
		<div class="controls">
			<div id="avanzamento"></div>
		</div>
	</div>


	<div class="control-group" id="boxProssimaEsecuzione" style="display: none;">
		<label class="control-label" for="stato">Prossima esecuzione</label>
		<div class="controls">
			<span id="prossimaEsecuzione"></span>
		</div>
	</div>	
	
	<div class="control-group" >
		<label class="control-label" for="stato">Schedulazione cron</label>
		<div class="controls">
			<aui:input type="text" value="${cronExp}" cssClass="input-xlarge" label="" inlineField="true"  name="cronExp" ></aui:input>
		</div>
	</div>

	<div class="control-group">
		<div class="pull-right">
			<aui:button type="button" cssClass="btn-primary" value="Schedula indicizzazione" onClick="javascript:submitTheForm('schedulaIndicizzazione')"></aui:button>
			<aui:button type="button" cssClass="btn" value="Cancella schedulazione" onClick="javascript:submitTheForm('cancellaSchedulazione')"></aui:button>
			<aui:button type="button" cssClass="btn" value="Avvio manuale" onClick="javascript:submitTheForm('avvioManuale')"></aui:button>
		</div>
	</div>
	
	
	<div class="control-group">
		<div class="pull-left">
			<aui:button type="button" cssClass="btn-primary" value="Test Cluster" onClick="javascript:submitTheForm('testCluster')"></aui:button>
		</div>
	</div>	
</aui:form>


