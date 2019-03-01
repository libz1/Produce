unit UnitXu;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms, Dialogs, StdCtrls, Buttons;

type
  TForm1 = class(TForm)
    GroupBox3: TGroupBox;
    Label6: TLabel;
    Label7: TLabel;
    Label51: TLabel;
    ComboBox1: TComboBox;
    Edit2: TEdit;
    Edit1: TEdit;
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
    Button1: TButton;
    ComboBox2: TComboBox;
    Edit6: TEdit;
    Edit7: TEdit;
    Edit8: TEdit;
    ComboBox3: TComboBox;
    Edit9: TEdit;
    Edit10: TEdit;
    Edit11: TEdit;
    Edit12: TEdit;
    Edit13: TEdit;
    Edit14: TEdit;
    ComboBox4: TComboBox;
    Button13: TButton;
    CheckBox1: TCheckBox;
    Button18: TButton;
    Button21: TButton;
    GroupBox7: TGroupBox;
    Label50: TLabel;
    Button6: TButton;
    Memo1: TMemo;
    btn1: TButton;
    Edit15: TEdit;
    Edit16: TEdit;
    procedure Button1Click(Sender: TObject);
    procedure Button13Click(Sender: TObject);
    procedure Button6Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

function Adjust_UI1(Phase: Integer; Rated_Volt, Rated_Curr, Rated_Freq: Double; PhaseSequence, Revers: Integer; Volt_Per1, Volt_Per2, Volt_Per3, Curr_Per: Double; IABC, CosP: PChar; SModel: PChar; Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

function Power_Off(Dev_Port: Byte): Boolean; stdcall; external 'hscom.DLL';

function StdMeter_Read(var SData: Pchar; SModel: PChar; Dev_Port: Integer): Boolean; stdcall; external 'hscom.DLL';

implementation

{$R *.dfm}

procedure TForm1.Button1Click(Sender: TObject);
var
  tt: Boolean;
  dy, bddl, pl, dy_a, dy_b, dy_c, dl: Double;
  xx, xxu, fx: Integer;
   IABC, CosP, SModel: pchar;
  s: string;
  i: integer;

begin
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

  tt := Adjust_UI1(xx, dy, bddl, pl, xxu, fx, dy_a, dy_b, dy_c, dl, IABC, Pchar(Edit12.text), Pchar(ComboBox1.Text), Strtointdef(Edit2.text, 2));

//  tt := Adjust_UI1(xx, dy, bddl, pl, xxu, fx, dy_a, dy_b, dy_c, dl, Pchar(Edit11.text), Pchar(Edit12.text), Pchar(ComboBox1.Text), Strtointdef(Edit2.text, 2));
//tt:=Adjust_UI1(1, 220, 5, 50, 0, 0, 0,0,0, 0, 'H', '0.5L',Pchar(ComboBox1.Text), Strtointdef(Edit2.text,2));
  if tt then
    Edit1.text := 'OK'
  else
    Edit1.text := 'ERROR';
end;

procedure TForm1.Button13Click(Sender: TObject);
var
  tt: Boolean;
begin
  Edit1.text := '';
  tt := Power_Off(StrtoIntDef(Edit2.Text, 2));
  if tt then
    Edit1.text := 'OK'
  else
    Edit1.text := 'ERROR';

end;

procedure TForm1.Button6Click(Sender: TObject);
var
  tt: Boolean;
  str: Pchar;
begin
  getmem(str, 1500);
  Edit1.text := '';

  tt := StdMeter_Read(str, Pchar(ComboBox1.Text), Strtointdef(Edit2.text, 2));
  if tt then
    Memo1.text := (str)
  else
    Memo1.text := 'ERROR';
  freemem(str);

end;

end.


