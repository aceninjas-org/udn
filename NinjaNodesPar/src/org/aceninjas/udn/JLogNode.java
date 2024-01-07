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

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputTerminal;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNode;
import com.ibm.broker.plugin.MbNodeInterface;
import com.ibm.broker.plugin.MbOutputTerminal;

public class JLogNode extends MbNode implements MbNodeInterface {

	private String maxFileSize = "10MB";
	private String maxBackupIndex = "10";
	private String file;
	private String threshold = "debug";
	private String layoutConversionPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p  [THREAD ID=%t] %C{1}.%M %m%n";

	private boolean logMessageBody = true;
	private boolean logException = true;

	private String text;

	public JLogNode() throws MbException {
		createInputTerminal("in");
		createOutputTerminal("out");
	}

	public void evaluate(MbMessageAssembly inAssembly,
			MbInputTerminal inputtermal) throws MbException {

		Properties props = loadLog4JProperties();
		StringBuffer infor = new StringBuffer(); 
		StringBuffer error = new StringBuffer();
		
		if (!StringUtils.isEmpty(text)) {
			infor.append(text);
		}
		try {

			if (logMessageBody) {
				MbMessage inMessage = inAssembly.getMessage();
				if (inMessage != null && inMessage.getRootElement() != null
						&& inMessage.getRootElement().getLastChild() != null) {
					MbElement message = inMessage.getRootElement()
							.getLastChild();
					if (message != null && "XMLNSC".equals(message.getName())) {
						infor.append(nodeToString(message.getDOMNode()));
					} else {
						String msg = new String(inMessage.getRootElement()
								.getLastChild()
								.toBitstream("", "", "", 0, 0, 0));
						if( infor.length() > 0 ) infor.append("\n");
						infor.append(msg);
					}
				}
			}

			if (logException) {
				MbMessage exceptionList = inAssembly.getExceptionList();
				if (exceptionList != null
						&& exceptionList.getRootElement() != null
						&& exceptionList.getRootElement().getFirstChild() != null) {
					String msg = toExceptionXML(exceptionList);
					error.append(msg);
				}
			}
			//String textInfo = infor.toString();
			//String texterror = error.toString();
			PropertyConfigurator.configure(props);
			/*
			Logger logger = Logger.getLogger("file");
			String text = infor.toString();
			if (!StringUtils.isEmpty(textInfo)) {
				logger.info(textInfo);
			}			
			if (!StringUtils.isEmpty(texterror)) {
				logger.error(texterror);
			}*/
			LogManager.log(props, infor.toString(), error.toString());
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String err = sw.toString();
			try {
				FileWriter fw = new FileWriter("/iib/logs/jlog_exception.log");
				fw.write(err);
				fw.close();
			} catch (Exception ex) {

			}
		}
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,
				new MbMessage(inAssembly.getMessage()));
		MbOutputTerminal out = getOutputTerminal("out");
		out.propagate(outAssembly);
	}

	public static String getNodeName() {
		return "JLogNode";
	}

	private static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformerFactory.setAttribute("indent-number", 3);
			Transformer t = transformerFactory.newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

	public String toExceptionXML(MbMessage exceptionList) throws Exception {
		if (exceptionList == null)
			return "";
		final DOMImplementationRegistry registry = DOMImplementationRegistry
				.newInstance();
		final DOMImplementationLS impl = (DOMImplementationLS) registry
				.getDOMImplementation("LS");
		final LSSerializer writer = impl.createLSSerializer();
		writer.getDomConfig().setParameter("xml-declaration", false);
		writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
		return writer
				.writeToString(exceptionList.getRootElement().getDOMNode());
	}

	private Properties loadLog4JProperties() {
		Properties props = new Properties();
		props.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		props.put("log4j.appender.stdout.Target", "System.out");
		props.put("log4j.appender.stdout.layout",
				"org.apache.log4j.SimpleLayout");
		props.put("log4j.rootLogger", "debug,file");
		props.put("log4j.appender.file", "org.apache.log4j.RollingFileAppender");

		if (StringUtils.isEmpty(file)) {
			try {
				String msgFlowName = getMessageFlow().getName();
				file = "/iib/logs/" + msgFlowName + ".log";
			} catch (Exception e) {
			}
		}
		props.put("log4j.appender.file.File", file);

		if (StringUtils.isEmpty(maxFileSize))
			maxFileSize = "10";
		props.put("log4j.appender.file.maxFileSize", maxFileSize + "MB");

		if (StringUtils.isEmpty(maxBackupIndex))
			maxBackupIndex = "10";
		props.put("log4j.appender.file.maxBackupIndex", maxBackupIndex);

		if (StringUtils.isEmpty(threshold))
			threshold = "debug";
		props.put("log4j.appender.file.threshold", threshold);

		props.put("log4j.appender.file.layout",
				"org.apache.log4j.PatternLayout");

		if (StringUtils.isEmpty(layoutConversionPattern))
			layoutConversionPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p  [THREAD ID=%t] %C{1}.%M %m%n";
		props.put("log4j.appender.file.layout.ConversionPattern",
				layoutConversionPattern);
		props.put("log4j.logger.org.quartz", "OFF");
		return props;
	}

	public String getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public String getMaxBackupIndex() {
		return maxBackupIndex;
	}

	public void setMaxBackupIndex(String maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getLayoutConversionPattern() {
		return layoutConversionPattern;
	}

	public void setLayoutConversionPattern(String layoutConversionPattern) {
		this.layoutConversionPattern = layoutConversionPattern;
	}

	public String getText() {
		return text;
	}

	public String getLogMessageBody() {
		if (!logMessageBody)
			return "false";
		else
			return "true";

	}

	public void setLogMessageBody(String logMessageBody) {
		if ("false".equalsIgnoreCase(logMessageBody)) {
			this.logMessageBody = false;
		} else {
			this.logMessageBody = true;
		}
	}

	public String getLogException() {
		if (!logException)
			return "false";
		else
			return "true";
	}

	public void setLogException(String logException) {
		if ("false".equalsIgnoreCase(logException)) {
			this.logException = false;
		} else {
			this.logException = true;
		}
	}

	public void setText(String text) {
		this.text = text;
	}
}
