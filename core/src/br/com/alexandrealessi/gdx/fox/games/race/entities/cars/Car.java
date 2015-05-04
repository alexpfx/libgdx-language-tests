package br.com.alexandrealessi.gdx.fox.games.race.entities.cars;

import br.com.alexandrealessi.gdx.fox.base.entities.*;
import br.com.alexandrealessi.gdx.fox.base.entities.utils.BodyName;
import br.com.alexandrealessi.gdx.fox.base.entities.utils.DrawableName;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by alex on 02/05/2015.
 */
//TODO: car nao eh Physcal nem Visual entity ja q nao tem corpo nem figura que represente.

public class Car implements Accelerable, PhysicalEntity, VisualEntity {

    private Chassis chassis;
    private Wheel frontWheel;
    private Wheel rearWheel;

    public Car() {
        chassis = new Chassis();
        frontWheel = new Wheel();
        rearWheel = new Wheel();
    }

    @DrawableName(atlasName = "game.atlas", drawableName = "peugeot_chassis")
    @BodyName(bodyNameReference = "peugeot_chassis")
    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
    }

    @DrawableName(atlasName = "game.atlas", drawableName = "peugeot_front_wheel")
    @BodyName(bodyNameReference = "peugeot_front_wheel")
    public Wheel getFrontWheel() {
        return frontWheel;
    }

    public void setFrontWheel(Wheel frontWheel) {
        this.frontWheel = frontWheel;
    }

    @DrawableName(atlasName = "game.atlas", drawableName = "peugeot_rear_wheel")
    @BodyName(bodyNameReference = "peugeot_rear_wheel")
    public Wheel getRearWheel() {
        return rearWheel;
    }

    public void setRearWheel(Wheel rearWheel) {
        this.rearWheel = rearWheel;
    }

    @Override
    public void accelerate(float amount, float direction) {
        frontWheel.accelerate(amount, direction);
        rearWheel.accelerate(amount,direction);
    }

    @Override
    public void brek(float amount) {
    }

    @Override
    public void setBody(Body body) {

    }

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public void draw(SpriteBatch batch, float alpha) {

    }

    @Override
    public void setDrawable(Drawable drawable) {

    }

    @Override
    public void dispose() {

    }
}
