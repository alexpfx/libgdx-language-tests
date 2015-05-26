package br.com.alexandrealessi.gdx.fox.games.soccer.screens;

import br.com.alexandrealessi.gdx.fox.base.entities.utils.RubeSceneWrapper;
import br.com.alexandrealessi.gdx.fox.base.screens.BaseScreen;
import br.com.alexandrealessi.gdx.fox.games.soccer.SoccerGame;
import br.com.alexandrealessi.gdx.fox.games.soccer.components.BodyComponent;
import br.com.alexandrealessi.gdx.fox.games.soccer.components.PositionComponent;
import br.com.alexandrealessi.gdx.fox.games.soccer.components.SpriteComponent;
import br.com.alexandrealessi.gdx.fox.games.soccer.entities.Player;
import br.com.alexandrealessi.gdx.fox.games.soccer.entities.Team;
import br.com.alexandrealessi.gdx.fox.games.soccer.entities.utils.PlayerBuilder;
import br.com.alexandrealessi.gdx.fox.games.soccer.entities.utils.PlayerData;
import br.com.alexandrealessi.gdx.fox.games.soccer.entities.utils.PlayerPosition;
import br.com.alexandrealessi.gdx.fox.games.soccer.systems.Box2dWorldSystem;
import br.com.alexandrealessi.gdx.fox.games.soccer.systems.PhysicToScreenSystem;
import br.com.alexandrealessi.gdx.fox.games.soccer.systems.RenderSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by alexandre on 23/05/15.
 */
public class GamePlayScreen extends BaseScreen {
    //1248 x 794
    private static final float SCENE_WIDTH = 160;
    private static final float SCENE_HEIGHT = 100;
    private static final float ASPECT_RATIO = SCENE_WIDTH / SCENE_HEIGHT;

    private Engine engine;
    private Entity field;
    private TextureAtlas atlas;
    private RubeSceneWrapper rubeSceneWrapper;
    private OrthographicCamera camera;
    private OrthographicCamera worldCamera;
    private Viewport viewport;
    private Viewport worldViewport;

    public GamePlayScreen(SoccerGame game) {
        super(game);
        atlas = new TextureAtlas(Gdx.files.internal("data/images/game.atlas"));
        rubeSceneWrapper = new RubeSceneWrapper("soccer.json");
        engine = new Engine();



        final Sprite panda = new Sprite(atlas.findRegion("panda"));
        panda.setScale(2.2f / panda.getHeight());

        field = new Entity();
        field.add(new BodyComponent(rubeSceneWrapper.getBody("field")));
        field.add(new PositionComponent());

        final Sprite soccer = new Sprite(atlas.findRegion("soccer"));
        soccer.setScale(100 / soccer.getHeight());
        field.add(new SpriteComponent(soccer));

        camera = new OrthographicCamera();
        worldCamera = new OrthographicCamera();

        viewport = new StretchViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

        Box2dWorldSystem box2dWorldSystem = new Box2dWorldSystem(rubeSceneWrapper.getWorld(), viewport);
        PhysicToScreenSystem physicToScreenSystem = new PhysicToScreenSystem(1);
        RenderSystem renderSystem = new RenderSystem(viewport);

        engine.addEntity(field);
        final Team galaticos = createTeam("galaticos", panda);

        addTeamToEngine (engine, galaticos);

        engine.addSystem(physicToScreenSystem);
        engine.addSystem(renderSystem);
        engine.addSystem(box2dWorldSystem);

    }

    private void addTeamToEngine(Engine engine, Team galaticos) {
        for (Player p:galaticos.getPlayers()){
            engine.addEntity(p);
        }
    }

    public Team createTeam(String name, Sprite uniform) {
        PlayerBuilder builder = new PlayerBuilder();
        final Body playerBody = rubeSceneWrapper.getBody("player");
        Array<Player> players = new Array<Player>();
        players.add(builder.createPlayerEntity(createPlayerData("Ochoa", 1, PlayerPosition.GK), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Messi", 10, PlayerPosition.ATTACKER), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Ronaldo", 9, PlayerPosition.ATTACKER), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Neymar", 11, PlayerPosition.ATTACKER), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Iniesta", 6, PlayerPosition.MIDDLEFIELD), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Xabi Alonso", 8, PlayerPosition.MIDDLEFIELD), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("James Rodriguez", 7, PlayerPosition.MIDDLEFIELD), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Dani Alves", 2, PlayerPosition.DEFENDER), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Mascherano", 3, PlayerPosition.DEFENDER), uniform, playerBody));
        players.add(builder.createPlayerEntity(createPlayerData("Boateng", 4, PlayerPosition.DEFENDER), uniform, playerBody));
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