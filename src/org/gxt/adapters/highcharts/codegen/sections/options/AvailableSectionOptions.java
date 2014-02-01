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
 * Filename: AvailableSectionOptions.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.sections.options;

import java.util.List;
import org.gxt.adapters.highcharts.codegen.sections.Section;
import org.gxt.adapters.highcharts.codegen.sections.SectionChart;
import org.gxt.adapters.highcharts.codegen.sections.SectionCredits;
import org.gxt.adapters.highcharts.codegen.sections.SectionLegend;
import org.gxt.adapters.highcharts.codegen.sections.SectionLoading;
import org.gxt.adapters.highcharts.codegen.sections.SectionSubTitle;
import org.gxt.adapters.highcharts.codegen.sections.SectionTitle;
import org.gxt.adapters.highcharts.codegen.sections.SectionTooltip;
import org.gxt.adapters.highcharts.codegen.sections.SectionXAxis;
import org.gxt.adapters.highcharts.codegen.sections.SectionYAxis;
import org.gxt.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.gxt.adapters.highcharts.codegen.sections.options.types.ZoomType;

/**
 * Mapping of all possible options declared in
 * <a href="http://www.highcharts.com/ref/#chart">
 * highcharts</a>.
 * @author Daniele Strollo
 *
 */
public enum AvailableSectionOptions {
	/*
	 * All available options on chart section
	 */
	/**
	 * Type: Boolean <br/>
	 * When using multiple axis, the ticks of two or more opposite axes will automatically
	 * be aligned by adding ticks to the axis or axes with the least ticks.
	 * This can be prevented by setting alignTicks to false. If the grid lines look messy,
	 * it's a good idea to hide them for the secondary axis by setting gridLineWidth to 0.
	 * Defaults to true.
	 */
	SEC_CHART_ALIGN_TICKS(SectionChart.class, "alignTicks", Boolean.class, true),
	/**
	 * Type: Boolean <br/>
	 * Set the overall animation for all chart updating.
	 */
	SEC_CHART_ANIMATION(SectionChart.class, "animation", Boolean.class, true),
	/**
	 * Type: String <br/>
	 * The background color or gradient for the outer chart area. Defaults to "#FFFFFF".
	 */
	SEC_CHART_BG_COLOR(SectionChart.class, "backgroundColor", String.class, null),
	/**
	 * Type: String <br/>
	 * The color of the outer chart border. The border is painted using vector graphic techniques
	 * to allow rounded corners. Defaults to "#4572A7".
	 */
	SEC_CHART_BORDER_COLOR(SectionChart.class, "borderColor", String.class, "#4572A7"),
	/**
	 * Type: Integer <br/>
	 * The corner radius of the outer chart border. Defaults to 5.
	 */
	SEC_CHART_BORDER_RADIUS(SectionChart.class, "borderRadius", Integer.class, 5),
	SEC_CHART_BORDER_WIDTH(SectionChart.class, "borderWidth", Integer.class, 0),
	SEC_CHART_CLASS_NAME(SectionChart.class, "className", String.class, null),
	SEC_CHART_DEFAULT_SERIES_TYPE(SectionChart.class, "defaultSeriesType", ChartType.class, ChartType.SPLINE),
	SEC_CHART_HEIGHT(SectionChart.class, "height", String.class, null),
	SEC_CHART_IGNORE_HIDDEN_SERIES(SectionChart.class, "ignoreHiddenSeries", Boolean.class, true),
	SEC_CHART_INVERTED(SectionChart.class, "inverted", Boolean.class, false),
	SEC_CHART_MARGIN_TOP(SectionChart.class, "marginTop", Integer.class, 0),
	SEC_CHART_MARGIN_RIGHT(SectionChart.class, "marginRight", Integer.class, 50),
	SEC_CHART_MARGIN_BOTTOM(SectionChart.class, "marginBottom", Integer.class, 70),
	SEC_CHART_PLOT_BG_COLOR(SectionChart.class, "plotBackgroundColor", String.class, null),
	SEC_CHART_PLOT_BG_IMAGE(SectionChart.class, "plotBackgroundImage", String.class, null),
	SEC_CHART_PLOT_BORDER_COLOR(SectionChart.class, "plotBorderColor", String.class, "#C0C0C0"),
	SEC_CHART_PLOT_BORDER_WIDTH(SectionChart.class, "plotBorderWidth", Integer.class, 0),
	SEC_CHART_PLOT_SHADOW(SectionChart.class, "plotShadow", Boolean.class, false),
	SEC_CHART_REFLOW(SectionChart.class, "reflow", Boolean.class, true),
	SEC_CHART_SHADOW(SectionChart.class, "shadow", Boolean.class, false),
	SEC_CHART_SHOW_AXES(SectionChart.class, "showAxes", Boolean.class, false),
	SEC_CHART_SPACING_TOP(SectionChart.class, "spacingTop", Integer.class, 10),
	SEC_CHART_SPACING_RIGHT(SectionChart.class, "spacingRight", Integer.class, 10),
	SEC_CHART_SPACING_BOTTOM(SectionChart.class, "spacingBottom", Integer.class, 15),
	SEC_CHART_SPACING_LEFT(SectionChart.class, "spacingLeft", Integer.class, 10),
	SEC_CHART_STYLE(SectionChart.class, "style", String.class, null),
	SEC_CHART_TYPE(SectionChart.class, "type", ChartType.class, null),
	SEC_CHART_ZOOM(SectionChart.class, "zoomType", ZoomType.class, null),


	/*
	 * All available options on credits section
	 */
	SEC_CREDITS_ENABLED(SectionCredits.class, "enabled", Boolean.class, false),
	SEC_CREDITS_HREF(SectionCredits.class, "href", String.class, null),
	SEC_CREDITS_TEXT(SectionCredits.class, "text", String.class, null),

	/*
	 * All available options on legend section
	 */
	SEC_LEGEND_ALIGN(SectionLegend.class, "align", String.class, "center"),
	SEC_LEGEND_BG_COLOR(SectionLegend.class, "backgroundColor", String.class, null),
	SEC_LEGEND_BORDER_COLOR(SectionLegend.class, "borderColor", String.class, "#909090"),
	SEC_LEGEND_BORDER_RADIUS(SectionLegend.class, "borderRadius", Integer.class, 5),
	SEC_LEGEND_BORDER_WITH(SectionLegend.class, "borderWidth", Integer.class, 1),
	SEC_LEGEND_ENABLED(SectionLegend.class, "enabled", Boolean.class, true),
	SEC_LEGEND_FLOATING(SectionLegend.class, "floating", Boolean.class, false),
	SEC_LEGEND_STYLE(SectionLegend.class, "style", String.class, null),
	SEC_LEGEND_HIDDEN_STYLE(SectionLegend.class, "itemHiddenStyle", String.class, null),
	SEC_LEGEND_HOVER_STYLE(SectionLegend.class, "itemHoverStyle", String.class, null),
	SEC_LEGEND_ITEM_STYLE(SectionLegend.class, "itemStyle", String.class, null),
	SEC_LEGEND_ITEM_WIDTH(SectionLegend.class, "itemWidth", String.class, null),
	SEC_LEGEND_LAYOUT(SectionLegend.class, "layout", String.class, "horizontal"),
	SEC_LEGEND_LINE_HEIGHT(SectionLegend.class, "lineHeight", Integer.class, 16),
	SEC_LEGEND_MARGIN(SectionLegend.class, "margin", Integer.class, 15),
	SEC_LEGEND_REVERSED(SectionLegend.class, "reversed", Boolean.class, false),
	SEC_LEGEND_SHADOW(SectionLegend.class, "shadow", Boolean.class, false),
	SEC_LEGEND_X(SectionLegend.class, "x", Integer.class, 15),
	SEC_LEGEND_Y(SectionLegend.class, "y", Integer.class, 0),
	SEC_LEGEND_SYMBOL_PADDING(SectionLegend.class, "symbolPadding", Integer.class, 5),
	SEC_LEGEND_SYMBOL_WIDTH(SectionLegend.class, "symbolWidth", Integer.class, 30),
	SEC_LEGEND_VERTICAL_ALIGN(SectionLegend.class, "verticalAlign", String.class, "bottom"),
	SEC_LEGEND_WIDTH(SectionLegend.class, "width", String.class, null),

	/*
	 * All available options on loading section
	 */
	SEC_LOADING_HIDE_DURATION(SectionLoading.class, "hideDuration", Integer.class, 100),
	SEC_LOADING_LABEL_STYLE(SectionLoading.class, "labelStyle", String.class, null),
	SEC_LOADING_SHOW_DURATION(SectionLoading.class, "showDuration", Integer.class, 100),
	SEC_LOADING_STYLE(SectionLoading.class, "style", String.class, null),

	/*
	 * All available options on tooltip section
	 */
	SEC_TOOLTIP_BG_COLOR(SectionTooltip.class, "backgroundColor", String.class, "rgba(255, 255, 255, .85)"),
	SEC_TOOLTIP_BORDER_COLOR(SectionTooltip.class, "borderColor", String.class, "auto"),
	SEC_TOOLTIP_BORDER_RADIUS(SectionTooltip.class, "borderRadius", Integer.class, 5),
	SEC_TOOLTIP_BORDER_WIDTH(SectionTooltip.class, "borderWidth", Integer.class, 2),
	SEC_TOOLTIP_CROSS_HAIRS(SectionTooltip.class, "crosshairs", String.class, "[true, true]"),
	SEC_TOOLTIP_ENABLED(SectionTooltip.class, "enabled", Boolean.class, true),
	SEC_TOOLTIP_SHADOW(SectionTooltip.class, "shadow", Boolean.class, true),
	SEC_TOOLTIP_SHARED(SectionTooltip.class, "shared", Boolean.class, false),
	SEC_TOOLTIP_SNAP(SectionTooltip.class, "snap", Integer.class, 10),
	SEC_TOOLTIP_STYLE(SectionLoading.class, "style", String.class, null),

	/*
	 * All available options on title section
	 */
	SEC_TITLE_ALIGN(SectionTitle.class, "align", String.class, "center"),
	SEC_TITLE_FLOATING(SectionTitle.class, "floating", Boolean.class, false),
	SEC_TITLE_MARGIN(SectionTitle.class, "margin", Integer.class, 15),
	SEC_TITLE_TEXT(SectionTitle.class, "text", String.class, "Chart title"),
	SEC_TITLE_STYLE(SectionTitle.class, "style", String.class, null),
	SEC_TITLE_VERTICAL_ALIGN(SectionTitle.class, "verticalAlign", String.class, "top"),
	SEC_TITLE_X(SectionTitle.class, "x", Integer.class, 0),
	SEC_TITLE_Y(SectionTitle.class, "y", Integer.class, 25),

	/*
	 * All available options on subtitle section
	 */
	SEC_SUBTITLE_ALIGN(SectionSubTitle.class, "align", String.class, "center"),
	SEC_SUBTITLE_FLOATING(SectionSubTitle.class, "floating", Boolean.class, false),
	SEC_SUBTITLE_TEXT(SectionSubTitle.class, "text", String.class, null),
	SEC_SUBTITLE_STYLE(SectionSubTitle.class, "style", String.class, null),
	SEC_SUBTITLE_VERTICAL_ALIGN(SectionSubTitle.class, "verticalAlign", String.class, "top"),
	SEC_SUBTITLE_X(SectionSubTitle.class, "x", Integer.class, 0),
	SEC_SUBTITLE_Y(SectionSubTitle.class, "y", Integer.class, 40),

	/*
	 * All available options on x-axis section
	 */
	SEC_XAXIS_ALLOW_DECIMALS(SectionXAxis.class, "allowDecimals", Boolean.class, false),
	/**
	 * Type: List<String>
	 * If categories are present for the xAxis, names are used instead of numbers for that axis.
	 * <br/>
	 * Corresponds to the list of labels (e.g. First Period, Second Period, ...)
	 * to show on the X-axis in place of the x value (1,2,3...).
	 * <br/><b>Example:</b>
	 * <pre>
	 *
	 * {@link org.gxt.adapters.highcharts.codegen.types.HighChartJS} hc = ...;
	 * // Add labels on X-axis (will replace the x value).
	 * {@link SectionXAxis} sx = (SectionXAxis) hc.getSection(SectionKeys.SEC_XAXIS);
	 * try {
	 *   List&lt;String&gt; labels = new Vector&lt;String&gt;();
	 *   labels.add("X1");
	 *   labels.add("X2");
	 *   labels.add("X3");
	 *   sx.addOption({@link AvailableOptions#SEC_XAXIS_CATEGORIES}, labels);
	 * } catch (Exception e) {
	 *   e.printStackTrace();
	 * }
	 * </pre>
	 */
	SEC_XAXIS_CATEGORIES(SectionXAxis.class, "categories", List.class, null),
	SEC_XAXIS_END_ON_TICK(SectionXAxis.class, "endOnTick", Boolean.class, false),
	SEC_XAXIS_MIN(SectionXAxis.class, "min", Number.class, null),
	SEC_XAXIS_MAX(SectionXAxis.class, "max", Number.class, null),
	/**
	 * Type: Boolean | Required: false <br/>
	 * Whether to reverse the axis so that the highest number is closest to origin.
	 * If the chart is inverted, the x axis is reversed by default.
	 */
	SEC_XAXIS_REVERSED(SectionXAxis.class, "reversed", Boolean.class, false),



	/*
	 * All available options on y-axis section
	 */
	SEC_YAXIS_END_ON_TICK(SectionYAxis.class, "endOnTick", Boolean.class, true),
	SEC_YAXIS_GRID_LINE_WIDTH(SectionYAxis.class, "gridLineWidth", Integer.class, 1),
	SEC_YAXIS_LINE_WIDTH(SectionYAxis.class, "lineWidth", Integer.class, 0),
	SEC_YAXIS_START_ON_TICK(SectionYAxis.class, "startOnTick", Boolean.class, true),
	SEC_YAXIS_START_ALLOW_DECIMALS(SectionYAxis.class, "allowDecimals", Boolean.class, true),
	SEC_YAXIS_MIN(SectionYAxis.class, "min", Number.class, null),
	SEC_YAXIS_MAX(SectionYAxis.class, "max", Number.class, null),
	SEC_YAXIS_OPPOSITE(SectionYAxis.class, "opposite", Boolean.class, false),
	SEC_YAXIS_REVERSED(SectionYAxis.class, "reversed", Boolean.class, false);

	private Class<? extends Section> section = null;
	private SectionOption option = null;

	AvailableSectionOptions(
			final Class<? extends Section> section,
			final String name,
			final Class<?> type,
			final Object value) {
		this.section = section;
		this.option = new SectionOption(name, type, value);
	}

	public final Class<? extends Section> getSectionClass() {
		return this.section;
	}

	public SectionOption getOption() {
		return this.option;
	}
}
