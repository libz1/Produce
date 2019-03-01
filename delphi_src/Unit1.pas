unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls, OleCtrls, ExtCtrls, Buttons, ComCtrls;

type
  TForm1 = class(TForm)
    Timer1: TTimer;
    GroupBox1: TGroupBox;
    Button2: TButton;
    Label1: TLabel;
    Edit3: TEdit;
    Label2: TLabel;
    Label3: TLabel;
    Edit4: TEdit;
    Label4: TLabel;
    Label5: TLabel;
    Edit5: TEdit;
    GroupBox2: TGroupBox;
    Button1: TButton;
    GroupBox3: TGroupBox;
    ComboBox1: TComboBox;
    Edit2: TEdit;
    Label6: TLabel;
    Label7: TLabel;
    Label8: TLabel;
    ComboBox2: TComboBox;
    Label9: TLabel;
    Edit6: TEdit;
    Label10: TLabel;
    Edit7: TEdit;
    Label11: TLabel;
    Edit8: TEdit;
    Label12: TLabel;
    ComboBox3: TComboBox;
    Label13: TLabel;
    Edit9: TEdit;
    Label14: TLabel;
    Edit10: TEdit;
    Label15: TLabel;
    Label16: TLabel;
    Label17: TLabel;
    Edit11: TEdit;
    Label18: TLabel;
    Label19: TLabel;
    Edit12: TEdit;
    Label20: TLabel;
    Label21: TLabel;
    Edit13: TEdit;
    Label22: TLabel;
    Label23: TLabel;
    Label24: TLabel;
    Label25: TLabel;
    Edit14: TEdit;
    Label26: TLabel;
    ComboBox4: TComboBox;
    Label27: TLabel;
    Edit15: TEdit;
    Label28: TLabel;
    Label29: TLabel;
    Label30: TLabel;
    Edit16: TEdit;
    Label31: TLabel;
    Label32: TLabel;
    GroupBox4: TGroupBox;
    Button3: TButton;
    Label33: TLabel;
    Label34: TLabel;
    Label35: TLabel;
    Label37: TLabel;
    Label38: TLabel;
    Edit17: TEdit;
    Edit19: TEdit;
    ComboBox5: TComboBox;
    GroupBox7: TGroupBox;
    Button6: TButton;
    Memo1: TMemo;
    Label50: TLabel;
    Edit1: TEdit;
    Label51: TLabel;
    GroupBox8: TGroupBox;
    Label52: TLabel;
    Edit26: TEdit;
    Label53: TLabel;
    Label54: TLabel;
    Edit27: TEdit;
    Label55: TLabel;
    Button7: TButton;
    GroupBox9: TGroupBox;
    Label56: TLabel;
    Label57: TLabel;
    Label58: TLabel;
    Label59: TLabel;
    Edit28: TEdit;
    Edit29: TEdit;
    Button8: TButton;
    GroupBox10: TGroupBox;
    Label60: TLabel;
    Label61: TLabel;
    Label62: TLabel;
    Label63: TLabel;
    Edit30: TEdit;
    Edit31: TEdit;
    Button9: TButton;
    Button10: TButton;
    Edit32: TEdit;
    Label64: TLabel;
    GroupBox11: TGroupBox;
    Label65: TLabel;
    Edit33: TEdit;
    Label66: TLabel;
    Button11: TButton;
    Button12: TButton;
    Edit34: TEdit;
    Label67: TLabel;
    Button13: TButton;
    GroupBox12: TGroupBox;
    Label68: TLabel;
    Label69: TLabel;
    Edit35: TEdit;
    Label70: TLabel;
    Edit36: TEdit;
    Label71: TLabel;
    Edit37: TEdit;
    Edit38: TEdit;
    Label72: TLabel;
    Label73: TLabel;
    Label74: TLabel;
    Edit39: TEdit;
    Label75: TLabel;
    Label76: TLabel;
    Label77: TLabel;
    Button14: TButton;
    SpeedButton1: TSpeedButton;
    PageControl1: TPageControl;
    TabSheet1: TTabSheet;
    TabSheet2: TTabSheet;
    GroupBox5: TGroupBox;
    Label36: TLabel;
    Label39: TLabel;
    Label40: TLabel;
    Label41: TLabel;
    Label42: TLabel;
    Label43: TLabel;
    Button4: TButton;
    Edit18: TEdit;
    Edit20: TEdit;
    Edit21: TEdit;
    Edit22: TEdit;
    GroupBox6: TGroupBox;
    Label44: TLabel;
    Label45: TLabel;
    Label46: TLabel;
    Label47: TLabel;
    Label48: TLabel;
    Button5: TButton;
    Edit23: TEdit;
    Edit24: TEdit;
    Edit25: TEdit;
    TabSheet3: TTabSheet;
    Label49: TLabel;
    ComboBox6: TComboBox;
    Label78: TLabel;
    Button15: TButton;
    Button16: TButton;
    Button17: TButton;
    Label79: TLabel;
    Edit40: TEdit;
    Label80: TLabel;
    Edit41: TEdit;
    ComboBox7: TComboBox;
    Label81: TLabel;
    Edit42: TEdit;
    Label82: TLabel;
    Edit43: TEdit;
    Label83: TLabel;
    CheckBox1: TCheckBox;
    Button18: TButton;
    TabSheet4: TTabSheet;
    Button19: TButton;
    Edit44: TEdit;
    Edit45: TEdit;
    Label84: TLabel;
    Label85: TLabel;
    Button20: TButton;
    Button21: TButton;
    Edit46: TEdit;
    Button22: TButton;
    ComboBox8: TComboBox;
    Label86: TLabel;
    btn1: TButton;
    TabSheet5: TTabSheet;
    Label87: TLabel;
    Label88: TLabel;
    Edit47: TEdit;
    Button23: TButton;
    Button24: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure Button5Click(Sender: TObject);
    procedure Button6Click(Sender: TObject);
    procedure Timer1Timer(Sender: TObject);
    procedure FormCloseQuery(Sender: TObject; var CanClose: Boolean);
    procedure ComboBox1Change(Sender: TObject);
    procedure Edit2Exit(Sender: TObject);
    procedure Edit2KeyUp(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure Button7Click(Sender: TObject);
    procedure Button8Click(Sender: TObject);
    procedure Button9Click(Sender: TObject);
    procedure Button10Click(Sender: TObject);
    procedure FormShow(Sender: TObject);
    procedure Button11Click(Sender: TObject);
    procedure Button12Click(Sender: TObject);
    procedure Button13Click(Sender: TObject);
    procedure ComboBox2Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure Button14Click(Sender: TObject);
    procedure SpeedButton1Click(Sender: TObject);
    procedure Button15Click(Sender: TObject);
    procedure Button16Click(Sender: TObject);
    procedure Button17Click(Sender: TObject);
    procedure GroupBox2MouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure Button18Click(Sender: TObject);
    procedure Button19Click(Sender: TObject);
    procedure Button20Click(Sender: TObject);
    procedure Button21Click(Sender: TObject);
    procedure Button22Click(Sender: TObject);
    procedure ComboBox8KeyPress(Sender: TObject; var Key: Char);
    procedure btn1Click(Sender: TObject);
    procedure Button23Click(Sender: TObject);
    procedure Button24Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

  Function StdMeter_Read(var SData:Pchar;SModel:PChar;Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Error_Read(var MError:PChar;Meter_No,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Error_Start(Meter_No:Integer;Constant:Double;Pulse,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Set_Pulse_Channel(Meter_No,Channel_Flag,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Set_485_Channel(Meter_No,Open_Flag,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Adjust_UI1(Phase:Integer;Rated_Volt,Rated_Curr,Rated_Freq:Double;
                   PhaseSequence,Revers:Integer;
                   Volt_Per1,Volt_Per2,Volt_Per3,Curr_Per:Double;IABC,CosP:PChar;
                   SModel:PChar;Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Adjust_UI2(Phase:Integer;Rated_Volt,Rated_Curr,Rate_Freq:Double;
                   PhaseSequence,Revers:Byte;
                   Volt_Per1,Volt_Per2,Volt_Per3,Curr1,Curr2,Curr3:Double;IABC,CosP:PChar;
                   SModel:PChar;Dev_Port:Byte):Boolean;stdcall;external 'hscom.DLL';
Function Adjust_UI3(Phase:Integer;Rate_Freq:Double;
                    PhaseSequence,Revers:Byte;
                    Volt,Curr:Double;
                    IABC:PChar;COSP:Double;
                    SModel:PChar;Dev_Port:Byte):Boolean;stdcall;external 'hscom.DLL';
  Function Search_mark(Meter_No,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function CRPSTA_start(Meter_No,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function CRPSTA_clear(Meter_No,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Search_mark_Result(Meter_No,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function CRPSTA_Result(Meter_No,Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Error_Clear(Dev_Port:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function Power_Off(Dev_Port:Byte):Boolean;stdcall;external 'hscom.DLL';
  Function VoltFall(Dev_Port,ErrAddr:Byte;ZeroTime,FullTime:Double;FallTimes:Integer):Boolean;stdcall;external 'hscom.DLL';
  Function SetRefClock(SetFlag,Dev_Port:Byte):Boolean;stdcall; external 'hscom.DLL';
  Function Clock_Error_Start(Meter_No:Byte;TheoryFreq:Double;TestTime:Integer;Dev_Port:Byte):Boolean;stdcall; external 'hscom.DLL';
  Function Clock_Error_Read(var MError:PChar;TheoryFreq:Double;ErrorType,Meter_No,Dev_Port:Byte):Boolean;stdcall; external 'hscom.DLL';
  Function ErrorComm(Dev_Port,ErrAddr,ErrCmd:Byte;CmdData:PChar;var BackData:PChar):Boolean;stdcall;external 'hscom.DLL';

  Function SetMaxUI(SurMaxU,SurMaxI,FBU,FBI:Double):Boolean;stdcall;external 'hscom.DLL';
  Function Set_HMC_2011(IsHMC:Boolean;PPTBB,CCTBB,CCTXS1,CCTXS2,CCTXS3,PTBB,CTBB:Double):Boolean;stdcall;external 'hscom.DLL';

  Procedure Set_OutPutMode(IsVMode:Boolean);stdcall;external 'hscom.DLL';
   Function GetStdConst:Double;stdcall;external 'hscom.DLL';
//  procedure Dll_Port_Close;stdcall;external 'hscom.DLL';
   procedure SetStdFreq(StdFreq:Double);stdcall;external 'hscom.DLL';
    procedure SetStdConst(StdConst:Double); stdcall; external 'hscom.DLL';
  Function ReadVoltCur(var DevVolt:Double;var DevCur:Double;SModel:PChar;Dev_Port:Byte):Boolean;stdcall; external 'hscom.DLL';
    Function AutoSub_Reset(Meter_No,Dev_Port:Byte):Boolean;stdcall;external 'hscom.DLL';
  Function AutoSub(Meter_No,Dev_Port:Byte):Boolean;stdcall;external 'hscom.DLL';

implementation


{$R *.DFM}


procedure TForm1.Button1Click(Sender: TObject);
var
 tt:Boolean;
 dy,bddl,pl,dy_a,dy_b,dy_c,dl:Double;
 xx,xxu,fx:Integer;


begin
Edit1.text:='';
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

Set_OutPutMode(CheckBox1.Checked);
tt := Adjust_UI1(xx, dy, bddl, pl, xxu, fx, dy_a, dy_b, dy_c, dl, Pchar(Edit11.text), Pchar(Edit12.text),Pchar(ComboBox1.Text), Strtointdef(Edit2.text,2));
//tt:=Adjust_UI1(1, 220, 5, 50, 0, 0, 0,0,0, 0, 'H', '0.5L',Pchar(ComboBox1.Text), Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
SpeedButton1.Enabled := True;
end;

procedure TForm1.Button2Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
//tt:=Set_485_Channel(2,1,Strtointdef(Edit2.text,2));
tt:=Set_485_Channel(Strtointdef(Edit3.text,1),Strtointdef(Edit4.text,0),Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button3Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
//tt:=Set_Pulse_Channel(2,1,Strtointdef(Edit2.text,2));
tt:=Set_Pulse_Channel(StrToIntDef(Edit17.text,1),ComboBox5.ItemIndex,Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button4Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
//SetStdConst(5000000);
//tt:=Error_Start(2,1200,1,Strtointdef(Edit2.text,2));
tt:=Error_Start(StrToIntDef(Edit18.text,1),StrToIntDef(Edit21.text,1),StrToIntDef(Edit22.text,1),Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button5Click(Sender: TObject);
var
 tt:Boolean;
 str:PChar;

begin
Edit1.text:='';
getmem(str,20);
tt:=Error_Read(str,StrToIntDef(Edit24.text,1),Strtointdef(Edit2.text,2));
if tt then
 Edit23.text:=StrPas(str)
else
 Edit23.text:='ERROR';
FreeMem(str);
end;

procedure TForm1.Button6Click(Sender: TObject);
var
 tt:Boolean;
 str:Pchar;
begin
getmem(str,1500);
Edit1.text:='';

tt:=StdMeter_Read(str,Pchar(ComboBox1.Text),Strtointdef(Edit2.text,2));
if tt then
 Memo1.text:=(str)
else
 Memo1.text:='ERROR';
freemem(str);
end;

procedure TForm1.Timer1Timer(Sender: TObject);
begin
Button5Click(nil);
end;

procedure TForm1.FormCloseQuery(Sender: TObject; var CanClose: Boolean);
begin
//Dll_Port_Close;
end;

procedure TForm1.ComboBox1Change(Sender: TObject);
begin
Edit13.Text := ComboBox1.Text;
end;

procedure TForm1.Edit2Exit(Sender: TObject);
begin
{Edit14.Text := Edit2.text;
Edit5.Text := Edit2.text;
Edit19.Text := Edit2.text;
Edit20.Text := Edit2.text;
Edit25.Text := Edit2.text;
Edit27.Text := Edit2.text;}
end;

procedure TForm1.Edit2KeyUp(Sender: TObject; var Key: Word;
  Shift: TShiftState);
begin
Edit14.Text := Edit2.text;
Edit5.Text := Edit2.text;
Edit19.Text := Edit2.text;
Edit20.Text := Edit2.text;
Edit25.Text := Edit2.text;
Edit27.Text := Edit2.text;
Edit29.Text := Edit2.text;
Edit31.Text := Edit2.text;
Edit33.Text := Edit2.text;
Edit36.Text := Edit2.text;
end;

procedure TForm1.Button7Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
tt:=Search_mark(Strtointdef(Edit26.text,1),Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button8Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
tt:=CRPSTA_start(Strtointdef(Edit28.text,1),Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button9Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
tt:=CRPSTA_clear(Strtointdef(Edit30.text,1),Strtointdef(Edit2.text,2));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button10Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
tt:=Search_mark_Result(Strtointdef(Edit26.text,1),Strtointdef(Edit2.text,2));
if tt then begin
 Edit1.text:='OK';
 Edit32.Text := '已找到';
end else begin
 Edit1.text:='ERROR';
 Edit32.Text := '未找到';
end;
end;

procedure TForm1.FormShow(Sender: TObject);
begin
Edit14.Text := Edit2.text;
Edit5.Text := Edit2.text;
Edit19.Text := Edit2.text;
Edit20.Text := Edit2.text;
Edit25.Text := Edit2.text;
Edit27.Text := Edit2.text;
Edit29.Text := Edit2.text;
Edit31.Text := Edit2.text;
Edit33.Text := Edit2.text;
Edit36.Text := Edit2.text;
end;

procedure TForm1.Button11Click(Sender: TObject);
begin
if Error_Clear(StrtoIntDef(Edit2.Text,2)) then
Edit1.Text := 'OK';
end;

procedure TForm1.Button12Click(Sender: TObject);
var
 tt:Boolean;
begin
Edit1.text:='';
tt:=CRPSTA_Result(Strtointdef(Edit28.text,1),Strtointdef(Edit2.text,2));
if tt then begin
 Edit1.text:='OK';
 Edit34.Text := '有脉冲';
end else begin
 Edit1.text:='ERROR';
 Edit34.Text := '无脉冲';
end;
end;

procedure TForm1.Button13Click(Sender: TObject);
begin
Power_Off(StrtoIntDef(Edit2.Text,2));
end;

procedure TForm1.ComboBox2Click(Sender: TObject);
var
 tmp:Boolean;
begin
tmp:=(ComboBox2.ItemIndex>0);
ComboBox3.Enabled:=tmp;
Label29.Enabled:=tmp;
Label30.Enabled:=tmp;
Edit15.Enabled:=tmp;
Edit16.Enabled:=tmp;
Edit11.Enabled:=tmp;
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
Edit13.Text:=ComboBox1.Text;
ComboBox2Click(nil);
end;

procedure TForm1.Button14Click(Sender: TObject);
var
  tt:Boolean;
begin
tt:=VoltFall(StrToIntDef(Edit36.Text,1),StrToIntDef(Edit35.text,1),StrToFloatDef(Edit37.Text,10),StrToFloatDef(Edit38.Text,10),StrToIntDef(Edit39.Text,10));
if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
end;

procedure TForm1.SpeedButton1Click(Sender: TObject);
begin
if SpeedButton1.Down then
begin

end;


end;

procedure TForm1.Button15Click(Sender: TObject);
var
  tt:Boolean;
  dev_port:Byte;
begin
  Edit1.text:='';
  dev_port:=StrToInt(Edit2.Text);
  if ComboBox6.ItemIndex=-1 then
  begin
    Application.MessageBox('请设置''切换标志''', '错误', MB_OK + MB_ICONSTOP);
    Exit;
  end;
  tt:=SetRefClock(ComboBox6.ItemIndex,dev_port);
  if tt then
    Edit1.text:='OK'
  else
    Edit1.text:='ERROR';

 SetStdFreq(StrToFloatDef(ComboBox8.text,50));
 Edit1.text:=Edit1.text+'->'+ComboBox8.text+'kHz';
end;

procedure TForm1.Button16Click(Sender: TObject);
var
  tt:Boolean;
  dev_port,TestTime,Meter_No:Byte;
  TheoryFreq:Double;
begin
  Edit1.text:='';
  dev_port:=StrToIntDef(Edit2.Text,1);
  Meter_No:=StrToIntDef(Edit42.Text,1);
  TestTime:=StrToIntDef(Edit41.Text,10);
  TheoryFreq:=StrToFloatDef(Edit40.Text,1);
  Clock_Error_Start(Meter_No,TheoryFreq,TestTime,dev_port);
  tt:=SetRefClock(ComboBox6.ItemIndex,dev_port);
  if tt then
    Edit1.text:='OK'
  else
    Edit1.text:='ERROR';
end;

procedure TForm1.Button17Click(Sender: TObject);
var
  tt:Boolean;
  merror:PChar;
  TheoryFreq:Double;
  dev_port,ErrorType,Meter_No:Byte;
begin
  Edit1.text:='';
  GetMem(merror,20);
  TheoryFreq:=StrToFloatDef(Edit40.Text,1);
  dev_port:=StrToIntDef(Edit2.Text,1);
  Meter_No:=StrToIntDef(Edit42.Text,1);
  if ComboBox7.ItemIndex=-1 then
  begin
    Application.MessageBox('请设置''时钟误差类型''', '错误', MB_OK + MB_ICONSTOP);
    Exit;
  end;
  ErrorType:=ComboBox7.ItemIndex;
  tt:=Clock_Error_Read(merror,TheoryFreq,ErrorType,Meter_No,dev_port);
  if tt then
    Edit43.text:=StrPas(merror)
  else
    Edit43.text:='ERROR';
end;

procedure TForm1.GroupBox2MouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
if Shift=[ssCtrl,ssAlt] then
 Button18.Visible:=not Button18.Visible;

end;

Function getstr(surstr:String;wcno:Integer):String;
var tmpstr:String;
    i,tmpos,tmpno:integer;
begin
tmpstr:=surstr;
tmpno:=0;
for i:=1 to wcno-1 do begin
 tmpos:=Pos(',',tmpstr);
 if tmpos>0 then begin
   delete(tmpstr,1,tmpos);
   Inc(tmpno);
 end;
end;
if wcno>tmpno+1 then
 Result:=''
else begin
 tmpos:=Pos(',',tmpstr);
 if tmpos>0 then
  Result:=copy(tmpstr,1,tmpos-1)
 else
  Result:=tmpstr;
end;
end;


procedure TForm1.Button18Click(Sender: TObject);
var
 tt:Boolean;
 dy,bddl,pl,dy_a,dy_b,dy_c:Double;
 xx,xxu,fx:Integer;
 dl1,dl2,dl3:Double;


begin
Edit1.text:='';
xx := ComboBox2.ItemIndex;     //相线
dy := StrToFloat(Edit6.text);  //额定电压
bddl := StrToFloat(Edit7.text);  //额定电流
pl := StrToFloat(Edit8.text);  //额定频率
xxu := ComboBox3.ItemIndex;    //相序
fx := ComboBox4.ItemIndex;     //电流方向
dy_a := StrToFloat(Edit9.text);  //电压A百分数
dy_b := StrToFloat(Edit15.text); //电压B百分数
dy_c := StrToFloat(Edit16.text); //电压C百分数
dl1 := StrToFloat(Getstr(Edit10.text,1));   //电流百分数
if Getstr(Edit10.text,2)='' then
 dl2:=dl1
else
 dl2 :=  StrToFloat(Getstr(Edit10.text,2));   //电流百分数
if Getstr(Edit10.text,3)='' then
 dl3:=dl1
else
 dl3 :=  StrToFloat(Getstr(Edit10.text,3));   //电流百分数

Set_OutPutMode(CheckBox1.Checked);
//tt := Adjust_UI3(xx, pl, xxu, fx, dy_a, dl1, Pchar(Edit11.text), StrToFloat(Edit12.text),Pchar(ComboBox1.Text), Strtointdef(Edit2.text,2));
tt := Adjust_UI2(xx, dy, bddl, pl, xxu, fx, dy_a, dy_b, dy_c, dl1,dl2,dl3, Pchar(Edit11.text), Pchar(Edit12.text),Pchar(ComboBox1.Text), Strtointdef(Edit2.text,2));
//tt := Adjust_UI2(2, 100, 1.5, 50, 0, 0, 100, 50, 50, 100,100,100, Pchar(Edit11.text), Pchar(Edit12.text),Pchar(ComboBox1.Text), Strtointdef(Edit2.text,2));

if tt then
 Edit1.text:='OK'
else
 Edit1.text:='ERROR';
SpeedButton1.Enabled := True;

end;

Function StrToHex(tmpstr:String):String;
var
  i  : Integer;
begin
Result:='';
for i:=1 to length(tmpstr) do
 Result:=Result+IntToHex(Ord(tmpstr[i]),2);
end;

procedure TForm1.Button19Click(Sender: TObject);
var
  ReadStr:Pchar;
  tmp1,tmp2,tmp3:String;
begin
getmem(ReadStr,30);
tmp1:=Getstr(Edit44.text,1);
tmp2:=Getstr(Edit44.text,2);
tmp3:=Getstr(Edit44.text,3);
ErrorComm(StrToInt(Edit2.text),StrToInt('$'+tmp1),StrToInt('$'+tmp2),PChar(tmp3),ReadStr);
edit45.text:= StrToHex(ReadStr);freemem(ReadStr);end;

procedure TForm1.Button20Click(Sender: TObject);
var
   ReadStr:PChar;
begin
getmem(ReadStr,30);
ErrorComm(StrToInt(Edit2.text),$41,$34,'0',ReadStr);
FreeMem(ReadStr);
end;

procedure TForm1.Button21Click(Sender: TObject);
begin
SetMaxUI(200,10,100,1);
//Set_HMC_2011(True,100,5,1,1,1,100,5);
Set_HMC_2011(true,50,60,1.101988,1.099018,1.098602,100,300);
end;

procedure TForm1.Button22Click(Sender: TObject);
var
 tmpcs:Double;
begin
tmpcs:=GetStdConst;
Edit46.text:=FloatToStr(tmpcs);
end;

procedure TForm1.ComboBox8KeyPress(Sender: TObject; var Key: Char);
begin
Key:=#0;
end;

procedure TForm1.btn1Click(Sender: TObject);
var
  tmpVolt,tmpCurr:Double;
  tt:Boolean;
begin     
tt:=ReadVoltCur(tmpVolt,tmpCurr,Pchar(ComboBox1.Text),Strtointdef(Edit2.text,2));
if tt then
 begin
  Edit1.text:='OK';
  Memo1.Lines.Add(FloatToStr(tmpVolt)+'V;'+FloatToStr(tmpCurr)+'A');
 end
else
 Edit1.text:='ERROR';
end;

procedure TForm1.Button23Click(Sender: TObject);
var
  tt:Boolean;
  dev_port,TestTime,Meter_No:Byte;
  TheoryFreq:Double;
begin
  Edit1.text:='';
  dev_port:=StrToIntDef(Edit2.Text,1);
  Meter_No:=StrToIntDef(Edit47.Text,1);
  tt:=AutoSub(Meter_No,dev_port);
   if tt then
    Edit1.text:='OK'
  else
    Edit1.text:='ERROR';

end;

procedure TForm1.Button24Click(Sender: TObject);
var
  tt:Boolean;
  dev_port,TestTime,Meter_No:Byte;
  TheoryFreq:Double;
begin
  Edit1.text:='';
  dev_port:=StrToIntDef(Edit2.Text,1);
  Meter_No:=StrToIntDef(Edit47.Text,1);
  tt:=AutoSub_Reset(Meter_No,dev_port);
     if tt then
    Edit1.text:='OK'
  else
    Edit1.text:='ERROR';

end;

end.
