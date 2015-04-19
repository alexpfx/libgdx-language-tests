package br.com.alexandrealessi.gdx.fox.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.alexandrealessi.gdx.fox.MainGame;
import br.com.alexandrealessi.gdx.fox.android.services.google.GoogleApiClientWrapper;
import br.com.alexandrealessi.gdx.fox.android.services.google.GoogleApiLeaderboardsInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.plus.PlusOneButton;

public class AndroidLauncher extends AndroidApplication implements AndroidLauncherView, PlusOneButton.OnPlusOneClickListener {
    private static final String url = "https://market.android.com/details?id=br.com.alexmob.games.halley.comet";
    private GoogleApiClientWrapper googlePlayConnector;
    private PlusOneButton plusOneButton;

//    private RelativeLayout baseViewLayout;

            /*
        Important: Because it is hard to anticipate the state of each device, you must always check for a
		compatible Google Play services APK before you access Google Play services features.
		 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MainGame mainGame = new MainGame(new GoogleApiLeaderboardsInterface());
        AndroidApplicationConfiguration config = getAndroidApplicationConfig();
        RelativeLayout baseViewLayout = getBaseLayout(config, mainGame);
        plusOneButton = addPlusOneButton(baseViewLayout);
        setContentView(baseViewLayout);
        googlePlayConnector = new GoogleApiClientWrapper(this);
    }

    private AndroidApplicationConfiguration getAndroidApplicationConfig() {
        return new AndroidApplicationConfiguration();
    }

    private RelativeLayout getBaseLayout(AndroidApplicationConfiguration config, MainGame mainGame) {
        RelativeLayout layout = new RelativeLayout(this);
        final View view = initializeForView(mainGame, config);
        layout.addView(view);
        return layout;
    }

    private PlusOneButton addPlusOneButton(RelativeLayout baseViewLayout) {
        View plusOneLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.android_launcher_layout, null);
        baseViewLayout.addView(plusOneLayout, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return (PlusOneButton) plusOneLayout.findViewById(R.id.plus_one_button);
    }

    private void setupWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googlePlayConnector.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googlePlayConnector.disconnect();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            Toast.makeText(getApplicationContext(), "resolveu", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 9004) {
            Toast.makeText(getApplicationContext(), "plusOneButtonInitialize", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        plusOneButton.initialize(url, this);


//        plusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);
//        plusOneButton.initialize(URL, 9004);
    }

    @Override
    public void showConnected() {
        Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionSuspended() {
        Toast.makeText(getApplicationContext(), "Connection was Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionFailed() {
        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPlusOneClick(Intent intent) {
        startActivityForResult(intent, 0);

    }
}
