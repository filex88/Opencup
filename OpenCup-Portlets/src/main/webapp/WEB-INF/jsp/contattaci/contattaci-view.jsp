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
		
	<div id="my-toggler-affina-ricerca-classificazione">
		
		<div class="content">

			<portlet:actionURL var="affinaricercaActionVar">
			   	<portlet:param name="action" value="affinaricerca"></portlet:param>
			</portlet:actionURL>
			
			<aui:form 
				action="${affinaricercaActionVar}" 
				method="post" 
				name="affina-ricerca-form" 
				id="affina-ricerca-form"
				cssClass="affina-ricerca-form form-horizontal form-ricerca-padding">
					
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
									
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">CUP</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="cup" value="${contattaciBean.cup}" cssClass="input-xlarge" label=""/>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Nome</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="nome" cssClass="input-xlarge" label=""/>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Cognome</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="cognome" cssClass="input-xlarge" label=""/>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Email</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="email" cssClass="input-xlarge" label=""/>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Tipo Messaggio</label>
										<div class="controls">
											<aui:select bean="contattaciBean" name="tipoMessaggio" cssClass="input-xlarge" label=""></aui:select>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Oggetto</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="oggetto" cssClass="input-xlarge" label=""/>
										</div>
									</div>
									<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
										<label class="control-label" for="area-soggetto">Messaggio</label>
										<div class="controls">
											<aui:input type="text" bean="contattaciBean" name="messaggio" cssClass="input-xlarge" label=""/>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					
					<div class="card-action">
						<div class="control-group">
							<div class="pull-right">
								<aui:button id="affina-ricerca-classificazione" cssClass="btn btn-primary btn-filtra" value="Invia Messaggio"></aui:button>
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
	
</script>