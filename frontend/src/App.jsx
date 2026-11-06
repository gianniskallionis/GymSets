import {useEffect,useState } from "react";

// for a dynamic list (that changes) you need an id for each element
const muscles=[
    'TRAPS', 'RHOMBOIDS', 'LATS', 'ERECTORS','FRONT_DELTS','SIDE_DELTS','REAR_DELTS','BICEPS',
        'TRICEPS', 'FOREARMS', 'CHEST', 'ABS', 'QUADS', 'GLUTES', 'HAMSTRINGS', 'CALVES', 'ADDUCTORS']


function App(){
const [workoutId,setWorkoutId]=useState("");
const [exerciseName,setExerciseName]=useState("");
const [primaryMuscle,setPrimaryMuscle]=useState("");
const [exercises,setExercises]=useState([]);
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

 const handleAddExercise= ()=>{if(primaryMuscle) { // if primaryMuscle is not empty
     fetch("http://localhost:8080/exercises",
         {method:"POST",headers:{ "Content-Type":"application/json","Authorization":`Bearer ${token}`},
          body:JSON.stringify({name:exerciseName,primaryMuscle,listOfSecondaries:[]})   })
            //             java dto name: jsx var name         we send an empty list   fetch closes before .then
            .then(fetch_answer  =>{return fetch_answer.json();} ). // we only use the body from the whole answer(status,header,body)
            then(clean_data => {setExercises([...exercises,clean_data]) // on the list exercises we add the clean_data exercise
                setExerciseName("");} ) // the exercise now gets wiped clean
               }}


const handleCreateWorkout = ()=> {
    const year=new Date().getFullYear();
    const month= new Date().getMonth()+1;
    const day= new Date().getDate();

    fetch("http://localhost:8080/workouts",
    {method:"POST",headers:{"Content-Type":"application/json", "Authorization": `Bearer ${token}`  },
    body:JSON.stringify({year,month,day } ) } )
    .then( fetch_answer =>{return fetch_answer.json();}   ) // the clean data that the 1st then returns is a workoutResponse , it has id
    .then(clean_data => {setWorkoutId(clean_data.id);console.log(clean_data);}  )
    }













 useEffect( ()=> { if(token){
fetch("http://localhost:8080/exercises",
         { method:"GET", headers: {"Authorization":`Bearer ${token}` }   }  ).
         then(fetch_answer => {return fetch_answer.json(); }).// fetch_answer is the whole answer (status,header,body). with .json u open the body and make it into json
         then (clean_data => {   setExercises(clean_data);   }   ); // clean_data is the list of exercises
    }},[token] ) ;

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
        <ul> {/*ul=list , li=item of list            */}
                 {exercises.map(ex=> <li key={ex.id} >            {ex.name}       </li> ) }
        </ul> {/*  <li attribute > content </li>     */}
              {/* exercises= array, map=loop(like for each that returns a new list  ), ex=temp var(like i) */}
    <input
    type="text"
    value={exerciseName}
    placeholder="name of the exercise"
    onChange= { (event)=> setExerciseName(event.target.value) } />


    <select  // select requires <option> NOT li and ul
     value={primaryMuscle}
     onChange= {  (event)=> setPrimaryMuscle(event.target.value) }    >
     <option  value=""> {"select muscle"} </option>
     {muscles.map( muscle=>(
         <option key={muscle} value={muscle} >   {/* scroll wheel form */}
             {muscle}
        </option> ))}
    </select>
    <button onClick={handleAddExercise}>
        add an exercise
     </button>
     <button onClick={handleCreateWorkout}>
         Start a workout
         </button>








 </div>

    )


   }
export default App;























































