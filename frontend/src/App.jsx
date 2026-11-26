import { Routes, Route, Navigate,useNavigate} from "react-router";
import {useEffect,useState } from "react";
import ExercisesPage from "./Pages/Exercises.jsx"
import WorkoutsPage from "./Pages/Workouts.jsx"
import AWorkoutPage from "./Pages/AWorkout.jsx"
import LoginPage from "./Pages/Login.jsx"
import RegisterPage from "./Pages/Register.jsx"
import NavBarComp from "./components/NavBar.jsx"
// for a dynamic list (that changes) you need an id for each element

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
            const error_var= new Error("fetch's answer ok failed reason: "+ fetch_answer_text )
            error_var.status=fetch_answer.status;
            throw error_var; } /*now fetcher throws an error with message + status  */

            const answer_text = await fetch_answer.text(); // if body = empty (delete), we send 0 .else we send json(body)
            if (!answer_text){return 0;}
            return  JSON.parse(answer_text);
   }

function App(){

const [token,setToken]=useState(localStorage.getItem("token"));
const [exercisesList,setExercisesList]=useState([]);
const [workoutId,setWorkoutId]=useState("");
const [workoutsList,setWorkoutsList]=useState([]);
const [loginFlag,setLoginFlag]=useState("")
const [date,setDate]=useState("");
const [setsList,setSetsList]=useState([]);
const navigate=useNavigate();


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
const handleRegister= async (payload)=> {
    try{
        const dto_response = await fetcher("/auth/register","POST",payload,"")
            if (dto_response){console.log(dto_response);
              navigate("/login"); return true;}
            else{console.log("dto response not found");return false;}
                      }
                     catch (e){
                                           onFetchError(e); }return false; }

 /*   fetch sends username and password  to /auth/login . the first then  takes the answer(status, headers, body)
 keeps only its body and turns it into json
 the second then  takes the result which is {token:...} and saves it in the state and shows it on console
  */
  const handleLogin= async (payload)=>{
     try {                         /*          path       ,method, body                         */
         const dto_response = await fetcher("/auth/login","POST",payload,"")
            if(dto_response){
                setToken(dto_response.token);
                localStorage.setItem("token",dto_response.token);
                setLoginFlag("false");
                navigate("/workouts");
                        return true; }/* dto_response is {token: eyjXXXXXXXXXXX} and we need only the eyjxxxxx */


                else{console.log("dto response not found");return false;}
          }
         catch (e){
                        onFetchError(e);setLoginFlag("true"); }return false; }

/** it creates an exercise
  the header "Authorization": 'Bearer ${token}' says: authorization : eyjXXXXXXXXXXX
  it shows ur token to authenticate you . we need the header authorization in every handler except register/login
  */
 async function handleAddExercise(payload){
     try {                          /* fetcher takes body ready so body has to be in the form  Dto_name:name_given             */
        const dto_response= await fetcher("/exercises","POST", payload,token)
            if (dto_response && token ){
                setExercisesList([...exercisesList,dto_response]);
                return true;
                }
         }
       catch (e){ onFetchError(e); }return false;  }


const handleCreateWorkout=async (name)=> {
    let year=new Date().getFullYear();
    let month= new Date().getMonth()+1;
    let day= new Date().getDate();

      if(date){   /* the date is given as "2020-1-1" */       /*Check whether the date is custom or today's */
                    const dt=date.split("-");
                    year=Number(dt[0]);
                    month=Number(dt[1]);
                    day=Number(dt[2]);}
                     try{
        const dto_response= await fetcher("/workouts","POST",{name,year,month,day },token  )
            if(dto_response && token){
                setWorkoutId(dto_response.id);
                console.log(dto_response);
                handleWorkoutsList(); /*everytime i create a workout the list of workouts  gets updated */
                  navigate(`/workouts/${dto_response.id}`)
                } }
         catch (e){onFetchError(e); } }


/** to add a set to a workout we need : the exercise(id) the reps and the kg , we already have them as variables  */
const handleAddSet= async(payload)=>{
    try {
         const dto_response= await fetcher(`/workouts/${workoutId}/sets`,"POST",
              payload ,token  )
                    console.log(dto_response);
                         await handleSetsList();
                     return true;
        }
       catch (e){
                      onFetchError(e); }return false;  }


/* gets all the workouts(List) of the user */
async function handleWorkoutsList()   {
    try {
             const dto_response= await fetcher("/workouts","GET",undefined,token  )
                 if ( dto_response  && token){
                         setWorkoutsList(dto_response)
                        console.log(dto_response);
                                }
            }
            catch (e){
               onFetchError(e); } }


const handleLeaveWorkout= ()=> {
    setWorkoutId("");  // we make the workoutId empty

           setSetsList([]) ;  // we empty the list of sets of the workout
          navigate("/workouts");                 }


  const handleLogOut =()=>{
     localStorage.removeItem("token");
        setExercisesList([]);
        setWorkoutId("");
        setWorkoutsList([]);
        setSetsList([]);
        setToken("");
      }

  const onFetchError =(e)=>{
     if(e.status===401){handleLogOut()}
        console.error(e);}



async function handleSetsList(){/*WE CALL THE BACKEND ENDPOINT     getSetsOfWorkout
    getSetsOfWorkout asks for (ID,Authentication) and it returns a list with all of the sets of the workout
    we give id from ${workoutId} and authorization from token */
    if(!workoutId){setSetsList([]); return;}
    try{ /*the fetcher calls the function that returns the list of the sets  */
    const dto_response= await fetcher(`/workouts/${workoutId}/sets`,"GET",undefined,token)
        if(dto_response && workoutId){ setSetsList(dto_response);  }
        }
        catch (e){
                       onFetchError(e); }  }


  async function handleDeleteSet(set) { console.log(set)
    if (workoutId && set.setId){
     try{                             // the delete set function in backend is void so we dont have dto_response
     await fetcher(`/sets/${set.setId}`,"DELETE",undefined,token);
    /*By calling the backend (via fetcher) we already have deleted the set so we can get all the sets from handleSetsList */
   await handleSetsList(); /* handleSetsList shows the new list on the screen */}
             catch (e){
                            onFetchError(e); }
    }}  ;

    async function handleDeleteWorkout(workoutId){
try{
     await fetcher(`/workouts/${workoutId}`,"DELETE",undefined,token);
   await handleWorkoutsList();
   navigate("/workouts");}
             catch (e){
                            onFetchError(e); }
        }
    async function handleDeleteExercise(id){
try{
     await fetcher(`/exercises/${id}`,"DELETE",undefined,token);
  setExercisesList( exercisesList.filter(  ex => ex.id!== id )           )
                    return true;  }
             catch (e){

                            onFetchError(e);return e.status; }  }

async function handleGetVolume(id){
    try{
        const dto_response=await fetcher(`/workouts/${id}/volume`,"GET",undefined,token);
       return dto_response;
        }
        catch(e){ onFetchError(e);return null;  }
    }

        useEffect ( ()=> { if (token){
            handleWorkoutsList();
            }
            },[token]) /*everytime a user LOGS IN  it  returns the list of workouts */

    useEffect ( () => { if(workoutId){
            handleSetsList();     /*Everytime a workout changes we load the List of its sets */
           }
    },[workoutId])

 {/*SHOWS THE EXERCISES OF THE USER EVERYTIME A LOGIN OCCURS */}
  useEffect( ()=> { if(token){  /*useEffect=== When THIS [token] changes , DO:  whatever is within {} */
      async function startFetching(){
          try{
          const dto_response=  await fetcher("/exercises","GET",undefined,token)
          setExercisesList(dto_response);}
          catch (e){
                         onFetchError(e); } }
          startFetching();
      } },[token])


return(
<div>

{token&& <NavBarComp handleLogOut={handleLogOut} />}
    <Routes>

        <Route path="/register"
        element={ token ? <Navigate to="/workouts" replace />:      <RegisterPage handleRegister={handleRegister} />    } />

           <Route path="/login"
           element = {token ? <Navigate to="/workouts" replace />:   <LoginPage onLogin={handleLogin} token={token} loginFlag={loginFlag}/>  }
                    />
             <Route path="/exercises"
                 element={ token ?   <ExercisesPage exercisesList={exercisesList} handleAddExercise={handleAddExercise} handleDeleteExercise={handleDeleteExercise}  />
                   : <Navigate to="/login" replace /> } />

              <Route path="/workouts"
                element={ token ? <WorkoutsPage workoutsList={workoutsList} handleCreateWorkout={handleCreateWorkout}
                date={date} setDate={setDate}   />
                :  <Navigate to="/login" replace />   } />

                <Route path="/workouts/:id"
                  element={  token ? <AWorkoutPage setWorkoutId={setWorkoutId} handleDeleteSet={handleDeleteSet}
                  exercisesList={exercisesList} setsList={setsList}  handleAddSet={handleAddSet} token={token}
                    handleLeaveWorkout={handleLeaveWorkout}   handleDeleteWorkout={handleDeleteWorkout} handleGetVolume={handleGetVolume}  workoutsList={workoutsList}   />
                    :   <Navigate to="/login" replace /> } />

                    <Route path="*" element={ token ? <Navigate to="/workouts" replace />   :  <Navigate to="/login" replace />   } />

           </Routes>

 </div> )}
export default App;



























































