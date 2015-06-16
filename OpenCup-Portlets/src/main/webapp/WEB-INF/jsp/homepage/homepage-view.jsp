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

	div#noticeCarousel.carousel {height:18em; padding-top:3em;}
	ol#noticeSpinner.carousel-indicators{padding-right: 40em;z-index: 0; padding-top:23em;}
	ol#noticeSpinner li{background-color: #9fcfe0;}
	ol#noticeSpinner li:hover{cursor: pointer;}
	ol#noticeSpinner li.active{background-color:#0084b0;}
	div#textHome {text-align: center; color: #1f4e78; }
	div.stripe{background: #fff;height:26em;border-top:.5em solid #f0f0f0;}
	
	div.firstStripeLeft{text-align: center;padding-top:2em;color:#1f4e78;font-size:3em;}
	div.firstStripeLeft small{font-size: .5em; margin-bottom:1em;}
	div.secondStripeLeft{text-align: left;padding-top:2.3em;line-height: 1.3em;}
	div.secondStripeLeft div.linkScopri{float: right;height:2em;width:14em;text-align:center;text-transform: uppercase;background: #0084b0;}
	div.secondStripeLeft div a.linkHome{line-height: 2em;color:#fff;}
	div.secondStripeLeft div a.linkHome:hover {text-decoration: underline;cursor: pointer;}
	div.secondStripeLeft .titoloGraficoCup {font-size: 1.4em; text-align: right; padding-right:30px; margin-top:-1.5em;}
	
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
	div.summary ul li div.costo {font-size: 2em; padding-top:.5em; }
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
	
	div.row.graficoHome {height:22em;}
	div.row.graficoHome.progetti{border-bottom:0.5em solid #f08c00;}
	div.row.graficoHome.costo{border-bottom:0.5em solid #499652;}
	div.row.graficoHome.finanziamenti{border-bottom:0.5em solid #7ade87}
	
	
	
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
				<small></small>
				</div>
				<div class="left secondStripeLeft w40">
					<div>
OpenCUP presenta per la prima volta in un'unica area web le decisioni di investimento pubblico programmate sul territorio nazionale relative a progetti di sviluppo finanziati con risorse pubbliche (comunitarie, nazionali, regionali, etc.) o private.
<br /><br />
In questo spazio ogni cittadino, istituzione od ente potr&agrave; conoscere con l'ausilio di mappe, filtri e infografica quali e quanti interventi di sviluppo sono stati programmati sul territorio, in quali settori, da quali soggetti e con quali costi e finanziamenti previsti, nonch&egrave; scaricare i relativi dati in formato Open Data. 

					 </div>
					 <br /><br />
					 <div style="color:#1f4e78; text-align:right;">
					 		<a class="linkHome" style="color:#1f4e78; font-weight:bold;" href="#">Approfondisci >></a>

					 </div>
				</div>
				<div class="right secondStripeLeft w23">
					<div class="titoloGraficoCup">Lavori Pubblici</div>
					<div class="summary">
						<ul>
							<li class="graficoCup">
								<small>Progetti</small>
								<div class="costo"><fmt:formatNumber value="${numeProgettiClass}" type="number" minIntegerDigits="0"/></div>
								<div class="borderProgettiTotali"></div>
							</li>
							<li class="graficoCup">
								<small>Costo Previsto</small>
								<div class="costo"><span id="costoTotale"></span></div>
								<div class="borderProgettiTotali"></div>
							</li>
							<li class="graficoCup">
								<small>Finanziamento pubblico previsto</small>
								<div class="costo"><span id="importoTotale"></span></div>
								<div class="borderProgettiTotali"></div>
							</li>
						</ul>
					
					</div>
				</div>
			</div>
   			 <div class="item">
				<div  class="firstStripeLeft left w30">
					CUP<br/>
							<br/>
				<small></small>
				</div>
				<div class="left secondStripeLeft w40">
					<div>
Il CUP, Codice Unico di Progetto, identifica univocamente un progetto di investimento pubblico ed &egrave; composto da una stringa alfanumerica di 15 caratteri: va richiesto al momento della decisione di realizzare tale progetto, non varia e deve essere utilizzato fino alla chiusura dello stesso.<br/><br/>
Nasce quale codice identificativo dell’unit&agrave; elementare "progetto d'investimento pubblico" e permette la rilevazione dei dati per il  Sistema di Monitoraggio degli Investimenti Pubblici MIP. Il CUP &egrave; poi utilizzato quale strumento a supporto della tracciabilit&agrave; dei flussi finanziari prevista dalla legge 136/2010 s.m.i. e del Monitoraggio finanziario "Grandi Opere" ai fini anti criminalit&agrave; organizzata. 

					 </div>
					 <br /><br />
					 <div style="color:#1f4e78; text-align:right;">
					 		<a class="linkHome" style="color:#1f4e78; font-weight:bold;" href="#">Approfondisci >></a>

					 </div>
				</div>
				<div class="right secondStripeLeft w23">
					<div class="titoloGraficoCup"></div>
					<div class="summary">
						<ul>
							<li class="graficoCup">
								<small>Progetti</small>
								<div class="costo"><fmt:formatNumber value="${numeProgettiTotaliClass}" type="number" minIntegerDigits="0"/></div>
								<div class="borderProgettiTotali"></div>
							</li>
							<li class="graficoCup">
								<small>Costo Previsto</small>
								<div class="costo"><span id="impoCostoProgetti"></span></div>
								<div class="borderProgettiTotali"></div>
							</li>
							<li class="graficoCup">
								<small>Finanziamento pubblico previsto</small>
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
				<small></small>
				</div>
				<div class="left secondStripeLeft w40">
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque. 
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque.
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus quis lectus metus, at posuere neque 
					 Lorem ipsum dolor sit amet, consectetur adipiscing elit.
					<br /><br />
				 <div style="color:#1f4e78; text-align:right;">
				 		<a class="linkHome" style="color:#1f4e78; font-weight:bold;" href="#">Approfondisci >></a>
				 </div>
				</div>
				 <div class="right secondStripeLeft w23">
					<div class="titoloGraficoCup"></div>
					<div class="summary">
						
					
					</div>
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

<div class="row graficoHome ${tipoAggregazione}">

	<div class="span4" style="margin-left:0em; width:33%; border-right:0.5em solid #f5f5f5;">
		<div class="span12 chart-div" style="border-top:0.5em solid #0084b0; padding-top:1em; height:22em;">
			<div class="left" style="margin-left:0.5em;">
				<i class="icon-sitemap right" style="font-size:1.5em; border:0.25em solid #0084b0;color:#0084b0; border-radius:50%; padding: .25em;"></i>
			</div>
			<div style="margin-left:4em;">
				<span style="font-size: 2em; color: #0084b0">Classificazione</span><br/>
				<span style="font-size: 1em; color: #0084b0">
				<c:if test='${pattern eq "VOLUME"}'>
					Numero Progetti
				</c:if>
				<c:if test='${pattern eq "COSTO"}'>
					Costo previsto
				</c:if>	
				<c:if test='${pattern eq "IMPORTO"}'>
					Finanziamento Pubblico Previsto
				</c:if>	
				</span></div>
			<div style="margin-top:2em;">
				<div class="span6" id="chartLegendClassificazione"></div>
				<div class="span6" id="histogramClassificazione"></div>
			</div>
		</div>
	</div> 
	<div class="span4" style="margin:0; width:34%; border-right:0.5em solid #f5f5f5;  ">
		<div class="span12 chart-div" style="border-top:0.5em solid #00b0f0; padding-top:1em; padding-left:0.5em;">
			<div class="left">
				<i class="icon-globe right" style="font-size:1.5em; border:0.25em solid #00b0f0;color:#00b0f0; border-radius:50%; padding: .25em;"></i>
			</div>
			<div style="margin-left:4em;">
				<span style="font-size: 2em; color: #00b0f0">Localizzazione</span><br/>
				<span style="font-size: 1em; color: #00b0f0">
				<c:if test='${pattern eq "VOLUME"}'>
					Numero Progetti
				</c:if>
				<c:if test='${pattern eq "COSTO"}'>
					Costo previsto
				</c:if>	
				<c:if test='${pattern eq "IMPORTO"}'>
					Finanziamento Pubblico Previsto
				</c:if>	
				</span></div>
			<div style="margin-top:2em;">
				<div class="span6" id="chartLegendLocalizzazione"></div>
				<div class="span6" id="histogramLocalizzazione"></div>
			</div>
		</div> 
	</div>

	<div class="span4" style="margin:0; width:33%;  ">
		<div class="span12 chart-div" style="border-top:0.5em solid #86c5fc; padding-top:1em; padding-left:0.5em;">
			<div class="left">
				<i class="icon-user right" style="font-size:1.5em; border:0.25em solid #86c5fc;color:#86c5fc; border-radius:50%; padding: .25em;"></i>
			</div>
			<div style="margin-left:4em;">
				<span style="font-size: 2em; color: #86c5fc">Soggetto</span><br/>
				<span style="font-size: 1em; color: #86c5fc">
				<c:if test='${pattern eq "VOLUME"}'>
					Numero Progetti
				</c:if>
				<c:if test='${pattern eq "COSTO"}'>
					Costo previsto
				</c:if>	
				<c:if test='${pattern eq "IMPORTO"}'>
					Finanziamento Pubblico Previsto
				</c:if>	
				</span></div>
			<div style="margin-top:2em;">
				<div class="span4" id="chartLegendSoggetto"></div>
				<div class="span6 offset2" id="histogramSoggetto"></div>
			</div>
		</div> 
	</div>
	
</div>
	<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center; background: #f0f0f0; padding-bottom:1em;">
		<div class="offset3 span2">
			<c:if test='${pattern eq "VOLUME"}'>
				<div class="arrow-up-volume arrow-up-volume-distribuzione"></div>
			</c:if>
			<c:if test='${pattern ne "VOLUME"}'>
				<div style="height: 10px;"></div>
			</c:if>
			<div class="btn-carica-distribuzione volume-color sel-type-btn sel-type-btn-distribuzione" data-distribuzione="VOLUME">
				<aui:a href="#" onClick="return false" cssClass="block">
					Progetti
				</aui:a>
			</div>
		</div>
		<div class="span2">
			<c:if test='${pattern eq "COSTO"}'>
				<div class="arrow-up-costo arrow-up-costo-distribuzione"></div>
			</c:if>	
			<c:if test='${pattern ne "COSTO"}'>
				<div style="height: 10px;"></div>
			</c:if>
			<div class="btn-carica-distribuzione costo-color sel-type-btn sel-type-btn-distribuzione" data-distribuzione="COSTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					Costo
				</aui:a>
			</div>
		</div>
		<div class="span2">
			<c:if test='${pattern eq "IMPORTO"}'>
				<div class="arrow-up-importo arrow-up-importo-distribuzione"></div>
			</c:if>	
			<c:if test='${pattern ne "IMPORTO"}'>
				<div style="height: 10px;"></div>
			</c:if>
			<div class="btn-carica-distribuzione importo-color sel-type-btn sel-type-btn-distribuzione" data-distribuzione="IMPORTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					Finanziamenti
				</aui:a>
			</div>
		</div>
		<div class="clear"></div>
	
	</div>	
</div>

	<portlet:actionURL var="urlActionSoggettoVar">
	   	<portlet:param name="action" value="cambiaAggregazione"></portlet:param>
	</portlet:actionURL>
	
	<form 
		action="${urlActionSoggettoVar}" 
		method="post" 
		name="naviga-form-distribuzione" 
		class="naviga-form-distribuzione"
		id="naviga-form-distribuzione"
		style="display: none;">
	
			<aui:input cssClass="pattern" type="hidden" name="pattern" value="${pattern}" id="pattern" />
			
	</form>

<script>

		



var tipoAggregazioneSoggetto = '${pattern}';

var textColor = "#1f4e78";


// formatta importi

d3.selectAll("#costoTotale").text(nFormatterBar("${impoCostoProgettiClass}")+"\u20ac");
d3.selectAll("#importoTotale").text(nFormatterBar("${impoImportoFinanziatoClass}")+"\u20ac");

d3.selectAll("#impoCostoProgetti").text(nFormatterBar("${impoCostoProgetti}")+"\u20ac");
d3.selectAll("#importoFinanziato").text(nFormatterBar("${importoFinanziato}")+"\u20ac");

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

function drawLegend(divLegend, legendName, dataSet, hexColor){
	
	var widthTotal = 12;
	var heightLegend = 35; 
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
    .attr("color_value", function(d, i) { return hexColor; }) // Bar fill color...
    .attr("data_linkURL", function (d,i){ console.log(dataSet[i]); return dataSet[i].linkURL })
    .attr("style", "cursor:pointer;")
	.attr("class", function(d, i) { 
		retval = "link-url-naviga legend-" + legendName + "-legendText-index-" + i;
		return retval;
	 })
    .style("fill", textColor)
    .style("font-size", "0.9em") 
    .append("title")
    .text(function(d) { return d.label; });
	
};

function drawBar(chartName, histogramName, dataSet, hexColor) {
	
	var width_div_chart = d3.select(chartName).node().getBoundingClientRect().width - 30;
	
	var chartWidth       = (width_div_chart / 100 * 50);
	
	var barHeight        = 35, //220 / dataSet.length, //310
	    gapBetweenGroups = 30,
	    spaceForLabels   = width_div_chart - chartWidth;

	// Zip the series data together (first values, second values, etc.)
	var zippedData = [];
	for (var i=0; i<dataSet.length; i++) {
	   zippedData.push(dataSet[i].value); 
	}

	var chartHeight = barHeight * zippedData.length + (gapBetweenGroups * 1,3);

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
	    .attr("color_value", function(d, i) { return hexColor; }) // Bar fill color...
		
		.attr("class", function(d, i) { 
			retval = "";
			return retval;
		 })
		
		.attr("transform", function(d, i) {
	      return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/dataSet.length))) + ")";
	    });

	// Create rectangles of the correct width
	bar.append("rect")
	    .attr("fill", function(d,i) { return hexColor })
		.attr("class", function(d, i) { return "bar link-url-naviga histogram-" + histogramName + "-arc-index-" + i; })
	    .attr("width", x)
	    .attr("data_linkURL", function (d,i){ console.log(dataSet[i]); return dataSet[i].linkURL })
	    .attr("style", "cursor:pointer;")
	    .attr("height", barHeight - 10);

	
	// Draw labels
	bar.append("text")
	   .attr("class", function(d, i) { return "link-url-naviga histogram-" + histogramName + "-label-index-" + i; })
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
	    .attr("data_linkURL", function (d,i){ console.log(dataSet[i]); return dataSet[i].linkURL })
    	.attr("style", "cursor:pointer;")
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

legend = drawLegend("#chartLegendClassificazione", "LegendClassificazione", dataSet1, "#0084b0");

bar = drawBar("#histogramClassificazione", "HistogramClassificazione", dataSet1, "#0084b0");


// *** SECONDO GRAFICO ***//
var dataSet1 = ${aggregatiLocalizzazione};
var dataSet2 = eval( dataSet1 );

legend1 = drawLegend("#chartLegendLocalizzazione", "LegendLocalizzazione", dataSet2, "#00b0f0");

bar1 = drawBar("#histogramLocalizzazione", "HistogramLocalizzazione", dataSet2, "#00b0f0");

//*** TERZO GRAFICO ***//
var dataSet2 = ${aggregatiSoggetto};
var dataSet3 = eval( dataSet2 );

legend2 = drawLegend("#chartLegendSoggetto", "LegendSoggetto", dataSet3, "#86c5fc");

bar2 = drawBar("#histogramSoggetto", "HistogramSoggetto", dataSet3, "#86c5fc");


AUI().use('get', function(A){
	A.Get.script('${jsFolder}/jquery-1.11.0.min.js', {
		onSuccess: function(){
	    	A.Get.script('${jsFolder}/bootstrap.min.js', {
	       		onSuccess: function(){	
					
	       			$(".volume-color-distribuzione").mouseover(function() { 
	       				$(".arrow-up-volume-distribuzione").css('border-top','10px solid #d27900'); 
	       			});
	       			$(".volume-color-distribuzione").mouseout(function() { 
	       				$(".arrow-up-volume-distribuzione").css('border-top','10px solid #f08c00'); 
	       			});
	       				
	       			$(".costo-color-distribuzione").mouseover(function() { 
	       				$(".arrow-up-costo-distribuzione").css('border-top','10px solid #2c5831'); 
	       			});
	       			$(".costo-color-distribuzione").mouseout(function() { 
	       				$(".arrow-up-costo-distribuzione").css('border-top','10px solid #499652'); 
	       			});
	       				
	       			$(".importo-color-distribuzione").mouseover(function() { 
	       				$(".arrow-up-importo-distribuzione").css('border-top','10px solid #005500'); 
	       			});
	       			$(".importo-color-distribuzione").mouseout(function() { 
	       				$(".arrow-up-importo-distribuzione").css('border-top','10px solid #7ade87'); 
	       			});
	       				
	       			$( ".sel-type-btn-distribuzione" ).click(function() {
       					var arc = d3.select(this);
	       				var distribuzione = arc.attr("data-distribuzione");
	       				$( ".pattern" ).val(distribuzione);
	       				$( ".naviga-form-distribuzione" ).submit();
	       			});
	       			
					$(".link-url-naviga").click(function() {
						var obj = d3.select(this);
						var data_linkURL = obj.attr("data_linkURL");
						$(".naviga-form-distribuzione").attr("action",data_linkURL);
						$(".naviga-form-distribuzione").submit();
					});
	      		}
		 	});
	    }
	});
});


</script>