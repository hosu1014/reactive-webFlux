package yoonho.demo.reactive.util;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.data.annotation.Transient;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import io.r2dbc.spi.Row;
import lombok.extern.slf4j.Slf4j;
import yoonho.demo.reactive.base.dataencrypt.DataEncrypt;

@Slf4j
public class ReadWriteConverterUtil {
	public static void readConvert(Row source, Object object) {
		Arrays.stream(object.getClass().getDeclaredFields()) 
		.forEach(field -> {
			field.setAccessible(true);
			String db_column_name = getFieldName(field);
			if(field.isAnnotationPresent(Transient.class) == false 
					&& ObjectUtils.isEmpty(source.get(db_column_name)) == false) {
				if(field.isAnnotationPresent(DataEncrypt.class)) {
					String value = source.get(db_column_name, String.class);
					value = value.replaceAll("\\*", "");
					ReflectionUtils.setField(field, object, value );
				} else {
					ReflectionUtils.setField(field, object, source.get(db_column_name));
				}
			}
		});
	}
	
	public static OutboundRow writeConvert(Object source) {
		OutboundRow row = new OutboundRow();
		Arrays.stream(source.getClass().getDeclaredFields()) 
		.forEach(field -> {
			field.setAccessible(true);
			if(field.isAnnotationPresent(Transient.class) == false) {
				try {
					if(ObjectUtils.isEmpty(field.get(source))) return;
					
					if(field.isAnnotationPresent(DataEncrypt.class)) {
						row.put(getFieldName(field), Parameter.from(field.get(source) + "***"));
					} else {
						row.put(getFieldName(field), Parameter.from(field.get(source)));
					}
				} catch(IllegalAccessException e) {
					throw new RuntimeException("");
				}
			}
		});
		return row;
	}
	
	public static String getFieldName(Field field) {
		field.setAccessible(true);
		if(field.isAnnotationPresent(Column.class) == false) {
			return camelToSnake(field.getName());
		}
		
		return field.getDeclaredAnnotation(Column.class).value();
	}
	
	public static String camelToSnake(String str) {
        final String regex = "([a-z])([A-Z]+)";
        final String replacement = "$1_$2";
  
        return str.replaceAll(regex, replacement)
                 .toLowerCase();
    }
}
