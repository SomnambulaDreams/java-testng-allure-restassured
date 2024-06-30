package domain.responses;

import java.util.List;
import java.util.Objects;


public class GetAllPlayersResponse {

    private List<PlayerResponse> players;


    public GetAllPlayersResponse() {
    }

    public GetAllPlayersResponse(List<PlayerResponse> players) {
        this.players = players;
    }


    public List<PlayerResponse> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerResponse> players) {
        this.players = players;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof GetAllPlayersResponse)) return false;
        GetAllPlayersResponse that = (GetAllPlayersResponse) object;
        return Objects.equals(getPlayers(), that.getPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPlayers());
    }
}
