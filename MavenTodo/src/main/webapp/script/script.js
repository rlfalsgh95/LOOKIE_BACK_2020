/**
 * var hw = document.getElementById('hw');
hw.addEventListener('click', function(){
    alert('Hello world');
})
 */
function changeState(id,tmptype){
	 var type=tmptype.substr(7,tmptype.length);
	
	 var oReq = new XMLHttpRequest();
	 var query="id="+id+"&type="+type;
	 oReq.open("post", "/MavenTodo/TodoTypeServlet?"+query,true);//parameter를 붙여서 보낼수있음. 
	 oReq.send();
	 
	 var num="n"+id;
	 
	 
	 oReq.addEventListener("load", function(evt){
		 
		 var li=document.querySelector("#"+num);
		 var locate;
		 if(type=='TODO'){
			 locate=document.getElementById("doing");
			 document.getElementById(num).className="nav-li DOING"
		 }
		 else{
			 locate=document.getElementById("done");
			 document.getElementById(num).className="nav-li DONE"
		 }
		 
		 locate.appendChild(li);
		 
	 }, false);		 
	 
}

function checkNullform(){
	if(!document.AddTodo.title.value){
		alert("할일을 입력하세요.");
		document.AddTodo.title.focus();
		return false;
	}
	if(!document.AddTodo.name.value){
		alert("담당자를 입력하세요.");
		document.AddTodo.name.focus();
		return false;
	}
	if(!document.AddTodo.priority.value){
		alert("우선순위를 선택하세요.");
		return false;
	}
	return true;
}

