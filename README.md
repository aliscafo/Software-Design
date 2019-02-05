# Software-Design

![](CLI_diagram.png)

### CLI
Класс, который содержит множество команд (в классе CommandsPool) и множество переменных (в классе Environment), а также парсер (Parser).
CLI имеет команду для запуска командной строки. После её запуска ожидается ввод команды пользователем. Затем совершается разбор команды парсером, результат выводится. Результат выполнения частей команды, разделенных символом pipe, передается последоватеьно от последней команды к следующей. 

### Environment
Класс, хранящий информацию об имеющихся переменных и их значениях. Также хранит результат выполнения последней части команды (отделенной символом pipe).

### CommandExecutor
Интерфейс для всех поддерживаемых команд. Такой интерфейс позволяет выполнить требование о расширяемости.

### Parser
Класс, совершающий разбор полученной команды и подстановку переменных.