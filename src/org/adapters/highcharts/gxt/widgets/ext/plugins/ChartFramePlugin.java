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
 * Filename: ChartFramePlugin.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.gxt.widgets.ext.plugins;

import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.gxt.widgets.HighChart;
import org.adapters.highcharts.gxt.widgets.ext.ChartFrame;
import org.adapters.highcharts.codegen.utils.ClientConsole;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * The basic class for options that can be added to the ChartFrame.
 * @author Daniele Strollo
 */
public abstract class ChartFramePlugin extends MenuItem {
	private HighChart chart = null;
	private ChartFrame parentContainer = null;

	public ChartFramePlugin(final String label) {
		super(label);
	}

	public final HighChart getChart() {
		return this.chart;
	}
	
	public final ChartFrame getParentContainer() {
		return this.parentContainer;
	}

	public void setChart(final HighChart chart) {
		this.chart = chart;
	}
	
	public void setParentContainer(final ChartFrame cf) {
		this.parentContainer = cf;
	}

	/**
	 * The handling of the click event to implement.
	 * @param be
	 */
	abstract protected void doTask(final ComponentEvent be);
	
	@Override
	protected final void onClick(final ComponentEvent be) {
		super.onClick(be);
		this.doTask(be);
	}
	
	protected final String getCurrentType() {
		final HighChart hc = getChart();
		try {
			Object param = hc.getOption(new OptionPath("/chart/type"));
			if (param != null) {
				return param.toString();
			} else {
				param = hc.getOption(new OptionPath("/chart/defaultSeriesType"));
				if (param != null) {
					return param.toString();
				}
			}
		} catch (Exception e) {
			ClientConsole.err("Getting type", e);
		}
		return null;
	}

}
