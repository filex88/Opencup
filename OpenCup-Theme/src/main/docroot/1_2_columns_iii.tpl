<div class="columns-1-2" id="main-content" role="main">
	<div class="portlet-layout row-fluid">
		<div class="portlet-column portlet-column-only span12" id="column-1">
			$processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")
		</div>
	</div>

	<div class="portlet-layout row-fluid">
		<div class="portlet-column portlet-column-first span6" id="column-2">
			$processor.processColumn("column-2", "portlet-column-content portlet-column-content-first")
		</div>

		<div class="portlet-column portlet-column-last span6" id="column-3" style="margin-left: 0px;">
			$processor.processColumn("column-3", "portlet-column-content portlet-column-content-last")
		</div>
	</div>
</div>

<script>
	/*
	d3.selectAll(".divider").each(
		function(){
			var c=d3.select(this).node().parentNode;
			d3.select(c)
				.style("font-weight","bold")
				.append("i")
				.attr("class","icon-caret-right")
				.attr("style","padding: 0 5px");
			
			d3.select(c).select("span").remove();
		});
	*/
</script>