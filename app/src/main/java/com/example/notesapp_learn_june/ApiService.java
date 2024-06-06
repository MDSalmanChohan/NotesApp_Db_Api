package com.example.notesapp_learn_june;




import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts") // Dummy endpoint for example
    Call<List<Note>> getNotes();
}
