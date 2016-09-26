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
 * Filename: OptionManager.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.codegen.sections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import org.adapters.highcharts.codegen.sections.options.OptionPath;
import org.adapters.highcharts.codegen.sections.options.types.ChartType;
import org.adapters.highcharts.codegen.sections.options.types.RawStringType;
import org.adapters.highcharts.codegen.utils.ClientConsole;
import org.adapters.highcharts.codegen.utils.StringUtils;

/**
 * <p>
 * Utility class used to store options inside the highchart artifact.
 * Once an highchart component should be rendered, its javascript
 * representation is generated according to the parameters registered
 * inside this store.
 * </p>
 * <p>
 * The {@link OptionManager} is not directly accessed by the user, the
 * parameters to the charts are instead accessible through the methods:
 * <ul>
 * <li>
 * {@link org.adapters.highcharts.gxt.widgets.HighChart#setOption(OptionPath, Object)},
 * </li>
 * <li>
 * {@link org.adapters.highcharts.gxt.widgets.HighChart#getOption(OptionPath)}
 * </li>
 * <li>
 * {@link org.adapters.highcharts.gxt.widgets.HighChart#removeOption(OptionPath)}
 * </li>
 * </ul>
 *
 * @see OptionPath
 * @see org.adapters.highcharts.gxt.widgets.HighChart
 * @author Daniele Strollo
 */
public class OptionManager {
	private final Map<String, Object> options = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public final synchronized boolean contains(final OptionPath path) throws Exception {
		if (path == null || path.getSubPaths() == null) {
			throw new Exception("Invalid path: null or empty value not allowed");
		}
		
		String[] head = path.head();
		if (head == null) {
			return false;
		}

		Map<String, Object> root = options;
		for (final String subpath : head) {
			if (root.containsKey(subpath)) {
				root = (Map<String, Object>) root.get(subpath);
			} else {
				return false;
			}
		}

		String key = path.last();

		return root.containsKey(key);
	}
	
	@SuppressWarnings("unchecked")
	public final synchronized void setOption(final OptionPath path, final Object option) throws Exception {
		if (path == null || path.getSubPaths() == null) {
			throw new Exception("Invalid path: null or empty value not allowed");
		}
		if (option == null) {
			throw new Exception("Invalid option: null not allowed");
		}

		String[] head = path.head();
		if (head == null) {
			return;
		}
		String key = path.last();

		Map<String, Object> root = options;
		for (final String subpath : head) {
			if (!root.containsKey(subpath)) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				root.put(subpath, tmp);
				root = tmp;
			} else {
				root = (Map<String, Object>) root.get(subpath);
			}
		}

		root.put(key, option);
	}

	@SuppressWarnings("unchecked")
	public final synchronized void removeOption(final OptionPath path) throws Exception {
		if (path == null || path.getSubPaths() == null) {
			throw new Exception("Invalid path: null or empty value not allowed");
		}

		String[] head = path.head();
		if (head == null) {
			return;
		}

		Map<String, Object> root = options;
		for (final String subpath : head) {
			if (root.containsKey(subpath)) {
				root = (Map<String, Object>) root.get(subpath);
			} else {
				return;
			}
		}

		root.remove(path.last());
	}

	@SuppressWarnings("unchecked")
	public final synchronized Object getOption(final OptionPath path) throws Exception {
		if (path == null || path.getSubPaths() == null) {
			throw new Exception("Invalid path: null or empty value not allowed");
		}

		String[] head = path.head();
		if (head == null) {
			return null;
		}

		Map<String, Object> root = options;
		for (final String subpath : head) {
			if (root.containsKey(subpath)) {
				root = (Map<String, Object>) root.get(subpath);
			} else {
				return null;
			}
		}

		String key = path.last();

		return root.get(key);
	}

	/**
	 * Converts a Map into a String representation for JS.
	 */
	@SuppressWarnings("unchecked")
	private String mapToString(final Object param) {
		try {
			StringBuffer retval = new StringBuffer();
			retval.append("{");
			for (Entry<String, Object> entry : ((Map<String, Object>) param).entrySet()) {
				retval.append(entry.getKey() + ": " + convertParam(entry.getValue()) + ", ");
			}
			retval.append("}");
			return retval.toString();
		} catch (Exception e) {
			ClientConsole.err("Converting parameter", e);
		}
		return null;
	}

	/**
	 * Converts lists to String.
	 */
	private final String listToString(final Object param) {
		StringBuffer retval = new StringBuffer();
		retval.append("[");
		List<String> elems = new Vector<String>();
		for (Object elem : (List<?>) param) {
			elems.add(convertParam(elem));
		}
		retval.append(StringUtils.join(elems, ", "));
		retval.append("]");
		return retval.toString();
	}

	/**
	 * Converts a primitive array into its String representation for JS.
	 * @param param
	 * @return
	 */
	private String arrayToString(final Object param) {
		try {
			StringBuffer retval = new StringBuffer();
			retval.append("[");
			List<String> elems = new Vector<String>();
			Class<?> paramElType = param.getClass().getComponentType();
			// Array of primitive types
			if (paramElType.isPrimitive()) {
				if (paramElType == boolean.class) {
					for (boolean elem : (boolean[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == byte.class) {
					for (byte elem : (byte[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == char.class) {
					for (char elem : (char[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == short.class) {
					for (short elem : (short[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == int.class) {
					for (int elem : (int[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == long.class) {
					for (long elem : (long[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == float.class) {
					for (float elem : (float[]) param) {
						elems.add(convertParam(elem));
					}
				}
				if (paramElType == double.class) {
					for (double elem : (double[]) param) {
						elems.add(convertParam(elem));
					}
				}
			} 
			// Array of Objects
			else {
				for (Object elem : (Object[]) param) {
					elems.add(convertParam(elem));
				}
			}

			retval.append(StringUtils.join(elems, ", "));
			retval.append("]");
			return retval.toString();
		} catch (Exception e) {
			ClientConsole.err("Cannot convert array", e);
		}
		return null;
	}

	/**
	 * The representation of the value in js.
	 */
	private String convertParam(final Object param) {
		if (param == null) {
			return "";
		}
		if (param instanceof Number || param instanceof Boolean) {
			return param.toString();
		}
		if (param instanceof RawStringType) {
			return param.toString();
		}
		if (param instanceof ChartType) {
			return "'" + param.toString() + "'";
		}
		if (param instanceof Map) {
			return mapToString(param);
		}
		// primitive arrays
		if (param.getClass().isArray()) {
			return arrayToString(param);
		}
		if (param instanceof List) {
			return listToString(param);
		}
		return "'" + param + "'";
	}

	/**
	 * Each option has a corresponding javascript code that will
	 * be replaced in the chart template at rendering phase.
	 * @return the corresponding javascript entry for the option.
	 */
	public final String getJS() {
		if (this.options.size() == 0) { // && this.rawOptions.size() == 0) {
			return null;
		}

		StringBuilder retval = new StringBuilder();
		retval.append(convertParam(this.options));
		retval.trimToSize();
		String toclean = retval.toString();
		if (toclean != null && toclean.startsWith("{")) {
			toclean = toclean.substring(1);
		}
		if (toclean != null && toclean.endsWith("}")) {
			toclean = toclean.substring(0, toclean.length() - 2);
		}
		return toclean;
	}
}
