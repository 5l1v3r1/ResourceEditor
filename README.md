Windows Executable Resource Editor
=====

This java application allows you to edit resource data that is stored in compiled windows executables. The application locates the resources in the binary by parsing the target binary's PE header. Right now the application supports the editing of string table entries and raw binary resources.

This tool was created so configuration data stored in an application could be altered without having to recompile the binary.

The tool currently supports two forms of encoding: XOR and Byte Spacer.

![resource_screen_shot](https://cloud.githubusercontent.com/assets/12126525/20630405/bd068856-b2fe-11e6-9ea9-bcd7e39ab642.png)