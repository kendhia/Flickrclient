# Flickrclient
Flickr client is a sample app that use Flickr api to search for public photos.

## Libs used :
### JSONSCHEME2POJO
For generating the models that will be parsed JSONSCHEME2POJO is being used see https://github.com/joelittlejohn/jsonschema2pojo
The generated models will generated in the build folder and thus not clutter the source folder

### Picasso
Picasso  image loading library by Square is used to load images efficiently see https://github.com/square/picasso

### Networking
For making API calls to Flickr retrofit and for parsing the response to object GSON is being used
* https://github.com/google/gson
* http://square.github.io/retrofit/
