/**
 * Copyright (C) 2014 The logback-extensions developers (logback-user@qos.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xgame.framework.config;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import com.google.common.base.Strings;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.selector.ContextSelector;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Convenience class that features simple methods for custom Log4J
 * configuration.
 * <p/>
 * Only needed for non-default Logback initialization with a custom config
 * location. By default, Logback will simply read its configuration from a
 * "logback.xml" or "logback_test.xml" file in the root of the classpath.
 * <p/>
 * For web environments, the analogous LogbackWebConfigurer class can be found
 * in the web package, reading in its configuration from context-params in
 * web.xml. In a JEE web application, Logback is usually set up via
 * LogbackConfigListener or LogbackConfigServlet, delegating to
 * LogbackWebConfigurer underneath.
 *
 * @author Juergen Hoeller
 * @author Bryan Turner
 * @author Les Hazlewood
 * @author Knute Axelson
 * @since 0.1
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogbackConfigurer {

	private static final String ENV_KEY_PROFILE = "xgame.profile";
	private static final String LOGBACK_PATH = "classpath:logback/";

	public static void init() throws FileNotFoundException, JoranException {
		URL url = null;

		if (Strings.isNullOrEmpty(System.getProperty("sysconfig"))) {
			String location = LOGBACK_PATH + "logback"
					+ getSuffix(ENV_KEY_PROFILE);
			String resolvedLocation = SystemPropertyUtils
					.resolvePlaceholders(location);
			url = ResourceUtils.getURL(resolvedLocation);
		} else {
			url = ResourceUtils.getURL(System.getProperty("sysconfig")
					+ "/logback/logback" + getSuffix(ENV_KEY_PROFILE));
		}

		LoggerContext loggerContext = (LoggerContext) StaticLoggerBinder
				.getSingleton().getLoggerFactory();

		loggerContext.reset();

		// reinitialize the logger context. calling this method allows
		// configuration through groovy or xml
		new ContextInitializer(loggerContext).configureByResource(url);
	}

	private static String getSuffix(String key) {
		String value = System.getProperty(key,
				System.getProperty(key.replace('.', '_')));
		if (Objects.isNull(value) || value.trim().isEmpty()) {
			return "-default.xml";
		} else {
			return "-" + value + ".xml";
		}
	}

	/**
	 * Shut down Logback.
	 * <p/>
	 * This isn't strictly necessary, but recommended for shutting down logback
	 * in a scenario where the host VM stays alive (for example, when shutting
	 * down an application in a J2EE environment).
	 */
	public static void shutdownLogging() {
		ContextSelector selector = ContextSelectorStaticBinder.getSingleton()
				.getContextSelector();
		LoggerContext loggerContext = selector.getLoggerContext();
		String loggerContextName = loggerContext.getName();
		LoggerContext context = selector.detachLoggerContext(loggerContextName);
		context.reset();
	}
}
