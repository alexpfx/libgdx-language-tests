package br.com.alexandrealessi.gdx.fox.android.services.google;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import br.com.alexandrealessi.gdx.fox.android.AndroidLauncher;
import br.com.alexandrealessi.gdx.fox.android.AndroidLauncherView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

/**
 * Created by alexandre on 16/04/15.
 */
public class GoogleApiClientWrapper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int RC_SIGN_IN = 9001;

    //    https://developers.google.com/games/services/checklist#improving_the_sign_in_experience_for_games
    private GoogleApiClient googleApiClient;
    private AndroidLauncherView view;
    private Activity launcher;

    public GoogleApiClientWrapper(AndroidLauncher androidLauncher) {
        view = (AndroidLauncherView) androidLauncher;
        launcher = androidLauncher;
        googleApiClient = new GoogleApiClient.Builder(androidLauncher.getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addApi(Games.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Games.SCOPE_GAMES).build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        view.showConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        view.showConnectionSuspended();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(launcher, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                googleApiClient.connect();
            }
        } else {
            view.showConnectionFailed();
        }
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        googleApiClient.disconnect();
    }

}
