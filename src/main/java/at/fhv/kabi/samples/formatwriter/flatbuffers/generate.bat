@echo off
rem Generate java files
echo Compiling all .fbs files to Java...

for %%f in (*.fbs) do (
    echo Processing %%f...
    .\flatc --java %%f
    if %ERRORLEVEL% neq 0 (
        echo Failed to process %%f.
        exit /b %ERRORLEVEL%
    )
)

rem Correct package
set "folder=at\fhv\kabi\samples\formatwriter\flatbuffers\"
echo %folder%

for %%j in (%folder%\*.java) do (
    echo Replacing string in %%j...
    powershell -Command "(Get-Content '%%j') -replace 'at.fhv.kabi.samples.formatwriter.flatbuffers', 'at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers' | Set-Content '%%j'"
)
echo All .fbs files compiled successfully.
pause
