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
public class PlgSharedTooltip extends ChartFramePlugin {
	private static final String SHOW_MSG = "Share tooltip";
	private static final String HIDE_MSG = "Unshare tooltip";
	private boolean showMarker = false;

	public PlgSharedTooltip() {
		super(SHOW_MSG);
	}

	@Override
	protected void doTask(ComponentEvent be) {
		if (showMarker) {
			this.setText(SHOW_MSG);
			this.showMarker(false);
		} else {
			this.setText(HIDE_MSG);
			this.showMarker(true);
		}
		showMarker = !showMarker;
	}

	@SuppressWarnings("deprecation")
	private void showMarker(final boolean show) {
		if (getCurrentType() != null) {
			try {
				final HighChart chart = getChart();
				chart.setOption(new OptionPath("/tooltip/enabled"), true);
				chart.setOption(new OptionPath("/tooltip/shared"), show);
				chart.setOption(new OptionPath("/tooltip/crosshairs"), show);
				getChart().injectJS();
			} catch (Exception e) {
				ClientConsole.err("ShareTooltip command.", e);
			}
		}
	}
}
