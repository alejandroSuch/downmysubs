package org.downmysubs

class Subtitle {
	def language
	def link
	
	def boolean equals(subtitle){
		def result = false
		if(subtitle instanceof Subtitle)
			result = language == subtitle.language && link == subtitle.link
	}
}
