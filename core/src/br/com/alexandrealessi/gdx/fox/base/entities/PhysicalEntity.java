package br.com.alexandrealessi.gdx.fox.base.entities;

/**
 * Created by alex on 02/05/2015.
 */
public interface PhysicalEntity extends Entity {
    void setBodyWrapper (BodyWrapper bodyWrapper);
    BodyWrapper getBodyWrapper ();
}
