package com.jj.util.push;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.jj.util.enumerador.Platform;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.push.android.PushServerAndroidImpl;
import com.jj.util.push.apple.PushServerAppleImpl;
import com.jj.util.push.windows.PushServerWindowsImpl;

/**
 * Classe responsável por enviar as mensagens de push notification.
 * 
 * Example: <br>
 * 
 * Device device1 = new Device(Platform.ANDROID, "AS89D7F87AS6D78FAS76DF78ASD6FA78SD6"); <br>
 * Device device2 = new Device(Platform.APPLE, "AS89D7F87AS6D78FAS76DF78ASD6FA78SD6"); <br>
 * Device device3 = new Device(Platform.WINDOWS, "AAEbMvVEZNe-SrbRa1sVJquHAgAAAAADAQAAAAQUZm52OkJCMjg1QTg1QkZDMkUxREQ%22;"); <br>
 * 
 * Notification notification = new Notification("jj Alerta", "Mensagem blah blah blah"); <br>
 * 
 * PushNotification service = PushNotification.newService(); <br>
 * 
 * service.push(notification, device1, device2, device3); <br>
 *
 */
public class PushNotification {

    private static PushNotification pushNotification;

    private static Logger logger = Logger.getLogger(PushServerAppleImpl.class.getName());

    private Map<Platform, PushServer> servers = new HashMap<Platform, PushServer>();

    public static PushNotification newService() {

        if (pushNotification == null) {
            pushNotification = new PushNotification();
        }
        return pushNotification;

    }

    public void push(Notification notification) throws BusinessServerException {
        PushServer server = getPushServer(notification.getPlatform());
        server.sendMessage(notification.getToken(), new PushMessage(notification.getTitle(), notification.getMessage()));

    }

    public void push(PushMessage pushMessage, Device... devices) throws BusinessServerException {
        push(pushMessage, Arrays.asList(devices));

    }

    public void push(PushMessage pushMessage, List<Device> devices) throws BusinessServerException {

        devices.sort((p1, p2) -> p1.getPlatform().compareTo(p2.getPlatform()));

        for (Device device : devices) {
            PushServer server = getPushServer(device.getPlatform());
            server.sendMessage(device.getToken(), pushMessage);

        }
    }

    private PushServer getPushServer(Platform platform) {
        PushServer pushServer = servers.get(platform);

        try {
            if (pushServer == null) {
                switch (platform) {
                    case APPLE:
                        pushServer = new PushServerAppleImpl();
                        servers.put(platform, pushServer);
                        break;
                    case ANDROID:
                        pushServer = new PushServerAndroidImpl();
                        servers.put(platform, pushServer);
                        break;
                    case WINDOWS:
                        pushServer = new PushServerWindowsImpl();
                        servers.put(platform, pushServer);
                        break;
                    default:

                }
            }
        } catch (Exception e) {

            logger.info("Não foi possível conectar ao servidor de push da " + platform.toString() + ":" + e);
        }
        return pushServer;
    }
}
