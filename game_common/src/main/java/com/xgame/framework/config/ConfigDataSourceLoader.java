package com.xgame.framework.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Created by vyang on 6/12/16.
 */
@Slf4j
@AllArgsConstructor(staticName = "of")
public class ConfigDataSourceLoader {
	private static final String ENV_KEY_PROFILE = "xgame.profile";
	private static final String ENV_KEY_OVERRIDE = "xgame.override";

	private String projectName;

	boolean innerPath;

	public PropertiesPropertySource load(ResourceLoader resourceLoader) {
		Properties properties = loadPropertiesFromPaths(resourceLoader,
				getConfigFiles());
		return new PropertiesPropertySource("xgame-" + projectName, properties);
	}

	private List<String> getConfigFiles() {
		List<String> resPaths = Lists.newArrayList();

		String profile = getValue(ENV_KEY_PROFILE);

		if (!Strings.isNullOrEmpty(System.getProperty("sysconfig"))) {
			innerPath = false;
		}

		if (!innerPath) {
			// get default
			resPaths.add(System.getProperty("sysconfig") + "/xgame/"
					+ projectName + ".properties");
			// get active profile
			if (profile != null && !profile.trim().isEmpty()) {
				resPaths.add(System.getProperty("sysconfig") + "/xgame/"
						+ projectName + "-" + profile.trim() + ".properties");
			}
		} else {
			// get default
			resPaths.add("classpath:/xgame/" + projectName + ".properties");

			if (profile != null && !profile.trim().isEmpty()) {
				resPaths.add("classpath:/xgame/" + projectName + "-"
						+ profile.trim() + ".properties");
			}
		}

		// get override
		String override = getValue(ENV_KEY_OVERRIDE);
		if (override != null && !override.trim().isEmpty()) {
			resPaths.add(override);
		}
		return resPaths;
	}

	private String getValue(String key) {
		return System.getProperty(key,
				System.getProperty(key.replace('.', '_')));
	}

	private Properties loadPropertiesFromPaths(ResourceLoader resourceLoader,
			List<String> paths) {
		Properties configurationProperties = new Properties();
		for (String path : paths) {
			log.info("xgame config >>>>>>> : load config from: {}", path);
			// Resource resource = resourceLoader.getResource(path);
			InputStream in = null;
			try {
				if (innerPath) {
					Resource resource = resourceLoader.getResource(path);
					in = resource.getInputStream();
				} else {
					in = FileUtils.openInputStream(new File(path));
				}

				Properties properties = new Properties();
				properties.load(in);
				configurationProperties.putAll(properties);
			} catch (IOException ex) {
				log.error("fail to load configuration properties. res: {}",
						path, ex);
				throw new IllegalStateException(
						"Failed to load configuration properties. Resource - "
								+ path, ex);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}

		return configurationProperties;
	}
}
