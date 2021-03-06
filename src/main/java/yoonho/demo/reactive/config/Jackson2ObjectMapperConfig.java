package yoonho.demo.reactive.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import yoonho.demo.reactive.base.masking.MaskAnnotationIntrospector;

@Configuration
public class Jackson2ObjectMapperConfig {
	private static final String dateFormat = "yyyy-MM-dd";
	private static final String datetimeFormat = "yyyy-MM-dd HH:mm:ss";

	@Bean 
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
		return jacksonObjectMapperBuilder -> {
			jacksonObjectMapperBuilder
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
			.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(datetimeFormat)))
			.annotationIntrospector(new MaskAnnotationIntrospector())
			;
		};

	}
	
}
