




function WorkoutsPage({workoutsList}){


return (<div>   {/* if the workoutId is valid */}
              <ul>
                {/* we use ()=> so onClick doesnt automatically start without the ()=> the functs starts automatically*/}
                  { workoutsList.map(  wk=>   <li key={wk.id}    >
                      {wk.id} {wk.year}-{wk.month}-{wk.day}
                      </li>    )}
              </ul>

   </div> )




    }
    export default WorkoutsPage;