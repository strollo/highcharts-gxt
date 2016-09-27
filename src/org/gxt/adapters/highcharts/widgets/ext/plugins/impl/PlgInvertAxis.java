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
 * Filename: CmdInvertAxis.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.widgets.ext.plugins.impl;

import org.gxt.adapters.highcharts.codegen.sections.options.OptionPath;
import org.gxt.adapters.highcharts.widgets.HighChart;
import com.google.gwt.dom.client.NativeEvent;
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;
import org.gxt.adapters.highcharts.codegen.utils.ClientConsole;

/**
 * @author Daniele Strollo
 *
 */
public class PlgInvertAxis extends ChartFramePlugin {
	private static final String LABEL = "Invert Axis";
	private static final OptionPath OPT_INVERT_AXIS = new OptionPath("/chart/inverted");

	public PlgInvertAxis() {
		super(LABEL);
	}

	/* (non-Javadoc)
	 * @see org.gxt.adapters.highcharts.widgets.commands.MenuOption#onClick(com.extjs.gxt.ui.client.event.ComponentEvent)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void doTask(NativeEvent be) {
		final HighChart chart = getChart();
		if (chart == null) {
			ClientConsole.log("Error during command handling: the chart is null.");
			return;
		}

		try {
			boolean inverted = false;
			try {
				inverted = (Boolean) chart.getOption(OPT_INVERT_AXIS);
			} catch (Exception e) {
			}
			chart.setOption(OPT_INVERT_AXIS, !inverted);
			chart.injectJS();
		} catch (Exception e) {
			ClientConsole.err("During axis inversion", e);
		}
	}

}
