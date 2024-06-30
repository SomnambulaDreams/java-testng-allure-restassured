package domain.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.data.JsonConverter;

import java.util.Objects;


public class DeletePlayerRequest {

    private Object playerId;
    @JsonIgnore
    private Boolean ignoreNulls;


    public DeletePlayerRequest() {
    }

    public DeletePlayerRequest(Object playerId) {
        this.playerId = playerId;
    }

    public DeletePlayerRequest(Object playerId, Boolean ignoreNulls) {
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
