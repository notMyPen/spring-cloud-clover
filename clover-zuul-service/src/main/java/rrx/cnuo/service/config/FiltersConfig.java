package rrx.cnuo.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import rrx.cnuo.service.filters.ErrorFilter;
import rrx.cnuo.service.filters.FirstPreFilter;
import rrx.cnuo.service.filters.PostFilter;
import rrx.cnuo.service.filters.SecondPreFilter;
import rrx.cnuo.service.filters.ThirdPreFilter;

//@Component
public class FiltersConfig {

  @Bean
  public FirstPreFilter firstPreFilter(){
  	return new FirstPreFilter();
  }
  
  @Bean
  public SecondPreFilter secondPreFilter(){
  	return new SecondPreFilter();
  }
  
  @Bean
  public ThirdPreFilter thirdPreFilter(){
  	return new ThirdPreFilter();
  }
  
  @Bean
  public ErrorFilter errorFilter(){
  	return new ErrorFilter();
  }
  
  @Bean
  public PostFilter postFilter(){
  	return new PostFilter();
  }
}
