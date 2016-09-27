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
import org.gxt.adapters.highcharts.widgets.HighChart;
import com.google.gwt.dom.client.NativeEvent;
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;

/**
 * @author Daniele Strollo
 *
 */
public class PlgIncreaseInterval extends ChartFramePlugin {
	private static final String LABEL = "Increase Tick Interval";
	private int step = 5;

	public PlgIncreaseInterval(final int step) {
		super(LABEL);
		this.step = step;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doTask(NativeEvent be) {
		final HighChart hc = getChart();
		int curVal = 0;
		try {
			curVal = Integer.parseInt(hc.getOption(new OptionPath("/xAxis/tickInterval")).toString());
		} catch (Exception e) {}

		curVal += step;
		try {
			hc.setOption(new OptionPath("/xAxis/tickInterval"), Integer.valueOf(curVal));
			hc.injectJS();
		} catch (Exception e) {
		}
	}
}
