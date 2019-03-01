library Project1;

{ Important note about DLL memory management: ShareMem must be the
  first unit in your library's USES clause AND your project's (select
  Project-View Source) USES clause if your DLL exports any procedures or
  functions that pass strings as parameters or function results. This
  applies to all strings passed to and from your DLL--even those that
  are nested in records and classes. ShareMem is the interface unit to
  the BORLNDMM.DLL shared memory manager, which must be deployed along
  with your DLL. To avoid using BORLNDMM.DLL, pass string information
  using PChar or ShortString parameters. }

uses
  System.SysUtils,
  System.Classes;

function Power_Off(Dev_Port: Byte): Boolean; stdcall; external 'hscom.DLL';

function Adjust_UI1(Phase: Integer; Rated_Volt, Rated_Curr, Rated_Freq: Double; PhaseSequence, Revers: Integer; Volt_Per1, Volt_Per2, Volt_Per3, Curr_Per: Double; IABC, CosP: PChar; SModel: PChar; Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function StdMeter_Read(var SData: Pchar; SModel: PChar; Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

{$R *.res}
Function MyMax ( X , Y : integer ) : integer ; stdcall ;
begin
   if X > Y then
   Result := X
   else
   Result := Y ;
end ;

function Deal_String(type1, port: PWideChar; data : Pchar): PWideChar;
var
  str: string;
  i: integer;
  SModel: PChar;
begin
//  PWideChar转为string ==> WideCharToString
  str := WideCharToString(type1);

//  string转为PChar ==> GetMem、StrCopy、PChar
  i := length(str);
  GetMem(SModel, i);
  StrCopy(SModel, PChar(str));

//  PChar转为string ==>  :=
  str := SModel;
  Power_Off(StrtoIntDef('49',2));

  str := str + ' OK';

//  string转为PWideChar转为string ==> PWideChar
  result := PWideChar(str);

end;

function Power_On(type1, port: PWideChar): PWideChar;
var
  tt: Boolean;
  IABC, CosP, SModel: pchar;
  s: string;
  i: integer;
begin

  s := 'H';
  i := length(s);
  GetMem(IABC, i);
  StrCopy(IABC, PChar(s));

  s := '1.0';
  i := length(s);
  GetMem(CosP, i);
  StrCopy(CosP, PChar(s));

  i := length(type1);
  GetMem(SModel, i);
  StrCopy(SModel, PChar(type1));
  try
//    tt := Adjust_UI1(1, StrToFloat('220'), StrToFloat('1'), StrToFloat('50'), 0, 0, StrToFloat('100'), StrToFloat('100'), StrToFloat('100'), StrToFloat('100'), IABC, CosP, SModel, Strtointdef(port, 2));
    tt := true;
    if tt then
      result := 'OK'
    else
      result := 'ERROR';

  finally
    FreeMem(IABC);  //可以正确释放
    FreeMem(CosP);  //可以正确释放
    FreeMem(SModel);  //可以正确释放
  end;
  result := PWideChar(result);

end;

exports
    Power_On index 1,Deal_String,
    MyMax index 99;

begin
end.
