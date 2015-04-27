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

	#conteiner{padding-bottom: 0.5em; display: inline-block;}
	#conteiner .infiniteCarousel{background-color: #f0f0f0;height: 16em!important;}
	#conteiner .infiniteCarousel_item{float:left; background-color: #fff!important;margin: 0 6px 0 0;height:14.5em!important;}
	#conteiner i.icon-chevron-right, #rulloLoc i.icon-chevron-left {color:#f0f0f0!important; font-size: 40px!important;}
	#conteiner div.ic_tray:first-child{margin-left: -6px!important;}
	#conteiner div.ic_tray:last-child{margin-left: -6px!important;}
	#conteiner div.effHistogram img{width:100px;height: 100px;padding-left:1em;}
	#conteiner div.titolo p{padding:1em;font-size:18pt;color:#1f4e78;}
	#conteiner .firstLoc{padding-top:1em;padding-left:1em; color:#1f4e78;font-size:16pt;}
	#conteiner div.barchart p{padding-left:1em; color:#1f4e78;}
	#conteiner .classchart rect:first-of-type {fill: #d9d9d9;}
	#conteiner .classchart rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #1f4e78;}
	#conteiner div.barcontainer span.pubblico{color:#1f4e78;}
	#conteiner div.barcontainer span.privato{color:#ababab;}
	div.div_pie_chart_testata_stato, div.div_bar_chart_testata_anni {text-align: center;}
	.legend tr{border-bottom:1px solid #1f4e78;}
	.legend tr:first-child{border-top:1px solid #1f4e78;}
	.legend{margin-bottom:76px; display:inline-block; border-collapse: collapse; border-spacing: 0px;}
	.legend td{padding:4px 5px; vertical-align:bottom;}
	.legendLabel{text-align: left; width:50px; color: #1f4e78;}
	.legendValue{text-align: right; width:50px;color: #1f4e78;}
	
	.bar_testata {fill: #1f4e78;}
	.axis {font: 10px;color:#1f4e78;}
	.axis path,	.axis line {fill: none; stroke: #000; shape-rendering: crispEdges;}
	.x.axis path {display: none;}
	
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

<div class="portlet-body" style="height: 280px">
	
	<div class="span12" id="conteiner" >
		<div id="titolo"></div>
		<div id="corpo"></div>
	</div>
	
</div>

<script>
	
	var baseColor1 = "#b2c6ff";
	var baseColor2 = "#4472fb";
	var baseColor3 = "#1f4e78";
	
	var textColor = "#1f4e78";
	
	var JsonClass = ${jsonResultRiepilogo};
	var calculatedJsonClass=eval( JsonClass );

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
	
	var containerWClass = d3.select("#conteiner")[0][0].clientWidth;
	var singleLiWClass = (containerWClass-60)/3;
	var singleElMwClass = singleLiWClass/10;

	d3.selectAll("#titolo")
	.selectAll("div")
	.data(calculatedJsonClass)
	.enter()
	.append("div")
	.attr("class","titolo")
	.append("p")
	.text(function(d){
		var retval = d.desNatura;
		if( d.codArea != "-1" ){retval = d.desArea;	} 
		if( d.codSottoSettore != "-1" ){retval = retval + " // " + d.desSottoSettore;} 
		if( d.codCategoriaIntervento != "-1" ){retval = retval + " // " + d.desCategoriaIntervento;} 
		return retval;
	});
	
	d3.selectAll("#corpo")
	.selectAll("div")
	.data(calculatedJsonClass)
	.enter()
	.append("div")
	.style("width",singleLiWClass+"px")
	.style("display","inline")
	.style("list-style","none")
	.style("float","left")
	.append("div")
	.attr("class","classificazione");
	
	d3.selectAll("#corpo")
	.selectAll("div.classificazione")
	.append("div")
	.attr("class", "left effHistogram")
	.style("width", (singleElMwClass*4.3)+"px")
	.append("img")
	.attr("src",function(d){
		var areaCorrente=d.desArea.split(/[\s,]+/);
	 	var firstDesc=areaCorrente[0].toLowerCase();
	 	return "${imgFolder}/icona-"+firstDesc+".svg";
	});

	d3.selectAll("#corpo")
	.selectAll("div.classificazione")
	.append("div")
	.attr("class","right")
	.style("width",(singleElMwClass*5.5)+"px")
    .html(function(d){
    	return "<p class=\'firstLoc\'>"+nFormatter(d.numeProgetti)+"<br/><br/>"+nFormatter(d.impoCostoProgetti)+"</p>";
    });

	d3.selectAll("#corpo")
	.selectAll("div.classificazione")
	.append("div")
	.attr("class","barchart")
	.style("padding-top","8em")
	.append("div")
	.attr("class","left")
	.style("width",(singleElMwClass*4.3)+"px")
	.html("<p><small>Finanziamenti pubblici</small></p>");
	
	d3.selectAll("#corpo")
	.selectAll("div.barchart")
	.append("div")
	.attr("class","right barcontainer")
	.style("width",(singleElMwClass*5.5)+"px")
	.append("svg")
	.attr("class","classchart")
	.attr("width",(singleElMwClass*5))
	.attr("height",20);
	
	d3.selectAll(".classchart")
	.each(function(d){
		var range=[d.impoCostoProgetti, d.impoImportoFinanziato];
		var x=d3.scale.linear().domain([0, d3.max(range)]).range([0, (singleElMwClass*5)]);
		d3.select(this)
		.selectAll("rect")
		.data(range)
		.enter().append("rect")
	 	.attr("width", x)
	 	.attr("height", 20);
	 	
	});
	
	d3.selectAll(".barcontainer")
	.append("div")
	.style("width",(singleElMwClass*5)+"px")
	.html("<span class=\'left pubblico\'><small>Pubblico</small></span><span class=\'right privato\'><small>Privato</small></span>");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	var minDataStato = 0;
	var maxDataStato = ${ recordCountStato };
	var midDataStato = maxDataStato / 2;
	
	var dataSetTestataStato = ${jsonResultDistribuzione4PieTestataStato};
	var dataSetTestataStato1 = eval( dataSetTestataStato );
	
	function drawPieTestataStato ( pieName, dataSet, selectString, margin, outerRadius, innerRadius, sortArcs ) {
		
		// pieName => A unique drawing identifier that has no spaces, no "." and no "#" characters.
		// dataSet => Input Data for the chart, itself.
		// selectString => String that allows you to pass in
		//           a D3 select string.
		// margin => Integer margin offset value.
		// outerRadius => Integer outer radius value.
		// innerRadius => Integer inner radius value.
		// sortArcs => Controls sorting of Arcs by value.
		//              0 = No Sort.  Maintain original order.
		//              1 = Sort by arc value size.
				
		// Color Scale Handling...
		var colorScale = d3.scale.linear().domain([minDataStato, midDataStato, maxDataStato]).range([baseColor1,baseColor2,baseColor3]);
		
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
		var canvas = d3.select(selectString).append("svg:svg") //create the SVG element
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
				})
		.style("fill", "White")
		.style("font", "normal 18px Arial");
	
		// Computes the angle of an arc, converting from radians to degrees.
		function angle(d) {
			var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
			return a > 90 ? a - 180 : a;
		}
		
	};
	
	// function to handle legend.
    function legend(pieName, dataSet, selectString){
        
     	// Color Scale Handling...
		var colorScale = d3.scale.linear().domain([minDataStato, midDataStato, maxDataStato]).range([baseColor1,baseColor2,baseColor3]);
        
     	// create table for legend.
        var legend = d3.select(selectString).append("table").attr('class','legend');
        
        // create one row per segment.
        var tr = legend.append("tbody").selectAll("tr").data(dataSet).enter().append("tr");
            
        // create the first column for each segment.
        tr.append("td").append("svg")
        .attr("width", '16').attr("height", '16').append("rect")
        .attr("width", '16').attr("height", '16')
        .attr("fill", function(d, i) { return colorScale(i); });
            
        // create the second column for each segment.
        tr.append("td").attr("class",'legendLabel').text(function(d){ return d.label; });

    	// create the third column for each segment.
        tr.append("td").attr("class",'legendValue').text( function(d){ return d3.format(",")(d.value); } );
    }
	
	d3.selectAll("#corpo")
	.append("div")
	.style("width",singleLiWClass/2+"px")
	.style("display","inline")
	.style("list-style","none")
	.style("float","left")
	.attr("class","div_pie_chart_testata_stato pie_chart_testata_stato")
	.attr("id","pie_chart_testata_stato");
	
	var margin = 0;
	var outerRadius = 70;
	var innerRadius = 50;
	var sortArcs = 0;
	
	drawPieTestataStato("TestataPieStato", dataSetTestataStato1, ".pie_chart_testata_stato", margin, outerRadius, innerRadius, sortArcs);
	
	d3.selectAll("#corpo")
	.append("div")
	.style("width",singleLiWClass/2+"px")
	.style("display","inline")
	.style("list-style","none")
	.style("float","left")
	.attr("class","div_pie_chart_testata_stato pie_chart_testata_stato_legend")
	.attr("id","pie_chart_testata_stato_legend");
	
	legend("TestataPieStatoLegenda", dataSetTestataStato1, ".pie_chart_testata_stato_legend");
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	var minDataStato = 0;
	var maxDataStato = ${ recordCountAnni };
	var midDataStato = maxDataStato / 2;
	
	var dataSetTestataAnni = ${jsonResultDistribuzione4TestataAnni};
	var dataSetTestataAnni1 = eval( dataSetTestataAnni );
	
	var startYear = ${startYear};
	var endYear = ${endYear};
	
	d3.selectAll("#corpo")
	.append("div")
	.style("width",singleLiWClass+"px")
	.style("display","inline")
	.style("list-style","none")
	.style("float","left")
	.attr("class","div_bar_chart_testata_anni bar_chart_testata_anni")
	.attr("id","bar_chart_testata_anni");
	
	function drawBarTestataAnni ( barName, dataSet, selectString, totWidth , totHeight ) {
		
		var margin = {top: 5, right: 0, bottom: 30, left: 50},
	    width = totWidth - margin.left - margin.right,
	    height = totHeight - margin.top - margin.bottom;

		var x = d3.scale.ordinal()
	  	.rangeRoundBands([0, width], .1);

		var y = d3.scale.linear()
	  	.range([height, 0]);

		var xAxis = d3.svg.axis()
	  	.scale(x)
	  	.orient("bottom");

		var yAxis = d3.svg.axis()
	  	.scale(y)
	  	.orient("left");
	  	//.ticks(10, "%");

		var svg = d3.select(selectString).append("svg")
	  	.attr("width", width + margin.left + margin.right)
	  	.attr("height", height + margin.top + margin.bottom)
	  	.append("g")
	  	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	  	x.domain(dataSet.map(function(d) { return d.label; }));
	  	y.domain([0, d3.max(dataSet, function(d) { return d.volume; })]);

	  	svg.append("g")
	    .attr("class", "x axis")
	    .attr("transform", "translate(0," + height + ")")
	    .call(xAxis)
	    
	    ;
/*
	  	svg.append("g")
	    .attr("class", "y axis")
	    .call(yAxis)
	    .append("text")
	    .attr("transform", "rotate(-90)")
	    .attr("y", 6)
	    .attr("dy", ".71em")
	    .style("text-anchor", "end")
	    .text("progetti");
*/
		svg.selectAll(".bar_testata")
	  	.data(dataSet)
	  	.enter()
	  	.append("rect")
	  	.attr("class", "bar_testata")
	    .attr("x", function(d) { return x(d.label); })
	    .attr("width", x.rangeBand())
	    .attr("y", function(d) { return y(d.volume); })
	    .attr("height", function(d) { return height - y(d.volume); })
	    ;

	};
	
	var heightBarAnni = 160;
	
	drawBarTestataAnni("TestataBarAnni", dataSetTestataAnni1, ".bar_chart_testata_anni", singleLiWClass, heightBarAnni );
	
</script>