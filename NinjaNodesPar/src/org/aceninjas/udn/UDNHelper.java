/**
 *    Copyright (C) 2024 ACE Ninjas <aceninjas.org@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.aceninjas.udn;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbRecoverableException;

public class UDNHelper {

	public static String getExceptionFullStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String err = sw.toString();
		try {
			sw.close();
		} catch (Exception ex) {
		}
		pw.close();
		return err;
	}

	public static void createExceptionTree(MbElement root, MbException e)
			throws Exception {
		MbElement exceptionMbElement = root.createElementAsLastChild(
				MbElement.TYPE_NAME, e.getClass().getSimpleName(), null);
		if (e instanceof MbRecoverableException) {

			exceptionMbElement.createElementAsLastChild(
					MbElement.TYPE_NAME_VALUE, "File",
					((MbRecoverableException) e).getClassName());

			exceptionMbElement.createElementAsLastChild(
					MbElement.TYPE_NAME_VALUE, "Function",
					((MbRecoverableException) e).getMethodName());

		}
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Type", "");
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Name", "");
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Label", "");
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Catelog", e.getMessageSource() != null ? e.getMessageSource()
						: "");
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Severity", "3");
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Number", e.getMessageKey() != null ? e.getMessageKey() : "");
		exceptionMbElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,
				"Text", e.getTraceText());

		if (e.getInserts() != null) {
			Object[] inserts = e.getInserts();
			for (Object o : inserts) {
				MbElement insertMbElement = exceptionMbElement
						.createElementAsLastChild(MbElement.TYPE_NAME,
								"Insert", null);
				insertMbElement.createElementAsLastChild(
						MbElement.TYPE_NAME_VALUE, "Type", o.getClass()
								.getSimpleName());
				insertMbElement.createElementAsLastChild(
						MbElement.TYPE_NAME_VALUE, "Text", o.toString());
			}
		}

		if (e.getNestedExceptions() != null) {
			for (MbException mbException : e.getNestedExceptions()) {
				createExceptionTree(exceptionMbElement, mbException);
			}
		}

	}

}
