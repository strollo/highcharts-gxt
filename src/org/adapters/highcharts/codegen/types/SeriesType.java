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

package org.adapters.highcharts.codegen.types;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;
import org.adapters.highcharts.codegen.sections.OptionManager;
import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.codegen.sections.options.types.RawStringType;
import org.adapters.highcharts.codegen.utils.StringUtils;


/**
 * @author Daniele Strollo
 *
 */
public class SeriesType implements Serializable {

	public static class SeriesDataEntryOpt {
		private String key = null;
		private Object value = null;
		
		public SeriesDataEntryOpt(final String key, final Object value) {
			this.key = key;
			this.value = value;
		}
		
		public final String getKey() {
			return this.key;
		}
		
		public final Object getValue() {
			return this.value;
		}
	}
	
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
		private String pointName = null;
		private HashMap<String, Object> params = new HashMap<String, Object>();

		public SeriesDataEntry(final Number y, final SeriesDataEntryOpt... opts) {
			this.y = y;
			for (SeriesDataEntryOpt entry : opts) {
				this.setSubOption(entry.getKey(), entry.getValue());
			}
		}

		public SeriesDataEntry(final Number x, final Number y, SeriesDataEntryOpt... opts) {
			this(y, opts);
			this.x = x;
		}

		public SeriesDataEntry(final String pointName, final Number y, SeriesDataEntryOpt... opts) {
			this(y, opts);
			if (pointName != null) {
				this.pointName = pointName.trim();
			}
		}

		private String convertParam(final Object param) {
			if (param == null) {
				return "null";
			}
			if (param instanceof Number || param instanceof Boolean) {
				return param.toString();
			}
			if (param instanceof RawStringType) {
				return param.toString();
			}
			return "'" + param.toString() + "'";
		}

		public final void setSubOption(final String key, final Object param) {
			if (key == null || key.trim().length() == 0) {
				return;
			}
			this.params.put(key.trim(), param);
		}

		public final void removeSubOption(final String key, final Object param) {
			if (key == null || key.trim().length() == 0) {
				return;
			}
			this.params.remove(key.trim());
		}

		public final String getJS() {
			List<String> entries = new Vector<String>();

			// The name (e.g. name: 'Point 1').
			entries.add((pointName != null && pointName.length() > 0) ? "name: '" + pointName + "'" : null);

			// The optional x value (e.g. x: 5).
			entries.add((x != null) ? "x: " + x : null);

			// The optional x value (e.g. x: 5).
			entries.add("y: " + ((y != null) ? y : "null"));

			if (params != null && params.size() > 0) {
				for (Entry<String, Object> elems : params.entrySet()) {
					entries.add(elems.getKey() + ": " + convertParam(elems.getValue()));
				}
			}
			return
			"{" +
			StringUtils.join(entries, ", ") +
			"}";
		}
	}

	private static final long serialVersionUID = 1L;
	private List<SeriesDataEntry> series = new Vector<SeriesDataEntry>();
	private String name = null;
	private String seriesType = null;
	private OptionManager options = new OptionManager();

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
	 * @param series can be null (but why you require that?).
	 */
	public SeriesType(final String name, final SeriesDataEntry... series) {
		this(name);
		if (series != null) {
			for (SeriesDataEntry point : series) {
				this.series.add(point);
			}
		}
	}

	/**
	 * Options accepted are in the range Number, String,
	 * RawStringType, ChartType. 
	 * @param path
	 * @param option
	 * @throws Exception
	 */
	public final void setSubOption(final String path, final Object option) throws Exception {
		this.options.setOption(new OptionPath(path), option);
	}

	public final void removeSubOption(final String path) throws Exception {
		this.options.removeOption(new OptionPath(path));
	}

	public final Object getSubOption(final String path) throws Exception {
		return this.options.getOption(new OptionPath(path));
	}

	/**
	 * If a series must be rendered in a form different from the default one
	 * expressed for the chart (e.g. all series are columns and this one line).
	 * @param seriesType
	 */
	public final void setType(final String seriesType) {
		if (seriesType != null && seriesType.trim().length() > 0)
			this.seriesType = seriesType.trim();
	}

	public final void addEntry(final SeriesDataEntry entry) {
		this.series.add(entry);
	}

	public final String getJS() {
		StringBuffer retval = new StringBuffer();

		// The header
		retval.append("{ ");
		// The label of the series
		retval.append(((this.name != null && this.name.length() > 0) ?  "name: '" + this.name + "', " : ""));

		// The data
		List<String> entries = new Vector<String>();
		for (SeriesDataEntry point : this.series) {
			entries.add(point.getJS());
		}
		retval.append("data: [" + StringUtils.join(entries, ", ") + "],");

		if (this.seriesType != null) {
			retval.append("type: '" + this.seriesType.toString() + "',");
		}

		if (options != null && options.getJS() != null) {
			retval.append(options.getJS());
		}

		// The footer
		retval.append(" }");

		return retval.toString();
	}
}
