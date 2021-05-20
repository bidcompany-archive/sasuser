%macro encodePwd(in=);
	%global 
	filename temp pwfile 'E:\JsonWork\prova.tmp';

	proc pwencode in="&in." out=pwfile;
	run;


	options symbolgen;
	data _null_;
		infile pwfile truncover;
		input line :$50.;
		call symputx("ret",line);
	run;

%mend;
%encode(in=SASAdmin1878)
%put &ret; 