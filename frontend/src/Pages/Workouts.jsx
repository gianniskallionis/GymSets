import {Link} from "react-router";
import {useState } from "react";



function WorkoutsPage({workoutsList,handleCreateWorkout ,date ,setDate}){

const [name,setName]=useState("");

return (<div>   {/* if the workoutId is valid */}
     <input
                             type="text" // the types are: text,email,password,number,tel,search,url
                             value={name}
                             onChange={  (event)=> setName(event.target.value)   }
                             placeholder="workout name"
                             />
            <button onClick={()=> handleCreateWorkout(name)}>
                                            Start a workout
                                        </button>

<input  type ="date" value={date} placeholder="date" onChange ={ (event)=>setDate(event.target.value) }  />
              <ul>
                {/* we use ()=> so onClick doesnt automatically start without the ()=> the functs starts automatically*/}
                  { workoutsList.map(  wk=>   <li key={wk.id}    >
                      <Link to ={`/workouts/${wk.id}`}>
                       {wk.name}   {wk.id} {wk.year}-{wk.month}-{wk.day} </Link>

                      </li>    )}
              </ul>

   </div> )




    }
    export default WorkoutsPage;