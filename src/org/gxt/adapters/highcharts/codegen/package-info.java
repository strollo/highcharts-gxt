/**
 * Provides the subset of functionalities to convert from java
 * artifacts to the native javascript code to use for rendering
 * <a href="http://www.highcharts.com/">highcharts</a>.
 * <br/>
 * All classes here defined expose a getJS method that is invoked
 * ones the chart is ready to be rendered and uses this JS string
 * to replace the DOM of the container so that it can be adapted to
 * <a href="http://www.sencha.com/products/extgwt/">Ext-GWT (GXT) widgets.</a>
 */
package org.gxt.adapters.highcharts.codegen;
