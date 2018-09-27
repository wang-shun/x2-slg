----[Controller]
--require "lib/Controller/BattleLogicController"
 

 
----[Logic]-- data
require "lib/Logic/Data/EntityModel"
require "lib/Logic/Data/BuildingEntityModel"
require "lib/Logic/Entity/EntityTool"
require "lib/Logic/Data/TroopEntityModel"
require "lib/Logic/Data/AIModel"
require "lib/Logic/Fight/BattleType"
require "lib/Logic/Data/WarAttackLeftSoldierModel"


----[Logic]-- data -- service
require "lib/Logic/Data/Service/WarDataManager"
require "lib/Logic/Data/Service/WarAttackModel"
require "lib/Logic/Data/Service/WarDefenModel"
require "lib/Logic/Data/Service/WarAttrModel"
require "lib/Logic/Data/Service/WarBuildingModel"
require "lib/Logic/Data/Service/WarDataModel"
require "lib/Logic/Data/Service/WarDefenSoldierModel"
require "lib/Logic/Data/Service/WarResultModel"
require "lib/Logic/Data/Service/WarSoldierModel"

----[Logic]-- entity

require "lib/Logic/Entity/EntitySign"
require "lib/Logic/Entity/Entity"
require "lib/Logic/Entity/EntityTeam"
require "lib/Logic/Entity/EntityFactory"
require "lib/Logic/Entity/EntityParamModel"

----[Logic]-- entity--building
require "lib/Logic/Entity/Building/BuildingEntity"
require "lib/Logic/Entity/Building/ResBuildingEntity"
require "lib/Logic/Entity/Building/BuildingEntityType"
----[Logic]-- entity--troop
require "lib/Logic/Entity/Troop/TroopEntity"
require "lib/Logic/Entity/Troop/TankEntity"
require "lib/Logic/Entity/Troop/TroopType"

----[Logic]-- fight
require "lib/Logic/Fight/Battle"
require "lib/Logic/Fight/BattleTeam"
----[Logic]-- fight--logic--ai
require "lib/Logic/Fight/Logic/Ai/TargetingType"
require "lib/Logic/Fight/Logic/Ai/EntityAI"
require "lib/Logic/Fight/Logic/Ai/BuildingAI"
require "lib/Logic/Fight/Logic/Ai/TroopAI"
require "lib/Logic/Fight/Logic/Ai/TankAI"
require "lib/Logic/Fight/Logic/Ai/WeaponAI/WeaponAI"
require "lib/Logic/Fight/Logic/Ai/DianCiTaBuildingAI"
require "lib/Logic/Fight/Logic/Ai/HuoYanTaBuildingAI"


----[Logic]-- fight--logic--command
require "lib/Logic/Fight/Logic/Command/AICommand"
require "lib/Logic/Fight/Logic/Command/AttackCommand"
require "lib/Logic/Fight/Logic/Command/FindTargetCommand"
require "lib/Logic/Fight/Logic/Command/CommandTool"
require "lib/Logic/Fight/Logic/Command/FaceFireCommand"
require "lib/Logic/Fight/Logic/Command/AttackTargetCommand"
require "lib/Logic/Fight/Logic/Command/AttackApproachCommand"

require "lib/Logic/Fight/Logic/Command/HomeCommand/WarningAroundBuildingCommand"
require "lib/Logic/Fight/Logic/Command/WeaponCommand/MainWeaponAttackCommand"
require "lib/Logic/Fight/Logic/Command/WeaponCommand/SubWeaponAttackCommand"
require "lib/Logic/Fight/Logic/Command/BuildingCommand/DianCiTaTowerDefenseCommand"


----[Logic]-- fight--logic--movement
require "lib/Logic/Fight/Logic/Movement/Movement"
require "lib/Logic/Fight/Logic/Movement/MovementMobile"
require "lib/Logic/Fight/Logic/Movement/MovementTank"

----[Logic]-- fight--logic--weapon

require "lib/Logic/Fight/Logic/Weapon/WeaponConst"
require "lib/Logic/Fight/Logic/Weapon/CheckTargetResult"
require "lib/Logic/Fight/Logic/Weapon/FightWeapon"
require "lib/Logic/Fight/Logic/Weapon/TroopFightWeapon"
require "lib/Logic/Fight/Logic/Weapon/BuildingFightWeapon"
require "lib/Logic/Fight/Logic/Weapon/WeaponRotationAxis"

----[Logic]-- fight--logic--path
require "lib/Logic/Fight/Logic/Path/PathTool"
require "lib/Logic/Fight/Logic/Path/AttackPathData"
require "lib/Logic/Fight/Logic/Path/AttackPathRequest"
require "lib/Logic/Fight/Logic/Path/BlockingTroop"
require "lib/Logic/Fight/Logic/Path/GridPath"
require "lib/Logic/Fight/Logic/Path/GridPather"
require "lib/Logic/Fight/Logic/Path/GridPatherNode"
require "lib/Logic/Fight/Logic/Path/GridPathFollower"
require "lib/Logic/Fight/Logic/Path/HashCell"
require "lib/Logic/Fight/Logic/Path/HashGrid"
require "lib/Logic/Fight/Logic/Path/HierarchicalHashGrid"
require "lib/Logic/Fight/Logic/Path/MoveModes"
require "lib/Logic/Fight/Logic/Path/ParkSize"
require "lib/Logic/Fight/Logic/Path/ParkT"
require "lib/Logic/Fight/Logic/Path/PathType"
require "lib/Logic/Fight/Logic/Path/RTSPathFollower"

----[Logic]-- fight--logic--Projectile
require "lib/Logic/Fight/Logic/Projectile/ProjectileManager"
require "lib/Logic/Fight/Logic/Projectile/Projectile"
require "lib/Logic/Fight/Logic/Projectile/ProjectileConst"

----[Logic]-- fight--logic--util
require "lib/Logic/Fight/Logic/Util/BoardUtil"
require "lib/Logic/Fight/Logic/Util/FastBitArray"
require "lib/Logic/Fight/Logic/Util/FBoundingBox2d"
require "lib/Logic/Fight/Logic/Util/Utility"

----[Logic]-- fight--logic--math
require "lib/Logic/Fight/Math/MathConst"
require "lib/Logic/Fight/Math/FVector3"
require "lib/Logic/Fight/Math/FixedAngle"
require "lib/Logic/Fight/Math/FQuaternion"

----[Util]
require "lib/Util/BoardBlockManager"
require "lib/Util/WallUtil"
require "lib/Logic/Fight/Logic/Util/AttrCalc"
----[Logic]
require "lib/Logic/Board"
require "lib/Logic/CollisionManager"
require "lib/Logic/BattleDataController"

--------skill
require "lib/Logic/Fight/Logic/Skill/Skill"
require "lib/Logic/Fight/Logic/Skill/SkillShooter"

----buff
require "lib/Logic/Fight/Logic/Buff/Buff"
require "lib/Logic/Fight/Logic/Buff/BuffManager"
require "lib/Logic/Fight/Logic/Buff/Ai_CeFanBuff"
require "lib/Logic/Fight/Logic/Buff/Ai_ClearFocusEnemyBuff"
require "lib/Logic/Fight/Logic/Buff/AttrBuff"
require "lib/Logic/Fight/Logic/Buff/BuffConst"
require "lib/Logic/Fight/Logic/Buff/BuffGroupConst"
require "lib/Logic/Fight/Logic/Buff/Effect_XuanYunBuff"
require "lib/Logic/Fight/Logic/Buff/Skill_LanJieBuff"
require "lib/Logic/Fight/Logic/Buff/Skill_YouErBuff"

-------battle end


require "lib/Logic/Fight/Logic/BattleEnd/BattleOverProccesser" 
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_Explorer"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_AdapterPvp"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_Camp"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_AllianceActivityFight"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_BossFight"

require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_FubenFight"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_MonsterInvasion"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_RvrFight"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_TeamAttack"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_Territory"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_WorldCity"
require "lib/Logic/Fight/Logic/BattleEnd/BattleEnd_WorldMonster"
 

require "lib/Logic/Fight/Logic/Util/AttrCalc"

 
 