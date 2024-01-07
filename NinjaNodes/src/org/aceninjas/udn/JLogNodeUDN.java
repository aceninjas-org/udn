package org.aceninjas.udn;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>JLogNodeUDN</I> instance</p>
 * <p></p>
 */
public class JLogNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "org/aceninjas/udn/JLogNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/Ninjas/icons/full/obj16/org/aceninjas/udn/JLog.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/Ninjas/icons/full/obj30/org/aceninjas/udn/JLog.gif";

	protected final static String PROPERTY_TEXT = "text";
	protected final static String PROPERTY_MAXFILESIZE = "maxFileSize";
	protected final static String PROPERTY_MAXBACKUPINDEX = "maxBackupIndex";
	protected final static String PROPERTY_FILE = "file";
	protected final static String PROPERTY_THRESHOLD = "threshold";
	protected final static String PROPERTY_LAYOUTCONVERSIONPATTERN = "layoutConversionPattern";
	protected final static String PROPERTY_LOGMESSAGEBODY = "logMessageBody";
	protected final static String PROPERTY_LOGEXCEPTION = "logException";


	/**
	 * <I>ENUM_JLOG_THRESHOLD</I>
	 * <pre>
	 * ENUM_JLOG_THRESHOLD.debug = debug
	 * ENUM_JLOG_THRESHOLD.info = info
	 * ENUM_JLOG_THRESHOLD.trace = trace
	 * ENUM_JLOG_THRESHOLD.warn = warn
	 * ENUM_JLOG_THRESHOLD.error = error
	 * </pre>
	 */
	public static class ENUM_JLOG_THRESHOLD {
		private String value;

		public static final ENUM_JLOG_THRESHOLD debug = new ENUM_JLOG_THRESHOLD("debug");
		public static final ENUM_JLOG_THRESHOLD info = new ENUM_JLOG_THRESHOLD("info");
		public static final ENUM_JLOG_THRESHOLD trace = new ENUM_JLOG_THRESHOLD("trace");
		public static final ENUM_JLOG_THRESHOLD warn = new ENUM_JLOG_THRESHOLD("warn");
		public static final ENUM_JLOG_THRESHOLD error = new ENUM_JLOG_THRESHOLD("error");

		protected ENUM_JLOG_THRESHOLD(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}

		protected static ENUM_JLOG_THRESHOLD getEnumFromString(String enumValue) {
			ENUM_JLOG_THRESHOLD enumConst = ENUM_JLOG_THRESHOLD.debug;
			if (ENUM_JLOG_THRESHOLD.info.value.equals(enumValue)) enumConst = ENUM_JLOG_THRESHOLD.info;
			if (ENUM_JLOG_THRESHOLD.trace.value.equals(enumValue)) enumConst = ENUM_JLOG_THRESHOLD.trace;
			if (ENUM_JLOG_THRESHOLD.warn.value.equals(enumValue)) enumConst = ENUM_JLOG_THRESHOLD.warn;
			if (ENUM_JLOG_THRESHOLD.error.value.equals(enumValue)) enumConst = ENUM_JLOG_THRESHOLD.error;
			return enumConst;
		}

		public static String[] values = new String[]{ "debug", "info", "trace", "warn", "error" };

	}
	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(JLogNodeUDN.PROPERTY_TEXT,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.STRING, null,"","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_MAXFILESIZE,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.INTEGER, "10","","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_MAXBACKUPINDEX,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.INTEGER, "10","","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_FILE,		NodeProperty.Usage.MANDATORY,	true,	NodeProperty.Type.STRING, null,"","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_THRESHOLD,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.ENUMERATION, "debug", ENUM_JLOG_THRESHOLD.class,"","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_LAYOUTCONVERSIONPATTERN,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.STRING, "%d{yyyy-MM-dd HH:mm:ss} %-5p  [THREAD ID=%t] %C{1}.%M %m%n","","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_LOGMESSAGEBODY,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.BOOLEAN, "true","","",	"org/aceninjas/udn/JLog",	"Ninjas"),
			new NodeProperty(JLogNodeUDN.PROPERTY_LOGEXCEPTION,		NodeProperty.Usage.OPTIONAL,	true,	NodeProperty.Type.BOOLEAN, "true","","",	"org/aceninjas/udn/JLog",	"Ninjas")
		};
	}

	public JLogNodeUDN() {
	}

	public final InputTerminal INPUT_TERMINAL_IN = new InputTerminal(this,"InTerminal.in");
	@Override
	public InputTerminal[] getInputTerminals() {
		return new InputTerminal[] {
			INPUT_TERMINAL_IN
	};
	}

	public final OutputTerminal OUTPUT_TERMINAL_FAILURE = new OutputTerminal(this,"OutTerminal.failure");
	public final OutputTerminal OUTPUT_TERMINAL_OUT = new OutputTerminal(this,"OutTerminal.out");
	@Override
	public OutputTerminal[] getOutputTerminals() {
		return new OutputTerminal[] {
			OUTPUT_TERMINAL_FAILURE,
			OUTPUT_TERMINAL_OUT
		};
	}

	@Override
	public String getTypeName() {
		return NODE_TYPE_NAME;
	}

	protected String getGraphic16() {
		return NODE_GRAPHIC_16;
	}

	protected String getGraphic32() {
		return NODE_GRAPHIC_32;
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Text</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>Text</I>"
	 */
	public JLogNodeUDN setText(String value) {
		setProperty(JLogNodeUDN.PROPERTY_TEXT, value);
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> "<I>Text</I>" property
	 * 
	 * @return String; the value of the property "<I>Text</I>"
	 */
	public String getText() {
		return (String)getPropertyValue(JLogNodeUDN.PROPERTY_TEXT);
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Max File Size(MB)</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>Max File Size(MB)</I>"
	 */
	public JLogNodeUDN setMaxFileSize(int value) {
		setProperty(JLogNodeUDN.PROPERTY_MAXFILESIZE, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> <I>Max File Size(MB)</I> property
	 * 
	 * @return int; the value of the property "<I>Max File Size(MB)</I>"
	 */
	public int getMaxFileSize() {
		String value = (String)getPropertyValue(JLogNodeUDN.PROPERTY_MAXFILESIZE);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Max Backup Index</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>Max Backup Index</I>"
	 */
	public JLogNodeUDN setMaxBackupIndex(int value) {
		setProperty(JLogNodeUDN.PROPERTY_MAXBACKUPINDEX, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> <I>Max Backup Index</I> property
	 * 
	 * @return int; the value of the property "<I>Max Backup Index</I>"
	 */
	public int getMaxBackupIndex() {
		String value = (String)getPropertyValue(JLogNodeUDN.PROPERTY_MAXBACKUPINDEX);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>File</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>File</I>"
	 */
	public JLogNodeUDN setFile(String value) {
		setProperty(JLogNodeUDN.PROPERTY_FILE, value);
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> "<I>File</I>" property
	 * 
	 * @return String; the value of the property "<I>File</I>"
	 */
	public String getFile() {
		return (String)getPropertyValue(JLogNodeUDN.PROPERTY_FILE);
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Threshold</I>" property
	 * 
	 * @param value ENUM_JLOG_THRESHOLD ; the value to set the property "<I>Threshold</I>"
	 */
	public JLogNodeUDN setThreshold(ENUM_JLOG_THRESHOLD value) {
		setProperty(JLogNodeUDN.PROPERTY_THRESHOLD, value.toString());
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> "<I>Threshold</I>" property
	 * 
	 * @return ENUM_JLOG_THRESHOLD; the value of the property "<I>Threshold</I>"
	 */
	public ENUM_JLOG_THRESHOLD getThreshold() {
		ENUM_JLOG_THRESHOLD value = ENUM_JLOG_THRESHOLD.getEnumFromString((String)getPropertyValue(JLogNodeUDN.PROPERTY_THRESHOLD));
		return value;
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Pattern</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>Pattern</I>"
	 */
	public JLogNodeUDN setLayoutConversionPattern(String value) {
		setProperty(JLogNodeUDN.PROPERTY_LAYOUTCONVERSIONPATTERN, value);
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> "<I>Pattern</I>" property
	 * 
	 * @return String; the value of the property "<I>Pattern</I>"
	 */
	public String getLayoutConversionPattern() {
		return (String)getPropertyValue(JLogNodeUDN.PROPERTY_LAYOUTCONVERSIONPATTERN);
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Log Message Body</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>Log Message Body</I>"
	 */
	public JLogNodeUDN setLogMessageBody(boolean value) {
		setProperty(JLogNodeUDN.PROPERTY_LOGMESSAGEBODY, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> "<I>Log Message Body</I>" property
	 * 
	 * @return boolean; the value of the property "<I>Log Message Body</I>"
	 */
	public boolean getLogMessageBody(){
	if (getPropertyValue(JLogNodeUDN.PROPERTY_LOGMESSAGEBODY).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>JLogNodeUDN</I> "<I>Log Exception</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>Log Exception</I>"
	 */
	public JLogNodeUDN setLogException(boolean value) {
		setProperty(JLogNodeUDN.PROPERTY_LOGEXCEPTION, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>JLogNodeUDN</I> "<I>Log Exception</I>" property
	 * 
	 * @return boolean; the value of the property "<I>Log Exception</I>"
	 */
	public boolean getLogException(){
	if (getPropertyValue(JLogNodeUDN.PROPERTY_LOGEXCEPTION).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "JLog";
		return retVal;
	};
}
