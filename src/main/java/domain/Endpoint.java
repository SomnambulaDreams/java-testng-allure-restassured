package domain;

import utils.rest.RestMethod;

import static utils.rest.RestMethod.*;


public enum Endpoint {

    PLAYER_CREATE  ("/player/create/{editor}",      GET),
    PLAYER_GET     ("/player/get",                  POST),
    PLAYER_GET_ALL ("/player/get/all",              GET),
    PLAYER_DELETE  ("/player/delete/{editor}",      DELETE),
    PLAYER_UPDATE  ("/player/update/{editor}/{id}", PATCH);

    public final String route;
    public final RestMethod method;


    Endpoint(String route, RestMethod method) {
        this.route = route;
        this.method = method;
    }
}
