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
