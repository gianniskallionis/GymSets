import {useState } from "react"


function RegisterComp ({onRegister}) {


const [username,setUsername]= useState(""); // we use useState so changes on the username(only with setUsername) show on screen instantly
const [password,setPassword]= useState("");



const  handleSubmitRegister=  async()=>{
    if(username &&password){
        let payload={ username:username,password:password }
    const register_response= await onRegister(payload)
    if(register_response){
        setUsername("");
        setPassword("");
        } } }


    return (

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
            <button onClick={handleSubmitRegister}>
                Register
            </button>

             </div>  )
    }

function LoginComp({onLogin,loginFlag}){
    const [username,setUsername]= useState("");
    const [password,setPassword]= useState("");

    const  handleSubmitLogin=  async()=>{
        if(username &&password){
            let payload={ username:username,password:password }
        const login_response= await onLogin(payload)
        if(login_response){
            setUsername("");
            setPassword("");
            } } }

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
                <button onClick={handleSubmitLogin}>
                                Login
                            </button>
                               <div>{loginFlag==="true" && <p> Username or Password is incorrect</p>} </div>

                    </div>   )


    }



    export {RegisterComp,LoginComp} ;
