API DEVELOPMENT APP

Once this app has been started the API can be tested by accessing the following Url http://localhost:8080/

for the getKey API the authorised password is: password1


This application was created using Java, Spring boot with web, Swagger and the google Guava Library.

The swagger framework is used to access the API for testing purposes.

During the creation of this application I made the assumption that the license should be created using both the name
of the user and the name of the software that the license is for as this would mean each user could get license information
for multiple pieces of software and those license keys would be different.

This also means that during the validation process the user would also have to indicate the software name as well as
their full name in order to check if their license key is valid.