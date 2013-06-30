/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.service.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class DataSource implements JRRewindableDataSource {
	private String[] columns;
	private List<Map<String, Object>> values;
	private Iterator<Map<String, Object>> iterator;
	private Map<String, Object> currentRecord;

	public DataSource(String ...columns) {
		this.columns = columns;
		this.values = new ArrayList<Map<String, Object>>();
	}

	public void add(Object ...values) {
		Map<String, Object> row = new HashMap<String, Object>();
		for (int i = 0; i < values.length; i++) {
			row.put(columns[i], values[i]);
		}
		this.values.add(row);
	}

	public Object getFieldValue(JRField field) throws JRException {
		return currentRecord.get(field.getName());
	}

	public boolean next() throws JRException {
		if (iterator == null) {
			this.iterator = values.iterator();
		}
		boolean hasNext = iterator.hasNext();
		if (hasNext) {
			currentRecord = iterator.next();
		}
		return hasNext;
	}

	public void moveFirst() throws JRException {
		this.iterator = null;
	}
}
