package com.emre.json.domain;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonOperation {

	@JsonInclude(Include.NON_NULL)
	public static class Request {
		public String requestType;
		public Data data = new Data();

		@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
		public static class Data {
			String username;
			String email;
			String password;
			String birthday;
			String coinsPackage;
			String coins;
			String transactionId;
			boolean isLoggedIn;
		}
	}

	@JsonInclude(Include.NON_EMPTY)
	public static class Response {
		public String requestType = null;
		public Data data = new Data();


		@JsonInclude(Include.NON_EMPTY)
		public static class Data {
			enum ErrorCode {
				ERROR_INVALID_LOGIN, ERROR_USERNAME_ALREADY_TAKEN, ERROR_EMAIL_ALREADY_TAKEN
			};

			enum Status {
				OK, ERROR
			};

			Status status;
			ErrorCode errorCode;
			String expiry;
			int coins;
			String email;
			String birthday;
			String pictureUrl;
		}
	}

	private Request request = new Request();
	private Response response = new Response();

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper(); 
		/*
		 * The default configuration of an ObjectMapper instance is to only access properties 
		 * that are public fields or have public getters/setters. 
		 * An alternative to changing the class definition to make a field public or to provide a public getter/setter is 
		 * to specify (to the underlying VisibilityChecker) a different property visibility rule. 
		 * Jackson 1.9 provides the ObjectMapper.setVisibility() convenience method for doing so.
		 */
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        JsonOperation subscribe         = new JsonOperation();
        subscribe.request.requestType   = "login";
        subscribe.request.data.username = "Vincent";
        subscribe.request.data.password = "test";
        subscribe.response.requestType  = "welcome";
        subscribe.response.data.status  = com.emre.json.domain.JsonOperation.Response.Data.Status.OK;

        Writer strWriter = new StringWriter();
        try {
        	String request = mapper.writeValueAsString(subscribe.request);
            String response = mapper.writeValueAsString(subscribe.response);
            mapper.writeValue(strWriter, subscribe);

            System.out.println(request);
            System.out.println(response);
            System.out.println(strWriter.toString());
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}