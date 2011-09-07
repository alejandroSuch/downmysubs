class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(uri:"/index.html")
		"500"(view:'/error')
		
		"/search"(controller:"search"){
			action = [GET:"search", POST:"search"]
		}
		
	}
}
