import {useState } from "react";
function App(){

const [token,setToken]=useState("");
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


 /**  fetch sends username and password  to /auth/login . the first then  takes the answer(status, headers, body)
 keeps only its body and turns it into json
 the second then  takes the result which is {token:...} and saves it in the state and shows it on console
  */
 const handleLogin= ()=>{
     fetch("http://localhost:8080/auth/login",
         {method:"POST", headers:{ "Content-Type": "application/json"},
         body: JSON.stringify({username,password})  })  .
         then( fetch_answer =>{return fetch_answer.json();}     ). //
         then(clean_data => {setToken(clean_data.token); // clean_data is {token: eyjXXXXXXXXXXX} and we need only the eyjxxxxx
             console.log(clean_data.token) ;
             });
     }




 const handleLoadExercises= ()=> {
     fetch("http://localhost:8080/exercises",
         { method:"GET", headers: {"Authorization":`Bearer ${token}` }   }  ).
         then(fetch_answer => {return fetch_answer.json(); }     ).
         then (clean_data => {   console.log(clean_data);   }   );
     }


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

    <button onClick={handleLogin}>
        Login
    </button>
    <div> { token && <p>Logged in </p> } </div>
    {/* this is as if you type: if(token){printf logged in},if var is "" it is false, so if its true then it isn't empty */}
    <button onClick={handleLoadExercises}>
        Load Exercises
        </button>


 </div> )












   }
export default App;