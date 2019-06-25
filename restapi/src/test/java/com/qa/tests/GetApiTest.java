package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.ClosedByInterruptException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetApiTest extends TestBase {
	TestBase testBase;
	String key;
	String token;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	String cardName;
	String cardId1;
	
	@BeforeMethod

	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		key = prop.getProperty("Key");
		token = prop.getProperty("Token");
		cardName = prop.getProperty("CardName");
		System.out.println("cardname is " + " " + cardName);
	}

	@Test(priority = 2)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException, URISyntaxException {
		restClient = new RestClient();
		closebaleHttpResponse = restClient.getCard("5d10b8e6e9fe4f2c8eb10d13",key, token);

		// a. Status Code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// b. Json String:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);

		System.out.println("Response JSON from API---> " + responseJson);

	}

	@Test(priority = 1)
	public void postBoard() throws ClientProtocolException, IOException, URISyntaxException {
		restClient = new RestClient();
		restClient.postBoard(key, token);
		restClient.postList(key, token);
		closebaleHttpResponse = restClient.postCard(key, token, cardName);
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
//		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
//		JSONObject responseJson = new JSONObject(responseString);
//		cardId1 = TestUtils.getValueByJPath(responseJson, "/id");
//		System.out.println("Card ID is" + " " + cardId1);
//		String cardNameromResponse = TestUtils.getValueByJPath(responseJson, "/name");
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
		
	}

	@Test(priority = 3)
	public void AddLableToCard() throws ClientProtocolException, IOException, URISyntaxException {
		restClient = new RestClient();
		restClient.postBoard(key, token);
		restClient.postList(key, token);
		restClient.postCard(key, token, cardName);
		restClient.postLable(key, token, "purple");
		CloseableHttpResponse closebaleHttpResponseForAddLabel = restClient.AddLableToCard(key, token);
		int statusCode = closebaleHttpResponseForAddLabel.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
	}

}