## Most Recent Changes (Changelog)
It should be noted this changelog is miniscule and random in it's tracking. It's more of an assortment of changes I would like to remind myself of, to enforce these behaviors/practices in the future, than an actual professional list of changes that would result in direct effects on users/developers.

Anyway....

[March 3rd, 2021] As of now, the only goal left to fulfill before a new major release is to provide easy access to the library on Maven Central, and through dependency management software like maven and gradle. I will probably have to rename the packaging just because of the requirements with ossrh hosting (com.github.jserve) or something similar. 

_Introduced middleware capabilities._
This is a major functionality point I wanted to introduce since the beginning, and now it's here - with limited power as of now. 
Any middleware can create event handlers for 3 events (as of now):
1. HTTP-REQUEST-RECEIVED (An http request has been received. Before anything is done to process it, this event offers middleware to do anything they'd like)
2. HTTP-RESPONSE-READY (An http response has been crafted based on the request, but the server hasn't sent it off yet)
3. HTTP-RESPONSE-SENT (The http response which was prepared has now been sent, any cleaning up should probably be done here)

_Renamed FileMIMETypeStore to FileMIMETypeRegistry. The ambiguity can definitely throw someone off._

_Refactored the framework by abstraction. Abstracted out concepts like Route handling into a registry of sorts, as well as a MIMEType store._


_Introduced a new exception for cases of a Null response status, and added unit testing to facilitate better code practices, and code quality._


_Replace ambiguous interfaces for Predicates, Consumers, etc... (if necessary and enough evidence is present to indicate negligent practices/code)_
- FilterData (resolved by introducing a Predicate<String> lineRejectable)
- NotFoundPageHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler)
- Response 
- TransformPath (resolved by introducing a Function<Path, Path> translatePath) 
- WebRouteHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> webRouteHandler)
  
