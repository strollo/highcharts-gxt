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

package org.adapters.highcharts.gxt.widgets.ext.plugins.impl;

import org.adapters.highcharts.gxt.widgets.ext.plugins.ChartFramePlugin;
import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.gxt.widgets.HighChart;
import org.adapters.highcharts.codegen.utils.ClientConsole;

import com.extjs.gxt.ui.client.event.ComponentEvent;

/**
 * @author Daniele Strollo
 *
 */
public class PlgShowHideDataLabels extends ChartFramePlugin {
	private static final String LABEL = "Show/Hide data labels";
	private boolean showMarker = false;

	public PlgShowHideDataLabels(final boolean initValue) {
		super(LABEL);
		this.showMarker = initValue;
	}

	@Override
	protected void doTask(ComponentEvent be) {
		if (showMarker) {
			this.showDataLabels(getChart().getChartJSId(), false);
		} else {
			this.showDataLabels(getChart().getChartJSId(), true);
		}
		showMarker = !showMarker;
	}

	@SuppressWarnings("deprecation")
	private void showDataLabels(final String chartId, final boolean show) {
		if (getCurrentType() != null) {
			final String chartType = getCurrentType().toString();
			final HighChart hc = getChart();
			try {
				hc.setOption(new OptionPath("/plotOptions/" + chartType + "/dataLabels/enabled"), show);
			} catch (Exception e) {
				ClientConsole.err("showDataLabels", e);
			}
			getChart().injectJS();
		}
	}
}
