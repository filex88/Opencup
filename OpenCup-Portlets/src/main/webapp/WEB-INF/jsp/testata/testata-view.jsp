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
	
	div.stripe{	background: #fff; border-bottom:.5em solid #f0f0f0;}
	div.stripeTestata{background: #fff;}
	
	#conteiner{padding-bottom: 0.5em;}
	#conteiner2{padding-bottom: 0.5em; display: inline-block;}
	#conteiner .infiniteCarousel{background-color: #f0f0f0;height: 16em!important;}
	#conteiner .infiniteCarousel_item{float:left; background-color: #fff!important;margin: 0 6px 0 0;height:14.5em!important;}
	#conteiner i.icon-chevron-right, #rulloLoc i.icon-chevron-left {color:#f0f0f0!important; font-size: 40px!important;}
	#conteiner div.ic_tray:first-child{margin-left: -6px!important;}
	#conteiner div.ic_tray:last-child{margin-left: -6px!important;}
	#conteiner div.effHistogram img{width:100px;height: 100px;padding-left:1em;}
	#conteiner div.titolo p{padding-top:0.5em; padding-left:0.5em; font-size:1.6em; color:#1f4e78; margin:0 0 5px;}
	#conteiner .firstLoc{ padding-top:0.8em; padding-left:1em; color:#1f4e78; font-size:2em; white-space: nowrap;}
	#conteiner div.barchart p{padding-left:1em; color:#1f4e78;}
	
	#conteiner .classchart-natura rect:first-of-type {fill: #d9d9d9;}
	#conteiner .classchart-natura rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #0f0e73;}
	
	#conteiner .classchart-localizzazione rect:first-of-type {fill: #d9d9d9;}
	#conteiner .classchart-localizzazione rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #3f8acc;}
	
	#conteiner .classchart-soggetto rect:first-of-type {fill: #d9d9d9;}
	#conteiner .classchart-soggetto rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #87b5de;}
	
	#conteiner div.barcontainer span.pubblico{color:#1f4e78;}
	#conteiner div.barcontainer span.privato{color:#ababab;}
	div.div_pie_chart_testata_stato, div.div_bar_chart_testata_anni {text-align: center;}
	.legend tr{border-bottom:0px solid #1f4e78;}
	.legend tr:first-child{border-top:0px solid #1f4e78;}
	.legend { margin-bottom: 10px; display:inline-block; border-collapse: collapse; border-spacing: 0px;}
	.legend td{padding:0px 4px; vertical-align:bottom;}
	.legendLabel{text-align: left; width:50px; color: #1f4e78; font-size: 85%}
	.legendLabelNowrap{text-align: left; width:50px; color: #1f4e78; white-space: nowrap; font-size: 85%}
	.legendValue{text-align: right; width:50px;color: #1f4e78;}
	
	.bar_testata {fill: #1f4e78;}
	.bar_testata_costo {fill: #1f4e78;}
	.bar_testata_finanziato {fill: #005500;}
	.axis {font: 10px;color:#1f4e78;}
	.axis path,	.axis line {fill: none; stroke: #000; shape-rendering: crispEdges;}
	.x.axis path {display: none;}
	
	
	.axis path,
	.axis line {
	  fill: none;
	  stroke: #000;
	  shape-rendering: crispEdges;
	}

	.x.axis path {
	  display: none;
	}

	.line {
		stroke: #1f4e78;
		fill:none;
		stroke-width: 2;
	}
	
	.axis path,
	.axis line {
		fill: none;
		stroke: black;
		shape-rendering: crispEdges;
	}
	
	.axis text {
		font-size: 10px;
		/* font-family: sans-serif; */
	}
	
	.text-label {
		font-size: 10px;
		/* font-family: sans-serif; */
	}
	
	.area {
	  fill: #b2c6ff;
	}

	.legendTrend{
		padding: 3px;
        font-size: 85%;
        background: #f0f0f0;
        box-shadow: 2px 2px 1px #888;
	}
	
	.d3-tip {
	  line-height: 1;
	  font-weight: bold;
	  padding: 12px;
	  background: rgba(0, 0, 0, 0.8);
	  color: #fff;
	  border-radius: 2px;
	}

	/* Creates a small triangle extender for the tooltip */
	.d3-tip:after {
	  box-sizing: border-box;
	  display: inline;
	  font-size: 10px;
	  width: 100%;
	  line-height: 1;
	  color: rgba(0, 0, 0, 0.8);
	  content: "\25BC";
	  position: absolute;
	  text-align: center;
	}

	/* Style northward tooltips differently */
	.d3-tip.n:after {
	  margin: -1px 0 0 0;
	  top: 100%;
	  left: 0;
	}
	
	
</style>

<div class="stripeTestata">
	
	<div class="contenitore-testata" style="height: auto; overflow: auto;">
		
		<div class="row" id="conteiner" >
			<div class="span12 titolo" id="titolo"></div>
		</div>
		
		<div class="row" id="conteiner" >
		
			<div class="span3 corpo_logo" id="corpo_logo"></div>
			
			<div class="span3" style="margin-top: 20px;">
				<div>
					<div class="span6 pie_chart_testata_stato" id="pie_chart_testata_stato" style="margin-left: 0px;"></div>
					<div class="span6 pie_chart_testata_stato_legend" id="pie_chart_testata_stato_legend" style="margin-left: 0px;"></div>
				</div>
			</div>
			
			<div class="span3" style="margin-left: 0px">
				<div>
					<div class="span8 bar_chart_testata_anni" id="bar_chart_testata_anni" style="margin-left: 0px; min-height: 152px"></div>
					<div class="span4 bar_chart_testata_anni_legend" id="bar_chart_testata_anni_legend" style="margin-left: 0px; color: #1f4e78;">
						<table class="legend">
							<tbody>
								<tr>
									<td class="legendLabel">Numerosit&agrave; Progetti</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="span3" style="margin-left: 0px">
				<div>
					<div class="span8 trend_chart_testata_anni" id="trend_chart_testata_anni" style="margin-left: 0px; min-height: 152px"></div>
					<div class="span4 trend_chart_testata_anni_legend" id="trend_chart_testata_anni_legend" style="margin-left: 0px;">
					
						<table class="legend">
							<tbody>
								<tr>
									<td width="10" style="vertical-align: inherit">
										<svg width="10" height="10">
											<rect width="10" height="2" fill="#1f4e78"></rect>
										</svg>
									</td>
									<td class="legendLabel">Costo previsto</td>
								</tr>
								<tr>
									<td width="10" style="vertical-align: inherit">
										<svg width="10" height="10">
											<rect width="10" height="10" fill="#b2c6ff"></rect>
										</svg>
									</td>
									<td class="legendLabel">Finanziamento pubblico previsto</td>
								</tr>
							</tbody>
						</table>
						
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>

<script>
	/*
	var baseColor1 = "#b2c6ff";
	var baseColor2 = "#4472fb";
	var baseColor3 = "#1f4e78";
	var textColor = "#1f4e78";
	*/
	
	var pagNavigazioneLogo = "${pagNavigazioneLogo}";
	
	var baseColor1 = "#87b5de";
	var baseColor2 = "#3f8acc";
	var baseColor3 = "#0f0e73";
	
	var textColor = "#0f0e73";
	if(pagNavigazioneLogo=='localizzazione'){
		textColor = "#3f8acc";
	}else if(pagNavigazioneLogo=='soggetto'){
		textColor = "#87b5de";
	}
	
	//console.log(pagNavigazioneLogo + " -> " + textColor);

	d3.selectAll(".contenitore-testata").style("border-left", "5px solid " + textColor);
	d3.selectAll(".legendLabel").style("color", textColor);
	d3.selectAll(".legendLabelNowrap").style("color", textColor);
	
	Number.prototype.formattaNumerico = 
		function(c, d, t) {
			var n = this, 
				c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "." : d, 
				t = t == undefined ? "," : t, s = n < 0 ? "-" : "", 
				i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", 
				j = (j = i.length) > 3 ? j % 3 : 0;
				
		return s + (j ? i.substr(0, j) + t : "")
			+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t)
			+ (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};
		 
	function nFormatter(num) {
	    if (num >= 1000000000) {
	       return '<strong>' + (num / 1000000000).formattaNumerico(0, ',', '.') + '</strong> <small>Mld &euro;</small>';
	    }
	    if (num >= 1000000) {
	       return '<strong>' + (num / 1000000).formattaNumerico(0, ',', '.') + '</strong> <small>Mil &euro;</small>';
	    }
	    if (num >= 1000) {
	    	// return '<strong>' + (num / 1000).toFixed(0).replace(/\.0$/, '') + '.000 </strong><small style="font-size: .5em;">progetti</small>';
	    	return '<strong>' + num.formattaNumerico(0, ',', '.')  + '</strong>';
	    }
	    return num;
	}
	
	function drawTitoloIntestazione ( elementName, calculatedJsonClass, pagNavigazioneLogo, separatore ) {
		
		d3.selectAll(elementName)
		.selectAll("div")
		.data(calculatedJsonClass)
		.enter()
		.append("p")
		.style("color", textColor)
		.html(function(d){
			var retval = d.desNatura;
			if( pagNavigazioneLogo == 'natura' ){
				if( d.idArea != "-1" ){retval = d.desArea;} 
				if( d.idSottoSettore != "-1" ){retval = retval + separatore + d.desSottoSettore;} 
				if( d.idCategoriaIntervento != "-1" ){retval = retval + separatore + d.desCategoriaIntervento;} 
			}else if( pagNavigazioneLogo == 'localizzazione' ){
				if( d.idAreaGeografica != "-1"){retval = d.descAreaGeografica;}
				if( d.idRegione != "-1" ){retval = retval + separatore + d.descRegione;} 
				if( d.idProvincia != "-1" ){retval = retval + separatore + d.descProvincia;} 
			}else if( pagNavigazioneLogo == 'soggetto' ){
				if( d.idAreaSoggetto != "-1" ){retval = d.descAreaSoggetto;} 
				if( d.idCategSoggetto != "-1" ){retval = retval + separatore + d.descCategSoggetto;} 
				if( d.idSottocategSoggetto != "-1" ){retval = retval + separatore + d.descSottocategSoggetto;} 
			}
			return retval;
		});
		
		
	}
	
	function drawLogoIntestazione( elementName, calculatedJsonClass ){
		
		var totWidth = d3.select(elementName).node().getBoundingClientRect().width;	
		var singleElMwClass = totWidth / 10;
		
		d3.selectAll(elementName)
		.selectAll("div")
		.data(calculatedJsonClass)
		.enter()
		.append("div")
		.style("width",totWidth+"px")
		.style("display","inline")
		.style("list-style","none")
		.style("float","left")
		.append("div")
		.attr("class", "classificazione");
		
		d3.selectAll(elementName)
		.selectAll("div.classificazione")
		.append("div")
		.attr("class", "left effHistogram")
		.style("width", (singleElMwClass*2.6)+"px")
		.append("img")
		.attr("src", function(d){
			
			var areaCorrente = d.desArea.split(/[\s,]+/);
		 	var desArea = areaCorrente[0].toLowerCase();
		 	
		 	var areaGeograficaCorrente = d.descAreaGeografica.split(/[\s,]+/);
		 	var descAreaGeografica = areaGeograficaCorrente[0].toLowerCase();
		 	
		 	var areaSoggettoCorrente = d.descAreaSoggetto.split(/[\s,]+/);
		 	var descAreaSoggetto = areaSoggettoCorrente[0].toLowerCase();
		 	if( areaSoggettoCorrente.length > 2 ){
		 		descAreaSoggetto = areaSoggettoCorrente[2].toLowerCase();
		 	}
		 	
		 	if( desArea != 'tutte' ){
		 		var appoUrl = "${imgFolder}/icona-" + desArea + ".svg";
		 	}else if( descAreaGeografica != 'tutte' ){
		 		var appoUrl = "${imgFolder}/icona-" + descAreaGeografica + ".svg";
		 	}else if( descAreaSoggetto != 'tutte' ){
		 		var appoUrl = "${imgFolder}/icona-" + descAreaSoggetto + ".svg";
		 	}else{
		 		var appoUrl = "${imgFolder}/icona-"+pagNavigazioneLogo+".svg";
		 	}

		 	return appoUrl
		});
		
		d3.selectAll(elementName)
		.selectAll("div.classificazione")
		.append("div")
		.attr("class","left")
		.style("width",(singleElMwClass*6.5)+"px")
		.append("p")
		.style("color", textColor)
		.attr("class", "firstLoc")
		.html(function(d){
	    	return nFormatter(d.numeProgetti)+' <small style="font-size: .5em;">progetti</small>'+"<br/><br/>"+nFormatter(d.impoCostoProgetti);
	    });
		
		d3.selectAll(elementName)
		.selectAll("div.classificazione")
		.append("div")
		.attr("class","barchart")
		.style("padding-top","8em")
		.append("div")
		.attr("class","left")
		.style("width",(singleElMwClass*4.3)+"px")
		.append("p")
		.style("color", textColor)
		.html("<small>Finanziamenti</small>");
		
		d3.selectAll(elementName)
		.selectAll("div.barchart")
		.append("div")
		.attr("class","right barcontainer")
		.style("width",(singleElMwClass*5.5)+"px")
		.style("padding-top","0px")
		.append("svg")
		.attr("class","classchart-"+pagNavigazioneLogo)
		.attr("width",(singleElMwClass*5))
		.attr("height", 10);
		
		d3.selectAll(".classchart-"+pagNavigazioneLogo)
		.each(function(d){
			var range=[d.impoCostoProgetti, d.impoImportoFinanziato];
			var x=d3.scale.linear().domain([0, d3.max(range)]).range([0, (singleElMwClass*5)]);
			d3.select(this)
			.selectAll("rect")
			.data(range)
			.enter().append("rect")
		 	.attr("width", x)
		 	.attr("height", 16);
		 	
		});
		
		var appo_div = d3.selectAll(".barcontainer")
		.append("div")
		.style("width",(singleElMwClass*5)+"px");
		
		appo_div.append("span")
		.attr("class", "left pubblico")
		.style("color", textColor)
		.html("<small>Pubblico</small>")
		
		appo_div.append("span")
		.attr("class", "right privato")
		.html("<small>Privato</small>")
		
		//.html("<span class=\'left pubblico\'><small>Pubblico</small></span><span class=\'right privato\'><small>Privato</small></span>");
		
	}
	
	function drawPieTestataStato ( pieName, dataSet, selectString) {
		
		var totWidth = d3.select(selectString).node().getBoundingClientRect().width;	
		
		var margin = 10;
		var outerRadius = (totWidth/100*80)/2;
		var innerRadius = outerRadius - (outerRadius / 3);
		var sortArcs = 0;
		
		var minDataStato = 0;
		var maxDataStato = ${ recordCountStato };
		var midDataStato = maxDataStato / 2;
		
		var divCanvas = d3.selectAll(selectString)
		.append("div")
		.style("width",totWidth+"px")
		.style("display", "inline")
		.style("list-style", "none")
		.style("float", "left")
		.attr("class", "div_pie_chart_testata_stato pie_chart_testata_stato")
		.attr("id", "pie_chart_testata_stato");
	
		// Color Scale Handling...
		var colorScale = d3.scale.linear().domain([minDataStato, midDataStato, maxDataStato]).range([baseColor1, baseColor2, baseColor3]);
		
		var pieWidthTotal = outerRadius * 2;
			
		var pieCenterX = outerRadius + margin / 2;
		var pieCenterY = outerRadius + margin / 2;
		var legendBulletOffset = 30;
		var legendVerticalOffset = outerRadius - margin;
		var legendTextOffset = 20;
		var textVerticalSpace = 20;
		
		var canvasWidth = outerRadius * 2 + margin * 2;//wigthDivPieChart;
		var canvasHeight = 0;
		
		var pieDrivenHeight = outerRadius * 2 + margin * 2;
			
		canvasHeight = pieDrivenHeight;

		var x = d3.scale.linear().domain([ 0, d3.max(dataSet, function(d) {	return d.value; }) ]).rangeRound([ 0, pieWidthTotal ]);
		var y = d3.scale.linear().domain([ 0, dataSet.length ]).range([ 0, (dataSet.length * 20) ]);
	
		var tweenPie = function(b) {
			b.innerRadius = 0;
			var i = d3.interpolate({
				startAngle : 0,
				endAngle : 0
			}, b);
			return function(t) {
				return arc(i(t));
			};
		}
	
		// Create a drawing canvas...
		var canvas = divCanvas.append("svg:svg") //create the SVG element
		.data([ dataSet ]) //associate our data with the document
		.attr("width", canvasWidth) //set the width of the canvas
		.attr("height", canvasHeight) //set the height of the canvas
		.append("svg:g") //make a group to hold our pie chart
		.attr("transform", "translate(" + pieCenterX + "," + pieCenterY + ")") // Set center of pie

		// Define an arc generator. This will create <path> elements for using arc data.
		var arc = d3.svg.arc().innerRadius(innerRadius) // Causes center of pie to be hollow
		.outerRadius(outerRadius);
	
		// Define a pie layout: the pie angle encodes the value of dataSet.
		// Since our data is in the form of a post-parsed CSV string, the
		// values are Strings which we coerce to Numbers.
		var pie = d3.layout.pie().value(
				function(d) {
					return d.value;
				}).sort(function(a, b) {
					if (sortArcs == 1) {
						return b.value - a.value;
					} else {
						return null;
					}
		});
	
		// Select all <g> elements with class slice (there aren't any yet)
		var arcs = canvas.selectAll("g.slice")
		// Associate the generated pie data (an array of arcs, each having startAngle,
		// endAngle and value properties) 
		.data(pie)
		// This will create <g> elements for every "extra" data element that should be associated
		// with a selection. The result is creating a <g> for every object in the data array
		// Create a group to hold each slice (we will have a <path> and a <text>      // element associated with each slice)
		.enter()
		.append("svg:g")
		.attr("class", "slice") //allow us to style things in the slices (like text)
		// Set the color for each slice to be chosen from the color function defined above
		// This creates the actual SVG path using the associated data (pie) with the arc drawing function
		.style("stroke", "White")
		.attr("d", arc);
	
		arcs.append("svg:path")
		// Set the color for each slice to be chosen from the color function defined above
		// This creates the actual SVG path using the associated data (pie) with the arc drawing function
		.attr("fill", function(d, i) { return colorScale(i); })
		
		.style("stroke", "White")
		.attr("d", arc)
		.transition()
		.ease("linear")
		.duration(500)
		.delay(function(d, i) { return i * 50; })
		.attrTween("d", tweenPie);
	
		// Add a value to the larger arcs, translated to the arc centroid and rotated.
		arcs.filter(function(d) { return d.endAngle - d.startAngle > .2; })
		.append("svg:text")
		.attr("dy", ".35em")
		.attr("text-anchor", "middle")
		.attr("transform", function(d) { //set the label's origin to the center of the arc
					//we have to make sure to set these before calling arc.centroid
					d.outerRadius = outerRadius; // Set Outer Coordinate
					d.innerRadius = innerRadius; // Set Inner Coordinate
					return "translate(" + arc.centroid(d) + ")";
				});
		//.style("fill", "White")
		//.style("font", "normal 18px Arial");
	
		// Computes the angle of an arc, converting from radians to degrees.
		function angle(d) {
			var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
			return a > 90 ? a - 180 : a;
		}
		
	};
	
	// function to handle legend.
    function legendTestata(pieName, dataSet, selectString){
       	
		var totWidth = d3.select(selectString).node().getBoundingClientRect().width;	
		
		var minDataStato = 0;
		var maxDataStato = ${ recordCountStato };
		var midDataStato = maxDataStato / 2;
		
     	// Color Scale Handling...
		var colorScale = d3.scale.linear().domain([minDataStato, midDataStato, maxDataStato]).range([baseColor1, baseColor2, baseColor3]);
        
     	// create table for legend.
        var legend = d3.select(selectString).append("table").attr('class', 'legend');
        
        // create one row per segment.
        var tr = legend.append("tbody").selectAll("tr").data(dataSet).enter().append("tr");
            
        // create the first column for each segment.
        tr.append("td")
        .attr("width", '10')
        .append("svg")
        .attr("width", '10').attr("height", '10').append("rect")
        .attr("width", '10').attr("height", '10')
        .attr("fill", function(d, i) { return colorScale(i); });
            
        // create the second column for each segment.
        tr.append("td")
        .attr("width", totWidth-10)
        .attr("class", 'legendLabelNowrap').text(function(d){ return d.label; })
        .style("color", textColor);
    }
	
	function drawBarTestataAnni( barName, dataSet, selectString  ) {
		
		var totWidth = d3.select(selectString).node().getBoundingClientRect().width;	
		
		var totHeight = d3.select(selectString).node().getBoundingClientRect().height;	
		
		var margin = {top: 5, right: 0, bottom: 30, left: 0},
	    width = totWidth - margin.left - margin.right - 5,
	    //height = (totWidth/2) - margin.top - margin.bottom;
	    height = totHeight - margin.top - margin.bottom;
	    
		var x = d3.scale.ordinal().rangeRoundBands([0, width], .1);

		var y = d3.scale.linear().range([height, 0]);

		var xAxis = d3.svg.axis().scale(x).orient("bottom");

		var yAxis = d3.svg.axis().scale(y).orient("left");

		var svg = d3.select(selectString).append("svg")
	  	.attr("width", width+5)
	  	.attr("height", height + margin.bottom)
	  	.append("g")
	  	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	  	x.domain(dataSet.map(function(d) { return d.label; }));
	  	
	  	y.domain([0, d3.max(dataSet, function(d) { return d.volume; })]);
	  	
	 	// extract the x labels for the axis and scale domain
		var xLabels = dataSet.map(function (d) { return d.label; })
		
		/*
	  	svg.append("g")
	    .attr("class", "x axis")
	    .attr("transform", "translate(0," + height + ")")
	    .call(xAxis.tickValues( xLabels.filter( 
				function(d, i) { 
					if ( i == 0 || i == 9 )
						return d;
					})))
		.selectAll("text")
		.style("text-anchor", "end")
	  	*/
	  	
	    svg.append("g")
      	.attr("class", "x axis")
     	.attr("transform", "translate(0," + height + ")")
      	//.call(xAxis)
      	.call( xAxis.tickValues( xLabels.filter( 
      		function(d, i) { 
      			if ( i == 0 || i == 9 ){
      				return d;
      			}
      		}
      	) ) )
    	.append("text")
      	.attr("class", "label")
      	.attr("x", width)
      	.attr("y", -6)
      	.style("text-anchor", "end")

		svg.selectAll(".bar_testata")
	  	.data(dataSet)
	  	.enter()
	  	.append("rect")
	  	.attr("class", "bar_testata")
	    .attr("x", function(d, i) { return x(d.label); })
	    .attr("width", x.rangeBand())
	    .attr("y", function(d) { return y(d.volume); })
	    .attr("height", function(d) { return height - y(d.volume); })
		.style("fill", textColor);
	};
	
	function drawTrendTestataAnni( barName, dataSet, selectString  ) {
		
		var totWidth = d3.select(selectString).node().getBoundingClientRect().width;	
		
		var totHeight = d3.select(selectString).node().getBoundingClientRect().height;	
		
		var decimalFormat = d3.format("0.2f");
		
		var margin = {top: 5, right: 0, bottom: 30, left: 0},
	    width = totWidth - margin.left - margin.right - 5,
	    //height = (totWidth/2) - margin.top - margin.bottom;
	    height = totHeight - margin.top - margin.bottom;
		
		var svgAll = d3.select(selectString).append("svg")
		  	.attr("width", totWidth+5)
		  	.attr("height", height +  + margin.bottom)
		  	.attr("class", barName)
		
		var svg = svgAll
		  	.append("g")
		  	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
		
		//svg.append("g")
		//	.attr("class", "y axis");
		
		svg.append("g")
			.attr("class", "x axis");
		
		///COSTO
		var xCosto = d3.scale.ordinal().rangeRoundBands([0, width], .1);

		var yCosto = d3.scale.linear().range([height, 0]);

		var xCostoAxis = d3.svg.axis().scale(xCosto).orient("bottom");

		var yCostoAxis = d3.svg.axis().scale(yCosto).orient("left");

	  	xCosto.domain(dataSet.map(function(d) { return d.label; }));
	  	
	  	yCosto.domain([0, d3.max(dataSet, function(d) { 
				if( d.costo > d.finanziato )
  					return d.costo;
  				else
  					return d.finanziato;
  			})]);
	  	
	  	var lineCosto = d3.svg.line()
			.x(function(d) { return xCosto(d.label); })
			.y(function(d) { return yCosto(d.costo); });
	  	
	  	svg.append("path")
			.datum(dataSet)
			.attr("class", "line")
			.attr("d", lineCosto);
	  	
	  	// extract the x labels for the axis and scale domain
		var xLabels = dataSet.map(function (d) { return d.label; })
		/*
		svg.select(".x.axis")
			.attr("transform", "translate(0," + (height) + ")")
			.call(xCostoAxis.tickValues( xLabels.filter( 
					function(d, i) { 
						if ( i == 0 || i == 9 )
							return d;
						})))
			.selectAll("text")
			.style("text-anchor", "end")
			/*
			.attr("transform", 
					function(d) {
						return "rotate(-45)";
			})*/;
	
	  	
	    svg.append("g")
	      	.attr("class", "x axis")
	     	.attr("transform", "translate(0," + height + ")")
	      	//.call(xCostoAxis)
	    	.call( xCostoAxis.tickValues( xLabels.filter( 
	      		function(d, i) { 
	      			if ( i == 0 || i == 9 ){
	      				return d;
	      			}
	      		}
	      	) ) )
	      	.append("text")
	      	.attr("class", "label")
	      	.attr("x", width)
	      	.attr("y", -6)
	      	.style("text-anchor", "end")
	  	
	  	/*
		svg.select(".y.axis")
			.attr("transform", "translate(" + (margin.left) + ",0)")
			.call(yCostoAxis.tickFormat(decimalFormat));
		*/
		
		///FINANZIATO
		var xFinanziato = d3.scale.ordinal().rangeRoundBands([0, width], .1);

		var yFinanziato = d3.scale.linear().range([height, 0]);

		var xFinanziatoAxis = d3.svg.axis().scale(xFinanziato).orient("bottom");

		var yFinanziatoAxis = d3.svg.axis().scale(yFinanziato).orient("left");

	  	xFinanziato.domain(dataSet.map(function(d) { return d.label; }));
	  	
	  	yFinanziato.domain([0, d3.max(dataSet, function(d) { 
			if( d.costo > d.finanziato )
					return d.costo;
				else
					return d.finanziato;
			})]);
	  	
	  	var areaFinanziato = d3.svg.area()
	  	 	.x(function(d) { return xFinanziato(d.label); })
	  	 	.y0(height)
    		.y1(function(d) { return yFinanziato(d.finanziato); });
	  	
	  	svg.append("path")
			.datum(dataSet)
			.attr("class", "area")
			.attr("d", areaFinanziato);
	
		svg.select(".y.axis")
			.attr("transform", "translate(" + (margin.left) + ",0)")
			.call(yFinanziatoAxis.tickFormat(decimalFormat));
	};
	
	
	var JsonClass = ${jsonResultRiepilogo};
	var calculatedJsonClass = eval( JsonClass );
	
	var separatore = " <i style='font-size:0.5em; vertical-align:middle; padding:10px;' class='icon-circle'></i> ";
	
	drawTitoloIntestazione( '#titolo', calculatedJsonClass, pagNavigazioneLogo, separatore);
	
	drawLogoIntestazione( '#corpo_logo', calculatedJsonClass );
	
	var dataSetTestataStato = ${jsonResultDistribuzione4PieTestataStato};
	var dataSetTestataStato1 = eval( dataSetTestataStato );

	drawPieTestataStato("TestataPieStato", dataSetTestataStato1, ".pie_chart_testata_stato");
	
	legendTestata("TestataPieStatoLegenda", dataSetTestataStato1, ".pie_chart_testata_stato_legend");
	
	var dataSetTestataAnni = ${jsonResultDistribuzione4TestataAnni};
	var dataSetTestataAnni1 = eval( dataSetTestataAnni );
	
	drawBarTestataAnni("TestataBarAnni", dataSetTestataAnni1, ".bar_chart_testata_anni" );
	
	drawTrendTestataAnni("trend_svg_testata_anni", dataSetTestataAnni1, ".trend_chart_testata_anni" );
	
	d3.selectAll(".divider").each(
			function(){
				var c=d3.select(this).node().parentNode;
				d3.select(c)
					.style("font-weight","bold")
					.append("i")
					.attr("class","icon-caret-right")
					.attr("style","padding: 0 5px");
			
					d3.select(c).select("span").remove();
			});
	
</script>