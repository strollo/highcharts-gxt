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
import org.gxt.adapters.highcharts.widgets.HighChart;
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;

import com.google.gwt.dom.client.NativeEvent;

/**
 * @author Daniele Strollo
 *
 */
public class PlgShowHideSubTitle extends ChartFramePlugin {
	private static final String LABEL = "Show/Hide subtitle";
	private boolean showMarker = false;

	public PlgShowHideSubTitle(final boolean initValue) {
		super(LABEL);
		this.showMarker = initValue;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doTask(NativeEvent be) {
		final HighChart hc = getChart();
		if (showMarker) {
			try {
				hc.setOption(new OptionPath("/subtitle/style/visibility"), "hidden");
			} catch (Exception e) {
			}
		} else {
			try {
				hc.removeOption(new OptionPath("/subtitle/style/visibility"));
			} catch (Exception e) {
			}
		}
		getChart().injectJS();
		showMarker = !showMarker;
	}

}
