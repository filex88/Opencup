<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
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

<h1>Per Province</h1>

<div id="italybymacroareas" style="text-align: left"></div>

<script>


var regioneSelezionata="${selectedRegion}";
console.log(regioneSelezionata);

var width = 500,
    height = 500,
    border=1
    bordercolor='black',
    smallrectW=50,
    smallrectH=50;

var svg = d3.select("#italybymacroareas").append("svg")
    .attr("width", width)
    .attr("height", height)
   	.attr("border",border);
   	
   	var borderPath = svg.append("rect")
		.attr("height", height)
       	.attr("width", width)
       	.style("stroke", bordercolor)
       	.style("fill", "none")
       	.style("stroke-width", border);
       	
  var tooltip = d3.select("#italybymacroareas").append("div")
    .attr("class", "selectiontip");

d3.json("/OpenCup-Theme-theme/js/all_province.json", function(error, it) {

    var projection = d3.geo.albers()
        .center([0, 41])
        .rotate([347, 0])
        .parallels([35, 45])
        .scale(2000)
        .translate([width / 2, height / 2]);
 
    var path = d3.geo.path()
        .projection(projection);
        
     
        
     svg.append("g").
     attr("id","regioneSel");
        
     svg.selectAll("g").selectAll("path").data(topojson.feature(it, it.objects.sub).features)
     .enter()
     .append("path")
     .attr("class",function(d) { 
     	var selectedRegion=d.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    	if (selectedRegion==regioneSelezionata){
     		return d.properties.TERR;}
     	else {
     		return "notIncluded";}
    
  	 })
    .attr("d",path)
    .attr ("id",function(d) { return d.properties.TR_REG_PRO; })
    .on("mouseover",function(a){
    	svg.selectAll("#"+a.properties.TR_REG_PRO)
    	.style("fill","#FFFFCC");
    	
    	var mouse = d3.mouse(svg.node()).map( function(d) { return parseInt(d); } );
    	//var valore_regione=getLocalizationValue(selectedTerritoryValues.selectedTerritoryValues,idSelected);
    	var nome_prov=a.properties.NOME.toUpperCase();
    	tooltip.classed("nascosto", false)
        .attr("style", "left:"+(mouse[0]+25)+"px;top:"+(mouse[1]+height)+"px")
         .html('<p><strong>PROVINCIA: </strong>'+nome_prov+'</p>');
    })
    .on("mouseout",function(a){
    	svg.selectAll("#"+a.properties.TR_REG_PRO)
    	.attr("style",null);
    	 tooltip.classed("nascosto", true)
    });
    
    svg.selectAll(".notIncluded").remove();
    
    var selection = d3.select("#regioneSel");
    
    // trovo coordinate quadrato che circonda la selezione  
    var currentX=selection[0][0].getBBox().x;
    var currentY=selection[0][0].getBBox().y;
    var currentW=selection[0][0].getBBox().width;
    var currentH=selection[0][0].getBBox().height;
    
    // calcolo spostamenti per portare il riferimento su angolo superiore sx (ossia sottraggo ascissa e ordinata)
    var xFirstTranslation=-currentX;
    var yFirstTranslation=-currentY;
    
    
    // dopo aver scalato sposto al centro il g contenitore
    var maxScale=4;
    
    var xSecondTranslation=(width/2)-(currentW*(maxScale/2));
    var ySecondTranslation=(height/2)-(currentH*(maxScale/2));
    
    
    //lo shape del veneto risulta piu' lungo e deve essere centrato manualmente veneto =150y x60
    
    if (regioneSelezionata=='VENETO'){
    	xSecondTranslation+=60;
    	ySecondTranslation+=150;
    }
    	
	// sposta all'angolo, poi quintuplica, poi sposta al centro
	selection.attr("transform", "translate("+xSecondTranslation+","+ySecondTranslation+")  scale("+maxScale+")  translate("+xFirstTranslation+","+yFirstTranslation+") " );
  	var newBorder=border/maxScale;
	selection.style("stroke-width",newBorder);
  
});



</script>
