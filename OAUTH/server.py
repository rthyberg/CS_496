# Copyright 2016 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import webapp2
from google.appengine.api import urlfetch
import urllib
import json

class MainPage(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.write('Go to /redirect')

class AuthRedirect(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.write('You are about to be redirected to authorize thru google"')
        url = "https://accounts.google.com/o/oauth2/v2/auth?"
        response = "code"
        client_id = "931207409604-i21kqk9t2ahbkjagc8qe93r1gbte1sgd.apps.googleusercontent.com"
        client_secret = "uVm9VSCtG8-ZOU_RfJ7NaVGL"
	redirecturl = "https://oauth-158308.appspot.com/oauth"
	scope = "email"
	state = "420bb"
        myurl = (url+"response_type=" + response\
				+ "&client_id=" + client_id\
				+ "&redirect_uri=" +redirecturl\
				+ "&scope="+scope\
				+ "&state="+state)
        self.redirect(myurl)

class OAuth(webapp2.RequestHandler):
    def get(self):
        code = self.request.get('code')
	secret = self.request.get('state')
	form_data = {"code": code,
			"client_id":"931207409604-i21kqk9t2ahbkjagc8qe93r1gbte1sgd.apps.googleusercontent.com",
			"client_secret":"uVm9VSCtG8-ZOU_RfJ7NaVGL",
			"redirect_uri":"https://oauth-158308.appspot.com/oauth",
			"grant_type":"authorization_code"}
	encoded = urllib.urlencode(form_data)
	headers = {'Content-Type': 'application/x-www-form-urlencoded'}
	result = urlfetch.fetch(
		url='https://www.googleapis.com/oauth2/v4/token',
                payload=encoded,
                method=urlfetch.POST,
                headers=headers)
        googleList = json.loads(result.content)
        token = googleList['access_token']
        info = urlfetch.fetch(
                url="https://www.googleapis.com/plus/v1/people/me",
                method=urlfetch.GET,
                headers = {'Authorization':'Bearer ' + token}
        )
	self.response.write(info.content)

app = webapp2.WSGIApplication([
    ('/', MainPage),
    ('/redirect', AuthRedirect),
    ('/oauth', OAuth),
], debug=True)
