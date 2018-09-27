package com.xgame.framework.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ConfigData {

	private final Environment env;

}
