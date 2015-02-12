		//'aui-modal', 
		//'aui-toggler',
		//'aui-io-plugin-deprecated',

AUI().use(	
		'aui-base',
		'liferay-util-window',
		function(A) {

			A.one('.affina-ricerca-natura').on('click', 
				function(event){
				
					var buttonClose = [
									{
										cssClass: 'close',
										label: '\u00D7',
										render: true,
										on: {
											click: function() {
												popUpWindow.hide();
											}
										}
									}
								];
				
					var buttons =[{
						cssClass: 'btn btn-primary',
				        label: 'Filtra',
				        render:true,
				        id: 'naturaRicercaContentSubmit',
				        on: {
					        click: function() {
					            naturaRicercaContentSubmit();
					        }}
				    	}];
				
					var popUpWindow=Liferay.Util.Window.getWindow(
							{
								dialog: {
									centered: true,
									constrain2view: true,
									//cssClass: 'yourCSSclassName',
									modal: true,
									resizable: true,
									width: 900,
									height: 580,
									toolbars: {
							            footer:buttons,
							            header: buttonClose
							        }
								}
							}
				).plug(
						A.Plugin.IO,
							{
								autoLoad: false
							}).render();
					
				popUpWindow.show();
				popUpWindow.titleNode.html("Affina Ricerca");
				popUpWindow.io.set('uri', uriDialogRicerca);
				popUpWindow.io.start();
			});
			
			
			
//			A.one('.affina-ricerca-natura').on('click', 
//					function(event){
//						A.one('#affina-ricerca-natura-modal-content').setStyle('display','block');
//						var modal = new A.Modal({
//													srcNode : '#affina-ricerca-natura-modal-content',  //contentBox:#affina-ricerca-natura-modal-content 
//													centered: true,
//													headerContent: 'Affina Ricerca',
//													modal: true,
//													render: '#affina-ricerca-natura-modal',
//													width: 550
//												}).render();
//			});
//			
//			function caricaCombo(pNamespaceRicerca, resourceId, pattern, target){
//   				
//				var select = document.getElementById(target);
//   				select.options.length = 0;
//   				select.options[select.options.length] = new Option("Tutte", -1);
//   				
//				if( pattern != -1 ){
//					var resourceURL = Liferay.PortletURL.createResourceURL();
//					resourceURL.setPortletId(pNamespaceRicerca);
//					resourceURL.setResourceId(resourceId);
//					resourceURL.setParameter("pattern", pattern);
//					resourceURL.setCopyCurrentRenderParameters(true);
//
//					A.io.request( resourceURL.toString(), {
//		   				method: 'GET',
//		       			dataType: 'json',
//		       			on: {
//		           			success: function(event, id, obj) {
//		           				var responseData = this.get('responseData');
//		           				A.Object.each(
//		           						responseData.lista, 
//		           						function(value, key){
//		           							console.log("label = " + value.label);
//		           							select.options[select.options.length] = new Option(value.label, value.id);
//		           				});
//		           			},
//		           			failure: function (e) {
//		           				var message = this.get('responseData');
//		           				alert("Ajax Error : "+message);	
//		           			}
//		       			}
//		   			});
//				}
//			}
//			
//			
//			A.one('.affina-ricerca-natura-modal-content-provincia').on(
//				    'change',
//				    function(event) {
//				    	caricaCombo(namespaceRicerca, "loadComuniByProvincia", this.val(), namespaceRicerca4js+"affina-ricerca-natura-modal-content-comune");
//					});
//			
//			A.one('.affina-ricerca-natura-modal-content-regione').on(
//				    'change',
//				    function(event) {
//				    	caricaCombo(namespaceRicerca, "loadProvinciaByRegione", this.val(), namespaceRicerca4js+"affina-ricerca-natura-modal-content-provincia");
//					});
			
			
			
});