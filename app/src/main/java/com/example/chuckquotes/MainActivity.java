package com.example.chuckquotes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chuckquotes.Entities.Joke;
import com.example.chuckquotes.Entities.JokeService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private MainActivity activity = this;
    private Button nextButton;
    private TextView jkText;
    private Joke joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = findViewById(R.id.nextButton);
        jkText = findViewById(R.id.jkText);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new jokeNow().execute();
                jkText.setText("");
            }
        });
    }

    private class jokeNow extends AsyncTask<Void, Void, Joke> {
        @Override
        protected Joke doInBackground(Void... voids) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.chucknorris.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JokeService service = retrofit.create(JokeService.class);
                Call<Joke> jokeCall = service.jokeNow();
                Response<Joke> jokeResponse = jokeCall.execute();
                Joke joke = jokeResponse.body();
                return joke;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(Joke joke) {

            if (joke != null) {
                jkText.setText(joke.getValue());
            } else {
                jkText.setText("Failed to get quote!");
                Toast toast = Toast.makeText(activity, "Joke Failed To Generate!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }
}
