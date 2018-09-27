package com.xgame.framework.network.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vyang on 6/19/16.
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public final class CallResult<T>{
	private int callbackId;
	private T result;
}
