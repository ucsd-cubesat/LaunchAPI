package com.tritoncubed.launchapi.exceptions;

public class UnsupportedAttributeException extends APIException {

	private static final long serialVersionUID = -6880568591127658510L;

	public UnsupportedAttributeException(String name, Class<?> type) {
		super(String.format("Unsupported attribute \"%s\" of type %s", name, type.getTypeName()));
	}

}
