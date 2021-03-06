package br.com.alexandrealessi.gdx.fox.games.soccer.ashley.components;

import br.com.alexandrealessi.gdx.fox.base.input.GamepadInputHandle;
import com.badlogic.ashley.core.Component;

/**
 * Created by alexandre on 15/06/15.
 * Deve considerar os controles: botao pressionado e botao segurado.
 */
public class GameInputControlsComponent extends Component implements GamepadInputHandle.InputHandleListener {

    private static final int N_AXIS = 4;
    private boolean buttonA;
    private boolean buttonB;
    private boolean buttonX;
    private boolean buttonY;

    private float[] axis = new float[N_AXIS];

    public boolean isButtonA() {
        return buttonA;
    }

    public void setButtonA(boolean buttonA) {
        this.buttonA = buttonA;
    }

    public boolean isButtonB() {
        return buttonB;
    }

    public void setButtonB(boolean buttonB) {
        this.buttonB = buttonB;
    }

    public boolean isButtonX() {
        return buttonX;
    }

    public void setButtonX(boolean buttonX) {
        this.buttonX = buttonX;
    }

    public boolean isButtonY() {
        return buttonY;
    }

    public void setButtonY(boolean buttonY) {
        this.buttonY = buttonY;
    }

    public void reset() {
        buttonX = false;
        buttonY = false;
        buttonB = false;
        buttonA = false;
        for (int i = 0; i < N_AXIS; i++) {
            axis[i] = 0f;
        }
    }

    @Override
    public void pressA() {
        buttonA = true;
    }

    @Override
    public void pressB() {
        buttonB = true;
    }

    @Override
    public void pressX() {
        buttonX = true;
    }

    @Override
    public void pressY() {
        buttonY = true;
    }

    @Override
    public void leftAxisX(int code, float value) {
        axis[code] = value;
    }

    @Override
    public void leftAxisY(int code, float value) {
        axis[code] = value;
    }

    @Override
    public void rightAxisX(int code, float value) {
        axis[code] = value;
    }

    @Override
    public void rightAxisY(int code, float value) {
        axis[code] = value;
    }

    @Override
    public void povUp() {

    }

    @Override
    public void povDown() {

    }

    @Override
    public void povLeft() {

    }

    @Override
    public void povRight() {

    }

    public float[] getAxis() {
        return axis;
    }
}
