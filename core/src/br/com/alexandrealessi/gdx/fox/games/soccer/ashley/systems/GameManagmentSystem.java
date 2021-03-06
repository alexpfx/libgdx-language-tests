package br.com.alexandrealessi.gdx.fox.games.soccer.ashley.systems;

import br.com.alexandrealessi.gdx.fox.base.box2d.MatchEventListener;
import br.com.alexandrealessi.gdx.fox.base.box2d.SoccerContactListener;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.components.*;
import br.com.alexandrealessi.gdx.fox.games.soccer.ashley.utils.ComponentMappers;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by alexandre on 07/06/15.
 */
public class GameManagmentSystem extends EntitySystem implements MatchEventListener {

    private Entity worldEntity;
    private Entity goalLineEntity;


    private MatchTimerComponent matchTimerComponent;
    private MatchScoreComponent matchScoreComponent;
    private MatchStatusComponent matchStatusComponent;

    @Override
    public void addedToEngine(Engine engine) {
        worldEntity = engine.getEntitiesFor(Family.one(WorldComponent.class).get()).first();
        //get entities that have all those components.
        final Entity ballEntity = engine.getEntitiesFor(Family
                .all(BallContextComponent.class, SpriteComponent.class, BodyComponent.class, PositionComponent.class, CameraFollowerComponent.class)
                .get()).first();

        final WorldComponent worldComponent = ComponentMappers.WORLD.get(worldEntity);
        final World world = worldComponent.getWorld();
        world.setContactListener(new SoccerContactListener(this));

        Entity matchEntity = engine
                .getEntitiesFor(Family.all(MatchTimerComponent.class, MatchScoreComponent.class).get()).first();
        matchTimerComponent = ComponentMappers.MATCH_TIMER.get(matchEntity);

        matchScoreComponent = ComponentMappers.MATCH_CONTEXT.get(matchEntity);

        matchStatusComponent = ComponentMappers.MATCH_STATUS.get(matchEntity);

        final ImmutableArray<Entity> entitiesFor = engine.getEntitiesFor(Family.one(TeamComponent.class).get());

    }

    @Override
    public void update(float deltaTime) {
        matchTimerComponent.increment(deltaTime);
    }

    @Override
    public void goal(Entity goalLineEntity) {
        final TeamComponent teamComponent = ComponentMappers.TEAM.get(goalLineEntity);
        final Entity team = teamComponent.getTeam();

        matchStatusComponent.setGameStatus(MatchStatusComponent.MatchGameStatus.AFTER_GOAL);

        if (team.equals(matchScoreComponent.getHomeTeam())) {
            matchScoreComponent.incrementAwayScore();
        } else {
            matchScoreComponent.incrementHomeScore();
        }
        System.out.println();
        System.out.println("Away Score: " + matchScoreComponent.getAwayScore());
        System.out.println("Home Score:  " + matchScoreComponent.getHomeScore());

    }

    @Override
    public void playerBall(Entity player) {

    }
}
