// ================================= GENERAL ================================= \\

parseXml = (xml, arrayTags) => {
    let dom = null;
    if (window.DOMParser) dom = (new DOMParser()).parseFromString(xml, "text/xml");
    else if (window.ActiveXObject) {
        dom = new ActiveXObject('Microsoft.XMLDOM');
        dom.async = false;
        if (!dom.loadXML(xml)) throw dom.parseError.reason + " " + dom.parseError.srcText;
    }
    else throw new Error("cannot parse xml string!");

    function parseNode(xmlNode, result) {
        if (xmlNode.nodeName == "#text") {
            let v = xmlNode.nodeValue;
            if (v.trim()) result['#text'] = v;
            return;
        }

        let jsonNode = {},
            existing = result[xmlNode.nodeName];
        if (existing) {
            if (!Array.isArray(existing)) result[xmlNode.nodeName] = [existing, jsonNode];
            else result[xmlNode.nodeName].push(jsonNode);
        }
        else {
            if (arrayTags && arrayTags.indexOf(xmlNode.nodeName) != -1) result[xmlNode.nodeName] = [jsonNode];
            else result[xmlNode.nodeName] = jsonNode;
        }

        if (xmlNode.attributes) for (let attribute of xmlNode.attributes) jsonNode[attribute.nodeName] = attribute.nodeValue;

        for (let node of xmlNode.childNodes) parseNode(node, jsonNode);
    }

    let result = {};
    for (let node of dom.childNodes) parseNode(node, result);

    return result;
}

// ================================= BOOKS ================================= \\

addBook = () => {
    var bookTitle = document.getElementById("bookTitle").value;

    if (bookTitle.length < 3 || bookTitle.length > 128) {
        document.getElementById("requestResponse").innerHTML = "Title must contains 3-128 characters!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><book><title>' + bookTitle +'</title></book>';

    request.open('POST', 'http://localhost:8080/take/library/addBook', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponse").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("bookTitle").value = "";
}

getBook = () => {
    var bookId = Number(document.getElementById("bookId").value);

    if (bookId == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/" + bookId;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponse").innerHTML = "There is no book with given id!";
                return;
            }

            var book = parseXml(request.responseText);
            document.getElementById("requestResponse").innerHTML = book.book.bookId["#text"] + " => " + book.book.title["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllBooks = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getBooks";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var books = parseXml(request.responseText);

            document.getElementById("requestResponse").innerHTML = "";
            if (books.books.books == undefined || books.books.books.length == 0) {
                document.getElementById("requestResponse").innerHTML = "Books table is empty!";
                return;
            }

            if (Array.isArray(books.books.books) == false) {
                document.getElementById("requestResponse").innerHTML = books.books.books.bookId["#text"] + " => " + books.books.books.title["#text"] + "<br>";
            }
            else {
                books.books.books.forEach((el) => {
                    document.getElementById("requestResponse").innerHTML += el.bookId["#text"] + " => " + el.title["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteBook = () => {
    var bookId = Number(document.getElementById("bookId").value);

    if (bookId == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/" + bookId;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponse").innerHTML = "There is no book with given id!";
            return;
        }
        getAllBooks();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateBook = () => {
    var bookTitle = document.getElementById("bookTitle").value;
    var bookId = Number(document.getElementById("bookId").value);

    if (bookTitle.length < 3 || bookTitle.length > 128) {
        document.getElementById("requestResponse").innerHTML = "Title must contains 3-128 characters!";
        return;
    }
    else if (bookId == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><book><bookId>' + bookId + '</bookId><title>' + bookTitle +'</title></book>';

    request.open('PUT', 'http://localhost:8080/take/library/updateBook', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            getAllBooks(); 
        }
        else {
            document.getElementById("requestResponse").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("bookTitle").value = "";
    document.getElementById("bookId").value = "";
}

// ================================= BOOK COPIES ================================= \\

addBookCopy = () => {
    var bookIsbn = document.getElementById("bookIsbn").value;
    var bookId = Number(document.getElementById("bookId").value);

    if (bookIsbn.length != 13) {
        document.getElementById("requestResponseBookCopy").innerHTML = "ISBN must contains 13 numbers!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><bookCopy><isbn>' + bookIsbn +'</isbn></bookCopy>';

    request.open('POST', 'http://localhost:8080/take/library/addBookCopy/' + bookId, true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponseBookCopy").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("bookIsbn").value = "";
    document.getElementById("bookId").value = "";
}

getBookCopy = () => {
    var bookCopyId = Number(document.getElementById("bookCopyId").value);

    if (bookCopyId == 0) {
        document.getElementById("requestResponseBookCopy").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getBookCopy/" + bookCopyId;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponseBookCopy").innerHTML = "There is no book with given id!";
                return;
            }

            var bookCopy = parseXml(request.responseText);
            document.getElementById("requestResponseBookCopy").innerHTML = bookCopy.bookCopy.bookCopyId["#text"] + " => " + bookCopy.bookCopy.isbn["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllBookCopies = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getBookCopies";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var bookCopies = parseXml(request.responseText);
            console.log(bookCopies);

            document.getElementById("requestResponseBookCopy").innerHTML = "";
            if (bookCopies.bookCopies == undefined || bookCopies.bookCopies.length == 0) {
                document.getElementById("requestResponseBookCopy").innerHTML = "Books table is empty!";
                return;
            }

            if (Array.isArray(bookCopies.bookCopies.bookCopies) == false) {
                document.getElementById("requestResponseBookCopy").innerHTML = bookCopies.bookCopies.bookCopies.bookCopyId["#text"] + " => " + bookCopies.bookCopies.bookCopies.isbn["#text"] + "<br>";
            }
            else {
                bookCopies.bookCopies.bookCopies.forEach((book) => {
                    document.getElementById("requestResponseBookCopy").innerHTML += book.bookCopyId["#text"] + " => " + book.isbn["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteBookCopy = () => {
    var bookCopyId = Number(document.getElementById("bookCopyId").value);

    if (bookCopyId == 0) {
        document.getElementById("requestResponseBookCopy").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/deleteBookCopy/" + bookCopyId;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponseBookCopy").innerHTML = "There is no book copy with given id!";
            return;
        }
        getAllBookCopies();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateBookCopy = () => {
    var bookIsbn = document.getElementById("bookIsbn").value;
    var bookCopyId = Number(document.getElementById("bookCopyId").value);

    if (bookIsbn.length != 13) {
        document.getElementById("requestResponseBookCopy").innerHTML = "ISBN must contains 13 numbers!";
        return;
    }
    else if (bookCopyId == 0) {
        document.getElementById("requestResponseBookCopy").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><bookCopy><bookCopyId>' + bookCopyId + '</bookCopyId><isbn>' + bookIsbn +'</isbn></bookCopy>';

    request.open('PUT', 'http://localhost:8080/take/library/updateBookCopy', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            getAllBookCopies(); 
        }
        else {
            document.getElementById("requestResponseBookCopy").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("bookIsbn").value = "";
    document.getElementById("bookCopyId").value = "";
}


// ================================= BORROWS ================================= \\

addBorrow = () => {
    var userId = Number(document.getElementById("userId").value);
    var bookCopyId = Number(document.getElementById("bookCopyId").value);
    var borrowDate = document.getElementById("borrowDate").value;

    if (userId == 0) {
        document.getElementById("requestResponseBorrow").innerHTML = "User id field cannot be empty and its value has to be different from 0!";
        return;
    }
    else if (bookCopyId == 0) {
        document.getElementById("requestResponseBorrow").innerHTML = "Book copy id field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><borrow><dateOfBorrow>' + borrowDate +'</dateOfBorrow></borrow>';

    request.open('POST', 'http://localhost:8080/take/library/addBorrow?bookCopyId=' + bookCopyId + '&userId=' + userId, true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponseBorrow").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("userId").value = "";
    document.getElementById("bookCopyId").value = "";
    document.getElementById("borrowDate").value = "";
}

getBorrow = () => {
    var borrowId = Number(document.getElementById("borrowId").value);

    if (borrowId == 0) {
        document.getElementById("requestResponseBorrow").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getBorrow/" + borrowId;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponseBorrow").innerHTML = "There is no borrow with given id!";
                return;
            }

            var borrow = parseXml(request.responseText);
            document.getElementById("requestResponseBorrow").innerHTML = borrow.borrow.borrowId["#text"] + " => " + borrow.borrow.dateOfBorrow["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllBorrows = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getBorrows";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var borrows = parseXml(request.responseText);
            console.log(borrows);

            document.getElementById("requestResponseBorrow").innerHTML = "";
            if (borrows.borrows.borrows == undefined || borrows.borrows.borrows.length == 0) {
                document.getElementById("requestResponseBorrow").innerHTML = "Borrows table is empty!";
                return;
            }

            if (Array.isArray(borrows.borrows.borrows) == false) {
                document.getElementById("requestResponseBorrow").innerHTML = borrows.borrows.borrows.borrowId["#text"] + " => " + borrows.borrows.borrows.dateOfBorrow["#text"] + "<br>";
            }
            else {
                borrows.borrows.borrows.forEach((borrow) => {
                    document.getElementById("requestResponseBorrow").innerHTML += borrow.borrowId["#text"] + " => " + borrow.dateOfBorrow["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteBorrow = () => {
    var borrowId = Number(document.getElementById("borrowId").value);

    if (borrowId == 0) {
        document.getElementById("requestResponseBorrow").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/deleteBorrow/" + borrowId;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponseBorrow").innerHTML = "There is no borrow with given id!";
            return;
        }
        getAllBorrows();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateBorrow = () => {
    var borrowId = Number(document.getElementById("borrowId").value);
    var borrowDate = document.getElementById("borrowDate").value;

    if (borrowId == 0) {
        document.getElementById("requestResponseBorrow").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><borrow><borrowId>' + borrowId + '</borrowId><dateOfBorrow>' + borrowDate +'</dateOfBorrow></borrow>';

    request.open('PUT', 'http://localhost:8080/take/library/updateBorrow', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            getAllBorrows(); 
        }
        else {
            document.getElementById("requestResponseBorrow").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("borrowId").value = "";
    document.getElementById("borrowDate").value = "";
}

// ================================= USERS ================================= \\

addUser = () => {
    var firstName = document.getElementById("firstName").value;
    var surname = document.getElementById("surname").value;
    var email = document.getElementById("email").value;

    if (firstName.length < 3 || firstName.length > 128 || 
        surname.length < 3 || surname.length > 128 ||
        email.length < 3 || email.length > 128) {
        document.getElementById("requestResponseUser").innerHTML = "Each field has to contain at least 3 characters!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><user><firstName>' + firstName +'</firstName><surname>' + surname + '</surname><email>' + email + '</email></user>';

    request.open('POST', 'http://localhost:8080/take/library/addUser', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponseUser").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("firstName").value = "";
    document.getElementById("surname").value = "";
    document.getElementById("email").value = "";
}

getUser = () => {
    var userId = Number(document.getElementById("userId").value);

    if (userId == 0) {
        document.getElementById("requestResponseUser").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getUser/" + userId;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponseUser").innerHTML = "There is no user with given id!";
                return;
            }

            var user = parseXml(request.responseText);
            document.getElementById("requestResponseUser").innerHTML = user.user.userId["#text"] + " => " +
                                                         user.user.firstName["#text"] + " => " +
                                                         user.user.surname["#text"] + " => " +
                                                         user.user.email["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllUsers = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getUsers";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var users = parseXml(request.responseText);

            document.getElementById("requestResponseUser").innerHTML = "";
            if (users.users.users == undefined || users.users.users.length == 0) {
                document.getElementById("requestResponseUser").innerHTML = "Users table is empty!";
                return;
            }

            if (Array.isArray(users.users.users) == false) {
                document.getElementById("requestResponseUser").innerHTML = users.users.users.userId["#text"] + " => " +
                                                                            users.users.users.firstName["#text"] + " => " +
                                                                            users.users.users.surname["#text"] + " => " +
                                                                            users.users.users.email["#text"];
            }
            else {
                users.users.users.forEach((el) => {
                    document.getElementById("requestResponseUser").innerHTML += el.userId["#text"] + " => " +
                                                                                el.firstName["#text"] + " => " +
                                                                                el.surname["#text"] + " => " +
                                                                                el.email["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteUser = () => {
    var userId = Number(document.getElementById("userId").value);

    if (userId == 0) {
        document.getElementById("requestResponseUser").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/deleteUser/" + userId;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponseUser").innerHTML = "There is no user with given id!";
            return;
        }
        getAllUsers();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateUser = () => {
    var firstName = document.getElementById("firstName").value;
    var surname = document.getElementById("surname").value;
    var email = document.getElementById("email").value;
    var userId = Number(document.getElementById("userId").value);

    if (firstName.length < 3 || firstName.length > 128 || 
        surname.length < 3 || surname.length > 128 ||
        email.length < 3 || email.length > 128) {
        document.getElementById("requestResponseUser").innerHTML = "Each field has to contain at least 3 characters!";
        return;
    }
    else if (userId == 0) {
        document.getElementById("requestResponseUser").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><user><userId>' + userId + '</userId><firstName>' + firstName +'</firstName><surname>' + surname + '</surname><email>' + email + '</email></user>';

    request.open('PUT', 'http://localhost:8080/take/library/updateUser', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            getAllUsers(); 
        }
        else {
            document.getElementById("requestResponseUser").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("firstName").value = "";
    document.getElementById("surname").value = "";
    document.getElementById("email").value = "";
    document.getElementById("userId").value = "";
}