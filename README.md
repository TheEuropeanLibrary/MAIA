#MAIA#

**CAUTION!**  
**The information below is subject to change**

###MAIA - Domain Model for The European Library ###
- - - 
MAIA is the wrapper project that holds a highly and typed data model. The core entity is basically a hasmap holding values of a typed key, a value and optional qualifiers to differentiate for example between persons that are publishers from those that are creators. Furthermore, one can link values to each other like that the publisher person can be identified by a certain value. These entities are the base class for all other concepts like provider, dataset or record. Additionally, it defines converters to allow easily converting between the object entity and formats like binary, xml or json. There are dynamic implemenations for those three availabe. 

###MAIA - Structure Overview###
- - - 
MAIA consists of following projects:

* __MAIA (maia)__  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This project is basically the parent project consisting of the modules common, converter and tel
* __MAIA Common (maia)__  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This project is the dynamic definition and implementation of the entity concept. It is independent of a specific use case. It also defines a registry to hold a domain model of keys and a converter concept to convert between the entity and formats like binary.
* __MAIA Converter (maia)__  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This project is basically the parent project for converters consisting of the modules binary, xml and json
* __MAIA TEL (maia)__  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This project holds The European Library domain model. It specifies keys and puts them together to represent a provider with TEL specific information. 


