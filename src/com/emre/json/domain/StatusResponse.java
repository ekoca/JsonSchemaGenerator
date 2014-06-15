package com.emre.json.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Basic standard web service response with status code.
 * 
 * @author Emre Koca
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StatusResponse {

	/** Generic codes that apply to all services **/
	public static final int SUCCESS = 0, INPUT_VALIDATION_ERROR = 9997,
			NOT_FOUND = 9996, SERVER_ERROR = 9999;

	public final int status;
	public String message;

	@SuppressWarnings("unused")
	private StatusResponse() {
		this(0);
	}

	public StatusResponse(int status) {
		this.status = status;
	}

	public StatusResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public static StatusResponse success() {
		return new StatusResponse(SUCCESS);
	}

	public static StatusResponse serverError() {
		return new StatusResponse(SERVER_ERROR);
	}

	public static StatusResponse serverError(String message) {
		return new StatusResponse(SERVER_ERROR, message);
	}

	public static StatusResponse dataNotFound(String message) {
		return new StatusResponse(NOT_FOUND, message);
	}

	public static StatusResponse inputValidationException(String message) {
		return new StatusResponse(INPUT_VALIDATION_ERROR, message);
	}

	public StatusResponse message(String message) {
		this.message = message;
		return this;
	}
}