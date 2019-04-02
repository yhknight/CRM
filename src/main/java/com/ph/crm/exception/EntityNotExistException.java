package com.ph.crm.exception;

import lombok.Getter;

public class EntityNotExistException extends RuntimeException {

	public EntityNotExistException(String errorMsg) {
		super(errorMsg);
	}

	public EntityNotExistException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
	}

}
