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
 * Filename: ZoomType.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.codegen.sections.options.types;

/**
 * List of possible values a Zoom can assume.
 * @author Daniele Strollo
 */
public enum ZoomType {
	ZOOM_X("x"),
	ZOOM_Y("y"),
	ZOOM_XY("xy"),
	ZOOM_NONE("");

	private String axis = null;

	ZoomType(final String axis) {
		this.axis = axis;
	}

	@Override
	public String toString() {
		return this.axis;
	}
}
