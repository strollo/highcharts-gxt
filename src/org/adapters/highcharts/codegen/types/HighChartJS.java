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
 * Filename: HighChartJS.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.codegen.types;

import java.util.List;
import java.util.Vector;

import org.adapters.highcharts.codegen.sections.OptionManager;
import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.codegen.sections.options.types.RawStringType;
import org.adapters.highcharts.codegen.utils.ClientConsole;
import org.adapters.highcharts.codegen.utils.StringUtils;

/**
 * Artifact for wrapping highcharts javascript objects inside java objects.
 * Through this class it is possible to generate the javascript code that
 * will represent the rendered highchart.
 * @author Daniele Strollo
 */
public class HighChartJS {
	private final OptionManager optionMgr = new OptionManager();
	private String id = null;
	private final List<SeriesType> seriesList = new Vector<SeriesType>();
	private String currDivId = null;
	private int widthOffset = -1;
	private int heightOffset = -1;
	private boolean autoResize = true;
	private int width = 400;
	private int height = 200;

	public HighChartJS(final String id) {
		if (id != null) {
			this.id = id.trim();
		}
	}

	public final void setAutoResize(boolean autoResize) {
		this.autoResize = autoResize;
	}

	/**
	 * Sets the width of the chart (valid only once autoResize disabled).
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Sets the width of the chart (valid only once autoResize disabled).
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

	public final void addSeries(final SeriesType series) {
		if (series != null) {
			this.seriesList.add(series);
		}
	}

	public final List<SeriesType> getSeriesList() {
		return seriesList;
	}

	public final void setSeriesType(final String type) {
		if (seriesList != null && seriesList.size() > 0 && type != null && type.trim().length() > 0) {
			for (SeriesType series : seriesList) {
				series.setType(type.trim());
			}
		}
	}

	public final String getJS() {
		StringBuilder retval = new StringBuilder();
		retval.append("var " + this.getJSChartName() + " = new $wnd.Highcharts.Chart({" + StringUtils.NEW_LINE);

		// THE OPTIONS
		try {
			optionMgr.setOption(new OptionPath("/chart/renderTo"), this.getDivId());
			optionMgr.setOption(new OptionPath("/chart/events/load"),
					new RawStringType(
							"function(event) { " +
							"this.id = '" + this.getJSChartName() + "'; " +
							"$wnd.registerChart(this, true); " + 
							"}"
					)
			);
		} catch (Exception e) {
			ClientConsole.err("During initialization of chart", e);
		}
		retval.append(optionMgr.getJS());

		// THE SERIES
		if (this.seriesList != null && this.seriesList.size() > 0) {
			// The series entries
			List<String> entries = new Vector<String>();
			for (SeriesType series : this.seriesList) {
				entries.add(series.getJS());
			}
			retval.append("series: [" + StringUtils.NEW_LINE +
					StringUtils.join(entries, ", " + StringUtils.NEW_LINE) +
					StringUtils.NEW_LINE + "],");
			retval.append(StringUtils.NEW_LINE);
		}

		retval.append("});");
		String toret = StringUtils.getCleanJS(retval.toString());
		return toret;
	}

	public final OptionManager getOptionManager() {
		return this.optionMgr;
	}

	public final String getId() {
		return this.id;
	}

	public final String getJSChartName() {
		return "chart" + this.id;
	}

	public final void setDivId(final String containerDiv) {
		this.currDivId = containerDiv;
	}

	public final String getDivId() {
		return this.currDivId;
	}

	public final void doRender() {
		this.injectJS(this.currDivId, this.getJS());
		this.doResize();
	}

	public final void doResize() {
		if (autoResize) {
			this.autoResize(this.getJSChartName(), this.widthOffset, this.heightOffset);
		} else {
			this.resize(this.getJSChartName(), this.width, this.height, this.widthOffset, this.heightOffset);
		}
	}

	public final void setUseParent(final boolean useParent) {
		this.setUseParent(this.getJSChartName(), useParent);
	}

	/**
	 * The id of the container responsible to handle the resize of this
	 * component.
	 * @param chartId
	 * @param widthOffset
	 * @param heightOffset
	 */
	private native void setUseParent(final String chartId, final boolean useParent) /*-{
		$wnd.setUseParent(chartId, useParent);
	}-*/;

	private native void autoResize(final String chartId, final int widthOffset, final int heightOffset) /*-{
		$wnd.autoResizeChart(chartId, widthOffset, heightOffset);
	}-*/;


	private native void resize(final String chartId, final int width, final int height, final int widthOffset, final int heightOffset) /*-{
		$wnd.resizeChart(chartId, width, height, widthOffset, heightOffset);
	}-*/;

	public final void setWidthOffset(final int widthOffset) {
		if (widthOffset >= 0) {
			this.widthOffset = widthOffset;
		}
	}

	public final void setHeightOffset(final int heightOffset) {
		if (heightOffset >= 0) {
			this.heightOffset = heightOffset;
		}
	}

	/*
	 * Used internally on render phase.
	 */
	private native synchronized void injectJS(final String divID, final String jsCode) /*-{
		var container = $doc.getElementById(divID);

		if (container != null && jsCode != null) {
			 eval(jsCode);
		} else {
			alert('div: ' + divID + ' not found ');
		}
	}-*/;
}
