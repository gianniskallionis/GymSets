import {Link} from "react-router";




function WorkoutsPage({workoutsList,handleCreateWorkout ,date ,setDate}){


return (<div>   {/* if the workoutId is valid */}
    <button onClick={handleCreateWorkout}>
         Start a workout
         </button>
<input  type ="date" value={date} placeholder="date" onChange ={ (event)=>setDate(event.target.value) }  />
              <ul>
                {/* we use ()=> so onClick doesnt automatically start without the ()=> the functs starts automatically*/}
                  { workoutsList.map(  wk=>   <li key={wk.id}    >
                      <Link to ={`/workouts/${wk.id}`}>
                      {wk.id} {wk.year}-{wk.month}-{wk.day} </Link>

                      </li>    )}
              </ul>

   </div> )




    }
    export default WorkoutsPage;