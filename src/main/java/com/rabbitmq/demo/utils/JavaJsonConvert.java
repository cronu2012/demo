package com.rabbitmq.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Json、Java互转工具类
 *
 * @author jqlin
 */
public class JavaJsonConvert {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JavaJsonConvert.class);

    /**
     * json字符串转换成java对象 
     * 
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     * @author jqlin
     */
    public static <T> T json2Java(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * Java对象转换为JSON字符串
     * 
     * @param entity
     * @return
     * @author jqlin
     */
    public static <T> String java2Json(T entity) {
        return JSON.toJSONString(entity, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * Java对象转换为JSON字符串，支持注解
     * 
     * @param entity
     * @return
     * @author jqlin
     */
    public static <T> String javaToJson(T entity) {
        try {
            return new ObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error("Java对象转换为JSON字符串失败：" + e.getMessage(), e);
        }

        return null;
    }

    /**
     * json字符串转换成java对象 
     * 
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     * @author jqlin
     */
    public static <T> T jsonToJava(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz); 
        } catch (Exception e) {
            log.error("Java对象转换为JSON字符串失败：" + e.getMessage(), e);
        }

        return null;
    }
    
	public static <T> T jsonToJava(String json, TypeReference<T> typeReference) {
		try {
			return (T) JSON.parseObject(json, typeReference);
		} catch (Exception e) {
			log.error("Java对象转换为JSON字符串失败：" + e.getMessage(), e);
		}

		return null;
	}
     
}
