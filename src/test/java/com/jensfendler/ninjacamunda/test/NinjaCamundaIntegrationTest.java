/*
 * Copyright 2016 Fendler Consulting cc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jensfendler.ninjacamunda.test;

import ninja.NinjaTest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Basic integration tests for Ninja Camunda module.
 * 
 * @author Jens Fendler
 *
 */
public class NinjaCamundaIntegrationTest extends NinjaTest {

	private static OkHttpClient client;

	private static okhttp3.Request.Builder requestBuilder;

	@BeforeClass
	static public void beforeClass() throws Exception {
		// prepare http client
		requestBuilder = new Request.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
		client = new OkHttpClient.Builder().followRedirects(true).addInterceptor(loggingInterceptor).build();
	}

	@Test
	public void testApplication() throws Exception {
		Response response = requestGet("/");
		assertTrue("Index page failed with code " + response.code(), response.isSuccessful());
	}

	@Test
	public void testDeployment() throws Exception {
		Response response = requestGet("/deployments/new");
		assertTrue("New deployment failed with code " + response.code(), response.isSuccessful());
	}

	/**
	 * HTTP Client Helper method to issue a GET request
	 * 
	 * @param appPath
	 * @return
	 * @throws IOException
	 */
	protected Response requestGet(String appPath) throws IOException {
		Request request = buildGetRequest(appPath);
		return client.newCall(request).execute();
	}

	/**
	 * HTTP Client helper method to build a GET request.
	 * 
	 * @param appPath
	 * @return
	 */
	protected Request buildGetRequest(String appPath) {
		return requestBuilder.get().url(getUrl(appPath)).build();
	}

	/**
	 * HTTP Client helper method to build a GET request.
	 * 
	 * @param appPath
	 * @return
	 */
	protected Request buildPostRequest(String appPath, RequestBody requestBody) {
		return requestBuilder.post(requestBody).url(getUrl(appPath)).build();
	}

	/**
	 * HTTP Client helper method to build a GET request.
	 * 
	 * @param appPath
	 * @return
	 */
	protected Request buildJsonPostRequest(String appPath, RequestBody requestBody) {
		return requestBuilder.post(requestBody).url(getUrl(appPath)).addHeader("Accept", "application/json").build();
	}

	/**
	 * @param appPath
	 * @return
	 */
	private String getUrl(String appPath) {
		String url = ninjaTestServer.getBaseUrl() + appPath;
		return url;
	}

}
