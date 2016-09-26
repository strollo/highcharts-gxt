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

import org.gxt.adapters.highcharts.codegen.sections.Section;
import org.gxt.adapters.highcharts.codegen.sections.Section.SectionKeys;
import org.gxt.adapters.highcharts.codegen.sections.options.AvailableSectionOptions;
import org.gxt.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.gxt.adapters.highcharts.codegen.types.HighChartJS;
import org.gxt.adapters.highcharts.codegen.types.SeriesType;
import org.gxt.adapters.highcharts.codegen.utils.IDGen;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.widget.core.client.Component;

/**
 * The wrapper of hicharts.
 * <pre>
 * // The id can be omitted, a random one will be assigned.
 * HighChart hc = new HighChart("mychart");
 *
 * <b>// 1 - SET OPTIONS</b>
 * try {
 *   // manually set the title of the chart
 *   hc.getSection(SectionKeys.SEC_TITLE).addRawOption("text: 'My first chart'");
 *   hc.getSection(SectionKeys.SEC_CHART).addOption(AvailableSectionOptions.SEC_CHART_DEFAULT_SERIES_TYPE, ChartType.SPLINE);
 *   // removes the credits
 *   hc.getSection(SectionKeys.SEC_CREDITS).addOption(AvailableSectionOptions.SEC_CREDITS_ENABLED, false);
 *   // no decimals
 *   hc.getSection(SectionKeys.SEC_XAXIS).addOption(AvailableSectionOptions.SEC_XAXIS_ALLOW_DECIMALS, false);
 *
 *   hc.getSection(SectionKeys.SEC_YAXIS).addOption(AvailableSectionOptions.SEC_YAXIS_MIN, 0);
 *
 *   ((SectionXAxis) hc.getSection(SectionKeys.SEC_XAXIS)).setTitle("And the X axis");
 *   ((SectionYAxis) hc.getSection(SectionKeys.SEC_YAXIS)).setTitle("The Y is here");
 *
 *   hc.getSection(SectionKeys.SEC_SUBTITLE).addOption(AvailableSectionOptions.SEC_SUBTITLE_TEXT, "the subtitle");
 *
 *   hc.getSection(SectionKeys.SEC_PLOT_OPTIONS).addRawOption("spline:{ marker: { enabled:true, radius: 4, lineColor: '#666666', lineWidth: 1 } }");
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
 * @author Daniele Strollo
 */
public class HighChart extends Component {
	private static final String DIV_ID_SUFFIX = "-frame";
	private HighChartJS chartJS = null;
	private int resizeDelay = 1000;
	private static final int ID_LENGTH = 16;
	private boolean autoResize = true;
	private boolean resizeOnWindow = false;

	/**
	 * If not id is passed from the user, a random one will be generated.
	 */
	public HighChart() {
		this(IDGen.generateID(ID_LENGTH));
	}

	/**
	 * Creates a new highchart widget with a given id.
	 * If the id parameter is null a random one will be assigned.
	 */
	public HighChart(final String id) {
		super();
		this.setMonitorWindowResize(true);
		this.chartJS = new HighChartJS((id != null) ? id : IDGen.generateID(ID_LENGTH));
		
		Element el = DOM.createElement("div");
		el.setId(chartJS.getId() + DIV_ID_SUFFIX);
		setElement(el);
		
		super.setId(chartJS.getId() + DIV_ID_SUFFIX);
	}

	public HighChart(final ChartType type) {
		this();
		this.setType(type, false);
	}

	public HighChart(final String id, final ChartType type) {
		this(id);
		this.setType(type, false);
	}

	/**
	 * @deprecated do not use this explicitly.
	 * The constructor will provide to properly set the id.
	 */
	public void setId(final String id) {
		// avoids the setId - only the super method can be invoked internally.
	}

	private void setType(final ChartType type, final boolean reloadJS) {
		try {
			this.getSection(SectionKeys.SEC_CHART).addOption(
					AvailableSectionOptions.SEC_CHART_DEFAULT_SERIES_TYPE,
					type);
			if (reloadJS) {
				this.chartJS.doRender();
				this.applyResize();
			}
		} catch (Exception e) {
			GWT.log("During setType", e);
		}
	}

	@Override
	protected final void onShow() {
		super.onShow();
		if (this.chartJS != null) {
			this.chartJS.doResize();
		}
	}

	@Override
	protected final void onAfterFirstAttach() {
		/*
		Element el = DOM.createElement("div");
		el.setId(chartJS.getId() + DIV_ID_SUFFIX);
		setElement(el);
		*/
		super.onAfterFirstAttach();
		this.chartJS.setDivId(this.getId());
		this.chartJS.doRender();
	}

	/**
	 * The delay to wait before applying the auto resize to the chart.
	 * It is suggested to use a value greater than 100 (also 1000 is suggested)
	 * since the resize must be applied only after it has been handled by parent
	 * gxt components.
	 * @param mills
	 */
	public final void setResizeDelay(final int mills) {
		this.resizeDelay = mills;
	}

	/**
	 * Propagates the resize event to the encapsulated chart.
	 */
	@Override
	protected final void onResize(final int width, final int height) {
		//super.onResize(width, height);
		if (this.autoResize && !resizeOnWindow) {
			this.applyResize();
		}
	}

	public final void followWindowResize(final boolean resizeOnWindow) {
		this.resizeOnWindow = resizeOnWindow;
	}

	@Override
	protected final void onWindowResize(final int width, final int height) {
		super.onWindowResize(width, height);
		if (this.autoResize && resizeOnWindow) {
			this.applyResize();
		}
	}

	/**
	 * This method is needed when the autoResize is disabled
	 * and demanded to the container itself.
	 */
	public final void doResize() {
		applyResize();
	}

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

	public final void addSeries(final SeriesType series) {
		this.chartJS.addSeries(series);
	}

	public final void setAutoResize(final boolean value) {
		this.autoResize = value;
	}

	public final Section getSection(final SectionKeys key) {
		return this.chartJS.getSection(key);
	}

	public final void setWidthOffset(final int widthOffset) {
		this.chartJS.setWidthOffset(widthOffset);
	}

	public final void setHeightOffset(final int heightOffset) {
		this.chartJS.setHeightOffset(heightOffset);
	}
}
