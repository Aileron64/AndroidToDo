package thomascasse.androidtodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends Activity
{
    //ListView threadListView;
    private RecyclerView recyclerView;
    private Button addPostBtn;
    private Button logoutBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.threadView);

        new DatabaseManager().loadPosts(new DatabaseManager.DataStatus()
        {
            @Override
            public void DataIsLoaded(List<Post> posts, List<String> keys)
            {
                findViewById(R.id.loading_icon).setVisibility(View.GONE);

                new RecyclerManager().setConfig(recyclerView,
                        MainActivity.this, posts, keys);
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

            }
        });

        addPostBtn = (Button)findViewById(R.id.addPostBtn);
        addPostBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn = (Button)findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                auth.signOut();
                RecyclerManager.logout();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}

