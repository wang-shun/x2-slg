package com.xgame.logic.server.game.country.structs;

import com.xgame.logic.server.core.utils.geometry.data.transform.BuildTransform;
import com.xgame.logic.server.game.country.entity.XBuild;

/**
 *
 *2016-12-22  10:05:24
 *@author ye.yuan
 *
 */
public class BuildInfo {
	
	private XBuild build;
	
	private BuildTransform buildTransform;

	public BuildInfo(XBuild build, BuildTransform buildTransform) {
		super();
		this.build = build;
		this.buildTransform = buildTransform;
	}

	public XBuild getBuild() {
		return build;
	}

	public void setBuild(XBuild build) {
		this.build = build;
	}

	public BuildTransform getBuildTransform() {
		return buildTransform;
	}

	public void setBuildTransform(BuildTransform buildTransform) {
		this.buildTransform = buildTransform;
	}
	
}
