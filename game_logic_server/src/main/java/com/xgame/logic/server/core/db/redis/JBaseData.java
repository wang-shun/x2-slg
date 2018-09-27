package com.xgame.logic.server.core.db.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JBaseData
 * @author jacky.jiang
 *
 */
public class JBaseData {
	
    private Map<String, Object> m = new HashMap<>();

    public JBaseData() {
    }

    public JBaseData(Map<String, Object> map) {
        m = map;
    }

    public Map<String, Object> getMap() {
        return m;
    }

    public JBaseData getBaseData(String k) {
        if (m.containsKey(k)) {
            return (JBaseData) m.get(k);
        } else {
            return (new JBaseData());
        }
    }

    public void put(String k, Object v) {
        m.put(k, v);
    }

    public boolean getBoolean(String k, boolean ret) {
        if (m.containsKey(k)) {
            return (Boolean) m.get(k);
        } else {
            return ret;
        }
    }

    public String getString(String k, String ret) {
        if (m.containsKey(k)) {
            return m.get(k) != null ? m.get(k).toString() : "";
        } else {
            return ret;
        }
    }

    public int getInt(String k, int ret) {
        if (m.containsKey(k)) {
            return Integer.parseInt(m.get(k).toString());
        } else {
            return ret;
        }
    }

    public long getLong(String k, long ret) {
        if (m.containsKey(k)) {
            return Long.parseLong(m.get(k).toString());
        } else {
            return ret;
        }
    }

    public float getFloat(String k, float ret) {
        if (m.containsKey(k)) {
            return Float.parseFloat(m.get(k).toString());
        } else {
            return ret;
        }
    }

    public double getDouble(String k, double ret) {
        if (m.containsKey(k)) {
            return Double.parseDouble(m.get(k).toString());
        } else {
            return ret;
        }
    }
    
    public Date getDate(String k,Date ret){
    	if (m.containsKey(k)) {
            return (Date)m.get(k);
        } else {
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
	public List<JBaseData> getSeqBaseData(String k) {
        if (m.containsKey(k)) {
            return (List<JBaseData>) m.get(k);
        } else {
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
	public List<Boolean> getSeqBoolean(String k) {
        if (m.containsKey(k)) {
            return (List<Boolean>) m.get(k);
        } else {
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
	public List<Integer> getSeqInt(String k) {
        if (m.containsKey(k)) {
            return (List<Integer>) m.get(k);
        } else {
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
	public List<Float> getSeqFloat(String k) {
        if (m.containsKey(k)) {
            return (List<Float>) m.get(k);
        } else {
            return new ArrayList<>();
        }
    }
}
