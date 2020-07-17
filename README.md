# Fluent interfaces using JAVA and JS
This repository is about a kind of fluent interface where we can make some tasks using a mixing between Java and JS.
### Requirements

<p>1 - Java version 8</p>
<p>2 - Maven 3.6.2</p> 

**This application was developed and tested using a Unix-like system.

### Concept

<p>
This homework instead of use the common CLI applications concept(<i>eg. commands as: add... edit... following by some input and so on</i>)
is using a Simple Script Language DSL.
</p> 
<p>
The DSL's language is based on Javasript. The main domain objects are representing by the two commands:
</p>

```bash
Category
Contact
```

### Compile

```bash
mvn clean install
```
# Category

### Create a category

```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Category.name('parents').type('family').description('my parents').save();"
```

### Remove a category

```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Category.getByName('parents').remove();"
```
# Contact

### Add a contact

```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.name('alisson').category('family').surname('pedrina').telephone('(+351) 914560821').email('pedrina.alisson@gmail.com').age(39).save();"
```
### Edit a contact
```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.getByNames('alisson','pedrina').setEmail('julio@gmail.com').edit();"
```

### Search a contact
```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.getByNames('julio','medina');"
```
To display the contact append the "show()" method, see command bellow:
```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.getByNames('alisson','pedrina').show();"
```

### Remove a contact

```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.getByNames('alisson','pedrina').remove();"
```

### Display

Display:
```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.getByNames('julio','medina').show();"
```
Display all contacts:
```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --statement:"Contact.showAll();"
```

# Script

To run script more complex first create a script:

```bash
Category.name('parents').type('family').description('my parents').save();

var data = [
    {'name':'maria','type':'family','surname':'silva','telephone':'(+352) 456560456', 'email':'maria@gmail.com','age':13},
    {'name':'alex','type':'family','surname':'silva','telephone':'(+352) 456560456', 'email':'alex@gmail.com','age':18},
    {'name':'alice','type':'family','surname':'silva','telephone':'(+352) 456560456', 'email':'alice@gmail.com','age':14}
];

for(var i =0; i < data.length; i++){
    Contact.name(data[i]['name'])
            .category(data[i]['type'])
            .surname(data[i]['surname'])
            .telephone(data[i]['telephone'])
            .email(data[i]['email'])
            .age(data[i]['age'])
            .save();

}
```
Then run the follow:

```bash
java -jar cli-1.0.0-jar-with-dependencies.jar --input:"/Users/xxx/script.js"
```
The value of input param is the absolute path to the script created on the previous step.
