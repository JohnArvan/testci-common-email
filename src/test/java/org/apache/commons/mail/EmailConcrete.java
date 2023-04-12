package org.apache.commons.mail;

import java.util.Map;

//Inherits Email class for testing
public class EmailConcrete extends Email {
	
	@Override
	public Email setMsg(String msg) throws EmailException
	{
		return null;
	}
	
	//return headers (not used)
	public Map<String, String> getHeaders()
	{
		return this.headers;
	}
	
	public String getContentType()
	{
		return this.contentType;
	}

}
