package thomascasse.androidtodo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerManager
{
    private Context context;
    private PostAdapter postAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Post> posts, List<String> keys)
    {
        this.context = context;
        this.postAdapter = new PostAdapter(posts, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(postAdapter);
    }

    class ThreadItemView extends RecyclerView.ViewHolder
    {
        private TextView message;

        private String key;

        public ThreadItemView(ViewGroup parent)
        {
            super(LayoutInflater.from(context).
                    inflate(R.layout.post_list_item, parent, false));

            message = (TextView) itemView.findViewById(R.id.postMessage);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, EditPostActivity.class);
                    intent.putExtra("thomascasse.POST_KEY", key);
                    intent.putExtra("thomascasse.POST_MESSAGE", message.getText().toString());

                    context.startActivity(intent);
                }
            });
        }

        public void bind(Post post, String key)
        {
            message.setText(post.getMessage());
            this.key = key;
        }
    }

    class PostAdapter extends RecyclerView.Adapter<ThreadItemView>
    {
        private List<Post> posts;
        private List<String> keys;

        public PostAdapter(List<Post> posts, List<String> keys)
        {
            this.posts = posts;
            this.keys = keys;
        }

        @NonNull
        @Override
        public ThreadItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ThreadItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ThreadItemView holder, int position)
        {
            holder.bind(posts.get(position), keys.get(position));
        }

        @Override
        public int getItemCount()
        {
            return posts.size();
        }
    }
}
