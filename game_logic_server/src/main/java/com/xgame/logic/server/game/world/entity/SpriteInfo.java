package com.xgame.logic.server.game.world.entity;

import io.protostuff.Tag;

import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.ClassNameUtils;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.entity.model.TerrainConfigModel;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;


/**
 * 精灵信息
 * @author jacky.jiang
 *
 */
@Slf4j
public class SpriteInfo extends AbstractEntity<Long> implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2502926670604440666L;

	/**
	 * 精灵id
	 */
	@Tag(1)
	private long id;
	
	/**
	 * 位置
	 */
	@Tag(2)
	private int index;
	
	/**
	 * 精灵类型
	 */
	@Tag(3)
	private int spriteType;
	
	/**
	 * 参数
	 */
	@Tag(4)
	private WorldSprite worldSprite = new WorldSprite();
	
	/**
	 * 地貌信息
	 */
	private LandFormData landform = new LandFormData();
	
	/**
	 * 地表信息
	 */
	private TerrainConfigModel terrainConfigModel = new TerrainConfigModel();
	
	
	public static SpriteInfo valueOf(long uid, int index, SpriteType spriteType, WorldSprite worldSprite) {
		SpriteInfo spriteInfo = new SpriteInfo();
		spriteInfo.index = index;
		spriteInfo.spriteType = spriteType.getType();
		spriteInfo.id = uid;
		spriteInfo.worldSprite = worldSprite;
		return spriteInfo;
	}
	
	/**
	 * 格子能否使用（空地才能被使用）
	 * @return
	 */
	public boolean canUse() {
		if (spriteType == SpriteType.NONE.getType() && worldSprite.getOwnerMarchId() <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是障碍戴安
	 * @return
	 */
	public boolean canMove() {
		if(spriteType != SpriteType.BLOCK.getType()) {
			return true;
		}
		return false;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getX() {
		return index % WorldConstant.X_GRIDNUM;
	}

	public int getY() {
		return index / WorldConstant.X_GRIDNUM;
	}
	
	/**
	 * 获取向量坐标
	 * @return
	 */
	public Vector2 getVector2() {
		Vector2 vector2 = new Vector2();
		vector2.setX(this.getX());
		vector2.setY(this.getY());
		return vector2;
	}

	public String getTargetName() {
		if(spriteType == SpriteType.PLAYER.getType()) {
			PlayerSprite playerSprite = (PlayerSprite)this.worldSprite;
			Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(playerSprite.getOwnerId()));
			return player.getName();
		} else if(spriteType == SpriteType.RESOURCE.getType()) {
			ResourceSprite resourceSprite = (ResourceSprite)this.worldSprite;
			return String.valueOf(resourceSprite.getLevel());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParam() {
		if (worldSprite != null) {
			return (T) worldSprite;
		}
		return null;
	}

	public void setParam(WorldSprite param) {
		this.worldSprite = param;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long k) {
		this.id = k;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}

	public int getSpriteType() {
		return spriteType;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public SpriteType getEnumSpriteType() {
		return SpriteType.valueOf(spriteType);
	}

	public LandFormData getLandform() {
		return landform;
	}

	public void setLandform(LandFormData landform) {
		this.landform = landform;
	}

	public TerrainConfigModel getTerrainConfigModel() {
		return terrainConfigModel;
	}

	public void setTerrainConfigModel(TerrainConfigModel terrainConfigModel) {
		this.terrainConfigModel = terrainConfigModel;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("index", index);
		jbaseData.put("spriteType", spriteType);
		
		if(worldSprite != null) {
			jbaseData.put("param", JsonUtil.toJSON(worldSprite.toJBaseData()));
			jbaseData.put("clazz", worldSprite.getClass().getName());
		}
		jbaseData.put("landform", ((JBaseTransform) landform).toJBaseData());
		jbaseData.put("terrainConfigModel", ((JBaseTransform) terrainConfigModel).toJBaseData());
		return jbaseData;
	}

	@SuppressWarnings({"rawtypes" })
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.index = jBaseData.getInt("index", 0);
		this.spriteType = jBaseData.getInt("spriteType", 0);
		
		JBaseData landForm = jBaseData.getBaseData("landform");
		LandFormData landFormData = new LandFormData();
		landFormData.fromJBaseData(landForm);
		this.landform = landFormData;
		
		JBaseData terrainConfigModelJBaseData = jBaseData.getBaseData("terrainConfigModel");
		TerrainConfigModel terrainConfigModel = new TerrainConfigModel();
		landFormData.fromJBaseData(terrainConfigModelJBaseData);
		this.terrainConfigModel = terrainConfigModel;
		
		String worldSpriteStr = jBaseData.getString("param", "");
		if(!StringUtils.isEmpty(worldSpriteStr)) {
			String clazzStr = jBaseData.getString("clazz", "");
			Class clazz = ClassNameUtils.getClass(clazzStr);
			JBaseData jBaseData2 = JsonUtil.fromJSON(worldSpriteStr, JBaseData.class);
			Object obj;
			try {
				obj = clazz.newInstance();
				((JBaseTransform)obj).fromJBaseData(jBaseData2);
				this.worldSprite = (WorldSprite)obj;
			} catch (Exception e) {
				log.error("sprite转换异常:", e);
			}
		}
	}
	
}
