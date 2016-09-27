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
 * Filename: ChartFrame.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.widgets.ext;

import java.util.HashMap;
import java.util.Map;
import org.gxt.adapters.highcharts.codegen.sections.options.OptionPath;
import org.gxt.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.gxt.adapters.highcharts.codegen.sections.options.types.ZoomType;
import org.gxt.adapters.highcharts.codegen.utils.ClientConsole;
import org.gxt.adapters.highcharts.widgets.HighChart;
import org.gxt.adapters.highcharts.widgets.ext.plugins.ChartFramePlugin;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgInvertAxis;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgRefreshChart;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgSetChartType;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgSetZoomType;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgSharedTooltip;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgShowHideDataLabels;
import org.gxt.adapters.highcharts.widgets.ext.plugins.impl.PlgShowHideMarker;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Widget;

enum SupportedChartTypes {
	AREA("area"),
	LINE("line"),
	SPLINE("spline"),
	SCATTER("scatter"),
	COLUMN("columns");

	private String label = null;
	SupportedChartTypes(final String label) {
		this.label = label;
	}
	public String getType() {
		return label;
	}
}

class ToolBarHandler {
	private Map<String, TextButton> menus = new HashMap<String, TextButton>();
	private ToolBar bar = new ToolBar();

	public final ToolBar getToolBar() {
		return bar;
	}

	public final void addPlugin(final String pluginPath, final ChartFramePlugin plugin) throws Exception {
		if (pluginPath == null || pluginPath.length() == 0) {
			throw new Exception("The parameter pluginPath is invalid. Null or empty string not allowed.");
		}
		OptionPath path = new OptionPath(pluginPath);
		String [] paths = path.getSubPaths();
		if (paths.length == 0) {
			throw new Exception("No valid path for plugin");
		}
		TextButton btnToAdd = null;
		if (menus.containsKey(paths[0])) {
			btnToAdd = menus.get(paths[0]);
		} else {
			btnToAdd = new TextButton(paths[0]);
			menus.put(paths[0], btnToAdd);
		}
		Menu currMenu = null;
		if (btnToAdd.getMenu() == null) {
			currMenu = new Menu();
			btnToAdd.setMenu(currMenu);
		}
		btnToAdd.getMenu().add(plugin);

		this.bar.add(btnToAdd);
	}
	
	public final void resetToolbar() {
		this.menus.clear();
		this.bar.clear();
	}
}

/**
 * Extended container for {@link HighChart}.
 * Provides a set of menu items for manipulating the encapsulated chart.
 * @author Daniele Strollo
 */
public class ChartFrame extends ContentPanel {
	protected HighChart chart = null;
	private boolean autoResize = true;
	private final ToolBarHandler tbMgr = new ToolBarHandler();
	private int minHeight = 200;
	private int minWidth = 200;
	private boolean inheritHeight = false;
	private boolean inheritWidth = false;
	private final int toolbarHeight = 40;

	public ChartFrame(final HighChart chart) {
		//this.setLayout(new FitLayout());
		this.setBorders(false);
		this.setDeferHeight(true);
		this.chart = chart;
		this.chart.setAutoResize(true);
		this.chart.setBorders(false);
		
		this.setHeaderVisible(false);
		this.setBorders(false);
		this.getHeader().setStyleName("x-hide-panel-header");
		
		this.initToolbar();
		
		BorderLayoutContainer container = new BorderLayoutContainer();
		BorderLayoutContainer.BorderLayoutData northData = new BorderLayoutContainer.BorderLayoutData(this.toolbarHeight);
		MarginData centerData = new MarginData();
		
		// Adjusts chart autoresize offsets
		chart.setHeightOffset(chart.getHeightOffset() + this.toolbarHeight);
		
		container.setNorthWidget(tbMgr.getToolBar(), northData);
		container.setCenterWidget(chart, centerData);
		this.add(container);
	}
	
	/**
	 * Looks up for its ancestors to find the right size matching the
	 * minimal requirements of minHeights. 
	 */
	public final void setInheritHeight(final boolean value) {
		this.inheritHeight = true;
	}
	
	/**
	 * Looks up for its ancestors to find the right size matching the
	 * minimal requirements of minWidth. 
	 */
	public final void setInheritWeight(final boolean value) {
		this.inheritWidth = true;
	}
	
	/**
	 * Sets the minHeight to resolve in recursion solving
	 * of parents height in resize.
	 * <p>
	 * <b>Usually not required.</b>
	 * It is enough to set the size of its container.
	 * For example, if inside a portlet, use the method
	 * portlet.setHeight(...) before adding this component inside it.
	 * </p>
	 */
	public final void setMinHeight(final int minHeight) {
		this.minHeight = minHeight;
	}
	
	/**
	 * Sets the minWidth to resolve in recursion solving
	 * of parents width in resize.
	 * <p>
	 * <b>Usually not required.</b>
	 * It is enough to set the size of its container.
	 * For example, if inside a portlet, use the method
	 * portlet.setHeight(...) before adding this component inside it.
	 * </p>
	 */
	public final void setMinWidth(final int minWidth) {
		this.minWidth = minWidth;
	}
	
	private int getInheritedHeight(Widget parent) {
		int retval = parent.getElement().getClientHeight();
		if (retval < this.minHeight) {
			return this.getInheritedHeight(parent.getParent());
		}
		return retval;
	}
	
	private int getInheritedWidth(Widget parent) {
		int retval = parent.getElement().getClientWidth();
		if (retval < this.minWidth) {
			return this.getInheritedWidth(parent.getParent());
		}
		return retval;
	}
	
	/**
	 * The default toolbar is automatically generated.
	 * If you want to customize it, you need to perform the following options:
	 * <pre>
	 * <b>chartFrame.clearToolbar();</b>
	 * 
	 * // Creates a button Options with a submenu with the following plugins 
	 * this.addPlugin("Options", new PlgInvertAxis(this.chart));
	 * this.addPlugin("Options", new PlgShowHideMarker(this.chart));
	 * this.addPlugin("Options", new PlgShowHideDataLabels(this.chart));
	 * this.addPlugin("Options", new PlgSharedTooltip(this.chart));
	 * </pre>
	 */
	public final void clearToolbar() {
		this.tbMgr.resetToolbar();
	}

	private void initToolbar() {
		try {
			this.addPlugin("Change Type", new PlgSetChartType(new ChartType("line")));
			this.addPlugin("Change Type", new PlgSetChartType(new ChartType("area")));
			this.addPlugin("Change Type", new PlgSetChartType(new ChartType("spline")));
			this.addPlugin("Change Type", new PlgSetChartType(new ChartType("areaspline")));
			this.addPlugin("Change Type", new PlgSetChartType(new ChartType("column")));
			
			this.addPlugin("Refresh", new PlgRefreshChart());

			this.addPlugin("Options", new PlgInvertAxis());
			this.addPlugin("Options", new PlgShowHideMarker(true));
			this.addPlugin("Options", new PlgShowHideDataLabels(true));
			this.addPlugin("Options", new PlgSharedTooltip());

			this.addPlugin("Zoom", new PlgSetZoomType(ZoomType.ZOOM_X));
			this.addPlugin("Zoom", new PlgSetZoomType(ZoomType.ZOOM_Y));
			this.addPlugin("Zoom", new PlgSetZoomType(ZoomType.ZOOM_XY));
			this.addPlugin("Zoom", new PlgSetZoomType(ZoomType.ZOOM_NONE));
		} catch (Exception e) {
			ClientConsole.err("Building toolbar", e);
		}
	}
	
	@Override
	protected void onShow() {
		super.onShow();
	}

	public final void addPlugin(final String pluginPath, final ChartFramePlugin plugin) throws Exception {
		this.tbMgr.addPlugin(pluginPath, plugin);
		plugin.setChart(this.chart);
		plugin.setParentContainer(this);
	}

	public final void refreshChart() {
		// forces the resize of embedded chart.
		// the autoresize of chart is so not needed.
		if (this.chart != null && autoResize) {
			chart.doResize();
		}
	}

	@Override
	protected void onResize(final int width, final int height) {
		super.onResize(width, height);
		
		if (inheritHeight && height < this.minHeight) {
			int theheight = getInheritedHeight(this.getParent());
			this.setHeight(theheight);
		}
		
		if (inheritWidth && height < this.minWidth) {
			int thewidth = getInheritedWidth(this.getParent());
			this.setWidth(thewidth);
		}
		
		refreshChart();
	}
}
