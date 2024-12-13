package com.booking.err;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookError {
	
	WebDriver driver;
	Select select;
	JavascriptExecutor js;
	List<Integer>dev = new ArrayList<Integer>();
	List<Integer>qa = new ArrayList<Integer>();
	
	
	public static void main(String[] args) throws InterruptedException {
		BookError bookMain = new BookError();
		bookMain.launchBrowser();
		bookMain.login();
		bookMain.selectHotelBooking();
		bookMain.getErrorMsg();
		bookMain.SearchHotels();
		bookMain.iterateHotelPrice();
		
	}
	
	public void launchBrowser() {
		driver = new ChromeDriver();
		driver.get("https://www.omrbranch.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}
	
	public void login() {
		
		WebElement userTxt = driver.findElement(By.id("email"));
		userTxt.sendKeys("manikandang0612@gmail.com");
		WebElement passTxt = driver.findElement(By.id("pass"));
		passTxt.sendKeys("Mani@0612");
		WebElement lgnBtnClick = driver.findElement(By.xpath("//button[@value = 'login']"));
		lgnBtnClick.click();
		
		
	}
	
	public void selectHotelBooking() {
		WebElement selectBook = driver.findElement(By.xpath("//img[@alt = 'hotel booking']"));
		selectBook.click();
	}
	
	public void searchBtnClicked() {
		WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'iframe']"));
		driver.switchTo().frame(frame);
		WebElement searchBtn = driver.findElement(By.id("searchBtn"));
		searchBtn.click();
		driver.switchTo().defaultContent();
	}
	
	public void getErrorMsg() {
		searchBtnClicked();
		
		WebElement inValidState = driver.findElement(By.id("invalid-state"));
		WebElement inValidCity = driver.findElement(By.id("invalid-city"));
		WebElement inValidCheckIn = driver.findElement(By.id("invalid-check_in"));
		WebElement inValidCheckOut = driver.findElement(By.id("invalid-check_out"));
		WebElement inValidRooms = driver.findElement(By.id("invalid-no_rooms"));
		WebElement inValidAdult = driver.findElement(By.id("invalid-no_adults"));
		List<String>errorList = Arrays.asList(inValidState.getText(),inValidCity.getText(),inValidCheckIn.getText(),inValidCheckOut.getText(),
				                inValidCheckOut.getText(),inValidRooms.getText(),inValidAdult.getText());
		for (String errorMsg : errorList) {
			System.out.println(errorMsg);
		}
		
	}
	
	public void SearchHotels() {
		WebElement selectState = driver.findElement(By.id("state"));
		select = new Select(selectState);
		select.selectByIndex(5);
		
		WebElement selectCity = driver.findElement(By.id("city"));
		select = new Select(selectCity);
		select.selectByIndex(4);
		
		WebElement selectRoom = driver.findElement(By.id("room_type"));
		select = new Select(selectRoom);
		List<WebElement> roomOptions = select.getOptions();
		for (int i = 1; i < roomOptions.size(); i++) {
			//WebElement option = roomOptions.get(i);
	       select.selectByIndex(i);
			
			
		}
		
		WebElement checkIn = driver.findElement(By.name("check_in"));
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('value','2024-12-17')", checkIn);
		
		
		WebElement checkOut = driver.findElement(By.name("check_out"));
		js.executeScript("arguments[0].setAttribute('value','2024-12-19')", checkOut);
		
		WebElement noOfRooms = driver.findElement(By.id("no_rooms"));
		select = new Select(noOfRooms);
		select.selectByIndex(1);
		
		WebElement noOfAdults = driver.findElement(By.id("no_adults"));
		select = new Select(noOfAdults);
		select.selectByIndex(2);
				
		WebElement noOdChilds = driver.findElement(By.id("no_child"));
		noOdChilds.sendKeys("1");
		
		searchBtnClicked();

	}
	
	public void selectLowToHigh() {
		WebElement lowToHighBtn = driver.findElement(By.id("value_pltoh"));
		js.executeScript("arguments[0].click()", lowToHighBtn);
		
	}
	
	public void iterateHotelPrice() throws InterruptedException {
		
		selectLowToHigh();
		Thread.sleep(5000);
		
		WebElement hotelList = driver.findElement(By.id("hotellist"));
		List<WebElement> hotelprices = hotelList.findElements(By.tagName("strong"));
		for (WebElement price : hotelprices) {
			
			dev.add(Integer.parseInt(price.getText().substring(2).replace(",", "")));
			
		}
		
		qa.addAll(dev);
		Collections.sort(qa);
		for (int i = 0; i < qa.size(); i++) {
			System.out.println("dev:" +"   " + dev.get(i) + "   " + "qa:" +"  " + qa.get(i));
				
		}
		
		
	}

}
