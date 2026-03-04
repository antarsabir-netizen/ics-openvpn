package de.blinkt.openvpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VPNLaunchHelper;

public class LaunchVPN extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        VpnProfile profile = importSaudiProfile();
        if (profile != null) {
            VPNLaunchHelper.startOpenVpn(profile, getBaseContext(), null, true);
        }
        finish();
    }

    private VpnProfile importSaudiProfile() {
        try {
            InputStream conf = getAssets().open("saudi.ovpn");
            BufferedReader br = new BufferedReader(new InputStreamReader(conf));
            StringBuilder config = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) config.append(line).append("\n");
            ConfigParser cp = new ConfigParser();
            cp.parseConfig(new StringReader(config.toString()));
            VpnProfile vp = cp.convertProfile();
            vp.mName = "Saudi Server - Alfahad";
            vp.mPassword = "@covyg"; 
            ProfileManager.getInstance(this).addProfile(vp);
            ProfileManager.saveProfile(this, vp);
            return vp;
        } catch (Exception e) { return null; }
    }
}
