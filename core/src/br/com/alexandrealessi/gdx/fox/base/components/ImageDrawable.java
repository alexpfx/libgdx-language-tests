package br.com.alexandrealessi.gdx.fox.base.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by alexandre on 26/04/15.
 */
public class ImageDrawable implements Drawable {

    private final SpriteDrawable spriteDrawable;

    private ImageDrawable(SpriteDrawable spriteDrawable) {
        this.spriteDrawable = spriteDrawable;
    }

    public static ImageDrawable createFromTextureRegion(TextureRegion textureRegion) {

        return new ImageDrawable(new SpriteDrawable(new Sprite(textureRegion)));
    }

    @Override
    public void draw(Batch batch, float alpha, Body body) {
        spriteDrawable.draw(batch, 500f, 250f, getWidth(), getHeight());
    }

    @Override
    public float getWidth() {
        return spriteDrawable.getSprite().getWidth();
    }

    @Override
    public float getHeight() {
        return spriteDrawable.getSprite().getHeight();
    }

}