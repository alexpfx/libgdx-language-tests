package br.com.alexandrealessi.gdx.fox.base.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

/**
 * Created by alex on 01/05/2015.
 */
public abstract class DefaultEntity implements MovableEntity, VisualEntity, PhysicalEntity {

    private Body body;
    private Drawable drawable;
    private final Array<Script> scripts;

    public DefaultEntity() {
        this.scripts = new Array<Script>();
    }

    public void addScript(Script script) {
        scripts.add(script);
    }

    @Override
    public void setBodyWrapper(Body body) {
        this.body = body;
    }

    @Override
    public Body getBodyWrapper() {
        return body;
    }

    @Override
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void update(float delta) {
        for (Script script : scripts) {
            script.run(delta);
        }
    }

    @Override
    public void draw(SpriteBatch batch, float alpha) {
        if (body == null || drawable == null)
            return;
        drawable.draw(batch, alpha, body.getPosition(), body.getAngle() * MathUtils.radDeg);
    }

    @Override
    public void dispose() {

    }
}
