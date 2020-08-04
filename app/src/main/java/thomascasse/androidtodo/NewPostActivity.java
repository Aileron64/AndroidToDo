package thomascasse.androidtodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewPostActivity extends Activity
{
    private EditText message;
    private Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        message = (EditText)findViewById(R.id.newPost);
        postBtn = (Button)findViewById(R.id.addPostBtn);

        postBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Post post = new Post();
                post.setMessage(message.getText().toString());
                Toast.makeText(NewPostActivity.this, "TEST", Toast.LENGTH_SHORT).show();

                new DatabaseManager().addPost(post, new DatabaseManager.DataStatus()
                {
                    @Override
                    public void DataIsLoaded(List<Post> posts, List<String> keys)
                    {

                    }

                    @Override
                    public void DataIsInserted()
                    {
                        Toast.makeText(NewPostActivity.this, "Post Added", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }

                    @Override
                    public void DataIsUpdated()
                    {

                    }

                    @Override
                    public void DataIsDeleted()
                    {

                    }
                });
            }
        });
    }
}