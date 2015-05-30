package br.com.alexandrealessi.gdx.fox.games.soccer.screens;

import br.com.alexandrealessi.gdx.fox.base.ashley.components.BodyComponent;
import br.com.alexandrealessi.gdx.fox.base.ashley.components.CameraFollowerComponent;
import br.com.alexandrealessi.gdx.fox.base.ashley.components.PositionComponent;
import br.com.alexandrealessi.gdx.fox.base.ashley.components.SpriteComponent;
import br.com.alexandrealessi.gdx.fox.base.screens.BaseScreen;
import br.com.alexandrealessi.gdx.fox.base.utils.BodyBuilder;
import br.com.alexandrealessi.gdx.fox.base.utils.RubeSceneHelper;
import br.com.alexandrealessi.gdx.fox.games.soccer.SoccerGame;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.entities.*;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.systems.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by alexandre on 23/05/15.
 */
public class GamePlayScreen extends BaseScreen {
    //1248 x 794
    //1700 x 1150

//    private static final float SCENE_WIDTH = 159.761f;
//    private static final float SCENE_HEIGHT = 100;

    private static final float SCENE_WIDTH = 178f;
    private static final float SCENE_HEIGHT = 120;
    private static final float ANIMAL_SPRITE_SCALE = 7F;
    private static final Rectangle SCENE_BOUNDS = new Rectangle(-SCENE_WIDTH , -SCENE_HEIGHT , SCENE_WIDTH , SCENE_HEIGHT );
    int n = 0;
    int c = MathUtils.random(4);
    private Engine engine;
    private Entity field;
    private TextureAtlas atlas;
    private RubeSceneHelper rubeSceneHelper;
    private OrthographicCamera camera;
    private Viewport viewport;

    public GamePlayScreen(SoccerGame game) {
        super(game);
        atlas = new TextureAtlas(Gdx.files.internal("data/images/game.atlas"));
        rubeSceneHelper = new RubeSceneHelper("soccer.json");
        engine = new Engine();
        camera = new OrthographicCamera();
        camera.zoom = 0.6f;

        Entity fieldEntity = new Entity();
        createWorldEntity(rubeSceneHelper.getWorld());

        final Sprite panda = new Sprite(atlas.findRegion("panda"));
        panda.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        panda.setScale(ANIMAL_SPRITE_SCALE / panda.getHeight());

        final Sprite girafa = new Sprite(atlas.findRegion("giraffe"));
        girafa.setScale(ANIMAL_SPRITE_SCALE / girafa.getHeight());

        field = new Entity();
        field.add(new BodyComponent(rubeSceneHelper.getBody("field")));
        field.add(new PositionComponent());

        final Sprite soccer = new Sprite(atlas.findRegion("small_field"));
        soccer.setScale(SCENE_HEIGHT / soccer.getHeight());
        field.add(new SpriteComponent(soccer));

        final Sprite ballSprite = new Sprite(atlas.findRegion("ball"));
        final Body ballBody = rubeSceneHelper.getBody("ball");
        final Entity ballEntity = new Entity();
        ballEntity.add(new SpriteComponent(ballSprite));
        ballEntity.add(new BodyComponent(ballBody));
        final PositionComponent positionComponent = new PositionComponent();
        ballEntity.add(positionComponent);
        ballEntity.add(new CameraFollowerComponent(camera, SCENE_BOUNDS));
        ballSprite.setScale(2 / ballSprite.getHeight());

        viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

        ContactSystem contactSystem = new ContactSystem();
        WorldStepSystem worldStepSystem = new WorldStepSystem();
        MetersToPixelConvertSystem metersToPixelConvertSystem = new MetersToPixelConvertSystem(1);
        RenderSystem renderSystem = new RenderSystem(viewport, true);
        CameraPositionSystem cameraPositionSystem = new CameraPositionSystem();
        AISystem aiSystem = new AISystem();

        final Sprite monkey = new Sprite(atlas.findRegion("monkey"));
        monkey.setScale(ANIMAL_SPRITE_SCALE / monkey.getHeight());

        final Sprite parrot = new Sprite(atlas.findRegion("parrot"));
        parrot.setScale(ANIMAL_SPRITE_SCALE / parrot.getHeight());

        engine.addEntity(field);
        engine.addEntity(ballEntity);

        final Team tpanda = createTeam("panda", panda);
        final Team tgirafa = createTeam("girafa", girafa);
        final Team tmonkey = createTeam("monkey", monkey);
        final Team tparrot = createTeam("parrot", parrot);

        addTeamToEngine(engine, tpanda);
        addTeamToEngine(engine, tgirafa);
        addTeamToEngine(engine, tmonkey);
        addTeamToEngine(engine, tparrot);

//        engine.addSystem(contactSystem);
        engine.addSystem(aiSystem);
        engine.addSystem(metersToPixelConvertSystem);

        engine.addSystem(cameraPositionSystem);
        engine.addSystem(renderSystem);
        engine.addSystem(worldStepSystem);

    }

    private void createWorldEntity(World world) {
        Entity worldEntity = new Entity();
        worldEntity.add(new WorldComponent(world));
        engine.addEntity(worldEntity);
    }

    private void addTeamToEngine(Engine engine, Team galaticos) {
        for (PlayerEntity p : galaticos.getPlayers()) {
            engine.addEntity(p);
        }
    }

    public Team createTeam(String name, Sprite uniform) {
        String playerName = "Ochoa";
        n++;
        PlayerPosition position = PlayerPosition.GK;
        Array<PlayerEntity> players = new Array<PlayerEntity>();

        PlayerEntity player = createPlayer(uniform, playerName, n, position, players);
        players.add(player);
        return new Team(name, players);
    }

    private PlayerEntity createPlayer(Sprite uniform, String playerName, int n, PlayerPosition position, Array<PlayerEntity> players) {
        final Body playerBodyModel = rubeSceneHelper.getBody("player");
        PlayerEntity.Builder builder = PlayerEntity.newBuilder().name(playerName).number(n).position(position);
        builder.addComponent(new SpriteComponent(uniform));
        builder.addComponent(new PositionComponent());
        final PlayerEntity player = builder.build();
        final Body body = BodyBuilder.clone(playerBodyModel).build();
        body.setUserData(PlayerUserData.getFor(player));
        player.add(new BodyComponent(body));
        return player;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//        worldViewport.update(width, height);
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
