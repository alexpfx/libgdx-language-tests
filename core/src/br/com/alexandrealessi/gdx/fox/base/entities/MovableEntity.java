package br.com.alexandrealessi.gdx.fox.base.entities;

/**
 * Created by alex on 02/05/2015.
 */
public interface MovableEntity extends Entity{

    void update(float delta);
    void dispose ();

}