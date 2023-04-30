package com.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	
	//URI versioning - twitter
	
	@GetMapping("v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//Request parameter versioning - Amazon
	
	@GetMapping(path = "person", params = "version=1")
	public PersonV1 getFirstVersionOfPersonRequestParameter() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path = "person", params = "version=2")
	public PersonV2 getSecondVersionOfPersonRequestParameter() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//Request Header versioning - Microsoft
	//in Headers add { X-API-VERSION:1 }
	
	@GetMapping(path = "person", headers = "X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPersonRequestHeader() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path = "person", headers = "X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPersonRequestHeader() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	//MediaType versioning - Github 
	//Content Negotiation OR Accept-Headers
	// in Headers add { Accept:application/vnd.company.app-v1+json }
	
	@GetMapping(path = "person", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfPersonRequestAccept() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path = "person", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPersonRequestAccept() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	
	/* Factors to consider for versioning
		1. URI pollution - in URI and Request params versioning
		                   different url for different version
		2. misuse of HTTP headers
		                 - in Header and MediaType versioning
		                 - headers never meant to be versioning
		3. Caching - Caching is done based on URL
		           - in header and mediatype versioning url is same so need to consider header also while caching
        4. Can we execute request on browser?   
                   - Yes for URI and Request params versioning
                   - No for Header and MediaType versioning - they need Command line utility or Rest media client
        5. API documentation
                   - Easy for URI and Request Params versioning 
        6. None of versioning method is perfact
	*/
}
