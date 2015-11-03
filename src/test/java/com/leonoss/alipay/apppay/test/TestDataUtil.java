package com.leonoss.alipay.apppay.test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import com.leonoss.alipay.apppay.dto.MaxLength;
import com.leonoss.alipay.apppay.dto.SignField;
import com.leonoss.alipay.apppay.util.Util;

public abstract class TestDataUtil {
	public static void setDefaultValue(Object object) {
		Field[] allFields = object.getClass().getDeclaredFields();
		for (Field field : allFields) {
			try {
				if (field.isAnnotationPresent(SignField.class)) {
					field.setAccessible(true);
					if (field.getType().equals(String.class)) {
						if (field.isAnnotationPresent(MaxLength.class)) {
							field.set(
									object,
									Util.generateString(field.getAnnotation(
											MaxLength.class).value()));
						} else {
							field.set(object, field.getName());
						}
					} else if (field.getType().equals(BigDecimal.class)) {
						field.set(object, new BigDecimal(1));
					} else if (field.getType().equals(Boolean.class)) {
						field.set(object, Boolean.valueOf(true));
					} else if (field.getType().equals(Date.class)) {
						field.set(object, new Date());
					}
				}
			} catch (IllegalAccessException iae) {
				throw new IllegalStateException("Should not happen", iae);
			}
		}
	}
}
