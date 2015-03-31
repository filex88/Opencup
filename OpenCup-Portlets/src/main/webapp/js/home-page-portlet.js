AUI().use(
		'node', 
		'node-focusmanager',
		'aui-base', 
		function( A ) {
			
		      var document = A.one(document),
	          toggler = A.one('.dropdown-toggle-home'),
	          menu = A.one('.dropdown-menu-home');

		      toggler.on('click', function(e) {
		    	  menu.toggleClass('show');
		    	  e.preventDefault();
		    	  e.stopPropagation();
		      });

//		      document.on('click', function() {
//		    	  dropdown.removeClass('show');
//		      });

			
		});