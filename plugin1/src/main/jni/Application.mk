# 用于描述应用需要的原生模块，模块可以是静态库、共享库或可执行文件。

# 选择不同的 ABI，多个使用空格作为分隔符，全部是all
APP_ABI := all
# 指定要使用的运行时
APP_STL := c++_shared