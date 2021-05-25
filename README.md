#SAS User Management
itacsq 2021.05.21 
v3

----

**Table of Contents**


[TOC]

----

##Description
Java tool used to synchronize user list between a CSV file and a metadata repository (platform).

----

##How To
As a first step it is required to compile [PROPS/CONFIG.PROPERTIES] file, accordingly to your environment: 
- meta.env.addr= < SERVER ADDRESS / IP >  e.g. 10.0.0.1
- meta.env.port= < SERVER PORT > e.g. 8591
- meta.user.name= < ---- > e.g. sasadm@saspw {must be an unrestricted user}
- meta.user.pwd= < ---- > e.g. password {could also be SAS002 encrypted}
- engine.csv.path= < --- > e.g. users/utenze.csv {could also be relative from jar executable location}

----                    

##Run Step
+ reads a csv file, which must follow the structure reported in config file (see -> CSV Structure).
+ extracts user list from metadataserver
+ compare the two list to insert only new users
+ add new users to metadataserver
+ upsert 

If have to specify either is an internal or an external login: within preference you can find a proper flag.

In log (log4j compliant and adjustable accordingly to admin preferences) you can find details about operations and errors (all new insertions are logged).

###CSV Structure
                    
Field  | Scope
------------- | -------------
`id` | UserName, NOTE: *must be unique in the whole environment*
`cognome` | LastName
`nome` | firstNAme, NOTE: *displayName is "LastName + " " + FirstNAme"*
`title` | JobTitle
`description` | Long description
`mails` | String list of email comma separated (e.g. *example@mail1.com*, *example@mail2.com* )
`groups` | List of Group Name that should be attached to the user comma separated (e.g. *SAS Demo Users*, *Group1* )
`dominio` | Active Directory domain (e.g. *europe* )
`authdomain` | SAS Authentication domain (e.g. *DefaultAuth* )
`pswd` | User Password, could be SAS Encoded
`loginid` | Login Identitifaction field used as <id>@<authdomain> 


## JAVA Version
Developed under java 1.8.17
