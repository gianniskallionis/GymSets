import {useEffect,useState } from "react";

// for a dynamic list (that changes) you need an id for each element
const muscles=[
    'TRAPS', 'RHOMBOIDS', 'LATS', 'ERECTORS','FRONT_DELTS','SIDE_DELTS','REAR_DELTS','BICEPS',
        'TRICEPS', 'FOREARMS', 'CHEST', 'ABS', 'QUADS', 'GLUTES', 'HAMSTRINGS', 'CALVES', 'ADDUCTORS']


function App(){

const [username,setUsername]= useState(""); // we use useState so changes on the username(only with setUsername) show on screen instantly
const [password,setPassword]= useState("");
const [token,setToken]=useState("");
const [exercisesList,setExercisesList]=useState([]);
const [primaryMuscle,setPrimaryMuscle]=useState("");
const [exerciseName,setExerciseName]=useState("");
const [workoutId,setWorkoutId]=useState("");
const [selectedExerciseId,setSelectedExerciseId]=useState("");
const [kg,setKg]=useState("");
const [reps,setReps]=useState("");
const [workoutSetId,setWorkoutSetId]=useState("");
const [workoutsList,setWorkoutsList]=useState([]);
const [loginFlag,setLoginFlag]=useState("");


/**the header "Content-Type": "application/json" means the type of content(body) of what we send is JSON
aka: we are POSTING something in JSON form
body: is what we  send in the POST request, it must match the parameters in the DTO request classes

body == JS object that is the same as DTO Request object
ex: createExerciseRequest needs(name,primaryMuscle,list of secondaries) in the body we must give those

in the first then we receive the answer of the server in fetch_answer ( status,headers,body )
we  keep only the body with .json() and we name it dto_response
dto_response == JS object that is same as DTO response object

 */
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
         then(
             fetch_answer =>{ // we only use ( && ) on the return part, on the functions we use if
                 if( !fetch_answer.ok ){ setLoginFlag("true");return ;  }
                 else{setLoginFlag("false");return fetch_answer.json();}
                }    )
         .then(dto_response => { console.log(dto_response);
             if(dto_response){
             setToken(dto_response.token); // dto_response is {token: eyjXXXXXXXXXXX} and we need only the eyjxxxxx
             console.log(dto_response.token) ;}
             else{console.log("dto response not found")}
             });
     }

/** it creates an exercise
  the header "Authorization": 'Bearer ${token}' says: authorization : eyjXXXXXXXXXXX
  it shows ur token to authenticate you . we need the header authorization in every handler except register/login


  */
 const handleAddExercise= ()=>{if(primaryMuscle) { // if primaryMuscle is not empty
     fetch("http://localhost:8080/exercises",
         {method:"POST",headers:{ "Content-Type":"application/json","Authorization":`Bearer ${token}`},
          body:JSON.stringify({name:exerciseName,primaryMuscle,listOfSecondaries:[]})   })
            //             java dto name: jsx var name         we send an empty list   fetch closes before .then
            .then(fetch_answer  =>{return fetch_answer.json();} ). // we only use the body from the whole answer(status,header,body)
            then(dto_response   => {setExercisesList([...exercisesList,dto_response]) // on the list exercises we add the dto_response exercise
                setExerciseName("");} ) //  exerciseName now is empty
               }}


const handleCreateWorkout = ()=> {
    const year=new Date().getFullYear();
    const month= new Date().getMonth()+1;
    const day= new Date().getDate();

    fetch("http://localhost:8080/workouts",
    {method:"POST",headers:{"Content-Type":"application/json", "Authorization": `Bearer ${token}`  },
    body:JSON.stringify({year,month,day } ) } )
    .then( fetch_answer =>{return fetch_answer.json();}   ) // the clean data that the 1st then returns is a workoutResponse , it has id
    .then(dto_response => {setWorkoutId(dto_response.id);console.log(dto_response);}  )
    }

/** to add a set to a workout we need : the exercise(id) the reps and the kg , we already have them as variables  */
const handleAddSet= ()=>{
// we check if all 3 requirements exist correctly
  if (selectedExerciseId && kg && reps){
    fetch(`http://localhost:8080/workouts/${workoutId}/sets`,
        {method:"POST",headers:{ "Content-Type":"application/json","Authorization":`Bearer ${token}`},
         body:JSON.stringify({ exerciseId:Number(selectedExerciseId),kg:Number(kg),reps:Number(reps) })   })     // matches CreateWorkoutSetRequest params
        .then(fetch_answer=> {return fetch_answer.json();}) // fetch_answer takes some time to come so we have to return it as a function cuz we dont have it ready
        .then ( dto_response=>{console.log(dto_response)}  )
        setKg("");setReps("");  // emptying out the input fields after submit
      }
 // if any requirement is not met we send nothing
 }

/* gets all the workouts of the user */
const handleWorkoutsList= ()=> {
fetch("http://localhost:8080/workouts",
    { method:"GET", headers:{"Authorization":`Bearer ${token}` } } ).
    then( fetch_answer => {return fetch_answer.json();} ).
    then(dto_response=>{setWorkoutsList(dto_response)
        console.log(dto_response)})
    }












 useEffect( ()=> { if(token){
fetch("http://localhost:8080/exercises",
         { method:"GET", headers: {"Authorization":`Bearer ${token}` }   }  ).
         then(fetch_answer => {return fetch_answer.json(); }).// fetch_answer is the whole answer (status,header,body). with .json u open the body and make it into json
         then (dto_response => {   setExercisesList(dto_response);   }   ); // dto_response is the list of exercises
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
    <div> { token && <p>Logged in </p> } </div>   <div>{loginFlag==="true" && <p> Username or Password is incorrect</p>} </div>
    {/* this is as if you type: if(token){printf logged in},if var is "" it is false, so if its true then it isn't empty */}
        <ul> {/*ul=list , li=item of list            */}
                 {exercisesList.map(ex=> <li key={ex.id} >            {ex.name}       </li> ) }
        </ul> {/*  <li attribute > content </li>     */}
              {/* exercises= array, map=loop(like for each that returns a new list  ), ex is the element of the exercises array           */}
    <input
    type="text"
    value={exerciseName}
    placeholder="name of the exercise"
    onChange= { (event)=> setExerciseName(event.target.value) } />


    <select  // select requires <option> also select creates a dropdown menu
     value={primaryMuscle} // what is shown in the dropdown menu , comes from primaryMuscle
     onChange= {  (event)=> setPrimaryMuscle(event.target.value) }    >
     <option  value=""> {"select muscle"} </option>
     {muscles.map( muscle=>(  // creates an option for every item in the array muscles
         <option key={muscle} value={muscle} >
             {muscle}
        </option> ))}
    </select>
    <button onClick={handleAddExercise}>
        add an exercise
     </button>
     <button onClick={handleCreateWorkout}>
         Start a workout
         </button>

   <select value={selectedExerciseId} //dropdown menu that shows the exercises that exist to add in the workout
   onChange={ (event)=>    setSelectedExerciseId(event.target.value)  } > {/*event.target.value returns String */}
   <option value=""> {"select exercise"} </option>

   {    exercisesList.map( ex=>(
        <option key={ex.id} value={ex.id}> {ex.name} </option> )) }

   </select>
   <input type="number" value={kg} placeholder="kg" onChange ={ (event)=>setKg(event.target.value) } />
   <input type="number" value={reps} placeholder="reps" onChange= {(event)=>setReps(event.target.value)} />


 <button onClick={handleAddSet}>
     add a set
     </button>
     <button onClick={handleWorkoutsList}>
         show all workouts
         </button>

 { workoutId &&  <p> {workoutId}    </p>     } {/* if the workoutId is valid */}
<ul>

  {/* we use ()=> so onClick doesnt automatically start without the ()=> the functs starts automatically*/}
    { workoutsList.map(wk=>   <li key={wk.id}     onClick = { ()=>setWorkoutId(wk.id)   } >
        {wk.id} {wk.year}-{wk.month}-{wk.day}

        </li>    )}

</ul>




 </div>

    )


   }
export default App;























































