local tconcat = table.concat
local tinsert = table.insert
local srep = string.rep
local type = type
local pairs = pairs
local tostring = tostring
local next = next

function print_r(root)
	print("-------------------------------->>>> ")
	if isEmpty(root) then
		print("--------------------------------<<<<empty\n")
		return
	end
	local cache = { [root] = "." }
	local function deal(t, space, name)
		local temp = {}
		for k, v in pairs(t) do
			local key = tostring(k)
			if cache[v] then
				tinsert(temp, "+" .. key .. " {" .. cache[v] .. "}")
			elseif type(v) == "table" then
				local new_key = name .. "." .. key
				cache[v] = new_key
				tinsert(temp, "+" .. key .. deal(v, space .. (next(t, k) and "|" or " " ) .. srep(" ", #key), new_key))
			else
				tinsert(temp, "+" .. key .. " [" .. tostring(v) .. "]")
			end
		end
		return tconcat(temp, "\n" .. space)
	end
	print("\n"..deal(root, "", ""))
	print("--------------------------------<<<<\n")
end

function getTableStr(root)
    local str = "";
    for k, v in pairs(root) do
        local vl = v;
        if type(v) == "table" then
            vl ="table"-- getTableStr(v);
        end

        local va = tostring(k)..","..tostring(vl);
        local tc = " [" .. va .. "] ";

        str = str..tc
    end

    return str;
end
