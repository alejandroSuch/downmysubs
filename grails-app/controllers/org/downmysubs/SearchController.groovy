package org.downmysubs


import groovy.net.xmlrpc.*;

class SearchController {

	def search = {
		def movies = new Movies()
		def query = []
		
		def clean = {
			def aux = (it =~ /\[.*\]/).replaceAll("")
			if(aux.endsWith(".")){
				aux = aux.substring(0, aux.length()-1)
			}
			aux
		}
		
		if(params.search.class.isArray()){
			params.search.each{
				query << ['sublanguageid': 'all', 'query':clean(it)]
			}
		} else {
			query << ['sublanguageid': 'all', 'query':clean(params.search)]
		}
		
		
		def OK = "200 OK"
		def os = new XMLRPCServerProxy("http://api.opensubtitles.org/xml-rpc")


		def login = os.LogIn("", "", "", "OS Test User Agent")
		def token = ""

		if(login.status == OK){
			token = login.token
		}


		
		def output = [:]
		
		
		
		output['errors'] = []
		output['warnings'] = []
		output['status'] = 'ERROR'
		def isError = false
		query.each{ queryItem ->
			def rs = os.SearchSubtitles(token, [queryItem])
			if(rs.status.equals(OK)){
				if(rs.data){
					output['status'] = 'OK';
					rs.data.each{ movieData ->
						def movie = null
						if((movie = movies.find(movieData.IDMovieImdb)) == null){
							def details = os.GetIMDBMovieDetails(token, movieData.IDMovieImdb)
							def coverAux = details.data.cover?.split("@@")[0]+"@@._V1._SY220_CR0,15,160,160_.jpg"
							
							movie = new Movie([
								imdbId : movieData.IDMovieImdb,
								name   : movieData.MovieName,
								cover  : coverAux,
								genres : details.data.genres,
								plot   : details.data.plot
							])
	
							movies << movie
						}
	//
						movie << new Release([name:movieData.MovieReleaseName, subtitles:[new Subtitle(language:movieData.LanguageName, link:movieData.ZipDownloadLink)]])
					}
					output['movies'] = movies
					output['query'] = query
				} else {
					output['warnings'] << 'No data for ' + queryItem['query']
				}
			} else {
				output['errors'] << 'Error communicating with OpenSubtitles when searching file ' + queryItem['query']
			}
			
		}

		try{
			os.LogOut(token)
		} catch(Exception e){
		}

		render(contentType:"text/json") {
 			output
		}	
	}
}
