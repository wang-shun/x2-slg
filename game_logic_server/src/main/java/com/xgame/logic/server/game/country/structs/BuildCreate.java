package com.xgame.logic.server.game.country.structs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *2016-11-05  22:34:00
 *@author ye.yuan
 *
 */
@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface BuildCreate {

}
