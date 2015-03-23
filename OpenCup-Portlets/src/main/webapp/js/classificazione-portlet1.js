
var svg = null;

AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated',
		'datatype-number',
		'aui-toggler',
		function( A ) {
			
			var heightNavigazioneDivContainer = d3.select('.navigazione-div-container').node().getBoundingClientRect().height;
			d3.select('.affina-ricerca-div-spazio').attr("style", "height: " + (heightNavigazioneDivContainer + 10) + "px");
			
			var myFormEmpty = A.one(".filtri-form");
			var myFormAffinaRicerca = A.one(".affina-ricerca-form");
			
			A.one('.btn-filtra').on(
				    'click',
				    function(event) {
				    	// submit
				    	myFormAffinaRicerca.submit();
					});
			
			A.one('.btn-rimuovi-filtri').on(
				    'click',
				    function(event) {
				    	// pulisco i campi
				    	A.one('.regione').val(-1);
				    	A.one('.provincia').val(-1);
				    	A.one('.area-geografica').val(-1);
				    	A.one('.regione').val(-1);
				    	A.one('.provincia').val(-1);
				    	A.one('.area-soggetto').val(-1);
				    	A.one('.categoria-soggetto').val(-1);
				    	A.one('.sotto-categoria-soggetto').val(-1);
				    	A.one('.anno').val(-1);
				    	A.one('.tipologia').val(-1);
				    	A.one('.statoprogetto').val(-1);

				    	
				    	// submit
				    	myFormAffinaRicerca.submit();
					});

			
			var toggler = new A.TogglerDelegate({
				        animated: true,
				        closeAllOnExpand: true,
				        container: '#my-toggler-affina-ricerca-classificazione',
				        content: '.content',
				        expanded: false,
				        header: '.header',
				        transition: {
				          duration: 0.2,
				          easing: 'cubic-bezier(0, 0.1, 0, 1)'
				        }
				      });

			A.all('.pulisci').each(
				function() {
					this.on('click', function(){
						for (i = 0; i < 5; i++) { 
							var dataParent = this.getAttribute("data-parent"+i);
							if( dataParent ){
								var target = "#"+namespaceRicerca4js+dataParent;
						    	var elemento = A.one(target);
								if( i==1 ){
									elemento.val(0);
								}else{
									elemento.val(-1);
								}
							}
						}
				    	submitForm();
					});
				});

			function submitForm(){
				//Rimuovo le parentesi quadre dalla lista degli id degli anni
				var target = "#"+namespaceRicerca4js+"idAnnoAggregatos";
				var elemento = A.one(target);
				var nuovoVal = elemento.val().replace("[","").replace("]","");
				elemento.val(nuovoVal);
				// submit
				myFormEmpty.submit();
			}	

			A.all('.link-url-naviga-dettaglio').each(
					function() {
						this.on('click', function(){
							var linkURL = this.getAttribute("data-url");
							myFormEmpty.setAttribute('action', linkURL);
							submitForm();
						});
						
						this.on('mouseover', function(){
							
							var indexValue = this.getAttribute("data-id");
							var arcSelector = ".pie-Pie1-arc-index-"+indexValue;
							var selectedArc = d3.select(arcSelector);
							selectedArc.style("fill", "Maroon");
							
							console.log(selectedArc);
							
							//Show the tooltip
//							var tooltip = d3.select("#tooltip-classificazione-portlet1");
//							tooltip.classed("hidden", false); 
//							tooltip.transition().duration(500).style("opacity", 100);
//							
//							tooltip.select("#label-tooltip-classificazione-portlet1").text(this.val());
							
//							tooltip.style("left", + selectedArc.getAttribute( 'x1' ) + "px");
//							tooltip.style("top", + selectedArc.getAttribute( 'y1' ) + "px");
							
						});
						
						this.on('mouseout', function(){
							
							var indexValue = this.getAttribute("data-id");
							var colorValue = this.getAttribute("color_value");
							var arcSelector = ".pie-Pie1-arc-index-"+indexValue;
							var selectedArc = d3.select(arcSelector);
							selectedArc.style("fill", colorValue);
							
							//Hide the tooltip
//							var tooltip = d3.select("#tooltip-classificazione-portlet1");
//						    tooltip.transition().duration(500).style("opacity", 0);   
//						    tooltip.classed("hidden", true);
//						    
						});
						
					});

			var pieClassificazione = null;
			var aggregate = null;
			var tipoAggregazione = null;
			
			A.all('.classificazione-sel-btn').each(
				function() {
					this.on('click', function(){
						var misura = this.getAttribute("data-classificazione");
						loadPie(misura, this);
					});
				});
			
			var el_div_pie_chart = A.one('#pieChartClassificazione');

			if(el_div_pie_chart) {
				loadPie("VOLUME", A.one('.classificazione-sel-btn'));
			}
   			
			function loadPie(pattern, button){
				
				A.one('#pieChartClassificazione').empty();
				A.all('.classificazione-sel-btn').replaceClass('active', '');
				button.replaceClass('btn-default', 'btn-default active');
				tipoAggregazione = pattern;
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(namespace);
				resourceURL.setResourceId("aggregati4Pie");
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setCopyCurrentRenderParameters(true);
				pieClassificazione = null;
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				aggregate = this.get('responseData');
	           				//console.log(aggregate);
	           			    if(aggregate.aggregati4Pie!=null && aggregate.aggregati4Pie!=""){
	           			    	//drawPie();
	           			    	drawPie("Pie1", aggregate.aggregati4Pie, "#pie_chart_1 .chart", "segments", 10, 155, 5, 0);
	           			    }else{
	           			  	        A.one(".pieChartClassificazioneEmpty").setStyles({
	           			    		display: 'block'
	           			    	});
	           			    	A.one(".pieChartClassificazioneToolBar").setStyles({
	           			    		display: 'none'
	           			    	});
	           			    }
	           			   
	           			},
	           			failure: function (e) {
	           				var message = this.get('responseData');
	           				alert("Ajax Error : "+message);	
	           			}
	       			}
	   			});
			}
			
			function formattaImporto(valore){
				return A.Number.format(valore,{
					prefix: "",
			        thousandsSeparator: ".",
			        decimalSeparator: ",",
			        decimalPlaces: 3,
			        suffix: ""
				});
			}
						
			function formattaIntero(valore){
				return A.Number.format(valore,{
					prefix: "",
			        thousandsSeparator: ".",
			        decimalSeparator: ",",
			        decimalPlaces: 0,
			        suffix: ""
				});
			}
			
//			function drawPie(){
//				pieClassificazione = new d3pie("pieChartClassificazione", {
//					"header": {
//						"title": {
//							"text": "Distribuzione percentuale",
//							"fontSize": 22,
//							"font": "helvetica"
//						},
//						"subtitle": {
//							"color": "#999999",
//							"fontSize": 12,
//							"font": "open sans"
//						},
//						"titleSubtitlePadding": 5
//					},
//					"footer": {
//						"color": "#999999",
//						"fontSize": 10,
//						"font": "open sans",
//						"location": "bottom-left"
//					},
//
//					"data": {
//						"sortOrder": "value-desc",
//						"content": aggregate.aggregati4Pie,
//						"color": "#FF0000"
//					},
//					misc: {
//						colors: {
//							segments: [
//							           "#b2c6ff", "#9eb5fc", "#90abfb", "#81a0fa", "#7597fb", "#678dfb", "#5a84fa", "#507cfb", "#4472fb", "#3869f9", "#2f62f2", "#275aea", "#2254e2", "#1b4bd8", "#1745ce", "#1240c3", "#0d39b8", "#0932a3"
//							]
//						}
//					},
//					"labels": {
//						"outer": {
//							"format": "percentage"
//						},
//						"inner": {
//							"format": "none",
//							"hideWhenLessThanPercentage": 3
//						},
//						"mainLabel": {
//							"fontSize": 11
//						},
//						"percentage": {
//							"color": "#333333",
//							"decimalPlaces": 0
//						},
//						"value": {
//							"color": "#adadad",
//							"fontSize": 11
//						},
//						"lines": {
//							"enabled": true,
//							"style": "straight"
//						}
//					},
//					"tooltips": {
//						"enabled": false,
//						"type": "placeholder",
//						"string": "{label} Costo:{value}&euro; {percentage}%",
//						"styles": {
//							"fadeInSpeed": 390,
//							"color": "#ffffff",
//							"borderRadius": 10,
//							"fontSize": 12,
//							"padding": 10
//						}
//					},
//					"effects": {
//						"pullOutSegmentOnClick": {
//							"effect": "linear",
//							"speed": 400,
//							"size": 8
//						}
//					},
//					"size": {
//						"canvasHeight": 450,
//						"canvasWidth": 450
//					},
//					"callbacks": {
//						onMouseoverSegment: function(info) {							
//							//Update the tooltip position and value
//							var tooltip = d3.select("#tooltip-classificazione-portlet1");
//							tooltip.select("#label-tooltip-classificazione-portlet1").text(info.data.label);
//							tooltip.select("#labelvalue-tooltip-classificazione-portlet1").text(tipoAggregazione);
//							if( tipoAggregazione == "VOLUME" ){
//								tooltip.select("#value-tooltip-classificazione-portlet1").text(formattaIntero(info.data.value));
//								tooltip.select("#umvalue-tooltip-classificazione-portlet1").classed("hidden", true);
//							}else{
//								tooltip.select("#value-tooltip-classificazione-portlet1").text(formattaImporto(info.data.value));
//								tooltip.select("#umvalue-tooltip-classificazione-portlet1").classed("hidden", false);
//							}
//							//Show the tooltip
//							tooltip.classed("hidden", false); 
//							tooltip.transition().duration(500).style("opacity", 100);
//						
//							//var mouse = d3.mouse(d3.select("body").node()).map( function(d) { return parseInt(d); } );
//							//tooltip.style("left", mouse[0] + "px");
//							
//						},
//						onMouseoutSegment: function(info) {
//							//Hide the tooltip
//							var tooltip = d3.select("#tooltip-classificazione-portlet1");
//						    tooltip.transition().duration(500).style("opacity", 0);   
//						    tooltip.classed("hidden", true);
//						},
//						onClickSegment: function(info) {
//							var linkURL = info.data.linkURL;
//							myFormEmpty.setAttribute('action', linkURL);
//							submitForm();
//						}
//					}
//				});
//				
//				//var SVGRoot = document.getElementsByTagName("svg")[0];
//				var SVGRoot = d3.select('#pieChartClassificazione').select('svg')[0][0];
//				var pt = SVGRoot.createSVGPoint();
//				
//				SVGRoot.addEventListener('mousemove',
//						function(evt){
//							var heightTogglerContent = d3.select('.toggler-content').node().getBoundingClientRect().height;
//							var loc = cursorPoint(evt)
//							var tooltip = d3.select("#tooltip-classificazione-portlet1");
//							tooltip.style("left", (evt.pageX - 50) + "px");
//							tooltip.style("top", (loc.y - 50 + heightTogglerContent) + "px");
//						}, false);
//				
//				//// Get point in global SVG space
//				function cursorPoint(evt){
//					pt.x = evt.clientX; 
//					pt.y = evt.clientY;
//					return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
//				}
//			}

		
		////////////////////// AFFINA RICERCA
		function caricaCombo2(pNamespaceRicerca, resourceId, pattern, pattern2, target){	
			var select = document.getElementById(target);
			select.options.length = 0;
			select.options[select.options.length] = new Option("Tutte", -1);
			
			if( pattern != -1 ){
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(pNamespaceRicerca);
				resourceURL.setResourceId(resourceId);
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setParameter("pattern2", pattern2);
				resourceURL.setCopyCurrentRenderParameters(true);
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				var responseData = this.get('responseData');
	           				//console.log(responseData);
	           				A.Object.each(
	           						responseData.lista, 
	           						function(value, key){
	           							//console.log("label = " + value.label);
	           							select.options[select.options.length] = new Option(value.label, value.id);
	           				});
	           			},
	           			failure: function (e) {
	           				var message = this.get('responseData');
	           				alert("Ajax Error : "+message);	
	           			}
	       			}
	   			});
			}
		}
		
		function caricaCombo(pNamespaceRicerca, resourceId, pattern, target){
			
			var select = document.getElementById(target);
				select.options.length = 0;
				select.options[select.options.length] = new Option("Tutte", -1);
				
			if( pattern != -1 ){
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(pNamespaceRicerca);
				resourceURL.setResourceId(resourceId);
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setCopyCurrentRenderParameters(true);

				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				var responseData = this.get('responseData');
	           				//console.log(responseData);
	           				A.Object.each(
	           						responseData.lista, 
	           						function(value, key){
	           							//console.log("label = " + value.label);
	           							select.options[select.options.length] = new Option(value.label, value.id);
	           				});
	           			},
	           			failure: function (e) {
	           				var message = this.get('responseData');
	           				alert("Ajax Error : "+message);	
	           			}
	       			}
	   			});
			}
		}
		
		///////////// LOCALIZZAZIONE /////////////
		A.one('.area-geografica').on(
			    'change',
			    function(event) {
			    	A.one('.regione').val(-1);
			    	A.one('.provincia').val(-1);
//			    	A.one('.comune').val(-1);
			    	caricaCombo(namespaceRicerca, "loadRegioneByAreaGeografica", this.val(), namespaceRicerca4js+"regione");
				});
		
		A.one('.regione').on(
			    'change',
			    function(event) {
			    	A.one('.provincia').val(-1);
//			    	A.one('.comune').val(-1);
			    	caricaCombo(namespaceRicerca, "loadProvinciaByRegione", this.val(), namespaceRicerca4js+"provincia");
				});
		
//		A.one('.provincia').on(
//			    'change',
//			    function(event) {
//			    	A.one('.comune').val(-1);
//			    	caricaCombo(namespaceRicerca, "loadComuniByProvincia", this.val(), namespaceRicerca4js+"comune");
//				});
				
		///////////// CLASSIFICAZIONE /////////////
//		A.one('.area-intervento').on(
//			    'change',
//			    function(event) {
//			    	A.one('.sotto-settore-intervento').val(-1);
//			    	caricaCombo(namespaceRicerca, "loadSottosettoreInterventoByArea", this.val(), namespaceRicerca4js+"sotto-settore-intervento");
//				});
//				
//		A.one('.sotto-settore-intervento').on(
//			    'change',
//			    function(event) {
//			    	A.one('.categoria-intervento').val(-1);
//			    	var area = A.one('.area-intervento').val();
//			    	caricaCombo2(namespaceRicerca, "loadCategoriaInterventoByAreaSottosettore", area, this.val(), namespaceRicerca4js+"categoria-intervento");
//				});
//		
		///////////// GERARCHIA SOGGETTO /////////////
		A.one('.area-soggetto').on(
			    'change',
			    function(event) {
			    	A.one('.categoria-soggetto').val(-1);
			    	A.one('.sotto-categoria-soggetto').val(-1);
			    	caricaCombo(namespaceRicerca, "loadCategoriaSoggettoByAreaSoggetto", this.val(), namespaceRicerca4js+"categoria-soggetto");
				});
		
		A.one('.categoria-soggetto').on(
			    'change',
			    function(event) {
			    	A.one('.sotto-categoria-soggetto').val(-1);
			    	caricaCombo(namespaceRicerca, "loadSottoCategoriaSoggettoByCategoriaSoggetto", this.val(), namespaceRicerca4js+"sotto-categoria-soggetto");
				});
		
		
		A.one('.pulisciElementoAreaGeografica').on(
			    'click',
			    function(event) {
			    	A.one('.area-geografica').val(-1);
			    	A.one('.regione').val(-1);
			    	A.one('.provincia').val(-1);
//			    	A.one('.comune').val(-1);
				});
		
		A.one('.pulisciElementoRegione').on(
			    'click',
			    function(event) {
			    	A.one('.regione').val(-1);
			    	A.one('.provincia').val(-1);
//			    	A.one('.comune').val(-1);
				});
		
		A.one('.pulisciElementoProvincia').on(
			    'click',
			    function(event) {
			    	A.one('.provincia').val(-1);
//			    	A.one('.comune').val(-1);
				});
		
//		A.one('.pulisciElementoComune').on(
//			    'click',
//			    function(event) {
//			    	A.one('.comune').val(-1);
//				});
		
		A.one('.pulisciElementoAreaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.area-soggetto').val(-1);
			    	A.one('.categoria-soggetto').val(-1);
			    	A.one('.sotto-categoria-soggetto').val(-1);
				});
		
		A.one('.pulisciElementoCategoriaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.categoria-soggetto').val(-1);
			    	A.one('.sotto-categoria-soggetto').val(-1);
				});

		A.one('.pulisciElementoSottoCategoriaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.sotto-categoria-soggetto').val(-1);
				});

		A.one('.pulisciElementoAnno').on(
			    'click',
			    function(event) {
			    	A.one('.anno').val(-1);
				});

		A.one('.pulisciElementoTipologia').on(
			    'click',
			    function(event) {
			    	A.one('.tipologia').val(-1);
				});

		A.one('.pulisciElementoStatoprogetto').on(
			    'click',
			    function(event) {
			    	A.one('.statoprogetto').val(-1);
				});

//		A.one('.pulisciElementoAreaIntervento').on(
//			    'click',
//			    function(event) {
//					A.one('.area-intervento').val(-1);
//					A.one('.sotto-settore-intervento').val(-1); 
//					A.one('.categoria-intervento').val(-1);
//				});
//
//		A.one('.pulisciElementoSottosettoreIntervento').on(
//			    'click',
//			    function(event) {
//					A.one('.sotto-settore-intervento').val(-1); 
//					A.one('.categoria-intervento').val(-1);
//				});
//
//		A.one('.pulisciElementoCategoriaIntervento').on(
//			    'click',
//			    function(event) {
//					A.one('.categoria-intervento').val(-1);
//				});
	
		
		
		///////////////////////////////////////////
		
		
		function drawPie( pieName, dataSet, selectString, colors, margin, outerRadius, innerRadius, sortArcs ) {

			// pieName => A unique drawing identifier that has no spaces, no "." and no "#" characters.
			// dataSet => Input Data for the chart, itself.
			// selectString => String that allows you to pass in
			//           a D3 select string.
			// colors => String to set color scale.  Values can be...
			//           => "colorScale10"
			//           => "colorScale20"
			//           => "colorScale20b"
			//           => "colorScale20c"
			// margin => Integer margin offset value.
			// outerRadius => Integer outer radius value.
			// innerRadius => Integer inner radius value.
			// sortArcs => Controls sorting of Arcs by value.
			//              0 = No Sort.  Maintain original order.
			//              1 = Sort by arc value size.
			
			var segments = [ "#b2c6ff", "#9eb5fc", "#90abfb", "#81a0fa", "#7597fb", "#678dfb", "#5a84fa", "#507cfb", "#4472fb", "#3869f9", "#2f62f2", "#275aea", "#2254e2", "#1b4bd8", "#1745ce", "#1240c3", "#0d39b8", "#0932a3" ];
			
//			// scala colori in base a valori calcolati
//			var colorScale = d3.scale.linear().domain([minData,midData,maxData])
//			.range([baseColor1,baseColor2,baseColor3]);
			
			// Color Scale Handling...
			var colorScale = d3.scale.category20c();
			switch (colors)
			{
			case "colorScale10":
				colorScale = d3.scale.category10();
				break;
			case "colorScale20":
				colorScale = d3.scale.category20();
				break;
			case "colorScale20b":
				colorScale = d3.scale.category20b();
				break;
			case "colorScale20c":
				colorScale = d3.scale.category20c();
				break;
			case "segments":
				colorScale = d3.scale.ordinal().range(segments);
				break;
			default:
				colorScale = d3.scale.category20c();
			};
			
			
			var pieWidthTotal = outerRadius * 2;
			var pieCenterX = outerRadius + margin/2;
			var pieCenterY = outerRadius + margin/2;
			var legendBulletOffset = 30;
			var legendVerticalOffset = outerRadius - margin;
			var legendTextOffset = 20;
			var textVerticalSpace = 20;
			
			var canvasHeight = 0;
			var canvasWidth = 0;
			
			var canvasHeight = 0;
			var pieDrivenHeight = outerRadius*2 + margin*2;
			var legendTextDrivenHeight = (dataSet.length * textVerticalSpace) + margin*2;
			// Autoadjust Canvas Height
			if (pieDrivenHeight >= legendTextDrivenHeight)
			{
				canvasHeight = pieDrivenHeight;
			}
			else
			{
				canvasHeight = legendTextDrivenHeight;
			}
			canvasWidth = canvasHeight;
			
			var x = d3.scale.linear().domain([0, d3.max(dataSet, function(d) { return d.value; })]).rangeRound([0, pieWidthTotal]);
			var y = d3.scale.linear().domain([0, dataSet.length]).range([0, (dataSet.length * 20)]);

			
			var synchronizedMouseOver = function(info) {
				
				var arc = d3.select(this);
		        var indexValue = arc.attr("index_value");

		        var arcSelector = "." + "pie-" + pieName + "-arc-" + indexValue;
		        var selectedArc = d3.selectAll(arcSelector);
		        selectedArc.style("fill", "Maroon");
				
				var tooltip = d3.select("#tooltip-classificazione-portlet1");
				//Update the tooltip position and value
				tooltip.select("#label-tooltip-classificazione-portlet1").text(info.data.label);
				tooltip.select("#labelvalue-tooltip-classificazione-portlet1").text(tipoAggregazione);
				if( tipoAggregazione == "VOLUME" ){
					tooltip.select("#value-tooltip-classificazione-portlet1").text(formattaIntero(info.data.value));
					tooltip.select("#umvalue-tooltip-classificazione-portlet1").classed("hidden", true);
				}else{
					tooltip.select("#value-tooltip-classificazione-portlet1").text(formattaImporto(info.data.value));
					tooltip.select("#umvalue-tooltip-classificazione-portlet1").classed("hidden", false);
				}
				//Show the tooltip
				tooltip.classed("hidden", false); 
				tooltip.transition().duration(500).style("opacity", 100);
				
				
				/*
				var bulletSelector = "." + "pie-" + pieName + "-legendBullet-" + indexValue;
				var selectedLegendBullet = d3.selectAll(bulletSelector);
				selectedLegendBullet.style("fill", "Maroon");

				var textSelector = "." + "pie-" + pieName + "-legendText-" + indexValue;
				var selectedLegendText = d3.selectAll(textSelector);
				selectedLegendText.style("fill", "Maroon");
				*/
			};
			
			var synchronizedMouseOut = function() {
				var arc = d3.select(this);
		        var indexValue = arc.attr("index_value");

		        var arcSelector = "." + "pie-" + pieName + "-arc-" + indexValue;
		        var selectedArc = d3.selectAll(arcSelector);
		        var colorValue = selectedArc.attr("color_value");
		        selectedArc.style("fill", colorValue);
				
				//Hide the tooltip
				var tooltip = d3.select("#tooltip-classificazione-portlet1");
			    tooltip.transition().duration(500).style("opacity", 0);   
			    tooltip.classed("hidden", true);
			    
				/*
				var bulletSelector = "." + "pie-" + pieName + "-legendBullet-" + indexValue;
				var selectedLegendBullet = d3.selectAll(bulletSelector);
				var colorValue = selectedLegendBullet.attr("color_value");
				selectedLegendBullet.style("fill", colorValue);

				var textSelector = "." + "pie-" + pieName + "-legendText-" + indexValue;
				var selectedLegendText = d3.selectAll(textSelector);
				selectedLegendText.style("fill", "Blue");
				*/
			};
			
			
			var tweenPie = function (b) {
				b.innerRadius = 0;
				var i = d3.interpolate({startAngle: 0, endAngle: 0}, b);
				return function(t) {
					return arc(i(t));
				};
			}

			// Create a drawing canvas...
			var canvas = d3.select(selectString)
			.append("svg:svg") //create the SVG element inside the <body>
			.data([dataSet]) //associate our data with the document
			.attr("width", canvasWidth) //set the width of the canvas
			.attr("height", canvasHeight) //set the height of the canvas
			.append("svg:g") //make a group to hold our pie chart
			.attr("transform", "translate(" + pieCenterX + "," + pieCenterY + ")") // Set center of pie

			// Define an arc generator. This will create <path> elements for using arc data.
			var arc = d3.svg.arc()
			.innerRadius(innerRadius) // Causes center of pie to be hollow
			.outerRadius(outerRadius);

			// Define a pie layout: the pie angle encodes the value of dataSet.
			// Since our data is in the form of a post-parsed CSV string, the
			// values are Strings which we coerce to Numbers.
			var pie = d3.layout.pie()
			.value(function(d) { return d.value; })
			.sort(function(a, b) {if (sortArcs==1) { return b.value - a.value; } else { return null; } });

			// Select all <g> elements with class slice (there aren't any yet)
			var arcs = canvas.selectAll("g.slice")
			// Associate the generated pie data (an array of arcs, each having startAngle,
			// endAngle and value properties) 
			.data(pie)
			// This will create <g> elements for every "extra" data element that should be associated
			// with a selection. The result is creating a <g> for every object in the data array
			// Create a group to hold each slice (we will have a <path> and a <text>      // element associated with each slice)
			.enter().append("svg:a")
			.attr("xlink:href", function(d) { return d.data.linkURL; })
			.append("svg:g")
			.attr("class", "slice")    //allow us to style things in the slices (like text)
			// Set the color for each slice to be chosen from the color function defined above
			// This creates the actual SVG path using the associated data (pie) with the arc drawing function
			//.style("stroke", "White" )
			.style("stroke", "#0932a3" )
			.attr("d", arc);

			arcs.append("svg:path")
			// Set the color for each slice to be chosen from the color function defined above
			// This creates the actual SVG path using the associated data (pie) with the arc drawing function
			.attr("fill", function(d, i) { return colorScale(i); } )
			.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
			.attr("index_value", function(d, i) { return "index-" + d.data.id; })
			.attr("class", function(d, i) { return "pie-" + pieName + "-arc-index-" + d.data.id; })
			.style("stroke", "White" )
			.attr("d", arc)
			.on('mouseover', synchronizedMouseOver)
			.on("mouseout", synchronizedMouseOut)
			.transition()
			.ease("bounce")
			.duration(2000)
			.delay(function(d, i) { return i * 50; })
			.attrTween("d", tweenPie);

			// Add a value value to the larger arcs, translated to the arc centroid and rotated.
			arcs.filter(function(d) { return d.endAngle - d.startAngle > .2; }).append("svg:text")
			.attr("dy", ".35em")
			.attr("text-anchor", "middle")
			//.attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")rotate(" + angle(d) + ")"; })
			.attr("transform", function(d) { //set the label's origin to the center of the arc
				//we have to make sure to set these before calling arc.centroid
				d.outerRadius = outerRadius; // Set Outer Coordinate
				d.innerRadius = innerRadius; // Set Inner Coordinate
				return "translate(" + arc.centroid(d) + ")rotate(" + angle(d) + ")";
			})
			.style("fill", "White")
			.style("font", "normal 10px Arial")
			.text(function(d) { return d.data.percentage; });

			// Computes the angle of an arc, converting from radians to degrees.
			function angle(d) {
				var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
				return a > 90 ? a - 180 : a;
			}
			
			
			
//			// GESTIONE TOOLTIP //
//			canvas.selectAll("g").selectAll("path")
//			.on("mouseover", function(info){
//				
//				
//			})
//			.on("mouseout", function(){
//				
//			});
			

			var SVGRoot = d3.select('#pieChartClassificazione').select('svg')[0][0];
			var pt = SVGRoot.createSVGPoint();
			SVGRoot.addEventListener('mousemove',
					function(evt){
						var heightTogglerContent = d3.select('.toggler-content').node().getBoundingClientRect().height;
						var loc = cursorPoint(evt)
						var tooltip = d3.select("#tooltip-classificazione-portlet1");
						tooltip.style("left", (evt.pageX - 100) + "px");
						tooltip.style("top", (loc.y + heightTogglerContent) + "px");
					}, false);
			
			//// Get point in global SVG space
			function cursorPoint(evt){
				pt.x = evt.clientX; 
				pt.y = evt.clientY;
				return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
			}
		    
/*
			// Plot the bullet circles...
			canvas.selectAll("circle")
			.data(dataSet).enter().append("svg:circle") // Append circle elements
			.attr("cx", pieWidthTotal + legendBulletOffset)
			.attr("cy", function(d, i) { return i*textVerticalSpace - legendVerticalOffset; } )
			.attr("stroke-width", ".5")
			.style("fill", function(d, i) { return colorScale(i); }) // Bullet fill color
			.attr("r", 5)
			.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
			.attr("index_value", function(d, i) { return "index-" + i; })
			.attr("class", function(d, i) { return "pie-" + pieName + "-legendBullet-index-" + i; })
			.on('mouseover', synchronizedMouseOver)
			.on("mouseout", synchronizedMouseOut);


			// Create hyper linked text at right that acts as label key...
			canvas.selectAll("a.legend_link")
			.data(dataSet) // Instruct to bind dataSet to text elements
			.enter().append("svg:a") // Append legend elements
			.attr("xlink:href", function(d) { return d.linkURL; })
			.append("text")
			.attr("text-anchor", "center")
			.attr("x", pieWidthTotal + legendBulletOffset + legendTextOffset)
			//.attr("y", function(d, i) { return legendOffset + i*20 - 10; })
			//.attr("cy", function(d, i) {    return i*textVerticalSpace - legendVerticalOffset; } )
			.attr("y", function(d, i) { return i*textVerticalSpace - legendVerticalOffset; } )
			.attr("dx", 0)
			.attr("dy", "5px") // Controls padding to place text in alignment with bullets
			.text(function(d) { return d.label;})
			.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
			.attr("index_value", function(d, i) { return "index-" + i; })
			.attr("class", function(d, i) { return "pie-" + pieName + "-legendText-index-" + i; })
			.style("fill", "Blue")
			.style("font", "normal 1.5em Arial")
			.on('mouseover', synchronizedMouseOver)
			.on("mouseout", synchronizedMouseOut);
*/
		};

		
	});