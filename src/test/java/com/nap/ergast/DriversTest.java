package com.nap.ergast;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class DriversTest {
	private Response response;

	@Given("I get all the drivers data from the api")
	public void getDriversDataFromApi() {
		response = given().get("http://ergast.com/api/f1/drivers.json").then().
				contentType(ContentType.JSON).extract().response();
	}
	
	@Then("^response should have status code \"([\\d]*)\"$")
	public void responseStatusCode(int statusCode) {
		 response.then().statusCode(equalTo(statusCode));
	}
	
	@Then("^that the total number of drivers should be \"([\\d]*)\"$")
	public void getTotalNumberOfDrivers(int noOfDrivers) {
		assertEquals(noOfDrivers, getDrivers().size());
	}

	@Then("^that the nationality of driver name \"([a-zA-Z]*)\" is \"([a-zA-Z]*)\"$")
	public void findNationalityOfDriver(String driverId, String nationality) {
		assertEquals(1, getDrivers().stream().filter(driver -> driverId.equals(driver.getDriverId()))
			.peek(driver -> assertEquals(nationality, driver.getNationality())).count());
		
		assertEquals(nationality, getDrivers().stream()
				.filter(driver -> driverId.equals(driver.getDriverId())).findFirst().get().getNationality());
	}

	@Then("^that the total number of drivers with nationality \"([a-zA-Z]*)\" should be \"([\\d]*)\" and their driverIds are \"(.*)\"$")
	public void findTotalNumberOfDriversWithSpecificNationality(String nationality, int noOfDrivers, String driverIds) {
		List<String> driverIdsList = Arrays.asList(driverIds.split(","));
		assertEquals(noOfDrivers, getDrivers().stream()
							.filter(driver -> nationality.equals(driver.getNationality()))
							.peek(driver -> assertTrue(driverIdsList.contains(driver.getDriverId())))
							.count());
	}
	
	@Then("^that the total number of drivers born between \"([\\d]*)\" and \"([\\d]*)\" should be \"([\\d]*)\" and their givenNames are \"(.*)\"$")
	public void findDriversBornBetweenSpecificYears(int year1, int year2, int noOfDrivers, String givenNames) {
		List<String> givenNamesList = Arrays.asList(givenNames.split(","));
		assertEquals(noOfDrivers, getDrivers().stream()
			.filter(driver -> new Integer(driver.getDateOfBirth().substring(0, 4)).intValue() >= year1 && new Integer(driver.getDateOfBirth().substring(0, 4)).intValue() <= year2)
			.peek(driver -> assertTrue(givenNamesList.contains(driver.getGivenName())))
			.count());
	}
	
	private List<Driver> getDrivers() {
		List<Driver> drivers = null;
        try {
        	drivers = response.jsonPath().getList("MRData.DriverTable.Drivers", Driver.class);
            if (drivers == null || drivers.size() == 0) {
                throw new IOException();
            }
        } catch (IOException e) {
            fail("Error occured while extracting drivers");
        }

        return drivers;
	}
}
