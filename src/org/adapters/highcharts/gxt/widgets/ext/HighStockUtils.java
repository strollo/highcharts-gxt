/****************************************************************************
 *  This software is part of the HighchartsJS adapters for Ext GWT.
 ****************************************************************************
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************
 * Filename: HighStockUtils.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.gxt.widgets.ext;

/**
 * Support class for accessing functionalities exposed by highstock by exposing
 * JSNI based implementations for getters on the charts.
 * @author Daniele Strollo
 */
public class HighStockUtils {
	public static native double getXAxisDataMax(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart != null) {
			try {
				return thechart.series[0].xAxis.getExtremes().dataMax;
			} catch (e) {
				return -1;
			}
		} 
		return -1;
	}-*/;
	
	public static native double getXAxisDataMin(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].xAxis.getExtremes().dataMin;
			} catch (e) {
				return -1;
			}
		}		
		return -1;
	}-*/;
	
	public static native double getXAxisMin(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].xAxis.getExtremes().min;
			} catch (e) {
				return -1;
			}
		}
		return -1;
	}-*/;
	
	public static native double getXAxisMax(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].xAxis.getExtremes().max;
			} catch (e) {
				return -1;
			}
		} 
		return -1;
	}-*/;
	
	public static native double getYAxisDataMax(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].yAxis.getExtremes().dataMax;
			} catch (e) {
				return -1;
			}
		} 
		return -1;
	}-*/;
	
	public static native double getYAxisDataMin(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].yAxis.getExtremes().dataMin;
			} catch (e) {
				return -1;
			}
		} 
		return -1;
	}-*/;
	
	public static native double getYAxisMin(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].yAxis.getExtremes().min;
			} catch (e) {
				return -1;
			}
		} 
		return -1;
	}-*/;
	
	public static native double getYAxisMax(final String chartID)  /*-{
		var thechart = $wnd.getChartById(chartID);
		if (thechart) {
			try {
				return thechart.series[0].yAxis.getExtremes().max;
			} catch (e) {
				return -1;
			}
		} 
		return -1;
	}-*/;
}
