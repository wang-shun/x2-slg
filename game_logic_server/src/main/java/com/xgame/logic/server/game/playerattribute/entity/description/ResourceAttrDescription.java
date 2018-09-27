package com.xgame.logic.server.game.playerattribute.entity.description;

/**
 * 资源描述
 * @author jacky.jiang
 *
 */
public class ResourceAttrDescription extends AttributeDescription {
	
	private int resourceType;
	
	private int level;

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
