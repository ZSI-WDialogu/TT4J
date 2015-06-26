package teamtalk.enums;

/**
 * Created by stokowiec on 2015-06-26.
 */
public class UserRight {

    public static final int USERRIGHT_NONE = 0;                             // Users who log onto the server has none of the rights below.
    public static final int USERRIGHT_MULTI_LOGIN = 1;                      // Allow multiple users to log on to the server with the same user account
    public static final int USERRIGHT_VIEW_ALL_USERS = 2;                   // User can see users in all other channels.
    public static final int USERRIGHT_CREATE_TEMPORARY_CHANNEL = 4;         // User is allowed to create temporary channels which disappear when last user leaves the channel.
    public static final int USERRIGHT_MODIFY_CHANNELS = 8;                  // User is allowed to create permanent channels which are stored in the server's configuration file
    public static final int USERRIGHT_TEXTMESSAGE_BROADCAST = 16;           // User can broadcast text message to all users.
    public static final int USERRIGHT_KICK_USERS = 32;                      // User can kick users off the server.
    public static final int USERRIGHT_BAN_USERS = 64;                       // User can add and remove banned users.
    public static final int USERRIGHT_MOVE_USERS = 128;                     // User can move users from one channel to another.
    public static final int USERRIGHT_OPERATOR_ENABLE = 256;                // User can make other users channel operator.
    public static final int USERRIGHT_UPLOAD_FILES = 512;                   // User can upload files to channels.
    public static final int USERRIGHT_DOWNLOAD_FILES = 1024;                // User can download files from channels.
    public static final int USERRIGHT_UPDATE_SERVERPROPERTIES = 2048;       // User can update server properties.
    public static final int USERRIGHT_TRANSMIT_VOICE = 4096;                // Users are allowed to forward audio packets through server.
    public static final int USERRIGHT_TRANSMIT_VIDEOCAPTURE = 8192;         // User is allowed to forward video packets through server.
    public static final int USERRIGHT_TRANSMIT_DESKTOP = 16384;             // User is allowed to forward desktop packets through server.
    public static final int USERRIGHT_TRANSMIT_DESKTOPINPUT = 32768;        // User is allowed to forward desktop input packets through server.
    public static final int USERRIGHT_TRANSMIT_MEDIAFILE_AUDIO = 65536;     // User is allowed to stream audio files to channel.
    public static final int USERRIGHT_TRANSMIT_MEDIAFILE_VIDEO = 13107;     // User is allowed to stream video files to channel.


    public static int getTransmissionRights(){
        return USERRIGHT_TRANSMIT_VOICE |
                USERRIGHT_TRANSMIT_VIDEOCAPTURE |
                USERRIGHT_TRANSMIT_DESKTOP |
                USERRIGHT_TRANSMIT_DESKTOPINPUT |
                USERRIGHT_TRANSMIT_MEDIAFILE_AUDIO |
                USERRIGHT_TRANSMIT_MEDIAFILE_VIDEO;

    }

    public static int getDefaultRights() {
        int flags = USERRIGHT_NONE;
        return flags| USERRIGHT_MULTI_LOGIN |
               USERRIGHT_VIEW_ALL_USERS |
               USERRIGHT_UPLOAD_FILES |
               USERRIGHT_DOWNLOAD_FILES;
    }

}
