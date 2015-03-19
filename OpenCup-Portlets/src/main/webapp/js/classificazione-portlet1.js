
var svg = null;

AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated',
		'datatype-number',
		'aui-toggler',
		function( A ) {
			
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
				    	A.one('.sotto-categoria-soggetto').val(-1);
				    	A.one('.area-geografica').val(-1);
				    	A.one('.regione').val(-1);
				    	A.one('.provincia').val(-1);
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
	           			    	drawPie();
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
			
			function drawPie(){
				pieClassificazione = new d3pie("pieChartClassificazione", {
					"header": {
						"title": {
							"text": "Distribuzione percentuale",
							"fontSize": 22,
							"font": "helvetica"
						},
						"subtitle": {
							"color": "#999999",
							"fontSize": 12,
							"font": "open sans"
						},
						"titleSubtitlePadding": 5
					},
					"footer": {
						"color": "#999999",
						"fontSize": 10,
						"font": "open sans",
						"location": "bottom-left"
					},

					"data": {
						"sortOrder": "value-desc",
						"content": aggregate.aggregati4Pie,
						"color": "#FF0000"
					},
					misc: {
						colors: {
							segments: [
							           "#b2c6ff", "#9eb5fc", "#90abfb", "#81a0fa", "#7597fb", "#678dfb", "#5a84fa", "#507cfb", "#4472fb", "#3869f9", "#2f62f2", "#275aea", "#2254e2", "#1b4bd8", "#1745ce", "#1240c3", "#0d39b8", "#0932a3"
							]
						}
					},
					"labels": {
						"outer": {
							"format": "percentage"
						},
						"inner": {
							"format": "none",
							"hideWhenLessThanPercentage": 3
						},
						"mainLabel": {
							"fontSize": 11
						},
						"percentage": {
							"color": "#333333",
							"decimalPlaces": 0
						},
						"value": {
							"color": "#adadad",
							"fontSize": 11
						},
						"lines": {
							"enabled": true,
							"style": "straight"
						}
					},
					"tooltips": {
						"enabled": false,
						"type": "placeholder",
						"string": "{label} Costo:{value}&euro; {percentage}%",
						"styles": {
							"fadeInSpeed": 390,
							"color": "#ffffff",
							"borderRadius": 10,
							"fontSize": 12,
							"padding": 10
						}
					},
					"effects": {
						"pullOutSegmentOnClick": {
							"effect": "linear",
							"speed": 400,
							"size": 8
						}
					},
					"size": {
						"canvasHeight": 450,
						"canvasWidth": 450
					},
					"callbacks": {
						onMouseoverSegment: function(info) {							
							//Update the tooltip position and value
							var tooltip = d3.select("#tooltip-classificazione-portlet1");
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
						
							//var mouse = d3.mouse(d3.select("body").node()).map( function(d) { return parseInt(d); } );
							//tooltip.style("left", mouse[0] + "px");
							
						},
						onMouseoutSegment: function(info) {
							//Hide the tooltip
							var tooltip = d3.select("#tooltip-classificazione-portlet1");
						    tooltip.transition().duration(500).style("opacity", 0);   
						    tooltip.classed("hidden", true);
						},
						onClickSegment: function(info) {
							var linkURL = info.data.linkURL;
							myFormEmpty.setAttribute('action', linkURL);
							submitForm();
						}
					}
				});
				
				//var SVGRoot = document.getElementsByTagName("svg")[0];
				var SVGRoot = d3.select('#pieChartClassificazione').select('svg')[0][0];
				var pt = SVGRoot.createSVGPoint();
				
				SVGRoot.addEventListener('mousemove',
						function(evt){
							var heightTogglerContent = d3.select('.toggler-content').node().getBoundingClientRect().height;
							var loc = cursorPoint(evt)
							var tooltip = d3.select("#tooltip-classificazione-portlet1");
							tooltip.style("left", (evt.pageX - 50) + "px");
							tooltip.style("top", (loc.y - 50 + heightTogglerContent) + "px");
						}, false);
				
				//// Get point in global SVG space
				function cursorPoint(evt){
					pt.x = evt.clientX; 
					pt.y = evt.clientY;
					return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
				}
			}

		
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
	
		
	});