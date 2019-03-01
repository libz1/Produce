object Form1: TForm1
  Left = 219
  Top = 103
  Width = 890
  Height = 603
  Caption = 'DLL TEST'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCloseQuery = FormCloseQuery
  OnCreate = FormCreate
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object Label27: TLabel
    Left = 10
    Top = 246
    Width = 81
    Height = 13
    AutoSize = False
    Caption = #30005#21387#30334#20998#25968
  end
  object Label28: TLabel
    Left = 203
    Top = 254
    Width = 8
    Height = 13
    Caption = '%'
  end
  object GroupBox12: TGroupBox
    Left = 272
    Top = 431
    Width = 365
    Height = 122
    Caption = #30005#21387#36300#33853'('#29305#23450#21488#36866#29992')'
    TabOrder = 11
    object Label68: TLabel
      Left = 4
      Top = 17
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #25511#21046#34920#20301#21495
    end
    object Label69: TLabel
      Left = 106
      Top = 17
      Width = 25
      Height = 13
      Caption = '1~96'
    end
    object Label70: TLabel
      Left = 4
      Top = 99
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label71: TLabel
      Left = 90
      Top = 101
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Label72: TLabel
      Left = 4
      Top = 38
      Width = 96
      Height = 13
      Caption = #36300#33853#38646#28857#20445#25345#26102#38388
    end
    object Label73: TLabel
      Left = 4
      Top = 58
      Width = 96
      Height = 13
      Caption = #36300#33853#28385#24230#20445#25345#26102#38388
    end
    object Label74: TLabel
      Left = 4
      Top = 80
      Width = 48
      Height = 13
      Caption = #36300#33853#27425#25968
    end
    object Label75: TLabel
      Left = 184
      Top = 40
      Width = 61
      Height = 13
      Caption = '0.1~6000 '#31186
    end
    object Label76: TLabel
      Left = 184
      Top = 61
      Width = 61
      Height = 13
      Caption = '0.1~6000 '#31186
    end
    object Label77: TLabel
      Left = 184
      Top = 83
      Width = 61
      Height = 13
      Caption = '1~100000'#27425
    end
    object Edit35: TEdit
      Left = 77
      Top = 13
      Width = 23
      Height = 21
      TabOrder = 0
      Text = '1'
    end
    object Edit36: TEdit
      Left = 65
      Top = 97
      Width = 23
      Height = 21
      TabOrder = 1
      Text = '1'
    end
    object Edit37: TEdit
      Left = 102
      Top = 34
      Width = 73
      Height = 21
      TabOrder = 2
      Text = '10'
    end
    object Edit38: TEdit
      Left = 102
      Top = 56
      Width = 73
      Height = 21
      TabOrder = 3
      Text = '10'
    end
    object Edit39: TEdit
      Left = 101
      Top = 78
      Width = 74
      Height = 21
      TabOrder = 4
      Text = '10'
    end
    object Button14: TButton
      Left = 280
      Top = 32
      Width = 65
      Height = 57
      Caption = #24320#22987#36300#33853
      TabOrder = 5
      OnClick = Button14Click
    end
  end
  object GroupBox1: TGroupBox
    Left = 272
    Top = 48
    Width = 181
    Height = 109
    Caption = '485'#36890#36947#20999#25442
    TabOrder = 0
    object Label1: TLabel
      Left = 4
      Top = 15
      Width = 77
      Height = 13
      AutoSize = False
      Caption = #25511#21046#34920#20301#21495
    end
    object Label2: TLabel
      Left = 4
      Top = 37
      Width = 74
      Height = 13
      AutoSize = False
      Caption = #36890#26029#29366#24577
    end
    object Label3: TLabel
      Left = 93
      Top = 15
      Width = 25
      Height = 13
      Caption = '1~96'
    end
    object Label4: TLabel
      Left = 93
      Top = 41
      Width = 82
      Height = 13
      AutoSize = False
      Caption = '1- '#21560#21512'  0- '#26029#24320
    end
    object Label5: TLabel
      Left = 4
      Top = 61
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label25: TLabel
      Left = 92
      Top = 63
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Button2: TButton
      Left = 16
      Top = 82
      Width = 149
      Height = 21
      Caption = #20999#25442
      TabOrder = 0
      OnClick = Button2Click
    end
    object Edit3: TEdit
      Left = 67
      Top = 15
      Width = 23
      Height = 21
      TabOrder = 1
      Text = '1'
    end
    object Edit4: TEdit
      Left = 67
      Top = 37
      Width = 23
      Height = 21
      TabOrder = 2
      Text = '1'
    end
    object Edit5: TEdit
      Left = 67
      Top = 59
      Width = 23
      Height = 21
      TabOrder = 3
      Text = '1'
    end
  end
  object GroupBox2: TGroupBox
    Left = 3
    Top = 48
    Width = 266
    Height = 505
    Caption = #30005#21387#30005#27969#25511#21046
    TabOrder = 1
    OnMouseUp = GroupBox2MouseUp
    object Label8: TLabel
      Left = 7
      Top = 16
      Width = 33
      Height = 13
      AutoSize = False
      Caption = #30456#32447
    end
    object Label9: TLabel
      Left = 7
      Top = 40
      Width = 73
      Height = 13
      AutoSize = False
      Caption = #39069#23450#30005#21387
    end
    object Label10: TLabel
      Left = 7
      Top = 64
      Width = 65
      Height = 13
      AutoSize = False
      Caption = #39069#23450#30005#27969
    end
    object Label11: TLabel
      Left = 7
      Top = 88
      Width = 58
      Height = 13
      AutoSize = False
      Caption = #39069#23450#39057#29575
    end
    object Label12: TLabel
      Left = 7
      Top = 112
      Width = 31
      Height = 13
      AutoSize = False
      Caption = #30456#24207
    end
    object Label13: TLabel
      Left = 7
      Top = 160
      Width = 82
      Height = 13
      AutoSize = False
      Caption = #30005#21387#30334#20998#25968'A'
    end
    object Label14: TLabel
      Left = 7
      Top = 232
      Width = 81
      Height = 13
      AutoSize = False
      Caption = #30005#27969#30334#20998#25968
    end
    object Label15: TLabel
      Left = 218
      Top = 168
      Width = 8
      Height = 13
      Caption = '%'
    end
    object Label16: TLabel
      Left = 202
      Top = 240
      Width = 8
      Height = 13
      Caption = '%'
    end
    object Label17: TLabel
      Left = 7
      Top = 256
      Width = 57
      Height = 13
      AutoSize = False
      Caption = #21512#20998#20803
    end
    object Label18: TLabel
      Left = 98
      Top = 280
      Width = 161
      Height = 13
      AutoSize = False
      Caption = ' H-'#21512#20803' A-'#20998'A B-'#20998'B C-'#20998'C'
    end
    object Label19: TLabel
      Left = 7
      Top = 296
      Width = 57
      Height = 13
      AutoSize = False
      Caption = #21151#29575#22240#25968
    end
    object Label20: TLabel
      Left = 98
      Top = 320
      Width = 129
      Height = 13
      AutoSize = False
      Caption = '1.0 0.5L 0.8C .... '
    end
    object Label21: TLabel
      Left = 7
      Top = 336
      Width = 73
      Height = 13
      AutoSize = False
      Caption = #26631#20934#34920#22411#21495
    end
    object Label22: TLabel
      Left = 98
      Top = 360
      Width = 84
      Height = 13
      Caption = #36319#21488#20307#21442#25968#35774#32622
    end
    object Label23: TLabel
      Left = 98
      Top = 400
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Label24: TLabel
      Left = 7
      Top = 376
      Width = 65
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label26: TLabel
      Left = 8
      Top = 136
      Width = 57
      Height = 13
      AutoSize = False
      Caption = #30005#27969#26041#21521' '
    end
    object Label29: TLabel
      Left = 7
      Top = 184
      Width = 90
      Height = 13
      AutoSize = False
      Caption = #30005#21387#30334#20998#25968'B'
    end
    object Label30: TLabel
      Left = 7
      Top = 208
      Width = 82
      Height = 13
      AutoSize = False
      Caption = #30005#21387#30334#20998#25968'C'
    end
    object Label31: TLabel
      Left = 218
      Top = 192
      Width = 8
      Height = 13
      Caption = '%'
    end
    object Label32: TLabel
      Left = 218
      Top = 213
      Width = 8
      Height = 13
      Caption = '%'
    end
    object SpeedButton1: TSpeedButton
      Left = 112
      Top = 443
      Width = 185
      Height = 25
      AllowAllUp = True
      GroupIndex = 3
      Caption = #32531#21319#32531#38477
      Visible = False
      OnClick = SpeedButton1Click
    end
    object Label86: TLabel
      Left = 218
      Top = 233
      Width = 8
      Height = 13
      Caption = '%'
    end
    object Button1: TButton
      Left = 32
      Top = 420
      Width = 184
      Height = 25
      Hint = 'Adjust_UI1'
      Caption = #30005#21387#30005#27969#36755#20986
      ParentShowHint = False
      ShowHint = True
      TabOrder = 0
      OnClick = Button1Click
    end
    object ComboBox2: TComboBox
      Left = 98
      Top = 16
      Width = 159
      Height = 21
      Style = csDropDownList
      ItemHeight = 13
      ItemIndex = 0
      TabOrder = 1
      Text = '0     '#21333#30456
      OnClick = ComboBox2Click
      Items.Strings = (
        '0     '#21333#30456
        '1     '#19977#30456#22235#32447#26377#21151
        '2     '#19977#30456#19977#32447#26377#21151
        '3     90'#24230#26080#21151
        '4     60'#24230#26080#21151
        '5     '#22235#32447#27491#24358#26080#21151
        '6     '#19977#32447#27491#24358#26080#21151)
    end
    object Edit6: TEdit
      Left = 98
      Top = 40
      Width = 121
      Height = 21
      TabOrder = 2
      Text = '220'
    end
    object Edit7: TEdit
      Left = 98
      Top = 64
      Width = 121
      Height = 21
      TabOrder = 3
      Text = '5'
    end
    object Edit8: TEdit
      Left = 98
      Top = 88
      Width = 121
      Height = 21
      TabOrder = 4
      Text = '50'
    end
    object ComboBox3: TComboBox
      Left = 98
      Top = 112
      Width = 121
      Height = 21
      ItemHeight = 13
      ItemIndex = 0
      TabOrder = 5
      Text = '0- '#27491#30456#24207' '
      Items.Strings = (
        '0- '#27491#30456#24207' '
        '1- '#36870#30456#24207)
    end
    object Edit9: TEdit
      Left = 98
      Top = 160
      Width = 113
      Height = 21
      TabOrder = 6
      Text = '100'
    end
    object Edit10: TEdit
      Left = 98
      Top = 230
      Width = 113
      Height = 21
      TabOrder = 7
      Text = '100'
    end
    object Edit11: TEdit
      Left = 98
      Top = 256
      Width = 121
      Height = 21
      TabOrder = 8
      Text = 'H'
    end
    object Edit12: TEdit
      Left = 98
      Top = 296
      Width = 121
      Height = 21
      TabOrder = 9
      Text = '0.5L'
    end
    object Edit13: TEdit
      Left = 98
      Top = 336
      Width = 121
      Height = 21
      TabOrder = 10
      Text = 'Edit13'
    end
    object Edit14: TEdit
      Left = 98
      Top = 376
      Width = 121
      Height = 21
      TabOrder = 11
      Text = 'Edit14'
    end
    object ComboBox4: TComboBox
      Left = 98
      Top = 136
      Width = 121
      Height = 21
      ItemHeight = 13
      ItemIndex = 0
      TabOrder = 12
      Text = '0- '#27491#30456'   '
      Items.Strings = (
        '0- '#27491#30456'   '
        '1- '#21453#30456)
    end
    object Button13: TButton
      Left = 32
      Top = 471
      Width = 185
      Height = 25
      Caption = #38477#30005#21387#30005#27969
      TabOrder = 13
      OnClick = Button13Click
    end
    object CheckBox1: TCheckBox
      Left = 32
      Top = 399
      Width = 53
      Height = 17
      Caption = 'V'#36755#20986
      TabOrder = 14
    end
    object Button18: TButton
      Left = 32
      Top = 445
      Width = 185
      Height = 25
      Hint = 'Adjust_UI2'
      Caption = #30005#21387#30005#27969#36755#20986'('#39640#32423')'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clRed
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      ParentShowHint = False
      ShowHint = True
      TabOrder = 15
      OnClick = Button18Click
    end
    object Button21: TButton
      Left = 224
      Top = 420
      Width = 33
      Height = 21
      Caption = #39640#21387
      TabOrder = 16
      OnClick = Button21Click
    end
  end
  object GroupBox3: TGroupBox
    Left = 3
    Top = 0
    Width = 864
    Height = 49
    Caption = #21488#20307#21442#25968#35774#32622
    TabOrder = 2
    object Label6: TLabel
      Left = 8
      Top = 21
      Width = 73
      Height = 13
      AutoSize = False
      Caption = #26631#20934#34920#22411#21495
    end
    object Label7: TLabel
      Left = 216
      Top = 21
      Width = 65
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label51: TLabel
      Left = 599
      Top = 23
      Width = 161
      Height = 13
      AutoSize = False
      Caption = #21629#20196#21457#36865#29366#24577'(OK'#25110'ERROR)'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clRed
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
    end
    object ComboBox1: TComboBox
      Left = 80
      Top = 17
      Width = 97
      Height = 21
      ItemHeight = 13
      TabOrder = 0
      Text = 'HS5100'
      OnChange = ComboBox1Change
      Items.Strings = (
        'HS5100'
        'HS5300'
        'HS5320'
        'TC-3000C'
        'TC-3000D'
        'TC-6300'
        '['#26080']')
    end
    object Edit2: TEdit
      Left = 288
      Top = 18
      Width = 33
      Height = 21
      Hint = #31471#21475
      ParentShowHint = False
      ShowHint = True
      TabOrder = 1
      Text = '4'
      OnExit = Edit2Exit
      OnKeyUp = Edit2KeyUp
    end
    object Edit1: TEdit
      Left = 763
      Top = 20
      Width = 78
      Height = 21
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clRed
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      TabOrder = 2
    end
  end
  object Edit15: TEdit
    Left = 101
    Top = 230
    Width = 113
    Height = 21
    TabOrder = 3
    Text = '100'
  end
  object Edit16: TEdit
    Left = 101
    Top = 254
    Width = 113
    Height = 21
    TabOrder = 4
    Text = '100'
  end
  object GroupBox4: TGroupBox
    Left = 456
    Top = 48
    Width = 181
    Height = 109
    Caption = #33033#20914#37319#26679#36890#36947#20999#25442
    TabOrder = 5
    object Label33: TLabel
      Left = 4
      Top = 14
      Width = 77
      Height = 13
      AutoSize = False
      Caption = #25511#21046#34920#20301#21495
    end
    object Label34: TLabel
      Left = 4
      Top = 36
      Width = 74
      Height = 13
      AutoSize = False
      Caption = #33033#20914#36890#36947
    end
    object Label35: TLabel
      Left = 93
      Top = 14
      Width = 25
      Height = 13
      Caption = '1~96'
    end
    object Label37: TLabel
      Left = 4
      Top = 60
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label38: TLabel
      Left = 92
      Top = 62
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Button3: TButton
      Left = 14
      Top = 83
      Width = 149
      Height = 21
      Caption = #20999#25442
      TabOrder = 0
      OnClick = Button3Click
    end
    object Edit17: TEdit
      Left = 66
      Top = 14
      Width = 23
      Height = 21
      TabOrder = 1
      Text = '1'
    end
    object Edit19: TEdit
      Left = 66
      Top = 58
      Width = 23
      Height = 21
      TabOrder = 2
      Text = '1'
    end
    object ComboBox5: TComboBox
      Left = 66
      Top = 36
      Width = 105
      Height = 21
      ItemHeight = 13
      TabOrder = 3
      Text = '0- '#31532#19968#36890#36947
      Items.Strings = (
        '0- '#31532#19968#36890#36947
        '1- '#31532#20108#36890#36947
        '2- '#31532#19977#36890#36947
        '3- '#31532#22235#36890#36947
        '4- '#26102#38047#36890#36947
        '5- '#25237#20999#36890#36947
        '6- '#38656#37327#36890#36947
        '7- '#31354' '#36890' '#36947
        '8- '#20809#30005#36890#36947)
    end
  end
  object GroupBox7: TGroupBox
    Left = 272
    Top = 301
    Width = 365
    Height = 129
    Caption = #25351#31034#20202#34920#35835#21462'  '
    TabOrder = 6
    object Label50: TLabel
      Left = 8
      Top = 75
      Width = 346
      Height = 26
      AutoSize = False
      Caption = 
        #25351#31034#20202#34920#35835#21462#36820#22238#26684#24335'("'#65292'"'#20998#38548#65292#26377#21151#21151#29575#21333#20301'W,'#26080#21151#21151#29575#21333#20301'var,'#35270#22312#21151#29575#21333#20301'VA)       U,I,'#966','#26377#21151#21151#29575','#26080#21151 +
        #21151#29575','#35270#22312#21151#29575
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clBlue
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      WordWrap = True
    end
    object Button6: TButton
      Left = 109
      Top = 103
      Width = 149
      Height = 21
      Caption = #35835#21462
      TabOrder = 0
      OnClick = Button6Click
    end
    object Memo1: TMemo
      Left = 5
      Top = 13
      Width = 354
      Height = 61
      Lines.Strings = (
        'Memo1')
      ScrollBars = ssVertical
      TabOrder = 1
    end
    object btn1: TButton
      Left = 280
      Top = 101
      Width = 77
      Height = 22
      Caption = #21333#30456'V/A'#35835#21462
      TabOrder = 2
      OnClick = btn1Click
    end
  end
  object GroupBox8: TGroupBox
    Left = 640
    Top = 257
    Width = 227
    Height = 144
    Caption = #24320#22987#23547#25214#33394#26631
    TabOrder = 7
    object Label52: TLabel
      Left = 4
      Top = 16
      Width = 77
      Height = 13
      AutoSize = False
      Caption = #25511#21046#34920#20301#21495
    end
    object Label53: TLabel
      Left = 112
      Top = 16
      Width = 25
      Height = 13
      Caption = '1~96'
    end
    object Label54: TLabel
      Left = 4
      Top = 42
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label55: TLabel
      Left = 111
      Top = 44
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Label64: TLabel
      Left = 85
      Top = 74
      Width = 48
      Height = 13
      Caption = #33394#26631#29366#24577
    end
    object Edit26: TEdit
      Left = 82
      Top = 16
      Width = 23
      Height = 21
      TabOrder = 0
      Text = '1'
    end
    object Edit27: TEdit
      Left = 82
      Top = 40
      Width = 23
      Height = 21
      TabOrder = 1
      Text = '1'
    end
    object Button7: TButton
      Left = 8
      Top = 112
      Width = 75
      Height = 25
      Caption = #24320#22987#23547#26631
      TabOrder = 2
      OnClick = Button7Click
    end
    object Button10: TButton
      Left = 136
      Top = 112
      Width = 75
      Height = 25
      Caption = #35835#21462#32467#26524
      TabOrder = 3
      OnClick = Button10Click
    end
    object Edit32: TEdit
      Left = 136
      Top = 71
      Width = 73
      Height = 21
      TabOrder = 4
      Text = 'Edit32'
    end
  end
  object GroupBox9: TGroupBox
    Left = 640
    Top = 400
    Width = 227
    Height = 153
    Caption = #24320#22987#28508#21160#25110#36215#21160
    TabOrder = 8
    object Label56: TLabel
      Left = 4
      Top = 24
      Width = 77
      Height = 13
      AutoSize = False
      Caption = #25511#21046#34920#20301#21495
    end
    object Label57: TLabel
      Left = 112
      Top = 24
      Width = 25
      Height = 13
      Caption = '1~96'
    end
    object Label58: TLabel
      Left = 4
      Top = 58
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label59: TLabel
      Left = 111
      Top = 60
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Label67: TLabel
      Left = 69
      Top = 90
      Width = 60
      Height = 13
      Caption = #28508#36215#21160#32467#26524
    end
    object Edit28: TEdit
      Left = 82
      Top = 24
      Width = 23
      Height = 21
      TabOrder = 0
      Text = '1'
    end
    object Edit29: TEdit
      Left = 82
      Top = 56
      Width = 23
      Height = 21
      TabOrder = 1
      Text = '1'
    end
    object Button8: TButton
      Left = 16
      Top = 120
      Width = 89
      Height = 25
      Caption = #24320#22987
      TabOrder = 2
      OnClick = Button8Click
    end
    object Button12: TButton
      Left = 136
      Top = 120
      Width = 75
      Height = 25
      Caption = #35835#21462#32467#26524
      TabOrder = 3
      OnClick = Button12Click
    end
    object Edit34: TEdit
      Left = 136
      Top = 88
      Width = 73
      Height = 21
      TabOrder = 4
      Text = 'Edit34'
    end
  end
  object GroupBox10: TGroupBox
    Left = 640
    Top = 138
    Width = 227
    Height = 119
    Caption = #28508#21160#25110#36215#21160#29366#24577#22797#20301
    TabOrder = 9
    object Label60: TLabel
      Left = 4
      Top = 24
      Width = 77
      Height = 13
      AutoSize = False
      Caption = #25511#21046#34920#20301#21495
    end
    object Label61: TLabel
      Left = 112
      Top = 24
      Width = 25
      Height = 13
      Caption = '1~96'
    end
    object Label62: TLabel
      Left = 4
      Top = 58
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label63: TLabel
      Left = 111
      Top = 60
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Edit30: TEdit
      Left = 82
      Top = 24
      Width = 23
      Height = 21
      TabOrder = 0
      Text = '1'
    end
    object Edit31: TEdit
      Left = 82
      Top = 56
      Width = 23
      Height = 21
      TabOrder = 1
      Text = '1'
    end
    object Button9: TButton
      Left = 16
      Top = 88
      Width = 179
      Height = 25
      Caption = #22797#20301
      TabOrder = 2
      OnClick = Button9Click
    end
  end
  object GroupBox11: TGroupBox
    Left = 640
    Top = 50
    Width = 227
    Height = 87
    Caption = #35823#24046#20202#34920#24635#28165
    TabOrder = 10
    object Label65: TLabel
      Left = 4
      Top = 26
      Width = 71
      Height = 13
      AutoSize = False
      Caption = #36890#35759#31471#21475
    end
    object Label66: TLabel
      Left = 111
      Top = 28
      Width = 84
      Height = 13
      Caption = #21516#21488#20307#21442#25968#35774#32622
    end
    object Edit33: TEdit
      Left = 82
      Top = 24
      Width = 23
      Height = 21
      TabOrder = 0
      Text = '1'
    end
    object Button11: TButton
      Left = 24
      Top = 56
      Width = 179
      Height = 25
      Caption = #28165#35823#24046#20202'('#24191#25773#21629#20196')'
      TabOrder = 1
      OnClick = Button11Click
    end
  end
  object PageControl1: TPageControl
    Left = 272
    Top = 156
    Width = 365
    Height = 145
    ActivePage = TabSheet1
    TabOrder = 12
    object TabSheet1: TTabSheet
      Caption = #35823#24046#22788#29702'('#36865#24120#25968#22280#25968')'
      object GroupBox5: TGroupBox
        Left = 8
        Top = -16
        Width = 181
        Height = 193
        Caption = #35823#24046#22788#29702'('#36865#24120#25968#22280#25968')'
        TabOrder = 0
        object Label36: TLabel
          Left = 4
          Top = 16
          Width = 62
          Height = 13
          AutoSize = False
          Caption = #25511#21046#34920#20301#21495
        end
        object Label39: TLabel
          Left = 4
          Top = 38
          Width = 69
          Height = 13
          AutoSize = False
          Caption = #33033#20914#24120#25968
        end
        object Label40: TLabel
          Left = 88
          Top = 16
          Width = 25
          Height = 13
          Caption = '1~96'
        end
        object Label41: TLabel
          Left = 4
          Top = 84
          Width = 71
          Height = 13
          AutoSize = False
          Caption = #36890#35759#31471#21475
        end
        object Label42: TLabel
          Left = 87
          Top = 86
          Width = 84
          Height = 13
          Caption = #21516#21488#20307#21442#25968#35774#32622
        end
        object Label43: TLabel
          Left = 4
          Top = 60
          Width = 65
          Height = 13
          AutoSize = False
          Caption = #26816#23450#22280#25968
        end
        object Button4: TButton
          Left = 12
          Top = 109
          Width = 149
          Height = 21
          Caption = #24320#22987#27979#35797#35823#24046
          TabOrder = 0
          OnClick = Button4Click
        end
        object Edit18: TEdit
          Left = 65
          Top = 16
          Width = 23
          Height = 21
          TabOrder = 1
          Text = '1'
        end
        object Edit20: TEdit
          Left = 65
          Top = 82
          Width = 23
          Height = 21
          TabOrder = 2
          Text = '1'
        end
        object Edit21: TEdit
          Left = 65
          Top = 38
          Width = 57
          Height = 21
          TabOrder = 3
          Text = '3200'
        end
        object Edit22: TEdit
          Left = 65
          Top = 60
          Width = 57
          Height = 21
          TabOrder = 4
          Text = '2'
        end
      end
      object Edit46: TEdit
        Left = 212
        Top = 28
        Width = 129
        Height = 21
        TabOrder = 1
      end
      object Button22: TButton
        Left = 208
        Top = 72
        Width = 137
        Height = 25
        Caption = #33719#21462#24403#21069#26631#20934#34920#24120#25968
        TabOrder = 2
        OnClick = Button22Click
      end
    end
    object TabSheet2: TTabSheet
      Caption = #35823#24046#22788#29702'('#21462#24471#35823#24046')'
      ImageIndex = 1
      object Label49: TLabel
        Left = 195
        Top = 24
        Width = 158
        Height = 41
        AutoSize = False
        Caption = #27425#25968'+","+'#35823#24046'  '#22914'2,-0.024 '#34920#31034#35823#24046#20026'-0.024,'#26159#31532#19977#27425#35823#24046','#31532#19968#27425#35823#24046#35831#23613#37327#19981#35201#12290
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clBlue
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        WordWrap = True
      end
      object GroupBox6: TGroupBox
        Left = 8
        Top = -15
        Width = 181
        Height = 144
        Caption = #35823#24046#22788#29702'('#21462#24471#35823#24046')'
        TabOrder = 0
        object Label44: TLabel
          Left = 4
          Top = 15
          Width = 77
          Height = 13
          AutoSize = False
          Caption = #25511#21046#34920#20301#21495
        end
        object Label45: TLabel
          Left = 88
          Top = 15
          Width = 25
          Height = 13
          Caption = '1~96'
        end
        object Label46: TLabel
          Left = 4
          Top = 39
          Width = 71
          Height = 13
          AutoSize = False
          Caption = #36890#35759#31471#21475
        end
        object Label47: TLabel
          Left = 87
          Top = 41
          Width = 84
          Height = 13
          Caption = #21516#21488#20307#21442#25968#35774#32622
        end
        object Label48: TLabel
          Left = 8
          Top = 59
          Width = 65
          Height = 13
          AutoSize = False
          Caption = #35823#24046#25968#25454
        end
        object Button5: TButton
          Left = 14
          Top = 102
          Width = 149
          Height = 21
          Caption = #35823#24046#35835#21462
          TabOrder = 0
          OnClick = Button5Click
        end
        object Edit23: TEdit
          Left = 63
          Top = 59
          Width = 113
          Height = 21
          TabOrder = 1
          Text = 'Edit23'
        end
        object Edit24: TEdit
          Left = 64
          Top = 15
          Width = 23
          Height = 21
          TabOrder = 2
          Text = '1'
        end
        object Edit25: TEdit
          Left = 64
          Top = 37
          Width = 23
          Height = 21
          TabOrder = 3
          Text = '1'
        end
      end
    end
    object TabSheet3: TTabSheet
      Caption = #26085#35745#26102#35823#24046
      ImageIndex = 2
      object Label78: TLabel
        Left = 4
        Top = 12
        Width = 65
        Height = 13
        AutoSize = False
        Caption = #20999#25442#26631#24535
        Transparent = True
      end
      object Label79: TLabel
        Left = 4
        Top = 44
        Width = 117
        Height = 13
        AutoSize = False
        Caption = #26102#38047#29702#35770#36755#20986#39057#29575
        Transparent = True
      end
      object Label80: TLabel
        Left = 4
        Top = 68
        Width = 61
        Height = 13
        AutoSize = False
        Caption = #27979#35797#26102#38388
        Transparent = True
      end
      object Label81: TLabel
        Left = 4
        Top = 96
        Width = 85
        Height = 13
        AutoSize = False
        Caption = #26102#38047#35823#24046#31867#22411
        Transparent = True
      end
      object Label82: TLabel
        Left = 108
        Top = 68
        Width = 69
        Height = 13
        AutoSize = False
        Caption = #25511#21046#34920#20301#21495
      end
      object Label83: TLabel
        Left = 208
        Top = 96
        Width = 57
        Height = 13
        AutoSize = False
        Caption = #35823#24046#25968#25454
      end
      object ComboBox6: TComboBox
        Left = 76
        Top = 8
        Width = 109
        Height = 21
        ItemHeight = 13
        TabOrder = 0
        Text = #20999#25442#26631#24535
        Items.Strings = (
          #20999#24320
          #20999#19978)
      end
      object Button15: TButton
        Left = 212
        Top = 8
        Width = 141
        Height = 25
        Caption = #26631#20934#26102#38047#20202#20999#25442
        TabOrder = 1
        OnClick = Button15Click
      end
      object Button16: TButton
        Left = 212
        Top = 36
        Width = 141
        Height = 25
        Caption = #24320#22987#27979#35797#26102#38047#35823#24046
        TabOrder = 2
        OnClick = Button16Click
      end
      object Button17: TButton
        Left = 212
        Top = 64
        Width = 141
        Height = 25
        BiDiMode = bdLeftToRight
        Caption = #35835#21462#26102#38047#35823#24046
        ParentBiDiMode = False
        TabOrder = 3
        OnClick = Button17Click
      end
      object Edit40: TEdit
        Left = 116
        Top = 40
        Width = 37
        Height = 21
        TabOrder = 4
        Text = '1'
      end
      object Edit41: TEdit
        Left = 60
        Top = 64
        Width = 45
        Height = 21
        TabOrder = 5
        Text = '30'
      end
      object ComboBox7: TComboBox
        Left = 92
        Top = 92
        Width = 101
        Height = 21
        ItemHeight = 13
        TabOrder = 6
        Text = #26102#38047#35823#24046#31867#22411
        Items.Strings = (
          #39057#29575' '
          #26085#35745#26102#35823#24046
          #30456#23545#35823#24046)
      end
      object Edit42: TEdit
        Left = 176
        Top = 64
        Width = 29
        Height = 21
        MaxLength = 2
        TabOrder = 7
        Text = '1'
      end
      object Edit43: TEdit
        Left = 264
        Top = 92
        Width = 89
        Height = 21
        TabOrder = 8
      end
      object ComboBox8: TComboBox
        Left = 156
        Top = 40
        Width = 49
        Height = 21
        Hint = #26631#20934#36755#20986#39057#29575'('#21333#20301':kHz)'
        ItemHeight = 13
        ParentShowHint = False
        ShowHint = True
        TabOrder = 9
        Text = '50'
        OnKeyPress = ComboBox8KeyPress
        Items.Strings = (
          '50'
          '250')
      end
    end
    object TabSheet5: TTabSheet
      Caption = #33258#21160#30701#25509
      ImageIndex = 4
      object Label87: TLabel
        Left = 116
        Top = 15
        Width = 25
        Height = 13
        Caption = '1~96'
      end
      object Label88: TLabel
        Left = 4
        Top = 15
        Width = 77
        Height = 13
        AutoSize = False
        Caption = #25511#21046#34920#20301#21495
      end
      object Edit47: TEdit
        Left = 80
        Top = 11
        Width = 29
        Height = 21
        TabOrder = 0
        Text = '1'
      end
      object Button23: TButton
        Left = 80
        Top = 60
        Width = 75
        Height = 25
        Caption = #30701#25509
        TabOrder = 1
        OnClick = Button23Click
      end
      object Button24: TButton
        Left = 204
        Top = 60
        Width = 75
        Height = 25
        Caption = #22797#20301
        TabOrder = 2
        OnClick = Button24Click
      end
    end
    object TabSheet4: TTabSheet
      Caption = #35823#24046#20202'CMD'
      ImageIndex = 3
      object Label84: TLabel
        Left = 24
        Top = 16
        Width = 33
        Height = 13
        AutoSize = False
        Caption = #21457#36865
      end
      object Label85: TLabel
        Left = 24
        Top = 40
        Width = 25
        Height = 13
        AutoSize = False
        Caption = #36820#22238
      end
      object Button19: TButton
        Left = 284
        Top = 4
        Width = 55
        Height = 25
        Caption = #21457#36865
        TabOrder = 0
        OnClick = Button19Click
      end
      object Edit44: TEdit
        Left = 64
        Top = 8
        Width = 217
        Height = 21
        TabOrder = 1
        Text = '41,38,1111'
      end
      object Edit45: TEdit
        Left = 64
        Top = 40
        Width = 217
        Height = 21
        TabOrder = 2
      end
      object Button20: TButton
        Left = 64
        Top = 80
        Width = 125
        Height = 25
        Caption = '0'#34920#20301#25511#21046#30005#21387#21319#38477
        TabOrder = 3
        OnClick = Button20Click
      end
    end
  end
  object Timer1: TTimer
    Enabled = False
    OnTimer = Timer1Timer
    Left = 403
    Top = 8
  end
end
