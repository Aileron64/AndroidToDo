package thomascasse.androidtodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditPostActivity extends Activity
{
    private EditText editMessage;
    private Button updateBtn;
    private Button deleteBtn;

    private String key;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        editMessage = (EditText)findViewById(R.id.editPost);
        updateBtn = (Button)findViewById(R.id.editPostBtn);
        deleteBtn = (Button)findViewById(R.id.deletePostBtn);

        key = getIntent().getStringExtra("thomascasse.POST_KEY");
        message = getIntent().getStringExtra("thomascasse.POST_MESSAGE");
        editMessage.setText(message);

        updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Post post = new Post();
                post.setMessage(editMessage.getText().toString());

                new DatabaseManager().updatePost(key, post, new DatabaseManager.DataStatus()
                {
                    @Override
                    public void DataIsLoaded(List<Post> posts, List<String> keys)
                    {

                    }

                    @Override
                    public void DataIsInserted()
                    {

                    }

                    @Override
                    public void DataIsUpdated()
                    {
                        Toast.makeText(EditPostActivity.this, "Post Updated", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }

                    @Override
                    public void DataIsDeleted()
                    {

                    }
                });
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatabaseManager().deletePost(key, new DatabaseManager.DataStatus()
                {
                    @Override
                    public void DataIsLoaded(List<Post> posts, List<String> keys)
                    {

                    }

                    @Override
                    public void DataIsInserted()
                    {

                    }

                    @Override
                    public void DataIsUpdated()
                    {

                    }

                    @Override
                    public void DataIsDeleted()
                    {
                        Toast.makeText(EditPostActivity.this, "Post Deleted", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                });
            }
        });
    }
}








