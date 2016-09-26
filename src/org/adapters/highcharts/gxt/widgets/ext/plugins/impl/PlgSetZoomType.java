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
 * Filename: PlgSetZoomType.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.gxt.widgets.ext.plugins.impl;

import org.adapters.highcharts.gxt.widgets.ext.plugins.ChartFramePlugin;
import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.codegen.sections.options.types.ZoomType;
import org.adapters.highcharts.codegen.utils.ClientConsole;

import com.extjs.gxt.ui.client.event.ComponentEvent;

/**
 * @author Daniele Strollo
 *
 */
public class PlgSetZoomType extends ChartFramePlugin {
	private ZoomType zoom = null;

	public PlgSetZoomType(final ZoomType zoomType) {
		super(zoomType.name());
		this.zoom = zoomType;
	}
	
	/* (non-Javadoc)
	 * @see org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin#doTask(com.extjs.gxt.ui.client.event.ComponentEvent)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void doTask(ComponentEvent be) {
		try {
			getChart().setOption(new OptionPath("/chart/zoomType"), zoom.toString());
			// the whole javascript must be rendered
			getChart().injectJS();
		} catch (Exception e) {
			ClientConsole.err("Setting zoom", e);
		}
	}

}
