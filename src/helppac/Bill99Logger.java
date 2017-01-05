package helppac;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * <p>
 * This is a wrapper class for Jakarta <i>Log4j</i> (v1.2.x). If you are not
 * familiar with <i>Log4J</i>, please refer to <a
 * href="http://jakarta.apache.org/log4j/docs/manual.html">Short introduction to
 * log4j</a> from its homepage for a general idea.
 * <p>
 * One of the distinctive features of log4j is the notion of inheritance in
 * loggers. Using a logger hierarchy it is possible to control which log
 * statements are output at arbitrarily fine granularity but also great ease.
 * This helps reduce the volume of logged output and minimize the cost of
 * logging. Before we start cutting description of <code>Bill99Logger</code>,
 * let's look at the basics of <i>Log4J</i>. There are three aspects of log4j:
 * logger, appender, and layout. A logger logs to an appender in a particular
 * layout (style). It is flexible and configurable. Bill99Logger is created
 * according to Log4J v1.2.x, so <code>Logger</code> and <code>Level</code> come
 * into pictures to replace old <code>Category</code> and <code>Priority</code>.
 * <p>
 * <p>
 * If you want to log something in www.99bill.com system, please use
 * <code>Bill99Logger</code>. We also design to use SMTPAppender to do runtime
 * monitoring for exceptional cases wich system admin are interested in.
 * <p>
 * <p>
 * For example, the following are code snippets for logging:
 * 
 * <pre>
 *  // Preferred form for retrieving loggers:
 *  private static Bill99Logger logger = Bill99Logger.getLogger(caller.class);
 *  
 *  // Preferred form for logging message:
 *  logger.logDebug(&quot;Message&quot;);
 *  
 *  // Preferred form for logging exception:
 *  // Note that we don't provide methods to log simple message for WARN, ERROR, and FATAL, 
 *  // as we believe they are for exceptional case and there should have an exception Throwable 
 *  // object to logs 
 *  logger.logError(ex)
 *  logger.logError(msg, ex);
 * </pre>
 * 
 * <p>
 * <p>
 * It is a good idea to make logger <em>static</em> and <em>private</em>. Also,
 * for one Java class, we only declare one logger.
 * <P>
 * Wrapper provides a way for us to add more specific log method, like trace()
 * method, in <code>Bill99Logger</code> if necessary.
 * <p>
 * Here are references to log4j<br>
 * <ul>
 * <li>http://logging.apache.org/log4j/docs/manual.html
 * <li>http://www.vipan.com/htdocs/log4jhelp.html
 * <li>http://supportweb.cs.bham.ac.uk/documentation/tutorials/docsystem/build/
 * tutorials/log4j/log4j.html
 * </ul>
 * 
 * @author 99bill
 * @version 1.0
 */

public final class Bill99Logger {

	/**
	 * Handler to the Current Logger (composition pattern)
	 */
	private Logger logger = null;

	private static final String CONF_PROPERTY_FILE = "/properties/log4j.properties";

	private static final String CONF_XML_FILE = "/log4j.xml";

	private static boolean CONF_FILE_TYPE_USEXML = false;
	//	private static final boolean CONF_FILE_TYPE_USEXML = false;	//old

	private static boolean isInitialized = false;

	@SuppressWarnings("rawtypes")
	private static synchronized void init(Class clazz) {
		try {
			System.setProperty("log4j.defaultInitOverride", "true");
			if (CONF_FILE_TYPE_USEXML) {
				// URL configFileResource = clazz.getResource(CONF_XML_FILE);
				// URL configFileResource =
				// ClassLoader.getSystemResource(CONF_XML_FILE);
				// DOMConfigurator.configure(configFileResource.getFile());
				DOMConfigurator.configure(CONF_XML_FILE);
			} else {
				try {
					Properties prop = new Properties();
					prop.load(clazz.getResourceAsStream(CONF_PROPERTY_FILE));
					// prop.load(new FileInputStream(CONF_PROPERTY_FILE));
					PropertyConfigurator.configure(prop);
				} catch (Exception e1) {//如加载properties时报错，则尝试加载xml  @feibu 2011-11-18
					System.out.println(e1.getMessage());
					System.out.println("===========log4j.properties load failed == try load log4j.xml=========");
					DOMConfigurator.configure(CONF_XML_FILE);
					CONF_FILE_TYPE_USEXML = true;
				}
			}
			LogLog.setQuietMode(true);
			isInitialized = true;
		} catch (Exception e) {
			LogLog.error("Failed to initializing log4j", e);
		}
	}

	/**
	 * Private constructor that is not available for public use.
	 * 
	 * @param logger
	 *            Logger to use
	 */
	private Bill99Logger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * <p>
	 * All <code>Bill99Logger</code> callers are assumed to get a Bill99Logger
	 * instance by this method instead of the traditional constructor's way.
	 * <p>
	 * 
	 * @param clazz
	 *            caller class
	 * @return Bill99Logger instance
	 */
	@SuppressWarnings("rawtypes")
	public static Bill99Logger getLogger(Class clazz) {
		try {
			//add 20111202 begin
			String className = null;
			if (CONF_FILE_TYPE_USEXML) {
				className = "com.bill99.golden.inf.applog.ApplogApender";
			} else {
				className = "org.apache.log4j.Logger";
			}//add 20111202 end
			Class.forName(className);
			//			Class.forName("com.bill99.golden.inf.applog.ApplogApender");	//old
			if (!isInitialized) {
				init(clazz);
			}
		} catch (ClassNotFoundException ex) {
			return new Bill99Logger(null);
		}

		Logger logger = Logger.getLogger(clazz);
		return new Bill99Logger(logger);
	}

	/**
	 * Log a message object with the DEBUG level. To print a stack trace use the
	 * {@link #logDebug(Object,Throwable)} form instead.
	 * 
	 * @param message
	 *            the message object to log.
	 */
	public void debug(Object message) {

		log(Level.DEBUG, message);
	}

	/**
	 * Log a message object with the DEBUG level including the stack trace of
	 * the <code>Throwable t</code> passed as parameter
	 * 
	 * @param message
	 *            the message object to log.
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void debug(Object message, Throwable throwable) {
		log(Level.DEBUG, message, throwable);
	}

	/**
	 * Log a message object with the DEBUG level. To print a stack trace use the
	 * {@link #debug(Object,Throwable)} form instead.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param
	 *            Parameter to pass to the message
	 */
	public void debug(String message, String param) {

		log1(Level.DEBUG, message, param);
	}

	/**
	 * Log a message object with the DEBUG level. To print a stack trace use the
	 * {@link #debug(Object,Throwable)} form instead.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param1
	 *            Parameter to pass to the message
	 * @param param2
	 *            Parameter to pass to the message
	 */
	public void debug(String message, String param1, String param2) {

		log2(Level.DEBUG, message, param1, param2);
	}

	/**
	 * Log a message object with the DEBUG level. To print a stack trace use the
	 * {@link #debug(Object,Throwable)} form instead.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param1
	 *            Parameter to pass to the message
	 * @param param2
	 *            Parameter to pass to the message
	 * @param param3
	 *            Parameter to pass to the message
	 */
	public void debug(String message, String param1, String param2, String param3) {

		log3(Level.DEBUG, message, param1, param2, param3);
	}

	/**
	 * Log a message object with the DEBUG level. To print a stack trace use the
	 * {@link #debug(Object,Throwable)} form instead.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param1
	 *            Parameter to pass to the message
	 * @param param2
	 *            Parameter to pass to the message
	 * @param param3
	 *            Parameter to pass to the message
	 * @param param4
	 *            Parameter to pass to the message
	 */
	public void debug(String message, String param1, String param2, String param3, String param4) {

		log4(Level.DEBUG, message, param1, param2, param3, param4);
	}

	/**
	 * Log a message object with the INFO level.
	 * 
	 * @param message
	 *            the message object to log.
	 */
	public void info(Object message) {
		log(Level.INFO, message);
	}

	/**
	 * Log a message object with the INFO level including the stack trace of the
	 * <code>Throwable t</code> passed as parameter
	 * 
	 * @param message
	 *            the message object to log.
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void info(Object message, Throwable throwable) {
		log(Level.INFO, message, throwable);
	}

	/**
	 * Log a message object with the INFO level.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param
	 *            Parameter to pass to the message
	 */
	public void info(String message, String param) {
		log1(Level.INFO, message, param);
	}

	/**
	 * Log a message object with the INFO level.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param1
	 *            Parameter to pass to the message
	 * @param param2
	 *            Parameter to pass to the message
	 */
	public void info(String message, String param1, String param2) {
		log2(Level.INFO, message, param1, param2);
	}

	/**
	 * Log a message object with the INFO level.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param1
	 *            Parameter to pass to the message
	 * @param param2
	 *            Parameter to pass to the message
	 * @param param3
	 *            Parameter to pass to the message
	 */
	public void info(String message, String param1, String param2, String param3) {
		log3(Level.INFO, message, param1, param2, param3);
	}

	/**
	 * Log a message object with the INFO level.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param param1
	 *            Parameter to pass to the message
	 * @param param2
	 *            Parameter to pass to the message
	 * @param param3
	 *            Parameter to pass to the message
	 * @param param4
	 *            Parameter to pass to the message
	 */
	public void info(String message, String param1, String param2, String param3, String param4) {
		log4(Level.INFO, message, param1, param2, param3, param4);
	}

	/**
	 * Log a message object with the WARN level including the stack trace of the
	 * <code>Throwable t</code> passed as parameter
	 * 
	 * @param message
	 *            the message object to log.
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void warn(Object message, Throwable throwable) {
		log(Level.WARN, message, throwable);
	}

	/**
	 * Log a message object with the WARN level including the stack trace of the
	 * <code>Throwable t</code> passed as parameter
	 * 
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void warn(Throwable throwable) {
		log(Level.WARN, throwable.getMessage(), throwable);
	}

	/**
	 * Log a message object with the ERROR level including the stack trace of
	 * the <code>Throwable throwable</code> passed as parameter
	 * 
	 * @param message
	 *            the message object to log.
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void error(Object message, Throwable throwable) {
		log(Level.ERROR, message, throwable);
	}

	/**
	 * Log a message object with the ERROR level including the stack trace of
	 * the <code>Throwable throwable</code> passed as parameter
	 * 
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void error(Throwable throwable) {
		log(Level.ERROR, throwable.getMessage(), throwable);
	}

	public void error(String error) {
		log(Level.ERROR, error);
	}

	/**
	 * Log a message object with the FATAL level including the stack trace of
	 * the <code>Throwable t</code> passed as parameter
	 * 
	 * @param message
	 *            the message object to log.
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void fatal(Object message, Throwable throwable) {
		log(Level.FATAL, message, throwable);
	}

	/**
	 * Log a message object with the FATAL level including the stack trace of
	 * the <code>Throwable t</code> passed as parameter
	 * 
	 * @param throwable
	 *            the exception to log, including its stack trace.
	 */
	public void fatal(Throwable throwable) {
		log(Level.FATAL, throwable.getMessage(), throwable);
	}

	/**
	 * To judge if DEBUG level is enabled by the logger or not.
	 * 
	 * @return boolean support the level will return true, otherwise return
	 *         false.
	 */
	public boolean isDebugOn() {
		return logger.isEnabledFor(Level.DEBUG);
	}

	/**
	 * To judge if INFO level is enabled by the logger or not.
	 * 
	 * @return boolean support the level will return true, otherwise return
	 *         false.
	 */
	public boolean isInfoOn() {
		return logger.isEnabledFor(Level.INFO);
	}

	/**
	 * To judge if WARN level is enabled by the logger or not.
	 * 
	 * @return boolean support the level will return true, otherwise return
	 *         false.
	 */
	public boolean isWarnOn() {
		return logger.isEnabledFor(Level.WARN);
	}

	/**
	 * Log the message at the specified level
	 * 
	 * @param level
	 *            Level to use for the message
	 * @param message
	 *            The message to Log
	 */
	private void log(Level level, Object message) {
		if (logger != null && logger.isEnabledFor(level)) {
			logger.log(level, message);
		}
	}

	/**
	 * Log the message and the exception at the specified level
	 * 
	 * @param level
	 *            Level to log the message at
	 * @param message
	 *            Message to log
	 * @param throwable
	 *            The exception to log
	 */
	private void log(Level level, Object message, Throwable throwable) {
		if (logger != null && logger.isEnabledFor(level)) {
			logger.log(level, message, throwable);
		}
	}

	/**
	 * Logs a message with a single object as additional paraeter. The message
	 * should contain a formatting string and the objects being passed have to
	 * support toString() method
	 * 
	 * @param level
	 *            Log level
	 * @param theMessage
	 *            the message to log
	 * @param object1
	 *            Object to log
	 */
	private void log1(Level level, String theMessage, Object object1) {
		if (logger != null && logger.isEnabledFor(level)) {
			Object[] args = { object1 };
			logger.log(level, MessageFormat.format(theMessage, args));
		}
	}

	/**
	 * Logs a message with two objects as additional paraeter. The message
	 * should contain a formatting string and the objects being passed have to
	 * support toString() method
	 * 
	 * @param level
	 *            Log level
	 * @param theMessage
	 *            the message to log
	 * @param object1
	 *            Object to log
	 * @param object2
	 *            Object to log
	 */
	private void log2(Level level, String theMessage, Object object1, Object object2) {
		if (logger != null && logger.isEnabledFor(level)) {
			Object[] args = { object1, object2 };
			logger.log(level, MessageFormat.format(theMessage, args));
		}
	}

	/**
	 * Logs a message with three objects as additional paraeter. The message
	 * should contain a formatting string and the objects being passed have to
	 * support toString() method
	 * 
	 * @param level
	 *            Log level
	 * @param theMessage
	 *            the message to log
	 * @param object1
	 *            Object to log
	 * @param object2
	 *            Object to log
	 * @param object3
	 *            Object to log
	 */
	private void log3(Level level, String theMessage, Object object1, Object object2, Object object3) {
		if (logger != null && logger.isEnabledFor(level)) {
			Object[] args = { object1, object2, object3 };
			logger.log(level, MessageFormat.format(theMessage, args));
		}
	}

	/**
	 * Logs a message with four objects as additional paraeter. The message
	 * should contain a formatting string and the objects being passed have to
	 * support toString() method
	 * 
	 * @param level
	 *            Log level
	 * @param theMessage
	 *            the message to log
	 * @param object1
	 *            Object to log
	 * @param object2
	 *            Object to log
	 * @param object3
	 *            Object to log
	 * @param object4
	 *            Object to log
	 */
	private void log4(Level level, String theMessage, Object object1, Object object2, Object object3, Object object4) {
		if (logger != null && logger.isEnabledFor(level)) {
			Object[] args = { object1, object2, object3, object4 };
			logger.log(level, MessageFormat.format(theMessage, args));
		}
	}

}
