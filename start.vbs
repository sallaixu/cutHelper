Set WshShell = CreateObject("WScript.Shell")
WshShell.Run "cmd /c .\minjre\bin\java -jar .\cutHelper-2.0.jar", 0
Set WshShell = Nothing