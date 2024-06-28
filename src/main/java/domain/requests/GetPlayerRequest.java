package domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.data.JsonConverter;

import java.util.Objects;


public class GetPlayerRequest {

    private Object playerId;
    @JsonIgnore
    private Boolean ignoreNulls;


    public GetPlayerRequest() {
    }

    public GetPlayerRequest(Object playerId) {
        this.playerId = playerId;
    }

    public GetPlayerRequest(Object playerId, Boolean ignoreNulls) {
        this.playerId = playerId;
        this.ignoreNulls = ignoreNulls;
    }


    public Object getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Object playerId) {
        this.playerId = playerId;
    }


    public String toJson() {
        return new JsonConverter().toJson(this, Objects.requireNonNullElse(ignoreNulls, false));
    }
}
