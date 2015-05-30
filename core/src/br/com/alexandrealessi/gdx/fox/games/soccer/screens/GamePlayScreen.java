package br.com.alexandrealessi.gdx.fox.games.soccer.screens;

import br.com.alexandrealessi.gdx.fox.base.ashley.components.BodyComponent;
import br.com.alexandrealessi.gdx.fox.base.ashley.components.PositionComponent;
import br.com.alexandrealessi.gdx.fox.base.ashley.components.SpriteComponent;
import br.com.alexandrealessi.gdx.fox.base.screens.BaseScreen;
import br.com.alexandrealessi.gdx.fox.base.utils.RubeSceneHelper;
import br.com.alexandrealessi.gdx.fox.games.soccer.SoccerGame;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.entities.*;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.entities.utils.PlayerBuilder;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.systems.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by alexandre on 23/05/15.
 */
public class GamePlayScreen extends BaseScreen {
    //1248 x 794
    private static final float SCENE_WIDTH = 159.761f;
    private static final float SCENE_HEIGHT = 100;
    private static final float ANIMAL_SPRITE_SCALE = 7F;

    private Engine engine;
    private Entity field;
    private TextureAtlas atlas;
    private RubeSceneHelper rubeSceneHelper;
    private OrthographicCamera camera;
    private OrthographicCamera worldCamera;
    private Viewport viewport;
    private PlayerBuilder builder = new PlayerBuilder(new Vector2(SCENE_WIDTH, SCENE_HEIGHT));

    public GamePlayScreen(SoccerGame game) {
        super(game);
        atlas = new TextureAtlas(Gdx.files.internal("data/images/game.atlas"));
        rubeSceneHelper = new RubeSceneHelper("soccer.json");
        engine = new Engine();


        Entity fieldEntity = new Entity();
        createWorldEntity (rubeSceneHelper.getWorld());


//        fieldEntity.add(new WorldComponent(rubeSceneHelper.getWorld()));


        final Sprite panda = new Sprite(atlas.findRegion("panda"));
        panda.getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        panda.setScale(ANIMAL_SPRITE_SCALE / panda.getHeight());

        final Sprite girafa = new Sprite(atlas.findRegion("giraffe"));
        girafa.setScale(ANIMAL_SPRITE_SCALE / girafa.getHeight());

        field = new Entity();
        field.add(new BodyComponent(rubeSceneHelper.getBody("field")));
        field.add(new PositionComponent());

        final Sprite soccer = new Sprite(atlas.findRegion("soccer"));
        soccer.setScale(100 / soccer.getHeight());
        field.add(new SpriteComponent(soccer));

        camera = new OrthographicCamera();

        viewport = new StretchViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

        ContactSystem contactSystem = new ContactSystem();
        WorldStepSystem worldStepSystem = new WorldStepSystem();
        PhysicToScreenSystem physicToScreenSystem = new PhysicToScreenSystem(1);
        RenderSystem renderSystem = new RenderSystem(viewport, true);
        AISystem aiSystem = new AISystem();


        final Sprite monkey = new Sprite(atlas.findRegion("monkey"));
        monkey.setScale(ANIMAL_SPRITE_SCALE / monkey.getHeight());

        final Sprite parrot = new Sprite(atlas.findRegion("parrot"));
        parrot.setScale(ANIMAL_SPRITE_SCALE / parrot.getHeight());

        engine.addEntity(field);

//        final Team tpanda = createTeam("panda", panda);
//        final Team tgirafa = createTeam("girafa", girafa);
//        final Team tmonkey = createTeam("monkey", monkey);
        final Team tparrot = createTeam("parrot", parrot);

//        addTeamToEngine(engine, tpanda);
//        addTeamToEngine(engine, tgirafa);
//        addTeamToEngine(engine, tmonkey);
        addTeamToEngine(engine, tparrot);


//        engine.addSystem(contactSystem);
        engine.addSystem(aiSystem);
        engine.addSystem(physicToScreenSystem);
        engine.addSystem(renderSystem);
        engine.addSystem(worldStepSystem);


    }

    private void createWorldEntity(World world) {
        Entity worldEntity = new Entity();
        worldEntity.add(new WorldComponent(world));
        engine.addEntity(worldEntity);
    }



    private void addTeamToEngine(Engine engine, Team galaticos) {
        for (Player p : galaticos.getPlayers()) {
            engine.addEntity(p);
        }
    }

    public Team createTeam(String name, Sprite uniform) {

        final Body playerBody = rubeSceneHelper.getBody("player");
        final Array<Fixture> head = rubeSceneHelper.getFixturesByName("head");
        for (Fixture f : head) {
            f.setUserData("head");
        }
        final Array<Fixture> tail = rubeSceneHelper.getFixturesByName("tail");
        for (Fixture f : tail) {
            f.setUserData("tail");
        }
        Array<Player> players = new Array<Player>();

//        players.add(builder.createPlayerEntity(createPlayerData("Ochoa", 1, PlayerPosition.GK), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Messi", 10, PlayerPosition.ATTACKER), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Ronaldo", 9, PlayerPosition.ATTACKER), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Neymar", 11, PlayerPosition.ATTACKER), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Iniesta", 6, PlayerPosition.MIDDLEFIELD), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Xabi Alonso", 8, PlayerPosition.MIDDLEFIELD), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("James Rodriguez", 7, PlayerPosition.MIDDLEFIELD), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Dani Alves", 2, PlayerPosition.DEFENDER), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Mascherano", 3, PlayerPosition.DEFENDER), uniform, playerBody));
//        players.add(builder.createPlayerEntity(createPlayerData("Boateng", 4, PlayerPosition.DEFENDER), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Lahm", 5, PlayerPosition.DEFENDER), uniform, playerBody));
        return new Team(name, players);
    }

    public PlayerData createPlayerData(String name, int number, PlayerPosition position) {
        PlayerData p = new PlayerData();
        p.position = position;
        p.number = number;
        p.name = name;
        return p;
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
