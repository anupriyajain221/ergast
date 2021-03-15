package com.nap.ergast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
	private String driverId;
	private String url;
	private String givenName;
	private String familyName;
	private String dateOfBirth;
	private String nationality;
	private String code;
	private int permanentNumber;
}
