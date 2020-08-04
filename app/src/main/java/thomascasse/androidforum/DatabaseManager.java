package thomascasse.androidforum;

import android.renderscript.Sampler;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager
{
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private List<Post> posts = new ArrayList<>();

    public interface DataStatus
    {
        void DataIsLoaded(List<Post> posts, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public DatabaseManager()
    {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("posts");
    }

    public void loadPosts(final DataStatus dataStatus)
    {
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                posts.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Post post = keyNode.getValue(Post.class);
                    posts.add(post);
                }

                dataStatus.DataIsLoaded(posts, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void addPost(Post post, final DataStatus dataStatus)
    {
        String key = ref.push().getKey();

        ref.child(key).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                dataStatus.DataIsInserted();
            }
        });
    }
}
