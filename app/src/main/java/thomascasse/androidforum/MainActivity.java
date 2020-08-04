package thomascasse.androidforum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends Activity
{
    //ListView threadListView;
    private RecyclerView recyclerView;
    private Button addPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent newPostActivity = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(newPostActivity);
            }
        });
    }
}

//        threadListView = (ListView)findViewById(R.id.threadList);
//
//        Thread[] testValues = new Thread[125];
//        for(int i = 0; i < testValues.length; i++)
//        {
//            testValues[i] = new Thread("Anonymous", "Value: " + i, "");
//        }
//
//        OldThreadAdapter adapter = new OldThreadAdapter(this, testValues);
//        threadListView.setAdapter(adapter);
//
//        threadListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                Intent showThread = new Intent(getApplicationContext(), ThreadActivity.class);
//                showThread.putExtra("thomascasse.THREAD_ID", i);
//                startActivity(showThread);
//            }
//        });