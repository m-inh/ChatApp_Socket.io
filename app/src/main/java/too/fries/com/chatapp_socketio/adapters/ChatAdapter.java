package too.fries.com.chatapp_socketio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import too.fries.com.chatapp_socketio.R;
import too.fries.com.chatapp_socketio.models.ItemChat;

/**
 * Created by TooNies1810 on 6/10/16.
 */
public class ChatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ItemChat> itemChatArr;

    public ChatAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        itemChatArr = new ArrayList<>();
    }

    public void addNewMsg(ItemChat itemChat){
        this.itemChatArr.add(itemChat);
    }

    @Override
    public int getCount() {
        return itemChatArr.size();
    }

    @Override
    public Object getItem(int position) {
        return itemChatArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_chat, null);
        }

        TextView tvUsername = (TextView) convertView.findViewById(R.id.tv_username);
        TextView tvMessage = (TextView) convertView.findViewById(R.id.tv_message);

        tvUsername.setText(itemChatArr.get(position).getUsername());
        tvMessage.setText(itemChatArr.get(position).getMessage());
        return convertView;
    }
}
