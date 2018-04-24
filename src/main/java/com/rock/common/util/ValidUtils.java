/**
 * @Copyright (C) 2017 wanda.cn Inc. All Rights Reserved.
 */
package com.rock.common.util;


import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

/**
 * @author rock
 *
 * @since 2017-12-07
 *
 */
@Slf4j
public class ValidUtils {

	private static Validator validator = null;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private static <T> Set<ConstraintViolation<T>> valid(T t) {
		if(t == null) return null;
		return validator.validate(t);
	}

	//对单个bean进行校验
	public static <T> void validBean(T t) {
		Set<ConstraintViolation<T>> validInfos = ValidUtils.valid(t);
		if (null == validInfos || validInfos.size() == 0)
			return;
		Optional<ConstraintViolation<T>> opt = validInfos.stream().findFirst();
		if (opt.isPresent()) {
			ConstraintViolation<T> valid = opt.get();
			log.error("parse param error:: {}", valid);
			throw new SwagBootCommonException(ExceptionConstants.errer10005.getCode(), valid.getPropertyPath() + valid.getMessage());
		}
	}

}
