package too.fries.com.chatapp_socketio.models;

/**
 * Created by TooNies1810 on 6/10/16.
 */
public class ItemChat {
    private String username;
    private String message;

    public ItemChat(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
