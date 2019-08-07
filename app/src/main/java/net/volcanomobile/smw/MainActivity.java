package net.volcanomobile.smw;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.widget.Toast;

import org.libsdl.app.SDLActivity;

public class MainActivity extends SDLActivity {

    private InputManager mInputManager;
    private InputManager.InputDeviceListener mInputDeviceListener = new InputManager.InputDeviceListener() {
        @Override
        public void onInputDeviceAdded(int deviceId) {
            updateJoysticks();
        }

        @Override
        public void onInputDeviceRemoved(int deviceId) {
            updateJoysticks();
        }

        @Override
        public void onInputDeviceChanged(int deviceId) {
            updateJoysticks();
        }
    };

    public static native int updateJoysticks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mInputManager = (InputManager) getBaseContext().getSystemService(Context.INPUT_SERVICE);
        mInputManager.registerInputDeviceListener(mInputDeviceListener, null);

        if(getPackageManager().hasSystemFeature("android.hardware.touchscreen"))
            Toast.makeText(this, "Tap the top half of the screen to display the on-screen controller.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mInputManager.unregisterInputDeviceListener(mInputDeviceListener);
    }

    @Override
    protected String[] getLibraries() {
        return new String[]{
                "hidapi",
                "SDL2",
                "SDL2_image",
                "SDL2_mixer",
                "enet",
                "yaml-cpp",
                "main"
        };
    }
}
