package utils.rest;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.List;


public class Rest {

    private final String baseUrl;
    private final String endpoint;
    private final RestMethod method;
    private final RequestSpecification requestSpecification;


    private Rest(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.endpoint = builder.endpoint;
        this.method = builder.method;
        this.requestSpecification = builder.requestSpecification;
    }


    public Response executeRequest() {
        if (method.equals(RestMethod.POST))   return requestSpecification.post(endpoint);
        if (method.equals(RestMethod.PUT))    return requestSpecification.put(endpoint);
        if (method.equals(RestMethod.PATCH))  return requestSpecification.patch(endpoint);
        if (method.equals(RestMethod.DELETE)) return requestSpecification.delete(endpoint);
        if (method.equals(RestMethod.GET))    return requestSpecification.get(endpoint);
        throw new RuntimeException("Pay attention! You are trying to use incorrect Request Method");
    }


    public static class Builder {

        private final String baseUrl;
        private final String endpoint;
        private final RestMethod method;
        private final RequestSpecification requestSpecification;


        public Builder(String baseUrl, String endpoint, RestMethod method) {
            if (baseUrl == null || endpoint == null || method == null)
                throw new IllegalArgumentException("BaseUrl, endpoint or rest method can not be null");
            this.baseUrl = baseUrl;
            this.endpoint = endpoint;
            this.method = method;
            RestAssured.baseURI = baseUrl;
            RestAssured.useRelaxedHTTPSValidation();
            this.requestSpecification = RestAssured.given();
        }

        public Builder listeners(Filter... filters) {
            for (Filter filter : filters)
                requestSpecification.filter(filter);
            return this;
        }

        public Builder headers(List<RestParameter> headers) {
            if (headers != null && !headers.isEmpty())
                for (RestParameter headerItem : headers)
                    requestSpecification.header(headerItem.getKey(), headerItem.getValue());
            return this;
        }

        public Builder oauth2(String token) {
            if (token != null)
                requestSpecification.auth().oauth2(token);
            return this;
        }

        public Builder pathParams(List<RestParameter> pathParams) {
            if (pathParams != null && !pathParams.isEmpty())
                for (RestParameter parameter : pathParams)
                    requestSpecification.pathParam(parameter.getKey(), parameter.getValue());
            return this;
        }

        public Builder queryParams(List<RestParameter> queryParams) {
            if (queryParams != null && !queryParams.isEmpty())
                for (RestParameter parameter : queryParams)
                    requestSpecification.queryParam(parameter.getKey(), parameter.getValue());
            return this;
        }

        public Builder formParams(List<RestParameter> formParams) {
            if (formParams != null && !formParams.isEmpty())
                for (RestParameter parameter : formParams)
                    requestSpecification.formParam(parameter.getKey(), parameter.getValue());
            return this;
        }

        public Builder multiPart(FileToUpload fileToUpload) {
            if (fileToUpload != null)
                requestSpecification.multiPart(fileToUpload.getMultipartName(), new File(fileToUpload.getFilePath()));
            return this;
        }

        public Builder body(String body) {
            if (body != null)
                requestSpecification.body(body);
            return this;
        }

        public Rest build() {
            return new Rest(this);
        }
    }
}
















