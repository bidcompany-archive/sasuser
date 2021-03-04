---------------------------------------------------------------------------------------
itacsq 2021.03.04
---------------------------------------------------------------------------------------


DESCRIPTION
Java tool used to synchronize user list between a CSV file and a metadata repository (platform).

HOW TO
As a first step it is required to compile [PROPS/CONFIG.PROPERTIES] file, accordingly to your environment: 
- meta.env.addr= < SERVER ADDRESS / IP >  e.g. 10.0.0.1
- meta.env.port= < SERVER PORT > e.g. 8591
- meta.user.name= < ---- > e.g. sasadm@saspw {must be an unrestricted user}
- meta.user.pwd= < ---- > e.g. password {could also be SAS002 encrypted}

- engine.csv.path= < --- > e.g. users/utenze.csv {could also be relative from jar executable location}

The tool:
1. reads a csv file, which must follow the structure reported in config file (see -> CSV Structure).
2. extracts user list from metadataserver
3. compare the two list to insert only new users
4. add new users to metadataserver

In the log (log4j compliant and adjustable accordingly to admin preferences) you can find details about operations and errors (all new insertions are logged).


NOTES

CSV Structure
- id: UserName {must be unique in the whole environment}
- surname: LastName
- name: FirstNAme {displayName is "LastName + " " + FirstNAme"}
- title: Job Title
- description: long description
- mails: String list of email comma separated ( example@mail1.com, example@mail2.com )
- groups: List of Group Name that should be attached to the user comma separated ( SAS Demo Users, Group1)
- domain: Active Directory domain e.g. europe
- authdomain: DefaultAuth 
- pswd: Password pushed within login authentication infos (not used from sas system)


JAVA Version
Developed under java 1.8.17