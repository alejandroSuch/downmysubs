package org.downmysubs

class Release {
	def name
	def subtitles = []
	
	def leftShift(item){
		def found = false
		if(item instanceof Subtitle){
			subtitles.each{ sub ->
				if(sub == item)
					found = true
			}
			
			if(!found)
				subtitles << item
		}
	}
	
	def boolean equals(item){
		def result = false
		if(item instanceof Release)
			result = name.equalsIgnoreCase(item.name)
		
		return result
	}
}
