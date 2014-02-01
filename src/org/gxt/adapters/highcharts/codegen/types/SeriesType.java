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
 * Filename: SeriesType.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.types;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import org.gxt.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.gxt.adapters.highcharts.codegen.utils.StringUtils;


/**
 * @author Daniele Strollo
 *
 */
public class SeriesType implements Serializable {

	/**
	 * Representations of single points of a line to draw
	 * in the chart.
	 * Points simply require the y value (a number) that can be
	 * also null in case of missing points
	 * (see <a href="http://www.highcharts.com/demo/?example=area-missing">
	 * here</a> for more details).
	 * The x and xLabels are considered optional parameters and are used to
	 * express a precise x position on the axis (e.g. x=5) or the label
	 * to show in the tooltip for the corresponding point (e.g. xLabel="Point1").
	 * @author Daniele Strollo
	 */
	public static class SeriesDataEntry {
		private Number x = null;
		private Number y = null;
		private String xLabel = null;

		public SeriesDataEntry(final Number y) {
			this.y = y;
		}

		public SeriesDataEntry(final Number x, final Number y) {
			this(y);
			this.x = x;
		}

		public SeriesDataEntry(final String xLabel, final Number y) {
			this(y);
			if (xLabel != null) {
				this.xLabel = xLabel.trim();
			}
		}

		public final String getJS() {
			List<String> entries = new Vector<String>();

			// The name (e.g. name: 'Point 1').
			entries.add((xLabel != null && xLabel.length() > 0) ? "name: '" + xLabel + "'" : null);

			// The optional x value (e.g. x: 5).
			entries.add((x != null) ? "x: " + x : null);

			// The optional x value (e.g. x: 5).
			entries.add("y: " + ((y != null) ? y : "null"));

			return
			"{" +
			StringUtils.join(entries, ", ") +
			"}";
		}
	}

	private static final long serialVersionUID = 1L;
	private List<SeriesDataEntry> points = new Vector<SeriesDataEntry>();
	private String name = null;
	private ChartType seriesType = null;

	private SeriesType() {
		// for serialization only
	}

	private SeriesType(final String name) {
		this();
		if (name != null) {
			this.name = name.trim();
		}
	}

	/**
	 * The name of the line to draw (e.g. "My first line").
	 * @param name can be null (a default label will be assigned (e.g. Series 1)).
	 * @param points can be null (but why you require that?).
	 */
	public SeriesType(final String name, final SeriesDataEntry... points) {
		this(name);
		if (points != null) {
			for (SeriesDataEntry point : points) {
				this.points.add(point);
			}
		}
	}

	/**
	 * If a series must be rendered in a form different from the default one
	 * expressed for the chart (e.g. all series are columns and this one line).
	 * @param seriesType
	 */
	public final void setType(final ChartType seriesType) {
		this.seriesType = seriesType;
	}

	public final void addEntry(final SeriesDataEntry entry) {
		this.points.add(entry);
	}

	public final String getJS() {
		StringBuffer retval = new StringBuffer();

		// The header
		retval.append("{ ");
		// The label of the series
		retval.append(((this.name != null && this.name.length() > 0) ?  "name: '" + this.name + "', " : ""));

		// The data
		List<String> entries = new Vector<String>();
		for (SeriesDataEntry point : this.points) {
			entries.add(point.getJS());
		}
		retval.append("data: [" + StringUtils.join(entries, ", ") + "],");

		if (this.seriesType != null) {
			retval.append("type: '" + this.seriesType.toString() + "'");
		}

		// The footer
		retval.append(" }");

		return retval.toString();
	}
}
