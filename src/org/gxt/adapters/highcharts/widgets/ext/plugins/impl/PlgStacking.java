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
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;
import com.google.gwt.dom.client.NativeEvent;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * @author Daniele Strollo
 *
 */
public class PlgStacking extends ChartFramePlugin {
	private static final String LABEL = "Stacking";

	@SuppressWarnings("deprecation")
	public PlgStacking() {
		super(LABEL);
		
		Menu mnu = new Menu();
		mnu.add(
				new MenuItem("normal") {		
					@Override
					protected void handleClick(NativeEvent be) {
						super.handleClick(be);
						try {
							getChart().setOption(new OptionPath("/plotOptions/series/stacking"), "normal");
							getChart().injectJS();
						} catch (Exception e) {
							ClientConsole.err("stacking", e);
						}
					}
				}
		);
		mnu.add(
				new MenuItem("percent") {
					@Override
					protected void handleClick(NativeEvent be) {
						super.onClick(be);
						try {
							getChart().setOption(new OptionPath("/plotOptions/series/stacking"), "percent");
							getChart().injectJS();
						} catch (Exception e) {
							ClientConsole.err("stacking", e);
						}
					};
				}
		);
		mnu.add(
				new MenuItem("none") {
					@Override
					protected void handleClick(NativeEvent be) {
						super.onClick(be);
						try {
							getChart().removeOption(new OptionPath("/plotOptions/series/stacking"));
							getChart().injectJS();
						} catch (Exception e) {
							ClientConsole.err("stacking", e);
						}
					};
				}
		);

		this.setSubMenu(mnu);
	}

	@Override
	protected void doTask(NativeEvent be) {
	}


}
