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
 * Filename: PlgSetChartType.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.widgets.ext.plugins.impl;

import org.gxt.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;
import com.google.gwt.dom.client.NativeEvent;

/**
 * @author Daniele Strollo
 *
 */
public class PlgSetChartType extends ChartFramePlugin {
	private ChartType type = null;
	
	public PlgSetChartType(final ChartType type) {
		super(type.toString());
		this.type = type;
	}
	
	@Override
	protected void doTask(NativeEvent be) {
		getChart().setType(type.toString());
	}

}
