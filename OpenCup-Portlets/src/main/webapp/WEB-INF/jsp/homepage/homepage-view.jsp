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

	
	div#sponsor-list{padding-top:1em;border-top: 0.5em solid #f0f0f0;background: #fff;}
	div#sponsor-list strong{font-size: 1.3em;color: #1f4e78;padding-left:.5em;}
	div#sponsor-list ul{padding-top: 1em;padding-bottom: 1em;}
	div#sponsor-list ul li {display: inline-block;list-style: none;width:19%;}
	div#sponsor-list ul li a{color: #1f4e78;padding-left:1em;line-height: 4em;}
	div#sponsor-list ul li div.sponsorImg{width: 4em;height: 4em;padding-left: .5em;}

	div#news-list{padding-top:1em;background: #fff;height: 11em;}
	div#news-list strong{font-size: 1.3em;color: #1f4e78;padding-left:.5em;}
	div#news-list ul{padding-top: 1em;width: 100%;margin-left: 1em;}
	div#news-list ul li {display: inline-block;list-style: none;width:100%;margin-left: 1em; background: #fff;}
	div#news-list ul li a{color: #1f4e78;font-style: italic;}
	div#news-list ul li i{color: #00b0f0;}
	div#news-list ul li strong.newsTitleHome{padding-left: 0px!important;}
	div#news-list ul li div.linkToPage{margin-top:.5em;}
	div#news-list ul li div.contNews{height:5em;text-align: justify;margin-right: .7em;}
	div#news-list ul li div.imgCont{width: 7.5em;height: 6.5em; }
	div#news-list div.allNewsLink{margin-top: 1em;margin-right: .8em;height:2em;width:14em;text-align:center;text-transform: uppercase;background: #0084b0;}
	div#news-list div.allNewsLink a{color: #fff;line-height: 2em;}
	div#news-list div.allNewsLink a:hover{text-decoration: underline;}
	
	
	div#approfondimenti-list{padding-top:1em;background: #fff;height: 11em;}
	div#approfondimenti-list strong{font-size: 1.3em;color: #1f4e78;padding-left:.5em;}
	div#approfondimenti-list ul{padding-top: 1em;margin-left: 0; width:100%;}
	div#approfondimenti-list ul li {display: inline-block;list-style: none;width:47%;margin-left: 1em; background: #fff;}
	div#approfondimenti-list ul li a{color: #1f4e78;font-style: italic;}
	div#approfondimenti-list ul li i{color: #00b0f0;}
	div#approfondimenti-list ul li strong.approfondimentoTitleHome{padding-left: 0px!important;}
	div#approfondimenti-list ul li div.linkToPage{margin-top:.5em;}
	div#approfondimenti-list ul li div.contApprofondimento{height:5em;text-align: justify;margin-right: .7em;}
	div#approfondimenti-list ul li div.imgCont{width: 7.5em;height: 6.5em; }
	div#approfondimenti-list div.allApprofondimentiLink{margin-top: 1em;margin-right: .8em;height:2em;width:14em;text-align:center;text-transform: uppercase;background: #0084b0;}
	div#approfondimenti-list div.allApprofondimentiLink a{color: #fff;line-height: 2em;}
	div#approfondimenti-list div.allApprofondimentiLink a:hover{text-decoration: underline;}

	div#noticeCarousel.carousel {height:18em;}
	ol#noticeSpinner.carousel-indicators{padding-right: 40em;z-index: 0; padding-top:17em;}
	ol#noticeSpinner li{background-color: #9fcfe0;}
	ol#noticeSpinner li:hover{cursor: pointer;}
	ol#noticeSpinner li.active{background-color:#0084b0;}
	div#textHome {text-align: center; color: #1f4e78;}
	div.stripe{background: #fff;height:20em;border-top:.5em solid #f0f0f0;}
	
	div.firstStripeLeft{text-align: center;padding-top:1.5em;color:#1f4e78;font-size:3em;}
	div.firstStripeLeft small{font-size: .5em;}
	div.secondStripeLeft{text-align: center;padding-top:2.3em;line-height: 1.3em;}
	div.secondStripeLeft div.linkScopri{float: right;height:2em;width:14em;text-align:center;text-transform: uppercase;background: #0084b0;}
	div.secondStripeLeft div a.linkHome{line-height: 2em;color:#fff;}
	div.secondStripeLeft div a.linkHome:hover {text-decoration: underline;cursor: pointer;}
	div.secondStripeLeft .titoloGraficoCup {font-size: 1.4em; text-align: right; padding-right:30px;}
	
	div.summary ul li{list-style: none;margin-top:1em;margin-left: -2em;}
	div.summary ul li span{color: #1f4e78;}
	div.summary ul li.sumVolume svg {height: .8em; background-color: #0084b0;}
	div.summary ul li.sumCosto svg {height: .8em; background-color: #00b0f0;}
	div.summary ul li.sumImporto svg {height: .8em;}
	div.summary ul li.sumImporto rect:first-of-type {fill: #d9d9d9;}
	div.summary ul li.sumImporto rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #1f4e78;}
	
	div.summary ul li.graficoCup {text-align:right; padding-right:30px;}
	div.summary ul li.graficoCup small{font-size: 1em;}
	div.summary ul li div.borderProgettiTotali {border-bottom: 1px solid; padding-top:10px;}
	div.summary ul li div.costo {font-size: 2em; }
	div.summary ul li div.borderProgettiTotali {border-bottom: 1px solid #1f4e78; padding-top:10px;}
	
	#rulloLoc{padding-bottom: 1em;}
	#rulloLoc .infiniteCarousel{background-color: #f0f0f0;height: 20em!important;}
	#rulloLoc .infiniteCarousel_item{float:left; background-color: #fff!important;margin: 0 6px 0 0;height:19.5em!important;}
	#rulloLoc .firstLoc{padding-top:1.5em;padding-left:1em; color:#00b0f0;font-size:18pt;}
	#rulloLoc .secondLoc{padding-top:1.5em;padding-left:1em; color:#00b0f0;font-size:16pt;}
	#rulloLoc i.icon-chevron-right, #rulloLoc i.icon-chevron-left {color:#f0f0f0!important; font-size: 40px!important;}
	#rulloLoc div.ic_tray:first-child{margin-left: -6px!important;}
	#rulloLoc div.ic_tray:last-child{margin-left: -6px!important;}
	#rulloLoc div.effDonut svg{padding-top: 1.5em;}
	#rulloLoc div.effDonut p{text-align:center;color:#00b0f0;}
	
	#rulloClass{padding-bottom: 0.5em;}
	#rulloClass .infiniteCarousel{background-color: #f0f0f0;height: 16em!important;}
	#rulloClass .infiniteCarousel_item{float:left; background-color: #fff!important;margin: 0 6px 0 0;height:19.5em!important;}
	#rulloClass i.icon-chevron-right, #rulloClass i.icon-chevron-left {color:#f0f0f0!important; font-size: 40px!important;}
	#rulloClass div.ic_tray:first-child{margin-left: -6px!important;}
	#rulloClass div.ic_tray:last-child{margin-left: -6px!important;}
	#rulloClass div.effHistogram img{width:100px;height: 100px;padding-left:1em;}
	#rulloClass div.titolo p{padding-top:.5em;padding-left:1em;font-size:18pt;color:#1f4e78;}
	#rulloClass .firstLoc{padding-top:1em;padding-left:1em; color:#1f4e78;font-size:16pt;}
	#rulloClass div.barchart p{padding-left:1em; color:#1f4e78;}
	#rulloClass .classchart rect:first-of-type {fill: #d9d9d9;}
	#rulloClass .classchart rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #1f4e78;}
	#rulloClass div.barcontainer span.pubblico{color:#1f4e78;}
	#rulloClass div.barcontainer span.privato{color:#ababab;}
	
</style>
<fmt:setLocale value="it_IT"/>
<div class="portlet-body">
	
	<div class="clear"></div>
	<div class="stripe">
	
	<div class="span12" >
		
		<div id="noticeCarousel" class="carousel slide"> 			
 		 <div id="textHome" class="carousel-inner left">
   			 <div class="active item">
				<div  class="firstStripeLeft left w30">
					OpenCUP<br/>
							<br/>
				<small>Ambito - Lavori Pubblici 1</small>
				</div>
				<div class="left secondStripeLeft w40">
					<div>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque. 
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque 
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit.
					 </div>
					 <br /><br />
					 <div class="linkScopri">
					 		<a class="linkHome" href="#">Approfondisci</a>

					 </div>
				</div>
				<div class="right secondStripeLeft w20">
					<div class="summary">
						<ul>
							<li class="sumVolume w100">
								<div>
									<span class="left"><small>Progetti censiti</small></span>
									<span class="right"><small><fmt:formatNumber value="${numeProgettiClass}" type="number" minIntegerDigits="0"/></small></span>
									<svg class="w100"></svg>
								</div>
							</li>
							<li class="sumCosto w100">
								<div>
									<span class="left"><small>Costo</small></span>
									<span class="right"><small id="costoTotale"></small></span>
									<svg class="w100"></svg>
								</div>
							</li>
							<li class="sumImporto w100">
							<div>
								<span class="left"><small>Finanziamento pubblico</small></span>
								<span class="right"><small id="importoTotale"></small></span>
								<svg class="w100"></svg>
							</div>
							</li>
						</ul>
					
					</div>
				</div>
			</div>
   			 <div class="item">
				<div  class="firstStripeLeft left w30">
					CUP<br/>
							<br/>
				<small>Ambito - Lavori Pubblici 2</small>
				</div>
				<div class="left secondStripeLeft w40">
					<div>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque. 
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque 
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit.
					 </div>
					 <br /><br />
					 <div class="linkScopri">
					 		<a class="linkHome" href="#">Approfondisci</a>

					 </div>
				</div>
				<div class="right secondStripeLeft w20">
					<div class="titoloGraficoCup">Lavori Pubblici</div>
					<div class="summary">
						<ul>
							<li class="graficoCup">
								<small>Progetti</small>
								<div class="costo"><fmt:formatNumber value="${numeProgettiTotaliClass}" type="number" minIntegerDigits="0"/></div>
								<div class="borderProgettiTotali"></div>
							</li>
							<li class="graficoCup">
								<small>Costo</small>
								<div class="costo"><span id="impoCostoProgetti"></span></div>
								<div class="borderProgettiTotali"></div>
							</li>
							<li class="graficoCup">
								<small>Finanziamento pubblico</small>
								<div class="costo"><span id="importoFinanziato"></span></div>
								<div class="borderProgettiTotali"></div>
							</li>
						</ul>
					
					</div>
				</div>
			</div>
   			<div class="item">
				<div  class="firstStripeLeft left w30">
					OpenData<br/>
							<br/>
				<small>Ambito - Lavori Pubblici 3</small>
				</div>
				<div class="right secondStripeLeft w70">
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque. 
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque 
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				</div>
			</div>
  		</div>
 		<div class="">
			<ol id="noticeSpinner" class="carousel-indicators">
  			 		<li data-target="#noticeCarousel" data-slide-to="0" class="active"></li>
  			 		<li data-target="#noticeCarousel" data-slide-to="1"></li>
  					 <li data-target="#noticeCarousel" data-slide-to="2"></li>
			 	</ol>
		</div>
	</div>
		
		
	</div>
		
</div>

<!-- <div class="clear"></div>

	<div class="stripe" id="rulloClass">
		<ul id="classCarousel" style="max-height: 20em;list-style: none;display: inline;" class="span12">
		</ul>
	</div> -->



<!-- <div class="row chart-div">
						<div class="span5 offset1" id="chartLegendPie"></div>
						<div class="span6" id="histogramChartPie"></div>
</div> -->

<div class="clear"></div>

<div class="row" style="height:24em;">

	<div class="span4 chart-div" style="border-top:0.5em solid #0084b0; padding-top:1em;">
		<div class="left" style="margin-left:0.5em;">
			<i class="icon-sitemap right" style="font-size:1.5em; border:0.25em solid #0084b0;color:#0084b0; border-radius:50%; padding: .25em;"></i>
		</div>
		<div style="margin-left:4em;">
			<span style="font-size: 2em; color: #0084b0">Classificazione</span><br/><span style="font-size: 1em; color: #0084b0">Numero Progetti</span></div>
		<div style="margin-top:2em;">
			<div class="span6" id="chartLegendClassificazione"></div>
			<div class="span6" id="histogramClassificazione"></div>
		</div>
	</div> 
	
	<div class="span4 chart-div" style="border-top:0.5em solid #00b0f0; padding-top:1em;">
		<div class="left">
			<i class="icon-globe right" style="font-size:1.5em; border:0.25em solid #00b0f0;color:#00b0f0; border-radius:50%; padding: .25em;"></i>
		</div>
		<div style="margin-left:4em;">
			<span style="font-size: 2em; color: #00b0f0">Localizzazione</span><br/><span style="font-size: 1em; color: #00b0f0">Numero Progetti</span></div>
		<div style="margin-top:2em;">
			<div class="span6" id="chartLegendLocalizzazione"></div>
			<div class="span6" id="histogramLocalizzazione"></div>
		</div>
	</div> 

	<div class="span4 chart-div" style="border-top:0.5em solid #86c5fc; padding-top:1em;">
		<div class="left">
			<i class="icon-user right" style="font-size:1.5em; border:0.25em solid #86c5fc;color:#86c5fc; border-radius:50%; padding: .25em;"></i>
		</div>
		<div style="margin-left:4em;">
			<span style="font-size: 2em; color: #86c5fc">Soggetto</span><br/><span style="font-size: 1em; color: #86c5fc">Numero Progetti</span></div>
		<div style="margin-top:2em;">
			<div class="span4" id="chartLegendSoggetto"></div>
			<div class="span6 offset2" id="histogramSoggetto"></div>
		</div>
	</div> 
	
</div>
	<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center; background: #f0f0f0;">
		<div class="offset3 span2">
			<div class="btn-carica-distribuzione volume-color sel-type-btn" data-distribuzione="VOLUME">
				<aui:a href="#" onClick="return false" cssClass="block">
					PROGETTI
				</aui:a>
			</div>
			<c:if test='${pattern eq "VOLUME"}'>
				<div class="arrow-down-volume"></div>
			</c:if>
		</div>
		<div class="span2">	
			<div class="btn-carica-distribuzione costo-color sel-type-btn" data-distribuzione="COSTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					COSTO
				</aui:a>
			</div>
			<c:if test='${pattern eq "COSTO"}'>
				<div class="arrow-down-costo"></div>
			</c:if>
		</div>
		<div class="span2">	
			<div class="btn-carica-distribuzione importo-color sel-type-btn" data-distribuzione="IMPORTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					IMPORTO
				</aui:a>
			</div>
			<c:if test='${pattern eq "IMPORTO"}'>
				<div class="arrow-down-importo"></div>
			</c:if>
		</div>
		<div class="clear"></div>
	
	</div>	

<!-- 	<div class="stripe" id="rulloLoc">
		<ul id="locCarousel" style="max-height: 20em;list-style: none;display: inline;" class="span12">
		</ul>

	</div> -->


</div>
<!-- 
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
		 -->
		 <!-- 
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
		 -->
<script>

		
		
/* AUI().use('get', function(A){
   A.Get.script('${jsFolder}/jquery-1.11.0.min.js', {
       	onSuccess: function(){
       		A.Get.script('${jsFolder}/bootstrap.min.js', {
       			onSuccess: function(){	
         			$('#noticeCarousel').carousel({
         				interval:false
         			});	
         			
         			 A.Get.script('${jsFolder}/js-infinite-carousel.js', {
                            onSuccess: function(){
                            caricaRulli();
                            $( window ).resize(function(){
                            	// esegue il redraw al resize
								d3.selectAll("li.sumImporto").select("div").select("svg").selectAll("rect").remove();
								drawFinBar();
 								d3.selectAll("#rulloClass").selectAll("div").remove();
								d3.selectAll("#rulloClass").append("ul").attr("id","classCarousel");
								drawRulloClassificazione();
 								d3.selectAll("#rulloLoc").selectAll("div").remove();
								d3.selectAll("#rulloLoc").append("ul").attr("id","locCarousel");
								drawRulloLocalizzazione();	
                        		caricaRulli();	
                        	});
                            
                            function caricaRulli(){
                            	console.log("sono jquery");
                            	 $('#locCarousel').infiniteCarousel({            
                            		transitionSpeed:600,
                            		displayTime: 6000,
				                    heightSource: 180,
                            		internalThumbnails: false,
                            		thumbnailType: 'none',
                            		customClass: 'locCarousel',
                            		displayProgressRing: false,
                            		margin: 0,
                            		inView: 3,
                            		advance: 1,
                            		autoPilot: false,
                            		prevNextInternal: true,
                            		autoHideCaptions: false,
                            	});
                            	
                             	$('#classCarousel').infiniteCarousel({            
                            		transitionSpeed:600,
                            		displayTime: 6000,
				                    heightSource: 180,
                            		internalThumbnails: false,
                            		thumbnailType: 'none',
                            		customClass: 'locCarousel',
                            		displayProgressRing: false,
                            		margin: 0,
                            		inView: 3,
                            		advance: 1,
                            		autoPilot: false,
                            		prevNextInternal: true,
                            		autoHideCaptions: false,
                            	});
                            
                            }

                        }

                    });
         			
         			
      			}
	 		});
      	}
	 });
}); */


var tipoAggregazioneSoggetto = '${pattern}';

var textColor = "#1f4e78";


// formatta importi

d3.selectAll("#costoTotale").text(nFormatterBar("${impoCostoProgettiClass}")+"\u20ac");
d3.selectAll("#importoTotale").text(nFormatterBar("${impoImportoFinanziatoClass}")+"\u20ac");

d3.selectAll("#impoCostoProgetti").text(nFormatterBar("${impoCostoProgetti}")+"\u20ac");
d3.selectAll("#importoFinanziato").text(nFormatterBar("${importoFinanziato}")+"\u20ac");



/* drawFinBar(); */
/* drawRulloClassificazione(); */
/* drawRulloLocalizzazione(); */

function drawFinBar(){
	// barra importo finanziato
	var widthLiFinanziato=d3.selectAll("li.sumImporto")[0][0].clientWidth;
	var rangeFinanziato=["${impoCostoProgettiClass}","${impoImportoFinanziatoClass}"];
	var xFinanziato=d3.scale.linear().domain([0, d3.max(rangeFinanziato)]).range([0, widthLiFinanziato]);

	d3.selectAll("li.sumImporto").select("div").select("svg").selectAll("rect").data(rangeFinanziato)
	.enter().append("rect")
	.attr("width", xFinanziato)
	.attr("height", ".8em");
}


/* function drawRulloClassificazione(){
	// rullo classificazione
	var calculatedJsonClass=eval('('+'${jsonResultClassificazione}'+')');
	var containerWClass=d3.select("#rulloClass")[0][0].clientWidth;
	var singleLiWClass=(containerWClass/3);
	var singleElMwClass=singleLiWClass/10;

	d3.selectAll("#classCarousel").selectAll("li").data(calculatedJsonClass)
	.enter().append("li")
	.style("width",singleLiWClass+"px")
	.style("display","inline")
	.style("list-style","none")
	.style("float","left")
	.append("div")
	.attr("class","classificazione")
	.append("div")
	.attr("class","titolo")
	.append("p")
	.text(function(d){
		return d.desArea;
	});

	d3.selectAll("#classCarousel").selectAll("div.classificazione")
	.append("div")
	.attr("class","left effHistogram")
	.style("width",(singleElMwClass*4.3)+"px")
	.append("img")
	.attr("src",function(d){
	 	var areaCorrente=d.desArea.split(/[\s,]+/);
	 	var firstDesc=areaCorrente[0].toLowerCase();
		return "${imgFolder}/icona-"+firstDesc+".svg";
	});
	
	d3.selectAll("#classCarousel").selectAll("div.classificazione")
	.append("div")
	.attr("class","right valori")
	.style("width",(singleElMwClass*5.5)+"px")
    .html(function(d){
     	return "<p class=\'firstLoc\'>"+nFormatter(d.numeProgetti)+"<br/><br/>"
     			+nFormatter(d.impoCostoProgetti)+"</p>";
     });
	
	d3.selectAll("#classCarousel").selectAll("div.classificazione")
	.append("div")
	.attr("class","barchart")
	.style("padding-top","8em")
	.append("div")
	.attr("class","left")
	.style("width",(singleElMwClass*4.3)+"px")
	.html("<p><small>Finanziamenti pubblici</small></p>");
	
	
	d3.selectAll("#classCarousel").selectAll("div.barchart")
	.append("div")
	.attr("class","right barcontainer")
	.style("width",(singleElMwClass*5.5)+"px")
	.append("svg")
	.attr("class","classchart")
	.attr("width",(singleElMwClass*5))
	.attr("height",20);
	
	d3.selectAll(".classchart").each(function(d){
	var range=[d.impoCostoProgetti, d.impoImportoFinanziato];
	var x=d3.scale.linear().domain([0, d3.max(range)]).range([0, (singleElMwClass*5)]);
	d3.select(this).selectAll("rect").data(range)
		.enter().append("rect")
	 	.attr("width", x)
	 	.attr("height", 20);
	 	
	});
	
	d3.selectAll(".barcontainer")
	.append("div")
	.style("width",(singleElMwClass*5)+"px")
	.html("<span class=\'left pubblico\'><small>Pubblico</small>"
		+"</span><span class=\'right privato\'><small>Privato</small></span>");
} */


/* function drawRulloLocalizzazione(){
	
	// rullo localizzazione
	var calculatedJson=eval('('+'${jsonResultLocalizzazione}'+')');
	var baseColor1="rgb(0,176,240)";
	var baseColor2="rgb(220,233,245)";

	var width = 120,
   	 	height = 120,
    	radius = Math.min(width, height) / 2;

	var color = d3.scale.linear().range([baseColor1,baseColor2]);

	var pie = d3.layout.pie()
    	.sort(null);

	var arc = d3.svg.arc()
    	.innerRadius(radius - 20)
    	.outerRadius(radius - 10);

	var containerW=d3.select("#rulloLoc")[0][0].clientWidth;
	var singleLiW=(containerW/3);
	var singleElMw=singleLiW/10;

	d3.selectAll("#locCarousel").selectAll("li").data(calculatedJson)
	.enter().append("li")
	.style("width",singleLiW+"px")
	.style("display","inline")
	.style("list-style","none")
	.style("float","left")
	.append("div")
	.attr("class","donut")
	.append("div")
	.attr("class","right effDonut")
	.style("width",(singleElMw*4.3)+"px")
	.append("svg")
	.attr("width", width)
    .attr("height", height)
    .style("padding-left",((singleElMw*4.3)-width)/2+"px")
    .append("g")
    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
    
    
    d3.selectAll("#locCarousel").selectAll("g").selectAll("path")
    .data(function(d){
    	var dataSet=[d.importoValue,(d.costoValue-d.importoValue)];
    	return pie(dataSet);
    })
    .enter().append("path")
    .attr("fill", function(a, i) { return color(i); })
    .attr("d", arc);
    
    d3.selectAll("#locCarousel").selectAll("g")
    .append("text")
    .attr("text-anchor", "middle")
    .attr("dy",".35em")
    .style("fill","#00b0f0")
    .style("font-size","18")
    .style("font-weight","bold")
    .text(function (d){
    	var dataCalc=d.importoValue/d.costoValue;
    	var dataPercent=dataCalc*100;
    	return (dataPercent.toFixed(1) +"%");
    });
    
     d3.selectAll("#locCarousel").selectAll("li").selectAll("div.donut")
     .append("div")
     .attr("class","left")
     .style("width",(singleElMw*5.5)+"px")
     .html(function(d){
     	
     	return "<p class=\'firstLoc\'><strong>"+d.fullLabel+"</strong></p>"+
     			"<p class=\'secondLoc\'>"+nFormatter(d.volumeValue)+"<br/><br/>"
     			+""+nFormatter(d.costoValue)+"</p>";
     });
    
     d3.selectAll("#locCarousel").selectAll("div.effDonut")
     .append("p")
     .html('<small>Finanziamenti Pubblici</small>');

}	 */
	

function nFormatter(num) {
    if (num >= 1000000000) {
       return (num / 1000000000).toFixed(0).replace(/\.0$/, '') + ' Mld';
    }
    if (num >= 1000000) {
       return (num / 1000000).toFixed(0).replace(/\.0$/, '') + ' Mil';
    }
    if (num >= 1000) {
       return (num / 1000).toFixed(0).replace(/\.0$/, '') + '.000';
    }
    return num;
}

function nFormatterBar(num){
	if (num >= 1000000000) {
        return (num / 1000000000).toFixed(1).replace(/\.0$/, '') + ' Mld ';
     }
     if (num >= 1000000) {
        return (num / 1000000).toFixed(1).replace(/\.0$/, '') + ' Mil ';
     }
     if (num >= 1000) {
        return (num / 1000).toFixed(0).replace(/\.0$/, '') + '.000 ';
     }
     return num;

}

function drawLegend(divLegend, legendName, dataSet){
	
	var widthTotal = 10;
	var heightLegend = 40; 
	var gapBetweenGroups = 30;
	var legendBulletOffset = 30;
	var legendTextOffset = 20;
	var textVerticalSpace = 20;

	var width_svg = d3.select(divLegend).node().getBoundingClientRect().width;
	
	var canvas = d3.select(divLegend).append("svg:svg")
	    .attr("width", width_svg)
	    .attr("height", gapBetweenGroups + (dataSet.length * heightLegend) );
		
	
//	var colorScale = d3.scale.ordinal().range(segments);
	
	// Plot the bullet circles...
/* 	canvas.selectAll("circle")
	.data(dataSet)
	.enter()
	.append("svg:circle") // Append circle elements
	
	.attr("cx", widthTotal)
	.attr("cy", function(d, i) { 
		return gapBetweenGroups + (heightLegend * i);
	})
		
	.attr("stroke-width", ".5").style("fill", function(d, i) { return colorScale(i); }) // Bullet fill color
	.attr("r", 5).attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
	.attr("class", function(d, i) { 
		retval = "legend-" + legendName + "-legendBullet-index-" + i;
		return retval;
	 }); */
	
	
	// Create hyper linked text at right that acts as label key...
    canvas.selectAll(".legend_link")
	.data(dataSet) // Instruct to bind dataSet to text elements
	.enter()
	.append("text")
	.attr("text-anchor", "center")
	.attr("x", widthTotal)
	.attr("y", function(d, i) { 
		return gapBetweenGroups + (heightLegend*i);
	})
	.attr("dx", 0)
    .attr("dy", "5px") // Controls padding to place text in alignment with bullets
    .text(function(d) { return (d.label).trunc(36, true); })
    .attr("color_value", function(d, i) { return "#0084b0"; }) // Bar fill color...
    
	.attr("class", function(d, i) { 
		retval = "label legend-" + legendName + "-legendText-index-" + i;
		return retval;
	 })
    .style("fill", textColor)
    .style("font-size", "1em") 
    .append("title")
    .text(function(d) { return d.label; });
	
};

function drawBar(chartName, histogramName, dataSet) {
	
	var width_div_chart = d3.select(chartName).node().getBoundingClientRect().width - 30;
	
	var chartWidth       = (width_div_chart / 100 * 50);
	
	var barHeight        = 40, //220 / dataSet.length, //310
	    gapBetweenGroups = 30,
	    spaceForLabels   = width_div_chart - chartWidth;

	// Zip the series data together (first values, second values, etc.)
	var zippedData = [];
	for (var i=0; i<dataSet.length; i++) {
	   zippedData.push(dataSet[i].value); 
	}

	var chartHeight = barHeight * zippedData.length + (gapBetweenGroups * 2);

	var x = d3.scale.linear().domain([0, d3.max(zippedData)]).range([0, chartWidth]);

	var y = d3.scale.linear().range([chartHeight + gapBetweenGroups, 0]);

	var yAxis = d3.svg.axis()
	    .scale(y)
	    .tickFormat('')
	    .tickSize(0)
	    .orient("left");

	// Specify the chart area and dimensions
	
	var chart = d3.select(chartName).append("svg:svg")
	    .attr("width", spaceForLabels + chartWidth)
	    .attr("height", chartHeight);

	// Create bars
	var bar = chart.selectAll("g")
	    .data(zippedData)
	    .enter()
		.append("g")
	    .attr("color_value", function(d, i) { return "#0084b0"; }) // Bar fill color...
		
		.attr("class", function(d, i) { 
			retval = "";
			return retval;
		 })
		
		.attr("transform", function(d, i) {
	      return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/dataSet.length))) + ")";
	    });

	// Create rectangles of the correct width
	bar.append("rect")
	    .attr("fill", function(d,i) { return "#0084b0" })
		.attr("class", function(d, i) { return "bar histogram-" + histogramName + "-arc-index-" + i; })
	    .attr("width", x)
	    .attr("height", barHeight - 10);

	
	// Draw labels
	bar.append("text")
	   .attr("class", function(d, i) { return "label histogram-" + histogramName + "-label-index-" + i; })
	   .attr("x", 
			function(d, i) { 
				var delta = 60;
				if(nFormatter( d ).length > 6){
					delta = nFormatter( d ).length * 10
				}
				return - delta; 
		})
	   .attr("y", (barHeight-10) / 2)
	   .attr("dy", ".25em")
	   .style("fill", textColor)
	   .style("font-size", "1em")
	   .text(function(d, i) { 
			return nFormatter(dataSet[i].value);
		});
};

String.prototype.trunc =
    function(n,useWordBoundary){
        var toLong = this.length>n,
        s_ = toLong ? this.substr(0,n-1) : this;
        s_ = useWordBoundary && toLong ? s_.substr(0,s_.lastIndexOf(' ')) : s_;
        //return  toLong ? s_ + '&hellip;' : s_;
        return  toLong ? s_ + '...' : s_;
     };

var dataSet = ${aggregatiClassificazione};
var dataSet1 = eval( dataSet );

legend = drawLegend("#chartLegendClassificazione", "LegendClassificazione", dataSet1);

bar = drawBar("#histogramClassificazione", "HistogramClassificazione", dataSet1);


// *** SECONDO GRAFICO ***//
var dataSet1 = ${aggregatiLocalizzazione};
var dataSet2 = eval( dataSet1 );

legend1 = drawLegend("#chartLegendLocalizzazione", "LegendLocalizzazione", dataSet2);

bar1 = drawBar("#histogramLocalizzazione", "HistogramLocalizzazione", dataSet2);

//*** TERZO GRAFICO ***//
var dataSet2 = ${aggregatiSoggetto};
var dataSet3 = eval( dataSet2 );

legend2 = drawLegend("#chartLegendSoggetto", "LegendSoggetto", dataSet3);

bar2 = drawBar("#histogramSoggetto", "HistogramSoggetto", dataSet3);





</script>