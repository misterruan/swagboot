package com.rock.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Description:JSON 工具 Date: 2015/11/5 9:19 Author: zhaozhiwei
 */
@Slf4j
public abstract class JsonUtils {

	private static final JsonFactory JSON_FACTORY = new JsonFactory();
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final ObjectMapper MAPPER2 = new ObjectMapper();

	static {
		MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		MAPPER2.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		MAPPER.setTimeZone(TimeZone.getDefault());
		MAPPER2.setTimeZone(TimeZone.getDefault());
	}

	public static JsonNode toTree(String json) throws IOException {
		return MAPPER.readTree(json);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) throws IOException {
		if (StringUtils.isEmpty(json)) {
			return new HashMap<String, Object>(0);
		}
		return MAPPER.readValue(json, Map.class);
	}

	public static <T> T toBean(String json, Class<T> clazz) throws IOException {
		if (StringUtils.isEmpty(json))
			return null;
		JsonParser parser = JSON_FACTORY.createParser(json);
		parser.setCodec(MAPPER);
		T t = parser.readValueAs(clazz);
		parser.close();
		return t;
	}

	public static <T> T parseJson(String json, TypeReference<T> typeOfT) throws IOException {
		if (StringUtils.isEmpty(json))
			return null;
		JsonParser parser = JSON_FACTORY.createParser(json);
		parser.setCodec(MAPPER);
		T t = parser.readValueAs(typeOfT);
		parser.close();
		return t;
	}

	public static String toJson(Object object) throws IOException {
		return useMapper(object, MAPPER);
	}

	/**
	 * 转json，并且忽略转换异常
	 * @param object
	 * @return
	 */
	public static String toJsonWithCatchException(Object object) {
		try {
			return useMapper(object, MAPPER);
		} catch (Exception e) {
			log.error("json parse error: " + object, e);
			return "";
		}
	}

	public static String toJsonWithoutNull(Object object) throws IOException {
		return useMapper(object, MAPPER2);
	}

	private static String useMapper(Object object, ObjectMapper mapper) throws IOException {
		StringWriter writer = new StringWriter();
		JsonGenerator generator = JSON_FACTORY.createGenerator(writer);
		generator.setCodec(mapper);
		generator.writeObject(object);
		generator.close();
		writer.close();
		return writer.toString();
	}

	public static <T> List<T> jsonToBeans(String json, Class<T> type) throws IOException {
		JavaType javaType = getCollectionType(List.class, type);
		return MAPPER.readValue(json, javaType);
	}

	/**
	 * 集合类型
	 * 
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
