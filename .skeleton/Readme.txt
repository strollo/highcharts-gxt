
1) copy from js the file gxt-adapter.js inside the gxt folder of tour project
e.g. <project>/war/highcharts/js/gxt-adapter.js

2) Insert inside your html module file (usually <project>/war/<prjname>.html) the following line:

	<!-- Adapter for integration with gxt -->
	<script type="text/javascript" src="highcharts/js/gxt-adapter.js"></script>
	
3) Inside your gwt.xml file insert the following line:
	<!-- Adapters for highcharts in gxt -->
	<inherits name='org.gxt.adapters.highcharts.gxthighcharts' />
	
4) enjoy!
