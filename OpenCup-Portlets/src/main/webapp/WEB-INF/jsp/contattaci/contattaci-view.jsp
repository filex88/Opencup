<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ page import="java.util.Random" %>

<portlet:defineObjects />

<div class="portlet-body">
		
	<div id="my-toggler-affina-ricerca-classificazione">
		
		<div class="content">

<%-- 			<portlet:actionURL var="affinaricercaActionVar">
			   	<portlet:param name="action" value="affinaricerca"></portlet:param>
			</portlet:actionURL> --%>
			<!-- This URL validate the CAPTCHA data entered by user -->
			<portlet:actionURL  var="validateURL" name="validateCaptcha">
				<portlet:param name="action" value="inviaEmail"></portlet:param>
			</portlet:actionURL>
 
			<liferay-ui:error key="errorMessage" message="Inserire il valore corretto nel Captcha"/>
			
			
			<aui:form 
				action="${validateURL}" 
				method="post" 
				name="contattaci-form" 
				id="contattaci-form"
				cssClass="contattaci-form form-horizontal form-ricerca-padding">
					
				<div>
					<div style="border-bottom:0.1em solid #f0f0f0; color:#1f4e78; padding-bottom:1em; font-weight:bold; font-size:1.3em; padding-left:1em; margin-top:1em;">
			           	<span class="title">CONTATTACI</span>
			       	</div>
			       	<div class="card-content">
					<div style="color:#1f4e78; padding-bottom:1em; padding-left:1em; margin-top:1em;">
			           	<span class="title" style="font-size:1.3em; font-weight:bold;">Segnalazione o Approfondimenti<br/></span>
			           	<span class="text" style="font-size:1.0em; margin-top:0.8em;">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</span>
			       	</div>
						
						<div>
							<div class="row">
								<div class="ricerca span5" style="color:#1f4e78;">
																
<!-- 									<div class="control-group no-margin-bottom">
										<strong class="control-label">Soggetto</strong>
										<div class="controls">&nbsp;</div>
									</div> -->
									<c:if test="${not empty contattaciBean.cup}">
										<aui:input type="text" bean="contattaciBean" name="cup" id="cup" value="${contattaciBean.cup}" cssClass="input-xlarge hidden" label=""/>
										<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
											<label class="control-label" for="area-soggetto">CUP</label>
											<div class="controls">
												<aui:input type="text" bean="contattaciBean" name="cupVisible" disabled="true" id="cupVisible" value="${contattaciBean.cupVisible}" cssClass="input-xlarge" label=""/>
											</div>
										</div>
									</c:if>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Nome</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="nome" id="nome" cssClass="input-xlarge" label="">
												<aui:validator name="custom" errorMessage="Attenzione. Superato il valore di caratteri massimo di 200. ">
												    function (val, fieldNode, ruleValue) {
												    	if(val.length<=200){
												    		return true;
												    	} else {
												    		return false;
												    	}
												    }
												</aui:validator>
												<aui:validator name="required" errorMessage="Attenzione. Campo Obbligatorio"></aui:validator>
											</aui:input>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Cognome</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="cognome" id="cognome" cssClass="input-xlarge" label="">
												<aui:validator name="custom" errorMessage="Attenzione. Superato il valore di caratteri massimo di 200. ">
												    function (val, fieldNode, ruleValue) {
												    	if(val.length<=200){
												    		return true;
												    	} else {
												    		return false;
												    	}
												    }
												</aui:validator>
												<aui:validator name="required" errorMessage="Attenzione. Campo Obbligatorio"></aui:validator>
											</aui:input>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Email</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="email" id="email" cssClass="input-xlarge" label="">
												<aui:validator name="required" errorMessage="Attenzione. Campo Obbligatorio"></aui:validator>
											</aui:input>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Tipo Messaggio</label>
										<div class="controls">
											<aui:select bean="contattaciBean" name="tipoMessaggio" cssClass="input-xlarge" label="">
											<c:if test="${not empty contattaciBean.cup}">
												<aui:option value="Segnalazione">Segnalazione</aui:option>
												<aui:option value="Approfondimento">Approfondimento</aui:option>
											</c:if>
											<c:if test="${empty contattaciBean.cup}">
												<aui:option value="Segnalazione">Assistenza</aui:option>
											</c:if>
											</aui:select>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Oggetto</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="oggetto" cssClass="input-xlarge" label="">
												<aui:validator name="custom" errorMessage="Attenzione. Superato il valore di caratteri massimo di 200. ">
												    function (val, fieldNode, ruleValue) {
												    	if(val.length<=200){
												    		return true;
												    	} else {
												    		return false;
												    	}
												    }
												</aui:validator>
												<aui:validator name="required" errorMessage="Attenzione. Campo Obbligatorio"></aui:validator>
											</aui:input>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Messaggio</label>
										<div class="controls">
											<aui:input type="textarea" bean="contattaciBean" name="messaggio" cssClass="input-xxlarge" label="" rows="7" cols="30">
												<aui:validator name="custom" errorMessage="Attenzione. Superato il valore di caratteri massimo di 4000. ">
												    function (val, fieldNode, ruleValue) {
												    	if(val.length<=4000){
												    		return true;
												    	} else {
												    		return false;
												    	}
												    }
												</aui:validator>
												<aui:validator name="required" errorMessage="Attenzione. Campo Obbligatorio"></aui:validator>
											</aui:input>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<portlet:resourceURL var="captchaURL"></portlet:resourceURL>
										<label class="control-label" for="area-soggetto">Captcha</label>
										<div class="controls">
											 <aui:input label="" name="captchaText" size="10" type="text" value="">
										      <aui:validator name="required" />
										    </aui:input>
										    <img style="float:left;padding:0px !important;margin-left:10px;" alt='<liferay-ui:message key="text-to-identify" />' class="captcha" border=0 src="<%= captchaURL + "&" + new Random().nextInt(Integer.MAX_VALUE) %>" />
										    <a href="#" class="refreshCaptcha captcha-reload" style="text-decoration: none;">&nbsp;<i class="icon-refresh"></i></a>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
<%-- 					<div class="control-group no-margin-bottom row-no-wrap">
					 The url that returns the image
					  <portlet:resourceURL var="captchaURL"></portlet:resourceURL>
					  <label>Testo </label>
					  <div style="float:left;">
					    the name of the field should be captchaText
					    <aui:input label="" name="captchaText" size="10" type="text" value="">
					      <aui:validator name="required" />
					    </aui:input>
					  </div>
					  <a href="#" class="refreshCaptcha captcha-reload" style="text-decoration: none;">refresh</a>
					  the refresh button %-->
					  <a href="#" class="refreshCaptcha captcha-reload" style="text-decoration: none;"></a>
					  <%-- the image
					  <img style="float:left;padding:0px !important;margin-left:10px;" alt='<liferay-ui:message key="text-to-identify" />' class="captcha" border=0 src="<%= captchaURL + "&" + new Random().nextInt(Integer.MAX_VALUE) %>" />
										
					
					
					
						<portlet:resourceURL var="captchaURL"></portlet:resourceURL>
						<liferay-ui:captcha url="<%=captchaURL%>" ></liferay-ui:captcha>
					</div> --%>
					<div class="card-action">
						<div class="control-group">
							<div class="pull-right">
								<aui:button id="affina-ricerca-classificazione" type="submit" cssClass="btn btn-primary btn-filtra" value="Invia Messaggio"></aui:button>
							</div>
						</div>
					</div>

				</div>
			
			</aui:form>
		
		</div>
		
	</div>
	
	
	
</div>


<script type="text/javascript">
			
	var namespace = "<portlet:namespace/>";
	namespace = namespace.substring(1, namespace.length - 1);
				
	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
	AUI().use('get', function(A){
		A.Get.script('${jsFolder}/jquery-1.11.0.min.js', {
			onSuccess: function(){
		    	A.Get.script('${jsFolder}/bootstrap.min.js', {
		       		onSuccess: function(){	
					 	$(".refreshCaptcha").click(function(evt){
							  jQuery(".captcha").attr('src', '${captchaURL}&force='+encodeURIComponent(Math.floor(Math.random()*Math.pow(2, 53))));
							  evt.preventDefault();
						});
		      		}
			 	});
		    }
		});
	});
</script>
