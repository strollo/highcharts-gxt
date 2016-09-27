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
 * Filename: HighChart.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.widgets;

import java.util.List;

import org.gxt.adapters.highcharts.codegen.sections.options.OptionPath;
import org.gxt.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.gxt.adapters.highcharts.codegen.types.HighChartJS;
import org.gxt.adapters.highcharts.codegen.types.SeriesType;
import org.gxt.adapters.highcharts.codegen.utils.IDGen;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.sencha.gxt.widget.core.client.Component;

/**
 * The wrapper of hicharts.
 * 
 * <pre>
 * // The id can be omitted, a random one will be assigned.
 * HighChart hc = new HighChart("mychart");
 * 
 * <b>// 1 - SET OPTIONS</b>
 * try {
 *   // manually set the title of the chart
 *   hc.setOption(new OptionPath("/title/text"), "My first chart");
 *   hc.setOption(new OptionPath("/chart/type"), "spline");
 *   // the credits
 *   hc.setOption(new OptionPath("/credits/enabled"), true);
 *   hc.setOption(new OptionPath("/credits/text"), "Sample of HighChart-GXT");
 *   hc.setOption(new OptionPath("/credits/href"), "http://sourceforge.net/projects/highcharts-gxt/");
 *   // no decimals
 *   hc.setOption(new OptionPath("/xAxis/allowDecimals"), false);
 * 
 *   hc.setOption(new OptionPath("/yAxis/min"), 0);
 * 
 *   hc.setOption(new OptionPath("/xAxis/title/text"), "And the X axis");
 *  hc.setOption(new OptionPath("/yAxis/title/text"), "And the Y axis");
 * 
 *   hc.setOption(new OptionPath("/subtitle/text"), "the subtitle");
 * 
 *   // a not so clean way to manually set options.
 *   // a further usage of setOption on the same path /plotOptions will overwrite these values
 *   // use responsibly :P
 *   hc.setOption(new OptionPath("/plotOptions").addRawOption("spline:{ marker: { enabled:true, radius: 4, lineColor: '#666666', lineWidth: 1 } }");
 * } catch (Exception e) {
 * }
 * 
 * <b>// 2 - ADD VALUES (series)</b>
 * hc.addSeries(new SeriesType(
 *   "first line",
 *   new SeriesType.SeriesDataEntry(5),
 *   new SeriesType.SeriesDataEntry(14),
 *   new SeriesType.SeriesDataEntry(3),
 *   new SeriesType.SeriesDataEntry(78),
 *   new SeriesType.SeriesDataEntry(38)
 * ));
 * 
 * <b>// 3 - Graphical options (if needed).</b>
 * // no offset in the resize
 * hc.setHeightOffset(0);
 * // reduces the refresh delay from 1000 to 100.
 * hc.setResizeDelay(100);
 * // not applied if hc.setResizable(false)
 * 
 * <b>// 4 - done!!! (e.g. insert inside a gxt LayoutContainer)</b>
 * layoutContainer.add(hc);
 * </pre>
 * 
 * @author Daniele Strollo
 */
public class HighChart extends Component {
	public class SelectionRange {
		public double minX;
		public double maxX;
		public double dataMinX;
		public double dataMaxX;

		public double minY;
		public double maxY;
		public double dataMinY;
		public double dataMaxY;
	}

	/**
	 * Diffent categories of highcharts have been defined from 2.x version.
	 * There are: HighCharts and, additionally, the HighStock.
	 * 
	 * @author daniele
	 * 
	 */
	public enum ChartCategory {
		HIGHCHARTS("Chart"), HIGHSTOCK("StockChart");

		private String jsName = null;

		ChartCategory(String jsName) {
			this.jsName = jsName;
		}

		public String getJSName() {
			return this.jsName;
		}
	}

	private static final Object SYNC_MONITOR = new Object();
	private static final String DIV_ID_SUFFIX = "-frame";
	private HighChartJS chartJS = null;
	private int resizeDelay = 1000;
	private static final int ID_LENGTH = 16;
	private boolean autoResize = true;
	private boolean resizeOnWindow = false;
	private String title = "Chart";
	private boolean isRendered = false;
	private ChartCategory category = null;

	/**
	 * If not id is passed from the user, a random one will be generated.
	 * Usually this constructor is to be preferred since otherwise the developer
	 * is responsible to assign unique identifiers to the charts.
	 */
	public HighChart(final ChartCategory category) {
		this(category, IDGen.generateID(ID_LENGTH));
	}

	/**
	 * Creates a new highchart widget with a given id. If the id parameter is
	 * null a random one will be assigned. The id of different charts must be
	 * different otherwise the resize and other management functionalities are
	 * not ensured to work well.
	 */
	public HighChart(final ChartCategory category, final String id) {
		super();
		this.setMonitorWindowResize(true);
		this.category = category;
		this.chartJS = new HighChartJS(category.getJSName(), (id != null) ? id
				: IDGen.generateID(ID_LENGTH));
		Element el = DOM.createElement("div");
		el.setId(chartJS.getId() + DIV_ID_SUFFIX);
		setElement(el);
		
		super.setId(chartJS.getId() + DIV_ID_SUFFIX);
	}

	public HighChart(final ChartCategory category, final String id,
			final ChartType type) {
		this(category, id);
		this.setType(type.toString(), false);
	}

	/**
	 * @deprecated do not use this explicitly. The constructor will provide to
	 *             properly set the id.
	 */
	public void setId(final String id) {
		// avoids the setId - only the super method can be invoked internally.
	}

	/**
	 * If needed to access the chart JS object it is needed its ID (typically of
	 * the form 'chart' + ID where id is the auto-generated random string).
	 * 
	 * This ID can be useful to natively modify the chart. e.g.:
	 * 
	 * <pre>
	 * var theChart = $wnd.getChartById(chartId);
	 * </pre>
	 */
	public final String getChartJSId() {
		return "chart" + this.chartJS.getId();
	}

	public void registerMouseHandler() {
		// observe mouse x and y
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				NativeEvent e = event.getNativeEvent();
				int mouseX = 0;
				int mouseY = 0;
				String type = null;
				type = e.getType();
				mouseX = e.getClientX();
				mouseY = e.getClientY();

				// test
				int wheel = e.getMouseWheelVelocityY();

				// debug
				System.out.println("x: " + mouseX + " y: " + mouseY
						+ " wheel: " + wheel + " type: " + type);
			}
		});

		// observe scroll event, so we can offset x and y for tooltip
		Window.addWindowScrollHandler(new ScrollHandler() {
			public void onWindowScroll(ScrollEvent event) {

				int scrollLeft = event.getScrollLeft();
				int scrollTop = event.getScrollTop();

				// debug
				System.out.println("scrollLeft: " + scrollLeft + " scrollTop: "
						+ scrollTop);
			}
		});
	}

	/**
	 * Gets the list of series to allow their low-level manipulation.
	 */
	public final List<SeriesType> getSeriesList() {
		return this.chartJS.getSeriesList();
	}

	/**
	 * Used to dynamically change the type of a chart. It applies to the chart
	 * the new type and re-inject the JS code inside the container.
	 * <p>
	 * This operation can require few seconds since the chart must be
	 * re-generated, replaced in the JS block and resized.
	 * </p>
	 * 
	 * @param type
	 */
	public final void setType(final String type) {
		this.setType(type, true, true);
	}

	/**
	 * Similar to the {@link HighChart#setType(String)} with an additional
	 * parameter. The propagate requires to apply this type to all series
	 * currently attached to the chart. By default it is considered true.
	 * 
	 * @param type
	 * @param propagate
	 */
	public final void setType(final String type, boolean propagate) {
		if (type != null) {
			this.setType(type, true, propagate);
		}
	}

	/**
	 * Re-applies the injection of JS inside its container. It can be used in
	 * some critic chart modifications (e.g. the axis inversion) that will
	 * require the explicit re-injection of the JS code inside the container.
	 * 
	 * @deprecated discouraged use. For internal use only.
	 */
	public final void injectJS() {
		this.chartJS.doRender();
	}

	/**
	 * This method force the refresh of a chart after its resize.
	 * It is needed by highstock chart since their size is recalculated
	 * after parent container resize.
	 */
	private void forceRefresh() {
		synchronized (SYNC_MONITOR) {
			try {
				// ensures the highchartsJS is rendered
				// only once its container is present and rendered.
				if (this.isRendered) {
					this.chartJS.doRender();
				}
			} catch (Exception e) {
				GWT.log("During setType", e);
			}
		}
	}

	/**
	 * Modifies the type of a chart and, if required to reload the JS, re-inject
	 * its JS code inside the container.
	 * 
	 * @param type
	 * @param reloadJS
	 */
	private void setType(final String type, final boolean reloadJS,
			final boolean propagate) {
		synchronized (SYNC_MONITOR) {
			try {
				this.setOption(new OptionPath("/chart/defaultSeriesType"), type);
				this.setOption(new OptionPath("/chart/type"), type);

				if (propagate) {
					this.chartJS.setSeriesType(type);
				}
				// ensures the highchartsJS is rendered
				// only once its container is present and rendered.
				if (reloadJS && this.isRendered) {
					this.chartJS.doRender();
				}
			} catch (Exception e) {
				GWT.log("During setType", e);
			}
		}
	}

	/**
	 * The first time a chart is shown this method will be invoked. Basically it
	 * applies the resize of the chart.
	 */
	@Override
	protected final void onShow() {
		super.onShow();
		if (this.chartJS != null) {
			this.chartJS.doResize();
		}
	}

	/**
	 * Creation at GWT phase of the chart. This method is responsible to create
	 * an additional div in which the charted will be encapsulated.
	 */
	@Override
	protected final void onAfterFirstAttach() {
		super.onAfterFirstAttach();
		this.chartJS.setDivId(this.getId());
		this.chartJS.doRender();
	}

	/**
	 * The delay to wait before applying the auto resize to the chart. It is
	 * suggested to use a value greater than 100 (also 1000 is suggested) since
	 * the resize must be applied only after it has been handled by parent GWT
	 * components.
	 * 
	 * @param mills
	 */
	public final void setResizeDelay(final int mills) {
		this.resizeDelay = mills;
	}

	/**
	 * Propagates the resize event to the encapsulated chart. The resize will be
	 * applied only if the autoResize flag is true and if the resizeOnWindow is
	 * false, otherwise the {@link HighChart#onWindowResize(int, int)} is used.
	 */
	@Override
	protected final void onResize(final int width, final int height) {
		super.onResize(width, height);
		if (this.autoResize && !resizeOnWindow) {
			this.applyResize();
		}
		if (this.category == ChartCategory.HIGHSTOCK) {
			this.forceRefresh();
		}
	}

	public final void followWindowResize(final boolean resizeOnWindow) {
		this.resizeOnWindow = resizeOnWindow;
	}

	/**
	 * If the chart has autoResize flag enabled, and resizeOnWindow is true this
	 * method is invoked (otherwise the {@link HighChart#onResize(int, int)}
	 * will be used).
	 */
	@Override
	protected final void onWindowResize(final int width, final int height) {
		super.onWindowResize(width, height);
		if (this.autoResize && resizeOnWindow) {
			this.applyResize();
		}
	}

	/**
	 * This method is needed when the autoResize is disabled and demanded to the
	 * container itself.
	 */
	public final void doResize() {
		applyResize();
	}

	/**
	 * Internally applies the delayed resize on the JS native chart. The delay
	 * can be changed by invoking the {@link HighChart#setResizeDelay(int)}
	 * method.
	 */
	private void applyResize() {
		// delayed refresh of chart
		if (this.chartJS != null) {
			Timer timer = new Timer() {
				public void run() {
					chartJS.doResize();
				}
			};
			// Execute the timer to expire 2 seconds in the future
			timer.schedule(this.resizeDelay);
		}
	}

	/**
	 * Sets the title of the chart.
	 */
	public final void setTitle(final String title) {
		synchronized (SYNC_MONITOR) {
			try {
				this.title = title.trim();
				this.setOption(new OptionPath("/title/text"), title);
			} catch (Exception e) {
			}
		}
	}

	public final String getTitle() {
		return this.title;
	}

	/**
	 * Adds a series (data) to a chart.
	 * 
	 * @param series
	 */
	public final void addSeries(final SeriesType series) {
		this.chartJS.addSeries(series);
	}

	public final void setAutoResize(final boolean value) {
		this.autoResize = value;
		this.chartJS.setAutoResize(value);
	}

	/**
	 * 
	 * @param path
	 *            the full path of the option to set (e.g. /chart/renderTo).
	 * @param option
	 *            the value to assign. Artifacts for generally used parameters
	 *            are given in
	 *            {@link org.gxt.adapters.highcharts.codegen.sections.options.types}
	 *            .
	 * @throws Exception
	 */
	public final void setOption(final OptionPath path, final Object option)
			throws Exception {
		this.chartJS.getOptionManager().setOption(path, option);
	}

	public final void removeOption(final OptionPath path) throws Exception {
		this.chartJS.getOptionManager().removeOption(path);
	}

	public final Object getOption(final OptionPath path) throws Exception {
		return this.chartJS.getOptionManager().getOption(path);
	}

	public final void setWidth(final int width) {
		this.chartJS.setWidth(width);
	}

	public final void setHeight(final int height) {
		this.chartJS.setHeight(height);
	}

	/**
	 * The offset to use when recalculating the width of a chart during its
	 * resize.
	 * 
	 * @param widthOffset
	 */
	public final void setWidthOffset(final int widthOffset) {
		this.chartJS.setWidthOffset(widthOffset);
	}

	/**
	 * The offset to use when recalculating the height of a chart during its
	 * resize.
	 * 
	 * @param heightOffset
	 */
	public final void setHeightOffset(final int heightOffset) {
		this.chartJS.setHeightOffset(heightOffset);
	}
	
	public final int getWidthOffset() {
		return this.chartJS.getWidthOffset();
	}
	public final int getHeightOffset() {
		return this.chartJS.getHeightOffset();
	}

}
