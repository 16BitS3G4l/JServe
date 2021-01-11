## Most Recent Changes (Changelog)
It should be noted this changelog is miniscule and random in it's tracking. It's more of an assortment of changes I would like to remind myself of, to enforce these behaviors/practices in the future, than an actual professional list of changes that would result in direct effects on users/developers.

Anyway....

_Renamed FileMIMETypeStore to FileMIMETypeRegistry. The ambiguity can definitely throw someone off._

_Refactored the framework by abstraction. Abstracted out concepts like Route handling into a registry of sorts, as well as a MIMEType store._


_Introduced a new exception for cases of a Null response status, and added unit testing to facilitate better code practices, and code quality._


_Replace ambiguous interfaces for Predicates, Consumers, etc... (if necessary and enough evidence is present to indicate negligent practices/code)_
- FilterData (resolved by introducing a Predicate<String> lineRejectable)
- NotFoundPageHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> notFoundPageHandler)
- Response 
- TransformPath (resolved by introducing a Function<Path, Path> translatePath) 
- WebRouteHandler (resolved by introducing a BiConsumer<RequestParser, ResponseWriter> webRouteHandler)
  
