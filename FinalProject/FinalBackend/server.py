
from google.appengine.ext import ndb
import webapp2
import json

""" Google Datastore Models"""
class User(ndb.Model):
    """ user Datastore Model"""
    id = ndb.StringProperty(required=True)
    urlsafe = ndb.StringProperty()
    name = ndb.StringProperty()
    beer =  ndb.StringProperty()
    rating = ndb.IntegerProperty()
    notes = ndb.TextProperty()


""" Page Handlers """
class MainPage(webapp2.RequestHandler):
    """/"""
    def get(self):
        self.response.out.write('hello')
    def delete(self):
        """ Deletes all entries in Book and Customer"""
        qo = ndb.QueryOptions(keys_only=True)
        table = User.query().fetch(options=qo)
        ndb.delete_multi(table)

class Users2Handle(webapp2.RequestHandler):
    """/users/:userID"""
    def get(self, userId):
        display_user = User.query(User.id == userId).get() # Query the user
        if display_user == None: # if not found then return 404
            self.response.status_int = 404
            self.response.write("User with id:"+ userId +" was not found")
        else:
            self.response.status_int = 200
            self.response.write(json.dumps(display_user.to_dict()))

    def put(self, userId):
        boo = json.loads(self.request.body) # load json into an dict
        new_user = User.query(User.id == userId).get()
        if new_user == None: # if it isnt a valid id then we can not put return a 404
            self.response.status_int = 404
            self.response.write("User with id:"+ new_user +" was not found")
        else: # else put in the new data or None since it is a put operation and is idemponet
            new_user.id = boo['userToken'] if 'userToken' in boo else None
            new_user.name = boo['name'] if 'name' in boo else None
            new_user.beer = boo['beer'] if 'beer' in boo else None
            new_user.rating = boo['rating'] if 'rating' in boo else None
            new_user.notes = boo['notes'] if 'notes' in boo else None
            new_user.put() # put into datastore
            self.response.status_int = 200
            self.response.write(json.dumps(new_user.to_dict())) # dumps most reccent obj added as json

    def delete(self, userId):
        """ Deletes a user"""
        qo = ndb.QueryOptions(keys_only=True)
        del_key = User.query(User.id == str(userId)).get(options=qo) # find the user with the matching id
        del_key.delete()
        self.response.write("Deleted")

class UsersHandle(webapp2.RequestHandler):
     """/users"""
     def post(self):
         userData = json.loads(self.request.body) # load json into an dict
         qo = ndb.QueryOptions(keys_only=True)
         usr_key = User.query(User.id == str(userData['userToken'])).get(options=qo) # find the user with the matching id
         if not usr_key:
             new_user = User(id=userData['userToken'], \
                             name=userData['name'] if 'name' in userData else None,\
                             beer=userData['beer'] if 'beer' in userData else None,\
                             rating=userData['rating'] if 'rating' in userData else None,\
                             notes=userData['notes'] if 'notes' in userData else None
                            )
             new_bkey = new_user.put() # put into datastore
             new_user.urlsafe = new_bkey.urlsafe() # update id to be the key
             new_user.put() # put back into datastore. THIS DOESNT CHANGE THE KEY
             self.response.status_int = 201
             self.response.write(json.dumps(new_user.to_dict())) # dumps most reccent obj added as json
         else:
             self.response.status_int = 200
             self.response.write("already a user")

     def get(self):
         q = User.query().fetch()
         json_table = list()
         for item in q:                # For each entry, make into a dictionary
             json_table.append(item.to_dict())

         self.response.write(json.dumps(json_table)) # parse the list of dicts into json

     def delete(self):
         qo = ndb.QueryOptions(keys_only=True)
         table = User.query().fetch(options=qo)
         ndb.delete_multi(table)

""" Webapp2 """
# tip taken from lecture on patching
allowed_methods = webapp2.WSGIApplication.allowed_methods
new_allowed_methods = allowed_methods.union(('PATCH',))
webapp2.WSGIApplication.allowed_methods = new_allowed_methods

app = webapp2.WSGIApplication([
    webapp2.Route(r'/', handler=MainPage, name='main'),
    webapp2.Route(r'/users', handler=UsersHandle, name='users'),
    webapp2.Route(r'/users/<userId:[a-zA-Z0-9_-]*$>', handler=Users2Handle, name='users2'),
])
# regex source http://stackoverflow.com/questions/24419067/validate-a-string-to-be-url-safe-using-regex
def main():
    app.run()

if __name__ == "__main__":
    main()
