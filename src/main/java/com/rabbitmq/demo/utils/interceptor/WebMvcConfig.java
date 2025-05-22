package com.rabbitmq.demo.utils.interceptor;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.rabbitmq.demo.utils.interceptor.BusinessAuthInterceptor;
import com.rabbitmq.demo.utils.interceptor.ConsumerAuthInterceptor;
import com.rabbitmq.demo.utils.interceptor.ExternalAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${consumer-path}")
    private String consumerPath;
//    @Value("${server.servlet.context-path}")
//    private String contextPath;

    @Value("${external-path}")
    private String externalPath;
    @Value("${business-path}")
    private String businessPath;

    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截C端api请求
        registry.addInterceptor(this.consumerInterceptor())
                .addPathPatterns("/" + consumerPath + "/**");
        //拦截external api请求, 主要处理IP白名单、appId key 相关验证
        registry.addInterceptor(this.externalAuthInterceptor()).addPathPatterns("/" + externalPath + "/**");
        // 拦截B端api请求
        registry.addInterceptor(this.businessAuthInterceptor())
                .addPathPatterns("/" + businessPath + "/**");
    }

    @Bean
    ConsumerAuthInterceptor consumerInterceptor() {
        return new ConsumerAuthInterceptor();
    }

    @Bean
    ExternalAuthInterceptor externalAuthInterceptor() {
        return new ExternalAuthInterceptor();
    }

    @Bean
    BusinessAuthInterceptor businessAuthInterceptor() {
        return new BusinessAuthInterceptor();
    }

    @PostConstruct
    public void configureObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }

    /**
     * ban use lambda
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DatePattern.UTC_SIMPLE_PATTERN));
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            }
        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
            }
        };
    }
}
