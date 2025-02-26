@echo off
rem Generate java files
echo Compiling all .thrift files to Java...

for %%f in (*.thrift) do (
    echo Processing %%f...
    .\thrift-0.21.0 -r -out .. --gen java %%f
    if %ERRORLEVEL% neq 0 (
        echo Failed to process %%f.
        exit /b %ERRORLEVEL%
    )
)

rem Correct package

set "folder=..\at\fhv\kabi\samples\formatwriter\thrift\"
echo %folder%

for %%j in (%folder%\*.java) do (
	echo %%j
    echo Replacing string in %%j...
    powershell -Command "(Get-Content '%%j') -replace 'at.fhv.kabi.samples.formatwriter.thrift', 'at.fhv.kabi.samples.formatwriter.thrift.at.fhv.kabi.samples.formatwriter.thrift' | Set-Content '%%j'"
)
echo All .thrift files compiled successfully.
pause
