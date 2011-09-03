package org.downmysubs

class Movies {
	def movies = []
	
	def leftShift(item){
		if(item instanceof Movie){
			def exists = false
			movies.each { movie ->
				if(item == movie)
					exists = true
			}
			
			if(!exists)
				movies << item
		}
	}
	
	def find(imdbId){
		def result = null
		movies.each{
			if(it.imdbId == imdbId)
				result = it
		}
		return result
	}
	
	def putAt(imdbId, movie){
		def pos = null
		movies.each{ key, val ->
			if(val.imdbId == imdbId)
				pos = key	
		}
		
		if(pos)
			movies[pos] = movie
		else
			movies << movie
	}
}
