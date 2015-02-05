
AUI().use(
		'aui-base', 
		function( A ) {
			
			var myFormEmpty = A.one(".formEmptyNatura2");
		
			A.all('.link-url-naviga-selezione').each(
					function() {
						this.on('click', function(){
							var linkURL = this.getAttribute("data-url");
							myFormEmpty.setAttribute('action', linkURL);
							myFormEmpty.submit();
						});
					});
		});