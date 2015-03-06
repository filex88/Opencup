AUI().use(	
		'aui-base',
		'liferay-util-window',
		function(A) {
			
			var el_affina_ricerca_natura = A.one('.affina-ricerca-natura');

			if(el_affina_ricerca_natura) {
				
				el_affina_ricerca_natura.on('click', 
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
				    		cssClass: '',
					        label: 'Pulisci',
					        render:true,
					        id: 'naturaRicercaContentReset',
					        on: {
						        click: function() {
						            naturaRicercaContentReset();
						        }}
				    	},{
							cssClass: '',
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
										width: 1025,
										height: 600,
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
			}
				
});