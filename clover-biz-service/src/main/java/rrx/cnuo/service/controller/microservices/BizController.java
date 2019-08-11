package rrx.cnuo.service.controller.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.service.service.TestService;

@RestController
public class BizController{

	@Autowired private TestService testService;
	
	@GetMapping("/test/insertStatisUser")
    public void testInsertStatisUser() {
    	testService.insertStatisUser();
    }
}
