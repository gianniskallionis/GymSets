




function WorkoutsPage({workoutsList,handleCreateWorkout ,date ,setDate}){


return (<div>   {/* if the workoutId is valid */}
    <button onClick={handleCreateWorkout}>
         Start a workout
         </button>
<input  type ="date" value={date} placeholder="date" onChange ={ (event)=>setDate(event.target.value) }  />
              <ul>
                {/* we use ()=> so onClick doesnt automatically start without the ()=> the functs starts automatically*/}
                  { workoutsList.map(  wk=>   <li key={wk.id}    >
                      {wk.id} {wk.year}-{wk.month}-{wk.day}
                      </li>    )}
              </ul>

   </div> )




    }
    export default WorkoutsPage;