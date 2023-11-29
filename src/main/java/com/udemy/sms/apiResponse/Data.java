package com.udemy.sms.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Data{

	@JsonProperty("token")
	private String token;
}