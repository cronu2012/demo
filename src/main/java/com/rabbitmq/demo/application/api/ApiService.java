package com.rabbitmq.demo.application.api;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

import java.util.Map;

@Component
@RetrofitClient(baseUrl = "")
public interface ApiService {

    @POST
    Call<String> get(@Url String url, @Body Map<String, Object> requestBody);
}
