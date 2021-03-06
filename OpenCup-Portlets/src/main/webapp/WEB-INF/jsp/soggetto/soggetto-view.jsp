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


<c:if test="${ configSoggetto.mostraPulsanti }">
	<div>	
		<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center; background: #f0f0f0; padding-top: 3px;">
			<div class="offset3 span2">
				<div class="btn-carica-distribuzione volume-color volume-color-soggetto sel-type-btn sel-type-btn-soggetto" data-distribuzione="VOLUME">
					<aui:a href="#" onClick="return false" cssClass="block">
						Progetti
					</aui:a>
				</div>
				<c:if test='${pattern eq "VOLUME"}'>
					<div class="arrow-down-volume arrow-down-volume-soggetto"></div>
				</c:if>
			</div>
			<div class="span2">	
				<div class="btn-carica-distribuzione costo-color costo-color-soggetto sel-type-btn sel-type-btn-soggetto" data-distribuzione="COSTO">
					<aui:a href="#" onClick="return false" cssClass="block">
						Costo
					</aui:a>
				</div>
				<c:if test='${pattern eq "COSTO"}'>
					<div class="arrow-down-costo arrow-down-costo-soggetto"></div>
				</c:if>
			</div>
			<div class="span2">	
				<div class="btn-carica-distribuzione importo-color importo-color-soggetto sel-type-btn sel-type-btn-soggetto" data-distribuzione="IMPORTO">
					<aui:a href="#" onClick="return false" cssClass="block">
						Finanziamento
					</aui:a>
				</div>
				<c:if test='${pattern eq "IMPORTO"}'>
					<div class="arrow-down-importo arrow-down-importo-soggetto"></div>
				</c:if>
			</div>
			<div class="clear"></div>
		
		</div>	
	</div>

</c:if>

<c:if test="${ configSoggetto.portletPrincipale }">
	<div class="stripe">
</c:if>
	
	<c:choose>
		<c:when test="${ configSoggetto.portletPrincipale }">
			<div id="container-soggetto-chart" class="container-soggetto-chart">
		</c:when>
		<c:otherwise>
			<div id="container-soggetto-chart" class="container-soggetto-chart" style="height: 500px;">
		</c:otherwise>
	</c:choose>
		
		<div class="row">
			<div class="titoloSoggetto" id="titoloSoggetto">
				Soggetto
			</div>
		</div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- GRAFICI --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<a id="soggetto-portlet"></a>
		
		<c:choose>
			<c:when test="${ configSoggetto.portletPrincipale }">
				<div class="div_soggetto_1 div_grafico_padding">
					<div class="row chart-div">
						<div class="span6 div_soggetto chart soggetto_1">
						</div>
						<div class="span6 div_soggetto chart soggetto_2">
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="div_soggetto_1 div_grafico_padding">
					<div class="row chart-div" style="min-height: 250px;">
						<div class="span8 offset2 div_soggetto chart soggetto_1" style="min-height: 250px;">
						</div>
					</div>
					<div class="row chart-div">
						<div class="span12 div_soggetto chart soggetto_2"></div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		

	</div>
	
	<portlet:actionURL var="urlActionSoggettoVar">
	   	<portlet:param name="action" value="cambiaAggregazione"></portlet:param>
	</portlet:actionURL>
	
	<form 
		action="${urlActionSoggettoVar}" 
		method="post" 
		name="naviga-form-soggetto" 
		class="naviga-form-soggetto"
		id="naviga-form-soggetto"
		style="display: none;">
	
			<aui:input cssClass="pattern-soggetto" type="hidden" name="pattern" value="${pattern}" id="pattern-soggetto" />
			<aui:input type="hidden" bean="navigaAggregata" name="pagAggregata" value="${navigaAggregata.pagAggregata}" id="pagAggregata" />
	
			<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" id="idNatura" />
			<aui:input type="hidden" bean="navigaAggregata" name="idAreaSoggetto" value="${navigaAggregata.idAreaSoggetto}" id="idAreaSoggetto" />
			<aui:input type="hidden" bean="navigaAggregata" name="idCategoriaSoggetto" value="${navigaAggregata.idCategoriaSoggetto}" id="idCategoriaSoggetto" />
			<aui:input type="hidden" bean="navigaAggregata" name="idSottoCategoriaSoggetto" value="${navigaAggregata.idSottoCategoriaSoggetto}" id="idSottoCategoriaSoggetto" />
			
	</form>
	
<c:if test="${ configSoggetto.portletPrincipale }">
	</div>
</c:if>

<script type="text/javascript">
	var JsonClass4Soggetto = ${aggregati4Soggetto};
	var calculatedJsonClass4Soggetto = eval( JsonClass4Soggetto );
	
	var tipoAggregazioneSoggetto = '${pattern}';
	var selezionabileSoggetto = ! ${configSoggetto.selezionabile};
	
	//var segments = [ "#b2c6ff", "#9eb5fc", "#90abfb", "#81a0fa", "#7597fb", "#678dfb", "#5a84fa", "#507cfb", "#4472fb", "#3869f9", "#2f62f2", "#275aea", "#2254e2", "#1b4bd8", "#1745ce", "#1240c3", "#0d39b8", "#0932a3" ];
	var baseColor1 = "#b2c6ff";
	var baseColor2 = "#4472fb";
	var baseColor3 = "#0932a3";
	
	var textColor = "#1f4e78";
	var fillColor = "Maroon";
	var coloreMisura = "#1f4e78";
	
	if (tipoAggregazioneSoggetto == "VOLUME"){
		baseColor1 = "#ffdbaa";
		baseColor2 = "#ffb551";
		baseColor3 = "#f08c00";
		fillColor = "#d27900";
		coloreMisura = "#f08c00";
	}else
	if (tipoAggregazioneSoggetto == "COSTO"){
		baseColor1 = "#69d876";
		baseColor2 = "#58b663";
		baseColor3 = "#499652";
		fillColor = "#3e7d46";
		coloreMisura = "#499652";
	}else
	if (tipoAggregazioneSoggetto == "IMPORTO"){
		baseColor1 = "#c1ffc1";
		baseColor2 = "#48ff48";
		baseColor3 = "#7ade87";
		fillColor = "#005500";
		coloreMisura = "#7ade87";
	}
	
	d3.select("#titoloSoggetto").style("color", coloreMisura);
	d3.select("#titoloSoggetto").style("text-align", "left");
	
	var bordoSoggetto = coloreMisura;
	var portletSecondariaDXSoggetto = "${ configSoggetto.portletSecondariaDX }";
	if(portletSecondariaDXSoggetto=="true"){
		bordoSoggetto = "#f0f0f0";
	}
	d3.select("#container-soggetto-chart").style("border-left", "5px solid " + bordoSoggetto);
	
	/*
	var minDataSoggetto = d3.min(calculatedJsonClass4Soggetto, function(d) { return d.volume; });
	var midDataSoggetto = d3.mean(calculatedJsonClass4Soggetto, function(d) { return d.volume; });
	var maxDataSoggetto = d3.max(calculatedJsonClass4Soggetto, function(d) { return d.volume; });
	*/
	
	var minDataSoggetto = 0;
	var maxDataSoggetto = ${ recordCountSoggetto };
	var midDataSoggetto = maxDataSoggetto / 2;
	
	// scala colori in base a valori calcolati
	var colorSoggetto = d3.scale.linear().domain([minDataSoggetto, midDataSoggetto, maxDataSoggetto]).range([baseColor1, baseColor2, baseColor3]);
	
	String.prototype.trunc =
	     function(n,useWordBoundary){
	         var toLong = this.length>n,
	         s_ = toLong ? this.substr(0,n-1) : this;
	         s_ = useWordBoundary && toLong ? s_.substr(0,s_.lastIndexOf(' ')) : s_;
	         //return  toLong ? s_ + '&hellip;' : s_;
	         return  toLong ? s_ + '...' : s_;
	      };
	 
 
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
			return (num / 1000000000).formattaNumerico(0, ',', '.') + ' Mld';
		}
		if (num >= 1000000) {
			return (num / 1000000).formattaNumerico(0, ',', '.') + ' Mil';
		}
		
		return num.formattaNumerico(0, ',', '.');
		
	}

	synchronizedMouseOverSoggetto = function(info) {
		var obj = d3.select(this);

		if (selezionabileSoggetto) {

			obj.style('cursor', 'default');

		} else {

			var indexValue = obj.attr("index_value");

			obj.style('cursor', 'pointer');

			var histogramSoggetto = d3
					.selectAll(".historgam-HistogramSoggetto-" + indexValue);
			histogramSoggetto.style("fill", fillColor);

			var circleSoggetto = d3.selectAll(".legend-circle-LegendSoggetto-"
					+ indexValue);
			circleSoggetto.style("fill", fillColor);

			var textSoggetto = d3.selectAll(".legend-text-LegendSoggetto-"
					+ indexValue);
			textSoggetto.style("fill", fillColor);

			var numberSoggetto = d3.selectAll(".legend-number-LegendSoggetto-"
					+ indexValue);
			numberSoggetto.style("fill", fillColor);
		}

	}

	synchronizedMouseOutSoggetto = function(info) {
		var obj = d3.select(this);
		var indexValue = obj.attr("index_value");
		var colorValue = obj.attr("color_value");

		var histogramSoggetto = d3.selectAll(".historgam-HistogramSoggetto-"
				+ indexValue);
		histogramSoggetto.style("fill", colorValue);

		var circleSoggetto = d3.selectAll(".legend-circle-LegendSoggetto-"
				+ indexValue);
		circleSoggetto.style("fill", colorValue);

		var textSoggetto = d3.selectAll(".legend-text-LegendSoggetto-"
				+ indexValue);
		textSoggetto.style("fill", textColor);

		var numberSoggetto = d3.selectAll(".legend-number-LegendSoggetto-"
				+ indexValue);
		numberSoggetto.style("fill", textColor);
	}

	function drawHistogramSoggetto(elementName, dataSet, selectString) {

		var totWidth = d3.select(selectString).node().getBoundingClientRect().width;

		var margin = {
			top : 15,
			right : 25,
			bottom : 15,
			left : 25
		};

		var width = totWidth - margin.left - margin.right;

		var height = (totWidth - margin.top - margin.bottom) / 2;

		var x = d3.scale.ordinal().rangeRoundBands([ 0, width ], .1);

		var y = d3.scale.linear().range([ height, 0 ]);

		//var xAxis = d3.svg.axis().scale(x).orient("bottom");
		//var yAxis = d3.svg.axis().scale(y).orient("left");

		var svg = d3.select(selectString).append("svg").attr("width",
				width + margin.left + margin.right).attr("height",
				height + margin.top + margin.bottom).append("g").attr(
				"transform",
				"translate(" + margin.left + "," + margin.top + ")");

		x.domain(dataSet.map(function(d) {
			return d.label;
		}));

		var maxVolume = d3.max(dataSet, function(d) {
			return d.volume;
		});
		y.domain([ 0, maxVolume ]);

		/*
		svg.append("g")
		.attr("class", "x axis")
		.attr("transform", "translate(0," + height + ")")
		.call(xAxis);
		
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

		var columnWidth = (x.rangeBand() > 100) ? 100 : x.rangeBand();
		var columnTraslate = x.rangeBand() - columnWidth;

		svg.selectAll(".bar_soggetto").data(dataSet).enter().append("rect")
				.attr("fill", function(d, i) {
					return colorSoggetto(i);
				}).attr(
						"class",
						function(d, i) {
							retval = "bar_soggetto historgam-" + elementName
									+ "-index-" + i;
							if (!selezionabileSoggetto) {
								retval = retval + " link-url-naviga-soggetto";
							}
							return retval;
						}).attr("color_value", function(d, i) {
					return colorSoggetto(i);
				}) // Bar fill color...
				.attr("index_value", function(d, i) {
					return "index-" + i;
				}).attr("data_linkURL", function(d, i) {
					return calculatedJsonClass4Soggetto[i].linkURL
				}).attr("x", function(d, i) {
					return x(d.label) + columnTraslate;
				}).attr("width", columnWidth).attr("y", function(d) {
					return y(d.volume);
				}).attr("height", function(d) {
					return height - y(d.volume);
				}).on('mouseover', synchronizedMouseOverSoggetto).on(
						"mouseout", synchronizedMouseOutSoggetto);

	};
	/*
	function drawLegendSoggetto ( elementName, dataSet, selectString ) {
		
		// create table for legend.
	    var legend = d3.select(selectString)
	    .append("table")
	    .attr('class', 'legend');
	    
	    // create one row per segment.
	    var tr = legend
	    .append("tbody")
	    .selectAll("tr")
	    .data(dataSet)
	    .enter()
	    .append("tr");
	        
	    // create the first column for each segment.
	    tr
	    .append("td")
	    .append("svg")
	    .attr("width", '16')
	    .attr("height", '16')
	    .append("rect")
	    .attr("width", '16')
	    .attr("height", '16')
	    .attr("fill", function(d, i) { return colorSoggetto(i); });
	        
	    // create the second column for each segment.
	    tr
	    .append("td")
	    .attr("class", 'legendLabel')
	    .text(function(d){ return (d.label).trunc(36, true); });
		
	}
	 */
	function drawLegendSoggetto(elementName, dataSet, selectString) {

		var widthTotal = 50;
		var heightLegend = 25;
		var gapBetweenGroups = 25;
		var margin = {
			top : 0,
			right : 15,
			bottom : 15,
			left : 15
		};

		var totWidth = d3.select(selectString).node().getBoundingClientRect().width;
		var width = totWidth - margin.left - margin.right;

		var svg = d3.select(selectString).append("svg").attr("width",
				width + margin.left + margin.right).attr("height",
				gapBetweenGroups + (dataSet.length * heightLegend)).append("g")
				.attr("transform",
						"translate(" + margin.left + "," + margin.top + ")");

		// Plot the bullet circles...
		/*
		svg
		.selectAll("circle")
		.data(dataSet)
		.enter()
		.append("svg:circle") // Append circle elements
		.attr("r", 5)
		.attr("cx", widthTotal)
		.attr("cy", function(d, i) { 
			return gapBetweenGroups + (heightLegend * i);
		})
		.attr("stroke-width", ".5")
		.style("fill", function(d, i) { return colorSoggetto(i); }) // Bullet fill color
		.attr("color_value", function(d, i) { return colorSoggetto(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-"+i; })
		.attr("data_linkURL", function(d, i) { return calculatedJsonClass4Soggetto[i].linkURL })
		.attr("class", function(d, i){
			retval = "legend-circle-"+elementName+"-index-"+i;
			if(!selezionabileSoggetto){
				retval = retval + " link-url-naviga-soggetto";
			}
			return retval;
		})
		.on('mouseover', synchronizedMouseOverSoggetto)
		.on("mouseout", synchronizedMouseOutSoggetto);
		 */

		svg.selectAll("rect").data(dataSet).enter().append("rect").attr(
				"width", "16").attr("height", "16").attr("x", widthTotal - 10)
				.attr("y", function(d, i) {
					//return ( gapBetweenGroups + (i * (heightTotal / dataSet.length)) + ((heightTotal / dataSet.length)/2) ) ; 
					return gapBetweenGroups + (heightLegend * i) - 10;
				}).style("fill", function(d, i) {
					return colorSoggetto(i);
				}) // Bullet fill color
				.attr("color_value", function(d, i) {
					return colorSoggetto(i);
				}) // Bar fill color...
				.attr("index_value", function(d, i) {
					return "index-" + i;
				}).attr("data_linkURL", function(d, i) {
					return calculatedJsonClass4Soggetto[i].linkURL
				}).attr("class", function(d, i) {
					retval = "legend-circle-" + elementName + "-index-" + i;
					if (!selezionabileSoggetto) {
						retval = retval + " link-url-naviga-soggetto";
					}
					return retval;
				}).on('mouseover', synchronizedMouseOverSoggetto).on(
						"mouseout", synchronizedMouseOutSoggetto);

		// Create text at right
		svg.selectAll(".testo").data(dataSet) // Instruct to bind dataSet to text elements
		.enter().append("text").attr("x", widthTotal + 20).attr("y",
				function(d, i) {
					return gapBetweenGroups + (heightLegend * i);
				}).attr("dx", 0).attr("dy", "5px") // Controls padding to place text in alignment with bullets
		.text(function(d) {
			return (d.label).trunc(36, true);
		}).attr("color_value", function(d, i) {
			return colorSoggetto(i);
		}) // Bar fill color...
		.attr("index_value", function(d, i) {
			return "index-" + i;
		}).attr("data_linkURL", function(d, i) {
			return calculatedJsonClass4Soggetto[i].linkURL
		}).attr("class", function(d, i) {
			retval = "label testo legend-text-" + elementName + "-index-" + i;
			if (!selezionabileSoggetto) {
				retval = retval + " link-url-naviga-soggetto";
			}
			return retval;
		}).style("fill", textColor).style("font-size", "1.8em").on('mouseover',
				synchronizedMouseOverSoggetto).on("mouseout",
				synchronizedMouseOutSoggetto).append("title").text(function(d) {
			return d.label;
		})

		// Create number at right
		svg.selectAll(".valore").data(dataSet) // Instruct to bind dataSet to text elements
		.enter().append("text")
		.attr("x", 
				function(d, i) { 
					var delta = 510 - (nFormatter(d.volume).length * 10);
					if( (nFormatter(d.volume).length < 4 ) ){
						delta = delta - 5;
					}
					return delta; 
			})
		//.attr("x", widthTotal + 393)
		.attr("y",
				function(d, i) {
					return gapBetweenGroups + (heightLegend * i);
				}).attr("dx", 0).attr("dy", "5px") // Controls padding to place text in alignment with bullets
		.text(function(d) {
			return nFormatter(d.volume);
		}).attr("color_value", function(d, i) {
			return colorSoggetto(i);
		}) // Bar fill color...
		.attr("index_value", function(d, i) {
			return "index-" + i;
		}).attr("data_linkURL", function(d, i) {
			return calculatedJsonClass4Soggetto[i].linkURL
		}).attr(
				"class",
				function(d, i) {
					retval = "label valore legend-number-" + elementName + "-index-" + i;
					if (!selezionabileSoggetto) {
						retval = retval + " link-url-naviga-soggetto";
					}
					return retval;
				}).style("fill", textColor).style("font-size", "1.8em").on(
				'mouseover', synchronizedMouseOverSoggetto).on("mouseout",
				synchronizedMouseOutSoggetto);

	}

	//var heightHistogramSoggetti = 250;

	drawHistogramSoggetto("HistogramSoggetto", calculatedJsonClass4Soggetto, ".soggetto_1");

	drawLegendSoggetto("LegendSoggetto", calculatedJsonClass4Soggetto, ".soggetto_2");

	AUI().use(
		'get',
		function(A) {
			A.Get.script('${jsFolder}/jquery-1.11.0.min.js',
				{onSuccess : function() {
					A.Get.script('${jsFolder}/bootstrap.min.js',
						{onSuccess : function() {
							$(".volume-color-soggetto").mouseover(
									function() {
										$(".arrow-down-volume-soggetto").css('border-top', '10px solid #d27900');
									});
							$(".volume-color-soggetto").mouseout(
									function() {
										$(".arrow-down-volume-soggetto").css('border-top', '10px solid #f08c00');
									});

							$(".costo-color-soggetto").mouseover(
									function() {
										$(".arrow-down-costo-soggetto").css('border-top', '10px solid #2c5831');
							});
								
							$(".costo-color-soggetto").mouseout(
									function() {
										$(".arrow-down-costo-soggetto").css('border-top', '10px solid #499652');
							});

							$(".importo-color-soggetto").mouseover(
									function() {
										$(".arrow-down-importo-soggetto").css('border-top', '10px solid #005500');
							});
																		
							$(".importo-color-soggetto").mouseout(
									function() {
										$(".arrow-down-importo-soggetto").css('border-top', '10px solid #7ade87');
							});

							$(".sel-type-btn-soggetto").click(
									function() {
										var arc = d3.select(this);
										var distribuzione = arc.attr("data-distribuzione");
										$(".pattern-soggetto").val(distribuzione);
										$(".naviga-form-soggetto").submit();
							});

							$(".link-url-naviga-soggetto").click(
									function() {
										if (!selezionabileSoggetto) {
											var obj = d3.select(this);
											var data_linkURL = obj.attr("data_linkURL");
											$(".naviga-form-soggetto").attr("action", data_linkURL);
											$(".naviga-form-soggetto").submit();
										}
							});
						}
					});
				}
		});
	});
</script>
	

