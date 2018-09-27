package com.xgame.framework.network.server;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SessionManager1 {
	private ConcurrentHashMap<Long, Session1> activeSessions;
	private AtomicLong sidSeq = new AtomicLong(0L);

	@Getter
	private int maxCount;

	public SessionManager1(int capacity) {
		this.maxCount = capacity;
		int i = 1;
		while (capacity > 0) {
			i <<= 1;
			capacity >>= 1;
		}
		this.activeSessions = new ConcurrentHashMap<>(Math.max(i, 16), 1f);
	}

	public int sessionCount() {
		return activeSessions.size();
	}

	public void getSession(long sessionId) {
		activeSessions.get(sessionId);
	}

	public long addSession(Session1 session) {
		long sid = sidSeq.incrementAndGet();
		activeSessions.put(sid, session);
		return sid;
	}

	public Session1 removeSession(long sessionId) {
		return activeSessions.remove(sessionId);
	}

	public List<Session1> allSessions() {
		return Lists.newArrayList(activeSessions.values());
	}

	public boolean isFull() {
		return maxCount <= sessionCount();
	}
}
