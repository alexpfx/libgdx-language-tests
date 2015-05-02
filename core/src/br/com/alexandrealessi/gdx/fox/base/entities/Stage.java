package br.com.alexandrealessi.gdx.fox.base.entities;

import br.com.alexandrealessi.gdx.fox.base.components.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by alex on 01/05/2015.
 */
public abstract class Stage implements InputProcessor {

    private Array<MovableEntity> movableEntities;
    private Array<VisualEntity> visualEntities;
    private SpriteBatch batch;
    private Viewport viewPort;

    public Stage(float width, float height) {
        Camera camera = new OrthographicCamera(width, height);
        viewPort = new StretchViewport(width, height, camera);
        movableEntities = new Array<MovableEntity>();
        visualEntities = new Array<VisualEntity>();
        viewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public final void addMovable(MovableEntity entity) {
        movableEntities.add(entity);
    }

    public final void addVisual(VisualEntity entity) {
        visualEntities.add(entity);
    }

    public final void addEntity(Entity entity) {
        if (entity instanceof VisualEntity) {
            visualEntities.add((VisualEntity) entity);
        }
        if (entity instanceof MovableEntity) {
            movableEntities.add((MovableEntity) entity);
        }
    }

    public final void render() {
        for (MovableEntity e : movableEntities) {
            e.update(Gdx.graphics.getDeltaTime());
        }

        Camera camera = viewPort.getCamera();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (VisualEntity e : visualEntities) {
            e.draw(batch, 1);
        }
        batch.end();
    }

    public void dispose() {
        for (MovableEntity e : movableEntities) {
            e.dispose();
        }
        for (VisualEntity v : visualEntities) {
            v.dispose();
        }

    }

    // Input

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}