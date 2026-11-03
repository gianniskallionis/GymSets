import {useState } from "react";
function App(){

// we have the username variable and setUsername changes it .
const [username,setUsername]= useState("");
const [password,setPassword]= useState("");
 //        we initialize the username variable with the useState("");
 // we can ONLY change username with setUsername, we do NOT use username=X because its const

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
    <button>
        Register
     </button>


 </div> )












   }
export default App;