package com.github.johnsonmoon.fastorm.mapper.common;

import java.io.Serializable;

/**
 * Created by johnsonmoon at 2018/4/8 14:00.
 */
public class MapperException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;

	public MapperException() {
	}

	public MapperException(String message) {
		super(message);
	}

	public MapperException(String message, Throwable cause) {
		super(message, cause);
	}

	public MapperException(Throwable cause) {
		super(cause);
	}

	public MapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
