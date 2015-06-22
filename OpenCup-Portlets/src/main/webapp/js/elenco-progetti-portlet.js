
// clear breadcrumb
//var fatherUl=d3.selectAll("li.first").node().parentNode;
//
//d3.select(fatherUl).insert("li",":first-child")
//	.attr("style","padding: 0 5px;color:#fcfcfc")
//	.text("Sei in: ");

//d3.selectAll(".divider").each(
//		function(){
//			var c=d3.select(this).node().parentNode;
//			d3.select(c)
//				.style("font-weight","bold")
//				.append("i")
//				.attr("class","icon-caret-right")
//				.attr("style","padding: 0 5px;color:#fcfcfc");
//		
//				d3.select(c).select("span").remove();
//		});
//
//d3.selectAll("li.current-parent.breadcrumb-truncate").selectAll("i").remove();
//d3.selectAll("li.active.last.breadcrumb-truncate").remove();


AUI().use(
		'aui-base', 
		'aui-toggler',
		function( A ) {
			if( !filtroExpanded ){
				var toggler = new A.TogglerDelegate({
			        animated: true,
			        closeAllOnExpand: true,
			        container: '#my-toggler-affina-ricerca-elenco-progetti',
			        content: '.content-elenco-progetti',
			        expanded: filtroExpanded,
			        header: '.header-elenco-progetti',
			        transition: {
			          duration: 0.6,
			          easing: 'cubic-bezier(0, 0.1, 0, 1)'
			        }
			      });
			}
			

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
					
					console.log(resourceURL.toString());
					
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
					
					console.log(resourceURL.toString());
					
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
				    	A.one('.comune').val(-1);
				    	caricaCombo(namespaceRicerca, "loadRegioneByAreaGeografica", this.val(), namespaceRicerca4js+"regione");
					});
			
			A.one('.regione').on(
				    'change',
				    function(event) {
				    	A.one('.provincia').val(-1);
				    	A.one('.comune').val(-1);
				    	caricaCombo(namespaceRicerca, "loadProvinciaByRegione", this.val(), namespaceRicerca4js+"provincia");
					});
			
			A.one('.provincia').on(
				    'change',
				    function(event) {
				    	A.one('.comune').val(-1);
				    	caricaCombo(namespaceRicerca, "loadComuniByProvincia", this.val(), namespaceRicerca4js+"comune");
					});
					
			///////////// CLASSIFICAZIONE /////////////
			A.one('.area-intervento').on(
				    'change',
				    function(event) {
				    	A.one('.sotto-settore-intervento').val(-1);
				    	A.one('.categoria-intervento').val(-1);
				    	caricaCombo(namespaceRicerca, "loadSottosettoreInterventoByArea", this.val(), namespaceRicerca4js+"sotto-settore-intervento");
					});
					
			A.one('.sotto-settore-intervento').on(
				    'change',
				    function(event) {
				    	A.one('.categoria-intervento').val(-1);
				    	var area = A.one('.area-intervento').val();
				    	caricaCombo2(namespaceRicerca, "loadCategoriaInterventoByAreaSottosettore", area, this.val(), namespaceRicerca4js+"categoria-intervento");
					});
			
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
			
			/*
			A.one('.pulisciElementoAreaGeografica').on(
				    'click',
				    function(event) {
				    	A.one('.area-geografica').val(-1);
				    	A.one('.regione').val(-1);
				    	A.one('.provincia').val(-1);
				    	A.one('.comune').val(-1);
					});
			
			A.one('.pulisciElementoRegione').on(
				    'click',
				    function(event) {
				    	A.one('.regione').val(-1);
				    	A.one('.provincia').val(-1);
				    	A.one('.comune').val(-1);
					});
			
			A.one('.pulisciElementoProvincia').on(
				    'click',
				    function(event) {
				    	A.one('.provincia').val(-1);
				    	A.one('.comune').val(-1);
					});
			
			A.one('.pulisciElementoComune').on(
				    'click',
				    function(event) {
				    	A.one('.comune').val(-1);
					});
			
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

			A.one('.pulisciElementoAreaIntervento').on(
				    'click',
				    function(event) {
						A.one('.area-intervento').val(-1);
						A.one('.sotto-settore-intervento').val(-1); 
						A.one('.categoria-intervento').val(-1);
					});

			A.one('.pulisciElementoSottosettoreIntervento').on(
				    'click',
				    function(event) {
						A.one('.sotto-settore-intervento').val(-1); 
						A.one('.categoria-intervento').val(-1);
					});

			A.one('.pulisciElementoCategoriaIntervento').on(
				    'click',
				    function(event) {
						A.one('.categoria-intervento').val(-1);
					});
			*/
			
			var myFormAffinaRicerca = A.one(".ricerca-form");
			A.one('.btn-rimuovi-filtri').on(
				    'click',
				    function(event) {
				    	// pulisco i campi
				    	A.one('.area-intervento').val(-1);
				    	A.one('.sotto-settore-intervento').val(-1);
				    	A.one('.categoria-intervento').val(-1);
				    	
				    	A.one('.regione').val(-1);
				    	A.one('.provincia').val(-1);
				    	A.one('.comune').val(-1);
				    	A.one('.area-geografica').val(-1);
				    	A.one('.area-soggetto').val(-1);
				    	A.one('.categoria-soggetto').val(-1);
				    	A.one('.sotto-categoria-soggetto').val(-1);
				    	A.one('.anno').val(-1);
				    	A.one('.tipologia').val(-1);
				    	A.one('.statoprogetto').val(-1);
				    	
				    	A.one('.cup').val("");
				    	A.one('.descrizione').val("");
				    	A.one('.soggettoResponsabile').val("");
				    	A.one('.cfPiSoggettoResponsabile').val("");

				    	// submit
				    	myFormAffinaRicerca.submit();
					});

		});