package br.com.alexandrealessi.gdx.fox.games.soccer.entities;

import br.com.alexandrealessi.gdx.fox.games.soccer.components.BodyComponent;
import br.com.alexandrealessi.gdx.fox.games.soccer.entities.utils.PlayerPosition;
import com.badlogic.ashley.core.Entity;

/**
 * Created by alexandre on 25/05/15.
 */
public class Player extends Entity {
    private String name;
    private int number;
    private PlayerPosition position;
    private Team team;

    public Player(String name, int number, PlayerPosition position) {
        this.team = team;
        this.name = name;
        this.number = number;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}