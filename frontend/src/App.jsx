import {useState } from "react";
function App(){

// we have the username variable and setUsername changes it .
const [username,setUsername]= useState("");
const [password,setPassword]= useState("");
 //        we initialize the username variable with the useState("");
 // we can ONLY change username with setUsername, we do NOT use username=X because its const

const handleRegister=  ()=> {
    fetch("http://localhost:8080/auth/register" ,
        { method:"POST", headers:{ "Content-Type": "application/json" } ,
        body: JSON.stringify({username,password})     }  ).
        then(  (fetch_answer)=>{console.log(fetch_answer)}    ); }
     /* JSON is of form X:Y, when we jsonify username we do
                      username: username
       NAME_of_the_javaDTO_field: the_VALUE_the_user_gave
    example.    username:giannis. same goes for password. we type password but its password:1234 or smth */


return(
 <div>
     <input
     type="text" // the types are: text,email,password,number,tel,search,url
     value={username}
     onChange={  (event)=> setUsername(event.target.value)   }
     placeholder="Username"
     />
    <input
    type="password"
    value={password}
    placeholder="password"
    onChange={ (event)=> setPassword(event.target.value)   }
    />
    <button onClick={handleRegister}>
        Register
    </button>


 </div> )












   }
export default App;