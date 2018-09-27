CommandTool = {}

local this = CommandTool;

function CommandTool.HandleTargetInRange(theTroop)

	theTroop:getAI():pushCommand(FaceFireCommand.new(theTroop, nil));
end	