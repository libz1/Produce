unit Unit21;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms, Dialogs, StdCtrls, Buttons, ScktComp, StrUtils;

type
  TForm1 = class(TForm)
    Button1: TButton;
    Button2: TButton;
    Label6: TLabel;
    ComboBox1: TComboBox;
    Label7: TLabel;
    Edit2: TEdit;
    GroupBox2: TGroupBox;
    Label8: TLabel;
    Label9: TLabel;
    Label10: TLabel;
    Label11: TLabel;
    Label12: TLabel;
    Label13: TLabel;
    Label14: TLabel;
    Label15: TLabel;
    Label16: TLabel;
    Label17: TLabel;
    Label18: TLabel;
    Label19: TLabel;
    Label20: TLabel;
    Label21: TLabel;
    Label22: TLabel;
    Label23: TLabel;
    Label24: TLabel;
    Label26: TLabel;
    Label29: TLabel;
    Label30: TLabel;
    Label31: TLabel;
    Label32: TLabel;
    SpeedButton1: TSpeedButton;
    Label86: TLabel;
    Button3: TButton;
    ComboBox2: TComboBox;
    Edit6: TEdit;
    Edit7: TEdit;
    Edit8: TEdit;
    ComboBox3: TComboBox;
    Edit9: TEdit;
    Edit10: TEdit;
    Edit11: TEdit;
    Edit12: TEdit;
    ComboBox4: TComboBox;
    Button13: TButton;
    CheckBox1: TCheckBox;
    Button18: TButton;
    Button21: TButton;
    Edit15: TEdit;
    Edit16: TEdit;
    GroupBox7: TGroupBox;
    Label50: TLabel;
    Button6: TButton;
    Memo1: TMemo;
    btn1: TButton;
    Edit1: TEdit;
    ServerSocket1: TServerSocket;
    Memo2: TMemo;
    BitBtn1: TBitBtn;
    BitBtn2: TBitBtn;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button6Click(Sender: TObject);
    procedure ServerSocket1ClientRead(Sender: TObject; Socket: TCustomWinSocket);
    procedure FormActivate(Sender: TObject);
    procedure BitBtn1Click(Sender: TObject);
    procedure BitBtn2Click(Sender: TObject);
  private
    All_frame: string;
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

procedure Dll_Port_Close; stdcall; external 'hscom.DLL';

function Power_Off(Dev_Port: Byte): Boolean; stdcall; external 'hscom.DLL';

function Adjust_UI1(Phase: Integer; Rated_Volt, Rated_Curr, Rated_Freq: Double; PhaseSequence, Revers: Integer; Volt_Per1, Volt_Per2, Volt_Per3, Curr_Per: Double; IABC, CosP: PChar; SModel: PChar; Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function StdMeter_Read(var SData: Pchar; SModel: PChar; Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function SetFCAddVolt(Meter_No, AddFlag, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function GetFCAddVolt(var AddState: PChar; Meter_No, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function SetFSState(Meter_No, Dev_Port: Integer; On_Flag: PChar): Boolean; stdcall; external 'hscom.DLL';

function GetFSState(var SState: PChar; Meter_No, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function GetFCState(var CState: PChar; Meter_No, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function SetCheckDCState(Meter_No, CtrlTag, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function GetDCValue(var DCValue: PChar; Meter_No, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function SetDCOutType(Meter_No, UIFlag, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function GetDCOutType(var UIState: PChar; Meter_No, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function SetPWMState(Meter_No, CtrlTag, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function SetPWMPara(Meter_No: Integer; PWMAmp: Double; PWMType, Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function ChangeMeterInterface(Meter_No, Dev_Port, MeterType: Integer): Boolean; stdcall; external 'hscom.DLL';

implementation

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
var
  tt: Boolean;
begin
  Edit1.text := '';
  tt := Power_Off(46);
//  tt := Power_Off(4);
  if tt then
    Edit1.text := 'OK'
  else
    Edit1.text := 'ERROR';
end;

function Power_On(type1, port: string): string;
var
  tt: Boolean;
  IABC, CosP, SModel: pchar;
  s: string;
  i: integer;
begin
  Form1.Edit1.text := '';
  s := 'H';
  i := length(s);
  GetMem(IABC, i);
  StrCopy(IABC, PChar(s));
//  IABC := PChar('H');
  s := '1.0';
  i := length(s);
  GetMem(CosP, i);
//  CosP := PChar('1.0');
  StrCopy(CosP, PChar(s));
//  i := length(type1);
  GetMem(SModel, i);
//  SModel := PChar(type1);
  StrCopy(SModel, PChar(type1));
  try
    tt := Adjust_UI1(1, StrToFloat('220'), StrToFloat('1'), StrToFloat('50'), 0, 0, StrToFloat('100'), StrToFloat('100'), StrToFloat('100'), StrToFloat('100'), IABC, CosP, SModel, Strtointdef(port, 0));
    if tt then
      Form1.Edit1.text := 'OK'
    else
      Form1.Edit1.text := 'ERROR';

  finally
    FreeMem(IABC);  //可以正确释放
    FreeMem(CosP);  //可以正确释放
    FreeMem(SModel);  //可以正确释放
  end;
  result := Form1.Edit1.text;

end;

function Power_Off_fun(port: string): string;
var
  tt: Boolean;
begin
  Form1.Edit1.text := '';
  tt := Power_Off(Strtointdef(port, 0));
  if tt then
    Form1.Edit1.text := 'OK'
  else
    Form1.Edit1.text := 'ERROR';
  result := Form1.Edit1.text;

end;

function ReadMeter(type1, port: string): string;
var
  tt: Boolean;
  SModel, str: Pchar;
  i: Integer;
begin
  getmem(str, 1500);
  Form1.Edit1.text := '';
  i := length(type1);
  GetMem(SModel, i);
  StrCopy(SModel, PChar(type1));

  tt := StdMeter_Read(str, SModel, Strtointdef(port, 0));
  if tt then
  begin
    Form1.Memo1.Lines.Add(str);
    Form1.Edit1.text := 'OK';
  end
  else
  begin
    Form1.Edit1.text := 'ERROR';
    Form1.Memo1.Lines.Add('ERROR');
  end;
  result := str;

  freemem(str);
  freemem(SModel);
end;

function OneInCharFun(fun, meter, port: string): string;
var
  tt: Boolean;
  outData, str: Pchar;
begin
  Form1.Edit1.text := '';
  getmem(outData, 1500);
  if (fun = 'GetFCAddVolt') then
    tt := GetFCAddVolt(outData, Strtointdef(meter, 0), Strtointdef(port, 0));
  if (fun = 'GetFCState') then
    tt := GetFCState(outData, Strtointdef(meter, 0), Strtointdef(port, 0));
  if (fun = 'GetDCValue') then
    tt := GetDCValue(outData, Strtointdef(meter, 0), Strtointdef(port, 0));
  if (fun = 'GetFSState') then
    tt := GetFSState(outData, Strtointdef(meter, 0), Strtointdef(port, 0));
  if (fun = 'GetDCOutType') then
    tt := GetDCOutType(outData, Strtointdef(meter, 0), Strtointdef(port, 0));

  if tt then
  begin
    Form1.Memo1.Lines.Add(outData);
    Form1.Edit1.text := 'OK';
  end
  else
  begin
    Form1.Edit1.text := 'ERROR';
    Form1.Memo1.Lines.Add('ERROR');
  end;
  result := outData;
  freemem(outData);
end;

function TwoIntOneCharInFun(fun, meter, port, Flag: string): string;
var
  tt: Boolean;
  str: string;
  On_Flag: Pchar;
  i: Integer;
begin
  i := length(Flag);
  GetMem(On_Flag, i);
  StrCopy(On_Flag, PChar(Flag));

  if (fun = 'SetFSState') then
    tt := SetFSState(Strtointdef(meter, 0), Strtointdef(port, 0), On_Flag);

  if tt then
    str := 'OK'
  else
    str := 'ERROR';
  Form1.Memo1.Lines.Add(str);
  Form1.Edit1.text := 'OK';
  result := str;
end;

function ThreeIntFun(fun, meter, port, Flag: string): string;
var
  tt: Boolean;
  str: string;
begin
  if (fun = 'SetFCAddVolt') then
    tt := SetFCAddVolt(Strtointdef(meter, 0), Strtointdef(Flag, 0), Strtointdef(port, 0));
  if (fun = 'SetCheckDCState') then
    tt := SetCheckDCState(Strtointdef(meter, 0), Strtointdef(Flag, 0), Strtointdef(port, 0));
  if (fun = 'SetDCOutType') then
    tt := SetDCOutType(Strtointdef(meter, 0), Strtointdef(Flag, 0), Strtointdef(port, 0));
  if (fun = 'SetPWMState') then
    tt := SetPWMState(Strtointdef(meter, 0), Strtointdef(Flag, 0), Strtointdef(port, 0));
  if (fun = 'ChangeMeterInterface') then
    tt := ChangeMeterInterface(Strtointdef(meter, 0), Strtointdef(port, 0), Strtointdef(Flag, 0));

  if tt then
    str := 'OK'
  else
    str := 'ERROR';
  Form1.Memo1.Lines.Add(str);
  Form1.Edit1.text := 'OK';
  result := str;
end;

function ThreeIntOneDoubleFun(fun, meter, port, Flag, DoubleData: string): string;
var
  tt: Boolean;
  str: string;
begin
  //Meter_No :Integer; PWMAmp:Double;PWMType,Dev_Port:Integer
  if (fun = 'SetPWMPara') then
    tt := SetPWMPara(Strtointdef(meter, 0), StrToFloat(DoubleData), Strtointdef(Flag, 0), Strtointdef(port, 0));

  if tt then
    str := 'OK'
  else
    str := 'ERROR';
  Form1.Memo1.Lines.Add(str);
  Form1.Edit1.text := 'OK';
  result := str;
end;

procedure TForm1.Button2Click(Sender: TObject);
var
  dy, bddl, pl, dy_a, dy_b, dy_c, dl: Double;
  xx, xxu, fx: Integer;
  tt: Boolean;
  IABC, CosP, SModel: pchar;
  s: string;
  i: integer;
begin
//  Power_On('HS5320', '4');
  Power_On('HS5300', '46');
{
  Edit1.text := '';
  xx := ComboBox2.ItemIndex;       //相线
  dy := StrToFloat(Edit6.text);    //额定电压
  bddl := StrToFloat(Edit7.text);  //额定电流
  pl := StrToFloat(Edit8.text);    //额定频率
  xxu := ComboBox3.ItemIndex;      //相序
  fx := ComboBox4.ItemIndex;       //电流方向
  dy_a := StrToFloat(Edit9.text);  //电压A百分数
  dy_b := StrToFloat(Edit15.text); //电压B百分数
  dy_c := StrToFloat(Edit16.text); //电压C百分数
  dl := StrToFloat(Edit10.text);   //电流百分数

  s := 'H';
  i := length(s);
  GetMem(IABC, i);
  s := '1.0';
  i := length(s);
  GetMem(CosP, i);
  s := 'HS5320';
  i := length(s);
  GetMem(SModel, i);
  try
    tt := Adjust_UI1(1, StrToFloat('220'), StrToFloat('0'), StrToFloat('50'), 0, 0, StrToFloat('100'), StrToFloat('100'), StrToFloat('100'), StrToFloat('100'), IABC, CosP, SModel, Strtointdef('48', 0));
    if tt then
      Edit1.text := 'OK'
    else
      Edit1.text := 'ERROR';

  finally
    FreeMem(IABC);  //可以正确释放
    FreeMem(CosP);  //可以正确释放
    FreeMem(SModel);  //可以正确释放
  end;


Phase:Integer【取值1】;
Rated_Volt【取值220.0】,Rated_Curr【取值5.0】,Rated_Freq【取值50】:Double;
PhaseSequence【取值0】,Revers【取值0】:Integer;
Volt_Per【取值100.0】,Curr_Per【取值100.0】:Double;
IABC【取值'H'】,CosP【取值'1.0'】:PChar;
SModel:PChar【取值'HS5300'】;Dev_Port【由外部调用时传入的参数值】:Integer
}
end;

procedure TForm1.Button6Click(Sender: TObject);
var
  tt: Boolean;
  str: Pchar;
begin
  getmem(str, 1500);
  Edit1.text := '';

  tt := StdMeter_Read(str, Pchar(ComboBox1.Text), Strtointdef(Edit2.text, 0));
  if tt then
  begin
    Memo1.text := (str);
    Edit1.text := 'OK';
  end
  else
  begin
    Edit1.text := 'ERROR';
    Memo1.text := 'ERROR';
  end;
  freemem(str);
end;

function Get_param(Str, param: string): string;
var
  tmp, tmp1: string;
  pos1, pos2: integer;
begin
  pos1 := Pos(param, Str);
  tmp := RightStr(Str, Length(Str) - pos1 - Length(param) + 1);
  pos2 := Pos(';', tmp);
  tmp1 := LeftStr(tmp, pos2 - 1);
  result := tmp1;
end;

//[SetPWMState]meter=1;port=49;Flag=0;
function Get_fun(Str: string): string;
var
  pos1: integer;
  tmp, a: string;
begin
  tmp := RightStr(Str, Length(Str) - 1);
  pos1 := Pos(']', tmp);
  result := LeftStr(tmp, pos1 - 1);
end;

function Dll_Port_Close_fun(): string;
begin
   Dll_Port_Close;
   result := '';
end;

function Deal_recv(Str: string; Socket: TCustomWinSocket): string;
var
  noFind: Boolean;
  inputStr: string;
begin
  result := '';
  noFind := false;
  Form1.Memo1.Lines.Add(Str);
  inputStr := Str;
  if Pos(#13, Str) > 0 then
    Str := LeftStr(Str, Length(Str) - 2);
  if Pos(#10, Str) > 0 then
    Str := LeftStr(Str, Length(Str) - 1);
  try
    begin
      if Pos('[on]', Str) > 0 then
        Str := Power_On(Get_param(Str, 'type='), Get_param(Str, 'port='))
      else if Pos('[Dll_Port_Close]', Str) > 0 then
        Dll_Port_Close_fun()
      else if Pos('[off]', Str) > 0 then
        Str := Power_Off_fun(Get_param(Str, 'port='))
      else if Pos('[ReadMeter]', Str) > 0 then
        Str := ReadMeter(Get_param(Str, 'type='), Get_param(Str, 'port='))

      else if (Pos('[SetFCAddVolt]', Str) > 0) or (Pos('[SetCheckDCState]', Str) > 0) or (Pos('[SetDCOutType]', Str) > 0) or (Pos('[SetPWMState]', Str) > 0) or (Pos('[ChangeMeterInterface]', Str) > 0) then
    // 三个入参都是int
        Str := ThreeIntFun(Get_fun(Str), Get_param(Str, 'meter='), Get_param(Str, 'port='), Get_param(Str, 'Flag='))

      else if (Pos('[SetFSState]', Str) > 0) or (Pos('[]', Str) > 0) or (Pos('[]', Str) > 0) or (Pos('[]', Str) > 0) then
    // 2个入参都是int,1个入参char
        Str := TwoIntOneCharInFun(Get_fun(Str), Get_param(Str, 'meter='), Get_param(Str, 'port='), Get_param(Str, 'Flag='))

      else if (Pos('[GetFCAddVolt]', Str) > 0) or (Pos('[GetFCState]', Str) > 0) or (Pos('[GetDCValue]', Str) > 0) or (Pos('[GetFSState]', Str) > 0) or (Pos('[GetDCOutType]', Str) > 0) then
    // 1个出参是char
        Str := OneInCharFun(Get_fun(Str), Get_param(Str, 'meter='), Get_param(Str, 'port='))

      else if (Pos('[SetPWMPara]', Str) > 0) then
    // 1个出参是char
        Str := ThreeIntOneDoubleFun(Get_fun(Str), Get_param(Str, 'meter='), Get_param(Str, 'port='), Get_param(Str, 'Flag='), Get_param(Str, 'Double='))

      else
      begin
        Str := Str + '没有找到！';
        noFind := True;
      end;
    end;
  except

  end;

  Socket.SendText('[' + Str + ']' + #10#13);
  Form1.Memo2.Lines.Add(Str);

end;

procedure TForm1.ServerSocket1ClientRead(Sender: TObject; Socket: TCustomWinSocket);
var
  Str, deal_frame: string;
begin
  Str := Socket.ReceiveText;
  if (Pos(#13, Str) > 0) or (Pos(#10, Str) > 0) then
  begin
    deal_frame := All_frame + Str;
    All_frame := '';
    Deal_recv(deal_frame, Socket);
  end
  else
  begin
    All_frame := All_frame + Str;
  end;

end;

procedure TForm1.FormActivate(Sender: TObject);
begin
  All_frame := '';
end;

procedure TForm1.BitBtn1Click(Sender: TObject);
var
  tt: Boolean;
begin
  Edit1.text := '';
  tt := ChangeMeterInterface(0, 46, 0);
  if tt then
    Edit1.text := 'OK'
  else
    Edit1.text := 'ERROR';
end;

procedure TForm1.BitBtn2Click(Sender: TObject);
begin
  Dll_Port_Close_fun();
end;

end.


