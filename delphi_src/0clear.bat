@echo "清除Delphi中的无用文件 1.0"
@echo "作者:DC"
@echo off 
if   "%1"=="" (goto currentPath)
 else goto pointPath
:currentPath
set work_path=.  
:pointPath
set work_path=%1 
@echo 确定要清理%work_path%吗？
choice /c ny /m "确定按Y,取消按N"
rem errorlevel1对应n,2对应y
if errorlevel 2 goto continue
if errorlevel 1 goto complate
:continue
cd %work_path% 
for /R %%s in (.) do ( 
 rem echo %%s 
rem 删除delphi无用文件
rem del ~*.* /s
 del *.~* /s
 del *.obj /s
 del *.dcp /s
 del *.dcu /s
 rem del *.dpl /s
) 
:complate
echo "运行完毕"
pause 