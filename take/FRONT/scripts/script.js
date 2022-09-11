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

// ================================= AUTHORS ================================= \\

addAuthor = () => {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value

    if (firstName.length < 3 || lastName.length < 3) {
        document.getElementById("requestResponse").innerHTML = "Variables must contains 3 characters!";
        return;
    }

    var request = new XMLHttpRequest();
    const params = '<?xml version="1.0"?><author><firstName>' + firstName +'</firstName><lastName>' + lastName + '</lastName></author>';

    request.open('POST', 'http://localhost:8080/take/library/authors', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponse").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
}

getAuthor = () => {
    const authorID = document.getElementById("authorID").value;

    if (authorID == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/authors/" + authorID;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponse").innerHTML = "There is no author with given id!";
                return;
            }

            const author = parseXml(request.responseText);
            document.getElementById("requestResponse").innerHTML = author.author.authorID["#text"] + " => " + author.author.firstName["#text"] + " " + author.author.lastName["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllAuthors = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/authors";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            const authors = parseXml(request.responseText);

            document.getElementById("requestResponse").innerHTML = "";
            if (authors?.authors?.authors?.length == 0) {
                document.getElementById("requestResponse").innerHTML = "Authors table is empty!";
                return;
            }

            if (Array.isArray(authors.authors.authors) == false) {
                document.getElementById("requestResponse").innerHTML = authors.authors.authors.authorId["#text"] + " => " + authors.authors.authors.title["#text"] + "<br>";
            }
            else {
                authors.authors.authors.forEach((el) => {
                    document.getElementById("requestResponse").innerHTML += el.authorId["#text"] + " => " + el.title["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteAuthor = () => {
    const authorID = document.getElementById("authorID").value;

    if (authorID == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/" + authorID;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponse").innerHTML = "There is no author with given id!";
            return;
        }
        getAllAuthors();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateAuthor = () => {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const authorID = document.getElementById("authorID").value;

    if (firstName.length < 3 || lastName.length < 3) {
        document.getElementById("requestResponse").innerHTML = "Variables must contains at least 3 characters!";
        return;
    }
    else if (authorID == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><author><authorID>' + authorID + '</authorID><firstName>' + firstName +'</firstName><lastName>' + lastName + '</lastName></author>';

    request.open('PUT', 'http://localhost:8080/take/library/authors', true);
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
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("authorID").value = "";
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

    request.open('POST', 'http://localhost:8080/take/library/books', true);
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
    const bookID = document.getElementById("bookID").value;

    if (bookID == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/books/" + bookID;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponse").innerHTML = "There is no book with given id!";
                return;
            }

            var book = parseXml(request.responseText);
            document.getElementById("requestResponse").innerHTML = book.book.bookID["#text"] + " => " + book.book.title["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllBooks = () => {
    const request = new XMLHttpRequest();
    const url = "http://localhost:8080/take/library/books";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var books = parseXml(request.responseText);

            document.getElementById("requestResponse").innerHTML = "";
            if (books.books.books == undefined || books.books.books.length == 0) {
                document.getElementById("requestResponse").innerHTML = "Books table is empty!";
                return;
            }

            if (Array.isArray(books.books.books) == false) {
                document.getElementById("requestResponse").innerHTML = books.books.books.bookID["#text"] + " => " + books.books.books.title["#text"] + "<br>";
            }
            else {
                books.books.books.forEach((el) => {
                    document.getElementById("requestResponse").innerHTML += el.bookID["#text"] + " => " + el.title["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteBook = () => {
    const bookID = document.getElementById("bookID").value;

    if (bookID == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/books/" + bookID;

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
    var bookID = document.getElementById("bookID").value;

    if (bookTitle.length < 3 || bookTitle.length > 128) {
        document.getElementById("requestResponse").innerHTML = "Title must contains 3-128 characters!";
        return;
    }
    else if (bookID == 0) {
        document.getElementById("requestResponse").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><book><bookID>' + bookID + '</bookID><title>' + bookTitle +'</title></book>';

    request.open('PUT', 'http://localhost:8080/take/library/books', true);
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
    document.getElementById("bookID").value = "";
}

// ================================= BOOK COPIES ================================= \\

addBookCopy = () => {
    var bookID = document.getElementById("bookID").value;

    if (bookID.length < 3) {
        document.getElementById("requestResponseBookCopy").innerHTML = "BookID must contain at least 3 characters!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><bookCopy><bookID>' + bookID +'</bookID></bookCopy>';

    request.open('POST', 'http://localhost:8080/take/library/copies', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponseBookCopy").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("bookID").value = "";
}

getBookCopy = () => {
    var bookCopyId = document.getElementById("bookCopyId").value;

    if (bookCopyId == 0) {
        document.getElementById("requestResponseBookCopy").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/copies/" + bookCopyId;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponseBookCopy").innerHTML = "There is no book with given id!";
                return;
            }

            var bookCopy = parseXml(request.responseText);
            document.getElementById("requestResponseBookCopy").innerHTML = bookCopy.bookCopy.copyID["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllBookCopies = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/copies";

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
                document.getElementById("requestResponseBookCopy").innerHTML = bookCopies.bookCopies.bookCopies.copyID["#text"] + "<br>";
            }
            else {
                bookCopies.bookCopies.bookCopies.forEach((book) => {
                    document.getElementById("requestResponseBookCopy").innerHTML += book.copyID["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteBookCopy = () => {
    var bookCopyId = document.getElementById("bookCopyId").value;

    if (bookCopyId == 0) {
        document.getElementById("requestResponseBookCopy").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/copies/" + bookCopyId;

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

    var bookCopyId = document.getElementById("bookCopyId").value;

    if (bookCopyId == 0) {
        document.getElementById("requestResponseBookCopy").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><bookCopy><copyID' + bookCopyId + '</copyID</bookCopy>';

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
    document.getElementById("bookCopyId").value = "";
}


// ================================= LENDS ================================= \\

addLend = () => {
    var readerID = document.getElementById("readerID").value;
    var bookCopyId = document.getElementById("bookCopyId").value;
    var lendDate = document.getElementById("lendDate").value;
    var params = '<?xml version="1.0"?><bookCopy><readerID>' + readerID +'</bookID></bookCopy>';

    if (readerID == 0) {
        document.getElementById("requestResponseLend").innerHTML = "User id field cannot be empty and its value has to be different from 0!";
        return;
    }
    else if (bookCopyId == 0) {
        document.getElementById("requestResponseLend").innerHTML = "Book copy id field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><lend><lendDate>' + lendDate +'</lendDate></lend>';

    request.open('POST', 'http://localhost:8080/take/library/lends', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponseLend").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("readerID").value = "";
    document.getElementById("bookCopyId").value = "";
    document.getElementById("lendDate").value = "";
}

getLend = () => {
    var lendID = document.getElementById("lendID").value;

    if (lendID == 0) {
        document.getElementById("requestResponseLend").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/lends/" + lendID;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponseLend").innerHTML = "There is no lend with given id!";
                return;
            }

            var lend = parseXml(request.responseText);
            document.getElementById("requestResponseLend").innerHTML = lend.lend.lendId["#text"] + " => " + lend.lend.lendDate["#text"] + '<br>' + lend.lend.returnDate['#text'];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllLends = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/getLends";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var lends = parseXml(request.responseText);
            console.log(lends);

            document.getElementById("requestResponseLend").innerHTML = "";
            if (lends.lends.lends == undefined || lends.lends.lends.length == 0) {
                document.getElementById("requestResponseLend").innerHTML = "Lends table is empty!";
                return;
            }

            if (Array.isArray(lends.lends.lends) == false) {
                document.getElementById("requestResponseLend").innerHTML = lends.lends.lends.lendId["#text"] + " => " + lends.lends.lends.lendDate["#text"] + "<br>";
            }
            else {
                lends.lends.lends.forEach((lend) => {
                    document.getElementById("requestResponseLend").innerHTML += lend.lendId["#text"] + " => " + lend.lendDate["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteLend = () => {
    var lendId = document.getElementById("lendId").value;

    if (lendId == 0) {
        document.getElementById("requestResponseLend").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/deleteLend/" + lendId;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponseLend").innerHTML = "There is no lend with given id!";
            return;
        }
        getAllLends();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateLend = () => {
    var lendId = document.getElementById("lendId").value;
    var lendDate = document.getElementById("startLendDate").value;
    var returnDate = document.getElementById("endLendDate").value;

    if (lendId == 0) {
        document.getElementById("requestResponseLend").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><lend><lendId>' + lendId + '</lendId><lendDate>' + lendDate +'</lendDate><returnDate>'+ returnDate + '</returnDate></lend>';

    request.open('PUT', 'http://localhost:8080/take/library/updateLend', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            getAllLends(); 
        }
        else {
            document.getElementById("requestResponseLend").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("lendId").value = "";
    document.getElementById("lendDate").value = "";
}

// ================================= USERS ================================= \\

addUser = () => {
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var personalID = document.getElementById("personalID").value;

    if (firstName.length < 3 || firstName.length > 128 || 
        lastName.length < 3 || lastName.length > 128 ||
        personalID.length < 3 || personalID.length > 128) {
        document.getElementById("requestResponseUser").innerHTML = "Each field has to contain at least 3 characters!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><reader><firstName>' + firstName +'</firstName><lastName>' + lastName + '</lastName><personalID>' + personalID + '</personalID></reader>';

    request.open('POST', 'http://localhost:8080/take/library/readers', true);
    request.setRequestHeader('Content-type', 'application/xml');

    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            document.getElementById("requestResponseUser").innerHTML = request.responseText;
        }
    }
    
    request.send(params);
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("personalID").value = "";
}

getUser = () => {
    var readerID = document.getElementById("readerID").value;

    if (readerID == 0) {
        document.getElementById("requestResponseUser").innerHTML = "Field cannot be empty!";
        return;
    }
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/readers/" + readerID;

    request.onreadystatechange = () => {
        if (request.readyState == 4) { 
            if (request.responseText == "") {
                document.getElementById("requestResponseUser").innerHTML = "There is no reader with given id!";
                return;
            }

            var reader = parseXml(request.responseText);
            document.getElementById("requestResponseUser").innerHTML = reader.reader.readerID["#text"] + " => " +
                                                         reader.reader.firstName["#text"] + " => " +
                                                         reader.reader.lastName["#text"] + " => " +
                                                         reader.reader.personalID["#text"];
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

getAllUsers = () => {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/readers";

    request.onreadystatechange = () => {
        if (request.readyState == 4) {

            var users = parseXml(request.responseText);

            document.getElementById("requestResponseUser").innerHTML = "";
            if (users.users.users == undefined || users.users.users.length == 0) {
                document.getElementById("requestResponseUser").innerHTML = "Users table is empty!";
                return;
            }

            if (Array.isArray(users.users.users) == false) {
                document.getElementById("requestResponseUser").innerHTML = users.users.users.readerID["#text"] + " => " +
                                                                            users.users.users.firstName["#text"] + " => " +
                                                                            users.users.users.lastName["#text"] + " => " +
                                                                            users.users.users.personalID["#text"];
            }
            else {
                users.users.users.forEach((el) => {
                    document.getElementById("requestResponseUser").innerHTML += el.readerID["#text"] + " => " +
                                                                                el.firstName["#text"] + " => " +
                                                                                el.lastName["#text"] + " => " +
                                                                                el.personalID["#text"] + "<br>";
                });
            }
        }
    }
    
    request.open("GET", url, true);
    request.send();
}

deleteUser = () => {
    var readerID = document.getElementById("readerID").value;

    if (readerID == 0) {
        document.getElementById("requestResponseUser").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/take/library/readers/" + readerID;

    request.onreadystatechange = () => {
        if (request.status == 0) {
            document.getElementById("requestResponseUser").innerHTML = "There is no reader with given id!";
            return;
        }
        getAllUsers();
    }
    
    request.open("DELETE", url, true);
    request.send();
}

updateUser = () => {
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var personalID = document.getElementById("personalID").value;
    var readerID = document.getElementById("readerID").value;

    if (firstName.length < 3 || firstName.length > 128 || 
        lastName.length < 3 || lastName.length > 128 ||
        personalID.length < 3 || personalID.length > 128) {
        document.getElementById("requestResponseUser").innerHTML = "Each field has to contain at least 3 characters!";
        return;
    }
    else if (readerID == 0) {
        document.getElementById("requestResponseUser").innerHTML = "Field cannot be empty and its value has to be different from 0!";
        return;
    }

    var request = new XMLHttpRequest();
    var params = '<?xml version="1.0"?><reader><readerID>' + readerID + '</readerID><firstName>' + firstName +'</firstName><lastName>' + lastName + '</lastName><personalID>' + personalID + '</personalID></reader>';

    request.open('PUT', 'http://localhost:8080/take/library/readers', true);
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
    document.getElementById("lastName").value = "";
    document.getElementById("personalID").value = "";
    document.getElementById("readerID").value = "";
}