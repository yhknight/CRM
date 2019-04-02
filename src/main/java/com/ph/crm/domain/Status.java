package com.ph.crm.domain;

public enum Status {
	PROSPECTIVE("00"), CURRENT("01"), NONACTIVE("02");

	private String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
