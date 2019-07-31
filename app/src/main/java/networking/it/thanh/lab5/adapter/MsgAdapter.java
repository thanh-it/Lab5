package networking.it.thanh.lab5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import networking.it.thanh.lab5.R;
import networking.it.thanh.lab5.model.Chat;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    public static final int MSG_LEFT = 1;
    public static final int MSG_RIGHT = 2;
    private Context mContext;
    private List<Chat> chatList;
    public MsgAdapter(Context context,List<Chat> chats){
        this.mContext = context;
        this.chatList = chats;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_LEFT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.itemright,parent,false);
            return new ViewHolder(view);
        }

    }
    public boolean isOnLoadMore() {
        return onLoadMore;
    }

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    private boolean onLoadMore = true;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.messages.setText(chat.getContent().getRendered());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView messages;
        public ImageView avt;
        public  ViewHolder(View itemView){
            super(itemView);
            messages = itemView.findViewById(R.id.show_msg);
            avt = itemView.findViewById(R.id.avts);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chatList.get(position).getAuthorName().equalsIgnoreCase("admin")){
            return MSG_RIGHT;
        }else{
            return MSG_LEFT;
        }

    }
}
