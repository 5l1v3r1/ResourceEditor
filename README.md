Windows Executable Resource Editor
=====

This java application allows you to edit resource data that is stored in compiled windows executables. The application locates the resources in the binary by parsing the target binary's PE header. Right now the application supports the editing of string table entries and raw binary resources.

This tool was created so configuration data stored in an application could be altered without having to recompile the binary.