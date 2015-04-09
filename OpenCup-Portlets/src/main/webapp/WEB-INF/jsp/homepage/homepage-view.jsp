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

<style>
	ol#noticeSpinner.carousel-indicators{position: relative!important;padding-top:11em;}
	ol#noticeSpinner li{background-color: #9fcfe0;}
	ol#noticeSpinner li.active{background-color:#0084b0;}
	div#textHome {text-align: center;margin-top: -8em;}
	div#firstStripe{background: #fff;height:14em;border-top:.5em solid #f0f0f0;}
	div#firstStripeLeft{text-align: center;padding-top:1.5em;color:#1f4e78;font-size:3em;}
	div#firstStripeLeft small{font-size: .5em;}
</style>

<div class="portlet-body">
	
	<div class="clear"></div>
	<div id="firstStripe">
	<div id="firstStripeLeft" class="span3 left">
		OpenCUP<br/>
		<br/>
		<small>Ambito - Lavori Pubblici</small>
	</div>
	<div class="span6" >
		
		<div id="noticeCarousel" class="carousel slide">
			<div class="left">
				<ol id="noticeSpinner" class="carousel-indicators">
   			 		<li data-target="#noticeCarousel" data-slide-to="0" class="active"></li>
   			 		<li data-target="#noticeCarousel" data-slide-to="1"></li>
   					 <li data-target="#noticeCarousel" data-slide-to="2"></li>
 			 	</ol>
			</div>
  			
 
 		 <div id="textHome" class="carousel-inner right">
   			 <div class="active item"><p>prova1</p></div>
   			 <div class="item"><p>prova2</p></div>
   			<div class="item"><p>prova3</p></div>
  		</div>
  
	</div>
		
		
	</div>
	<div class="span3">
	</div>
		
</div>


	
	<div class="card">
		<div class="card-title">
	    	<span class="title">Open CUP</span>
	    </div>
	    <div class="card-content">
			OpenCUP permette di accedere ai dati relativi agli investimenti pubblici.
		</div>	
		<div class="card-action">
			<div class="link span2 offset10">
				<aui:a href="#" cssClass="block">
					Scopri di pi&ugrave; <i class="icon-ellipsis-horizontal"></i>
				</aui:a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div>
		<div class="span6">
			<div class="card">
				<div class="card-title">
	            	
	            	<div class="span8">
	            		<span class="title">Sintesi per Natura</span>
	            	</div>
	            	<div class="span4 destra">
	            		<div class="dropdown"  id="drp">
						    <a class="dropdown-toggle dropdown-toggle-home" data-toggle="dropdown" href="#menu1">
						        Sintesi per 
						        <b class="caret"></b>
						    </a>
						    <ul class="dropdown-menu dropdown-menu-home">
						        <li><a href="#"><span class="nav-item-label">Tutto</span></a></li>
						        <li><a href="#"><span class="nav-item-label">Immobili</span></a></li>
						        <li><a href="#"><span class="nav-item-label">Trasporti</span></a></li>
						        <li><a href="#"><span class="nav-item-label">Ambiente ed Energia</span></a></li>
						        <li><a href="#"><span class="nav-item-label">Ricerca, ITC e Aree produttive</span></a></li>
						    </ul>
						</div>

	            	</div>
	            	
	            	<div class="clear"></div>
	        	</div>
	        	
	        	<div class="card-content">
	        		
	        		<div class="desc-riepilogo">
	        			La Sintesi per Natura mette in evidenza i dati aggregati della totalità dei progetti, mostrando il volume, costo e l'importo complessivo. Inoltre tramite i pulsanti sottostanti è possibile avere la vista rispetto alla famiglia dei progetti.
	        		</div>
	        		
					<div>
						<fmt:setLocale value="it_IT"/>
						
						<div>
							<div class="span1 font-size2em"><i class="icon-bar-chart"></i></div>
							<div class="span6 font-size1em">VOLUME DEI PROGETTI</div>
							<div class="span4 font-size1em valori-riepilogo">710.000</div>
							<div class="span1 font-size1em"></div>
							<div class="clear"></div>
						</div>
						
						<div>
							<div class="span1 font-size2em"><i class="icon-tags"></i></div>
							<div class="span6 font-size1em">COSTO DEI PROGETTI</div>
							<div class="span4 font-size1em valori-riepilogo">30.710.305.000</div>
							<div class="span1 font-size1em">&euro;</div>
							<div class="clear"></div>
						</div>
						
						<div>
							<div class="span1 font-size2em"><i class="icon-eur"></i></div>
							<div class="span6 font-size1em">IMPORTO FINANZIAMENTI</div>
							<div class="span4 font-size1em valori-riepilogo">12.340.702.000</div>
							<div class="span1 font-size1em">&euro;</div>
							<div class="clear"></div>
						</div>	
											
						<div class="clear"></div>
					</div>
				</div>
				
			</div>
		</div>
		
		<div class="clear"></div>
	
	</div>
	
</div>
	<div id="news-list">	
				<strong>
					Ultime notizie
				</strong>
			
				<ul>
					<li>
						<i class="icon-picture icon-3x"></i>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit: Phasellus ...</p>
						<a href="#">Leggi tutto </a>
						
					</li>
					<li>
						<i class="icon-picture icon-3x"></i>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit: Phasellus ...</p>
						<a href="#">Leggi tutto </a>
					</li>
					<li>
						<i class="icon-picture icon-3x"></i>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit: Phasellus ...</p>
						<a href="#">Leggi tutto </a>
					</li>
				</ul>
		</div>
		
		<div id="sponsor-list">	
				<strong>
					I nostri sponsor
				</strong>
			
			
				<ul>
					<li>
						<i class="icon-circle icon-3x"></i>
						<a href="#">Sponsor 1</a>
						
					</li>
					<li>
						<i class="icon-circle icon-3x"></i>
						<a href="#">Sponsor 2</a>
					</li>
					<li>
						<i class="icon-circle icon-3x"></i>
						<a href="#">Sponsor 3</a>
					</li>
					<li>
						<i class="icon-circle icon-3x"></i>
						<a href="#">Sponsor 4</a>
					</li>
					<li>
						<i class="icon-circle icon-3x"></i>
						<a href="#">Sponsor 5</a>
					</li>
				</ul>
		</div>
		
<script>

		
		
AUI().use('get', function(A){
   A.Get.script('${jsFolder}/jquery-1.11.0.min.js', {
       	onSuccess: function(){
       		A.Get.script('${jsFolder}/bootstrap.min.js', {
       			onSuccess: function(){	
       			 	
         			$('#noticeCarousel').carousel({
         			
         				interval:false
         			});	
      			}
	 		});
      	}
	 });
});

</script>