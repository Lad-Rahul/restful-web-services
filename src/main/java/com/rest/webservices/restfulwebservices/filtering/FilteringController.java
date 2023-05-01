package com.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering-static")
	public SomeBean filtering() {
		return new SomeBean("value1", "value2", "value3");
	}
	
	@GetMapping("/filtering-list-static")
	public List<SomeBean> filteringList(){
		List<SomeBean> list = Arrays.asList(
				new SomeBean("value1", "value2", "value3"), 
				new SomeBean("value4", "value5", "value6")
				);
		return list;
	}
	
	@GetMapping("/filtering-dynamic")
	public MappingJacksonValue filteringDynamic() {
		SomeBean2 someBean2 = new SomeBean2("value1","value2", "value3");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean2);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter); 
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list-dynamic")
	public MappingJacksonValue filteringListDynamic() {
        List<SomeBean2> list = Arrays.asList(
				new SomeBean2("value1", "value2", "value3"), 
				new SomeBean2("value4", "value5", "value6")
				);
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter); 
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
}
