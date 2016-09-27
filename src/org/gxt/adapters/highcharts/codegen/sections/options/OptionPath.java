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
 * Filename: OptionPath.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.sections.options;

import java.util.Vector;
import java.util.List;

/**
 * The path of options in the section blocks of highcharts.
 * <br/>
 * For example the path:
 * <pre>
 * <b>plotOptions/area/marker</b>
 * </pre>
 * will be used to set the highcharts options in the section:
 * <pre>
 * <b>plotOptions</b>: {
 *   <b>area</b>: {
 *     <b>marker</b>: {
 *       <em>// The options will go here</em>
 *     }
 *   }
 * }
 * </pre>
 *
 * The usage of dictionaries to store options comes natural from their tree based
 * structure and brings several advantages:
 * <ul>
 *  <li><b>to set an option</b> it is enough to express its path and its value.
 *  Notice that being a dictionary, multiple set of the same properties will overwrite
 *  the previous value.
 *  </li>
 *  <li><b>the value</b> of an option can be retrieved by simply specifying its path.
 *  </li>
 *  <li>the <b>deletion</b> can impact both a single option or a block of options
 *  by accessing the delete functionality.
 *  For example:
 *  <p>
 * 	Suppose to have the following structure of chart section:
 *  <pre>
 *  plotOptions: {
 *    area: {
 *      fillColor: '#3322cc',
 *        marker: {
 *          enabled: true,
 *          lineColor: '#FFFFFF',
 *        }
 *      }
 *    }
 *  }
 *  </pre>
 *  </p>
 *  <ul>
 *  <li><b>Subtree pruning</b>
 *  <pre>
 *   // Removes the node marker and all its children
 *   <b>hc.deleteOption(new OptionPath("plotOptions/area/marker"));</b>
 *  </pre>
 *  The resulting tree will be:
 *  <pre>
 *  plotOptions: {
 *    area: {
 *      fillColor: '#3322cc',
 *      }
 *    }
 *  }
 *  </pre>
 *  </li>
 *  <li><b>Single option removal</b>
 *  <pre>
 *   // Removes the single option
 *   <b>hc.deleteOption(new OptionPath("plotOptions/area/marker/lineColor"));</b>
 *  </pre>
 *  </li>
 *  </ul>
 *  </li>
 * </ul>
 *
 * See <a href="http://www.highcharts.com/ref/">Highcharts-JS Reference</a>
 * for a complete list of supported sections options.
 * @author Daniele Strollo
 */
public class OptionPath {
	private static final String SEPARATOR = "/";
	private String[] elems = null;
	private String path = null;

	/**
	 * A path must be of the form A/B/C/... or /A/B/C.
	 * </br>
	 * The u
	 * @param path
	 */
	public OptionPath(final String path) {
		if (path != null && path.trim().length() > 0) {
			this.path = path.trim();
			buildSubPaths();
		}
	}

	private void buildSubPaths() {
		List<String> toadd = new Vector<String>();
		for (String subpath : this.path.split(SEPARATOR)) {
			if (subpath != null && subpath.trim().length() > 0) {
				toadd.add(subpath.trim());
			}
		}
		this.elems = toadd.toArray(new String[]{});
	}

	public final String[] getSubPaths() {
		return this.elems;
	}

	public final String[] head() {
		if (this.elems != null && this.elems.length > 0) {
			List<String> retval = new Vector<String>();
			for (int i = 0; i < this.elems.length - 1; i++) {
				retval.add(this.elems[i]);
			}
			return retval.toArray(new String[] {});
		}
		return null;
	}

	public final String last() {
		if (this.elems != null && this.elems.length > 0) {
			return this.elems[this.elems.length - 1];
		}
		return null;
	}

	@Override
	public final String toString() {
		return this.path;
	}
}
