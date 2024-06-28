package utils.listener;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.logging.Logger;


public class RestAssuredListener implements Filter {

    private static final Logger logger = Logger.getLogger(RestAssuredListener.class.getName());


    @Override
    public Response filter(FilterableRequestSpecification requestSpecification, FilterableResponseSpecification responseSpecification, FilterContext context) {

        Response response = context.next(requestSpecification, responseSpecification);

        if(response.getStatusCode() != 200 || response.getStatusCode() != 201) {
            logger.info(
                    "Request Specification:" +
                            "\nURI:          " + requestSpecification.getURI() +
                            "\nMethod:       " + requestSpecification.getMethod() +
                            "\nHeaders:      " + requestSpecification.getHeaders() +
                            "\nPath params:  " + requestSpecification.getPathParams() +
                            "\nQuery params: " + requestSpecification.getQueryParams() +
                            "\nRequest body: " + requestSpecification.getBody() +
                            "\nResponse Specification:\n" +
                            "\nResponse Status code:\n" + response.getStatusCode() +
                            "\nResponse Body:\n" + response.getBody().prettyPrint()
            );
        }
        return response;
    }
}
