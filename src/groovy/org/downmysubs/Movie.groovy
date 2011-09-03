package org.downmysubs

class Movie {
	def imdbId = null
	def name = null
	def plot = null
	def cover = null
	def genres = []
	def releases = []
	
	def leftShift(item){
		if(item instanceof Release){
			def found = false
			releases.each{ rel ->
				if(rel == item) {
					item.subtitles.each{ sub ->
						rel << sub
					}
					found = true
				}
			}
			
			if(!found)
				releases << item
		}
	}
	
	def boolean equals(item){
		def result = false
		if(item instanceof Movie)
			result = imdbId == item.imdbId
		
		result
	}
}
