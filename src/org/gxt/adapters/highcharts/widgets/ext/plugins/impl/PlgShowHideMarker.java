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
 * Filename: CmdShowHideMarker.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.widgets.ext.plugins.impl;

import org.gxt.adapters.highcharts.codegen.sections.options.OptionPath;
import org.gxt.adapters.highcharts.codegen.utils.ClientConsole;
import org.gxt.adapters.highcharts.widgets.HighChart;
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;

import com.google.gwt.dom.client.NativeEvent;

/**
 * @author Daniele Strollo
 *
 */
public class PlgShowHideMarker extends ChartFramePlugin {
	private static final String LABEL = "Show/Hide marker";
	private boolean showMarker = true;

	public PlgShowHideMarker(final boolean initValue) {
		super(LABEL);
		this.showMarker = initValue;
	}

	@Override
	protected void doTask(NativeEvent be) {
		if (showMarker) {
			this.showMarker(getChart().getChartJSId(), false);
		} else {
			this.showMarker(getChart().getChartJSId(), true);
		}
		showMarker = !showMarker;
	}

	@SuppressWarnings("deprecation")
	private void showMarker(final String chartId, final boolean show) {
		final String chartType = getCurrentType().toString();
		final HighChart hc = getChart();
		final String optPrefix = "/plotOptions/" + chartType + "/marker/";

		try {
			hc.setOption(new OptionPath(optPrefix + "enabled"), show);
			hc.setOption(new OptionPath(optPrefix + "radius"), 4);
			hc.setOption(new OptionPath(optPrefix + "lineColor"), "#666666");
			hc.setOption(new OptionPath(optPrefix + "lineWidth"), 1);
		} catch (Exception e) {
			ClientConsole.err("During showMarker", e);
		}

		getChart().injectJS();
	}
}
