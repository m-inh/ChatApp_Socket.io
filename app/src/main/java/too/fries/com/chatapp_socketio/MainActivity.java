package too.fries.com.chatapp_socketio;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import too.fries.com.chatapp_socketio.adapters.ChatAdapter;
import too.fries.com.chatapp_socketio.models.ItemChat;

public class MainActivity extends Activity {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    private static final String USERNAME = "Android User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mSocket.on("new message", onNewMessage);
        mSocket.on("login", onNewMessageLogin);
        mSocket.connect();

        mSocket.emit("add user", USERNAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.off("new message");
        mSocket.off("login");
        mSocket.disconnect();
    }

    private Button btnSend;
    private EditText edtMsg;
    private ListView lvChat;
    private ChatAdapter mAdapter;

    private void initViews() {
        lvChat = (ListView) findViewById(R.id.lv_chat);
        btnSend = (Button) findViewById(R.id.btn_send);
        edtMsg = (EditText) findViewById(R.id.edt_input);
        mAdapter = new ChatAdapter(this);

        lvChat.setAdapter(mAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtMsg.getText().toString().isEmpty()){
                    String msg = edtMsg.getText().toString();
                    mSocket.emit("new message", msg);
                    edtMsg.setText("");

                    ItemChat itemChat = new ItemChat(USERNAME, msg);
                    mAdapter.addNewMsg(itemChat);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject(args[0].toString());
                        String username = jsonObject.getString("username");
                        String message = jsonObject.getString("message");

                        ItemChat itemChat = new ItemChat(username, message);
                        mAdapter.addNewMsg(itemChat);
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    Emitter.Listener onNewMessageLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("activity", args[0].toString());
        }
    };
}
