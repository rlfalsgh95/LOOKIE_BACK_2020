var buttons = document.querySelectorAll(".button");

for (let index = 0; index < buttons.length; index++) {
    buttons[index].addEventListener("click", remove, false);
}

function remove() {
    buttonInfo = this.id.split('#');
    var type = buttonInfo[0];
    var id = buttonInfo[1];

    var rmvtarget = this.parentNode;
    if (rmvtarget.parentNode) {
        rmvtarget.parentNode.removeChild(rmvtarget);
    }

    ajax(type, id);
}


function ajax(type, id) {
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('XMLHTTP 인스턴스를 만들 수가 없습니다.');
        return false;
    }

    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                console.log("success");

                var json = this.responseText;
                json = JSON.parse(json);

                var newtype = json['type'];
                var sequence = json['sequence'];
                var title = json['title'];
                var name = json['name'];

                var d = new Date(json["regdate"]);
                
                var year = d.getFullYear();

                var month = d.getMonth() + 1; //getMonth() returns the number between [0, 11]
                if (month < 10) 
                    month = "0" + month.toString();

                var date = d.getDate();
                if (date < 10) 
                    date = "0" + date.toString();

                var nextlist = document.querySelector("#" + newtype);

                var child = document.createElement("p");
                child.innerHTML +=
                    "<span class='item-title'>" + title + "</span> <br>" +
                    "등록날짜: " + year + ". " + month + ". " + date + ", " +
                    name + ", " +
                    "우선순위 " + sequence;

                if (newtype === "DOING") {
                    child.innerHTML += "<span class='button' id='" + newtype + "\#" + id + "'>-&gt;</span>";
                    child.children[2].addEventListener("click", remove, false);
                }

                nextlist.appendChild(child);

            } else {
                alert("some problems occured");
            }
        }
    };
    httpRequest.open("POST", "http://localhost:8080/Todo/type", true);
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    httpRequest.send("type=" + type + "&id=" + id);
}
