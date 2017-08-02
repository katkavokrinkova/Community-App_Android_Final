package net.impacthub.members.application;

import com.salesforce.androidsdk.rest.RestRequest;

import java.io.UnsupportedEncodingException;

public interface RestRequestFactory {

    RestRequest getForQuery(String sql) throws UnsupportedEncodingException;
}
