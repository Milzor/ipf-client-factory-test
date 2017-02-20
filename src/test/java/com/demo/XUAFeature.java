package com.demo;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by remcoo on 16/02/2017.
 */
public class XUAFeature extends AbstractFeature {

	@Override
	protected void initializeProvider(InterceptorProvider provider, Bus bus) {
		Map<String, Object> wss4jConfiguration = new HashMap<>();

		wss4jConfiguration.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		wss4jConfiguration.put(WSHandlerConstants.USER, "USERNAME");
		wss4jConfiguration.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		wss4jConfiguration.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());

		provider.getOutInterceptors().add(new WSS4JOutInterceptor(wss4jConfiguration));
	}

}
