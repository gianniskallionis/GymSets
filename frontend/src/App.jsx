import {useEffect,useState } from "react";


// for a dynamic list (that changes) you need an id for each element
const muscles=[
    'TRAPS', 'RHOMBOIDS', 'LATS', 'ERECTORS','FRONT_DELTS','SIDE_DELTS','REAR_DELTS','BICEPS',
        'TRICEPS', 'FOREARMS', 'CHEST', 'ABS', 'QUADS', 'GLUTES', 'HAMSTRINGS', 'CALVES', 'ADDUCTORS']

const urlHost=import.meta.env.VITE_LOCALURL;

/* headers look like a map "content-type" is the key, "application/json" is the value */
async function fetcher( path,method,body,token){
    const headers={};
    if(body){headers["Content-Type"]= "application/json" }
    if (token){ headers["Authorization"]= `Bearer ${token}`}
     const fetch_answer= await   fetch(`${urlHost}${path}`,
       { method:method,headers:headers,   /* we can use just method,headers , i choose the method:method for simplicity */
        body: body  ? JSON.stringify(body) : undefined } )              /*    fetch_method: parameter_method */

       if (!fetch_answer.ok){
            const fetch_answer_text= await fetch_answer.text() ;    /*  we have to wait for the fetch_answer.text() */
            throw new Error("fetch's answer ok failed reason: "+ fetch_answer_text  );
            }
            const answer_text = await fetch_answer.text(); // if body = empty (delete), we send 0 .else we send json(body)
            if (!answer_text){return 0;}
            return  JSON.parse(answer_text);
   }

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
const [workoutsList,setWorkoutsList]=useState([]);
const [loginFlag,setLoginFlag]=useState("");
const [workoutMsg,setWorkoutMsg]=useState("");
const [date,setDate]=useState("");
const [setsList,setSetsList]=useState([]);


/**the header "Content-Type": "application/json" means the type of content(body) of what we send is JSON
aka: we are POSTING something in JSON form
body: is what we  send in the POST request, it must match the parameters in the DTO request classes

body == JS object that is the same as DTO Request object
ex: createExerciseRequest needs(name,primaryMuscle,list of secondaries) in the body we must give those

in the first then we receive the answer of the server in fetch_answer ( status,headers,body )
we  keep only the body with .json() and we name it dto_response
dto_response == JS object that is same as DTO response object

EVERY HANDLER COMMUNICATES 1-1 WITH A BACKEND ENDPOINT FUNCTION

 */
const handleRegister= async ()=> {
    try{
        const dto_response = await fetcher("/auth/register","POST",{username,password},"")
            if (dto_response){console.log(dto_response);}
            else{console.log("dto response not found")}
                      }
                     catch(e){console.error(e)} }

 /*   fetch sends username and password  to /auth/login . the first then  takes the answer(status, headers, body)
 keeps only its body and turns it into json
 the second then  takes the result which is {token:...} and saves it in the state and shows it on console
  */
  const handleLogin= async ()=>{
     try {                         /*          path       ,method, body                         */
         const dto_response = await fetcher("/auth/login","POST",{username,password},"")
            if(dto_response){
                setToken(dto_response.token);setLoginFlag("false") } /* dto_response is {token: eyjXXXXXXXXXXX} and we need only the eyjxxxxx */
                else{console.log("dto response not found")}
          }
         catch(e){console.error(e);setLoginFlag("true")} }

/** it creates an exercise
  the header "Authorization": 'Bearer ${token}' says: authorization : eyjXXXXXXXXXXX
  it shows ur token to authenticate you . we need the header authorization in every handler except register/login
  */
 const handleAddExercise= async ()=>{if(primaryMuscle) { // if primaryMuscle is not empty
     try {                          /* fetcher takes body ready so body has to be in the form  Dto_name:name_given             */
        const dto_response= await fetcher("/exercises","POST", {name:exerciseName,primaryMuscle,listOfSecondaries:[] },token)
            if (dto_response && token ){
                setExercisesList([...exercisesList,dto_response]);
                setExerciseName("");
                setPrimaryMuscle("");
                }
         }
        catch (e){console.error(e);} } }


const handleCreateWorkout=async ()=> {
    let year=new Date().getFullYear();
    let month= new Date().getMonth()+1;
    let day= new Date().getDate();

    if ( workoutId){ setWorkoutMsg("you already started a workout"); return; }
      if(date){   /* the date is given as "2020-1-1" */       /*Check whether the date is custom or today's */
                    const dt=date.split("-");
                    year=Number(dt[0]);
                    month=Number(dt[1]);
                    day=Number(dt[2]);
                    }
    try{
        const dto_response= await fetcher("/workouts","POST",{year,month,day },token  )
            if(dto_response && token){
                setWorkoutId(dto_response.id);
                console.log(dto_response);
                }
        }
          catch (e){console.error(e);} }


/** to add a set to a workout we need : the exercise(id) the reps and the kg , we already have them as variables  */
const handleAddSet= async()=>{if (selectedExerciseId && kg && reps && workoutId   && token){
    try {
         const dto_response= await fetcher(`/workouts/${workoutId}/sets`,"POST",
             { exerciseId:Number(selectedExerciseId),kg:Number(kg),reps:Number(reps) },token  )
                    console.log(dto_response);
                    setKg("");
                    setReps("");
                     await handleSetsList();

        }
        catch (e){console.error(e);} } }


/* gets all the workouts of the user */
const handleWorkoutsList=   async ()=> {
    try {
             const dto_response= await fetcher("/workouts","GET",undefined,token  )
                 if ( dto_response  && token){
                        setWorkoutsList(dto_response)
                        console.log(dto_response);
                                }
            }
            catch (e){console.error(e);} }


const handleLeaveWorkout= ()=> {
    setWorkoutId("");  // we make the workoutId empty
       setWorkoutMsg("");} // we make  the message("you already started a workout") disappear


async function handleSetsList(){/*WE CALL THE BACKEND ENDPOINT     getSetsOfWorkout
    getSetsOfWorkout asks for (ID,Authentication) and it returns a list with all of the sets of the workout
    we give id from ${workoutId} and authorization from token */
    if(!workoutId){setSetsList([]); return;}
    try{ /*the fetcher calls the function that returns the list of the sets  */
    const dto_response= await fetcher(`/workouts/${workoutId}/sets`,"GET",undefined,token)
        if(dto_response && workoutId){ setSetsList(dto_response);  }
        }
        catch(e){console.error(e);}  }


  async function handleDeleteSet(set) { console.log(set)
    if (workoutId && set.setId){
     try{                             // the delete set function in backend is void so we dont have dto_response
     await fetcher(`/sets/${set.setId}`,"DELETE",undefined,token);
    /*By calling the backend (via fetcher) we already have deleted the set so we can get all the sets from handleSetsList */
   await handleSetsList(); /* handleSetsList shows the new list on the screen */}
             catch(e){console.error(e);}
    }}  ;




    useEffect ( () => { if(workoutId){
            handleSetsList();     /*Everytime a workout changes we load the List of its sets */
          }else{setSetsList([]); }
    },[workoutId])





  useEffect( ()=> { if(token){  /*useEffect=== When THIS [token] changes , DO:  whatever is within {} */
      async function startFetching(){
          try{
          const dto_response=  await fetcher("/exercises","GET",undefined,token)
          setExercisesList(dto_response);}
          catch(e){console.error(e);} }
          startFetching();
      } },[token])



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

{/*LEAVE WORKOUT MSG when Workout active AND  message that you cant start  the workout twice  */}
{workoutId && <button onClick={handleLeaveWorkout}> leave workout </button>  }
{/*IF workoutId exists then we show the button    */}
{  workoutMsg && <p>  {workoutMsg} </p> }   {/*IF workoutMsg is NOT empty we show the message:"you already started a workout"       */}


{/*THE DATE OF THE WORKOUT  */}
<input  type ="date" value={date} placeholder="date" onChange ={ (event)=>setDate(event.target.value) }  />



{/* PRINTS THE LIST OF THE SETS OF AN EXERCISE  */}
<ul>
    {   setsList.map (set =>  /* we find the exercise                            .we get its name         */
        <li key={set.setId}> {exercisesList.find( (e)=> e.id=== set.exerciseId )  ?.name } kg:{set.kg} reps:{set.reps}
            <button onClick={()=>handleDeleteSet(set)}> Delete set </button>     </li>  )   } </ul>
        {/*we use()=> so the function starts only when activated */}






 </div>

    )


   }
export default App;























































