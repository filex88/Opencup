<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

<div id="pieChart" style="text-align: center"></div>

<div id="pieAffix">
  <ul>
    <li><a href="#">VOLULE</a></li>
    <li><a href="#">COSTO</a></li>
    <li><a href="#">IMPORTO</a></li>
  </ul>
</div>

<script>
	/*
	YUI().use(
		'aui-affix',
		function( Y ) {
			new Y.Affix(
		    	{
		    		target: '#pieAffix',
		    		offsetTop: 0,
		    		offsetBottom: 0
		    	}
		    );
		}
	);
	*/

	AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated', 
		'aui-affix',
		function( A ) {
			
			var pie = null;
			var aggregate = null;
			
   			loadPie("0");
   			
			function loadPie(pattern){
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId("naturaportlet1_WAR_OpenCupPortletsportlet");
				resourceURL.setResourceId("naturaPortlet1");
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setCopyCurrentRenderParameters(true);
				
				console.log("resourceUrl = " + resourceURL.toString());
				
				pie = null;
				
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				aggregate = this.get('responseData');
	           				
	           				console.log("aggregate = " + aggregate);
	           				console.log("aggregate = " + aggregate.naturaPortlet1);
	           				
	           				// Select the node(s) using a css selector string
	           			    A.one('#pieChart').empty();
	           				drawPie();
	           			},
	           			failure: function (e) {
	           				var message = this.get('responseData');
	           				alert("Ajax Error : "+message);	
	           			}
	       			}
	   			});
			}
			
			
			function drawPie(){
				
				pie = new d3pie("pieChart", {
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
						"content": aggregate.naturaPortlet1
					},
					"labels": {
						"outer": {
							"format": "percentage",
							"pieDistance": 5
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
						}
					},
					"tooltips": {
						"enabled": true,
						"type": "placeholder",
						"string": "{label} Costo:{value}&euro; {percentage}%",
						"styles": {
							"fadeInSpeed": 396,
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
					"misc": {
						"gradient": {
							"enabled": true,
							"percentage": 100
						}
					}
				});
			}
			
			
			new A.Affix(
			    	{
			    		target: '#pieAffix',
			    		offsetTop: 0,
			    		offsetBottom: 0
			    	}
			    );
   			
   			
		}
	);
	
	
</script>