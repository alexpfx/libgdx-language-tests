package br.com.alexandrealessi.gdx.fox.android.model.interactors;

/**
 * Created by alexandre on 19/04/15.
 */
public interface GoogleApiClientInteractor {

    public interface OnConnectionResultReceivedListener {
        void onConnectionFailed();

        void onConnectionSuccess();
    }


    public void connect();
    public void disconnect ();

}
