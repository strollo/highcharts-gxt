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
 * Filename: PlgRotateDataLabels.java
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
public class PlgRotateDataLabels extends ChartFramePlugin {
	private static final String LABEL = "Rotate data labels";
	private boolean enabled = false;

	public PlgRotateDataLabels(final boolean initValue) {
		super(LABEL);
		this.enabled = initValue;
	}

	@Override
	protected void doTask(NativeEvent be) {
		if (enabled) {
			this.rotateDataLabels(getChart().getChartJSId(), false);
		} else {
			this.rotateDataLabels(getChart().getChartJSId(), true);
		}
		enabled = !enabled;
	}

	@SuppressWarnings("deprecation")
	private void rotateDataLabels(final String chartId, final boolean rotate) {
		if (getCurrentType() != null) {
			final HighChart hc = getChart();
			try {
				if (rotate) {
					hc.setOption(new OptionPath("/xAxis/labels/rotation"), "45");
				} else {
					hc.removeOption(new OptionPath("/xAxis/labels/rotation"));
				}
			} catch (Exception e) {
				ClientConsole.err("showDataLabels", e);
			}
			getChart().injectJS();
		}
	}
}
