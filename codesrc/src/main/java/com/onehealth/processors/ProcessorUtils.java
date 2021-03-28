package com.onehealth.processors;

import static java.util.Objects.isNull;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;

import com.onehealth.services.ServiceConsts;

public final class ProcessorUtils {

	protected static int getDefaultPageNumber(Integer pageNumber) {
		return getDefaultValue(pageNumber, ServiceConsts.DEFAULT_PAGE_NUMBER, value -> value < 0, value -> value);
	}

	protected static int getDefaultPageSize(Integer pageSize) {
		return getDefaultValue(pageSize, ServiceConsts.DEFAULT_PAGE_SIZE, value -> value < 0, value -> value);
	}
	
	private static int getDefaultValue(Integer value, int defaultValue, IntPredicate condition, IntFunction<Integer> finalResult) {

		if(isNull(value) || condition.test(value))
			return defaultValue;
		 
		  return finalResult.apply(value);
	}
}
