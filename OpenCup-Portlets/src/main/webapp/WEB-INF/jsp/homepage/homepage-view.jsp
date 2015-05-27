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

	div#news-list{padding-top:1em;background: #fff;height: 17em;}
	div#news-list strong{font-size: 1.3em;color: #1f4e78;padding-left:.5em;}
	div#news-list ul{padding-top: 1em;width: 100%;margin-left: 0;}
	div#news-list ul li {display: inline-block;list-style: none;width:32%;margin-left: 1em;}
	div#news-list ul li a{color: #1f4e78;font-style: italic;}
	div#news-list ul li i{color: #00b0f0;}
	div#news-list ul li strong.newsTitleHome{padding-left: 0px!important;}
	div#news-list ul li div.linkToPage{margin-top:.5em;}
	div#news-list ul li div.contNews{height:5em;text-align: justify;margin-right: .7em;}
	div#news-list ul li div.imgCont{width: 7.5em;height: 6.5em; }
	div#news-list div.allNewsLink{margin-top: 1em;margin-right: .8em;height:2em;width:14em;text-align:center;text-transform: uppercase;background: #0084b0;}
	div#news-list div.allNewsLink a{color: #fff;line-height: 2em;}
	div#news-list div.allNewsLink a:hover{text-decoration: underline;}

	div#noticeCarousel.carousel {height:18em;}
	ol#noticeSpinner.carousel-indicators{padding-right: 40em;z-index: 0; padding-top:17em;}
	ol#noticeSpinner li{background-color: #9fcfe0;}
	ol#noticeSpinner li:hover{cursor: pointer;}
	ol#noticeSpinner li.active{background-color:#0084b0;}
	div#textHome {text-align: center;}
	div.stripe{background: #fff;height:20em;border-top:.5em solid #f0f0f0;}
	
	div.firstStripeLeft{text-align: center;padding-top:1.5em;color:#1f4e78;font-size:3em;}
	div.firstStripeLeft small{font-size: .5em;}
	div.secondStripeLeft{text-align: justify;padding-top:3em;line-height: 1.3em;}
	div.secondStripeLeft div.linkScopri{float: right;height:2em;width:14em;text-align:center;text-transform: uppercase;background: #0084b0;}
	div.secondStripeLeft div a.linkHome{line-height: 2em;color:#fff;}
	div.secondStripeLeft div a.linkHome:hover {text-decoration: underline;cursor: pointer;}
	
	div.summary ul li{list-style: none;margin-top:1em;margin-left: -2em;}
	div.summary ul li span{color: #1f4e78;}
	div.summary ul li.sumVolume svg {height: .8em; background-color: #0084b0;}
	div.summary ul li.sumCosto svg {height: .8em; background-color: #00b0f0;}
	div.summary ul li.sumImporto svg {height: .8em;}
	div.summary ul li.sumImporto rect:first-of-type {fill: #d9d9d9;}
	div.summary ul li.sumImporto rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #1f4e78;}
	
	
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
					<div class="summary">
						<ul>
							<li class="sumVolume w100">
								<div>
									<div class="right">
										<small>Progetti</small>
										<div><fmt:formatNumber value="${numeProgettiTotaliClass}" type="number" minIntegerDigits="0"/></div>
									</div>
									<div class="borderProgettiTotali"></div>
								</div>
							</li>
							<li class="sumCosto w100">
								<div>
									<div class="right">
										<small>Costo</small>
										<div><fmt:formatNumber value="${impoCostoProgetti}" type="number" minIntegerDigits="0"/></div>
									</div>
									<div class="borderProgettiTotali"></div>
								</div>
							</li>
							<li class="sumImporto w100">
								<div>
									<div class="rigth">
										<small>Finanziamento pubblico</small>
										<div><fmt:formatNumber value="${importoFinanziato}" type="number" minIntegerDigits="0"/></div>
									</div>
									<div class="borderProgettiTotali"></div>
								</div>
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

<div class="clear"></div>

	<div class="stripe" id="rulloClass">
		<ul id="classCarousel" style="max-height: 20em;list-style: none;display: inline;" class="span12">
		</ul>
	</div>



<div class="clear"></div>

	<div class="stripe" id="rulloLoc">
		<ul id="locCarousel" style="max-height: 20em;list-style: none;display: inline;" class="span12">
		</ul>

	</div>


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

		
		
AUI().use('get', function(A){
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
});


// d3

// formatta importi

d3.selectAll("#costoTotale").text(nFormatterBar("${impoCostoProgettiClass}")+"\u20ac");
d3.selectAll("#importoTotale").text(nFormatterBar("${impoImportoFinanziatoClass}")+"\u20ac");



drawFinBar();
drawRulloClassificazione();
drawRulloLocalizzazione();

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


function drawRulloClassificazione(){
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
}


function drawRulloLocalizzazione(){
	
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

}	
	

function nFormatter(num) {
     if (num >= 1000000000) {
        return'<strong>'+ (num / 1000000000).toFixed(1).replace(/\.0$/, '') + ' Mld </strong><small>&euro;</small>';
     }
     if (num >= 1000000) {
        return '<strong>'+ (num / 1000000).toFixed(1).replace(/\.0$/, '') + ' Mil </strong><small>&euro;</small>';
     }
     if (num >= 1000) {
        return '<strong>' + (num / 1000).toFixed(0).replace(/\.0$/, '') + ' K </strong><small>progetti</small>';
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
        return (num / 1000).toFixed(0).replace(/\.0$/, '') + ' K ';
     }
     return num;

}




</script>