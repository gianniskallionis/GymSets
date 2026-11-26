
import {useState } from "react";

function AddSetComp( {onAddSet,token,exercisesList}){
  const [selectedExerciseId,setSelectedExerciseId]=useState("");
  const [kg,setKg]=useState("");
  const [reps,setReps]=useState("");

/* handlers submits skeleton: if (payload.content)  let payload= {content}
 response= await (app method)
 if (response)
  do smth

 */
   const handleSubmitAddSet= async ()=>{ // the method that calls the app add set method
        if(selectedExerciseId &&kg &&reps && token){
            let payload={ exerciseId:Number(selectedExerciseId),kg:Number(kg),reps:Number(reps) };
            /*                  dto_var=jsx_var                               */
            const response= await onAddSet(payload);
            if(response){
                  setKg("");
                    setReps("");} } }

    return (<div>

    <select value={selectedExerciseId} //dropdown menu that shows the exercises that exist to add in the workout
   onChange={ (event)=>    setSelectedExerciseId(event.target.value)  } > {/*event.target.value returns String */}
   <option value=""> {"select exercise"} </option>
     {    exercisesList.map( ex=>(
            <option key={ex.id} value={ex.id}> {ex.name} </option> )) }
       </select>

      <input type="number" value={kg} placeholder="kg" onChange ={ (event)=>setKg(event.target.value) } />
      <input type="number" value={reps} placeholder="reps" onChange= {(event)=>setReps(event.target.value)} />

    <button onClick={handleSubmitAddSet}>
        add a set
        </button>

        </div> )}



    function ShowSetsComp( {onDeleteSet,exercisesList,setsList}){



        return ( <div>
            {/* SHOWS THE LIST OF THE SETS OF AN EXERCISE  */}
                              <ul>
                                  {   setsList.map (set =>  /* we find the exercise                            .we get its name         */
                                      <li key={set.setId}> {exercisesList.find( (e)=> e.id=== set.exerciseId )  ?.name } kg:{set.kg} reps:{set.reps}
                                          <button onClick={()=>onDeleteSet(set)}> Delete set </button>     </li>  )   } </ul>
                                      {/*we use()=> so the function starts only when activated */}
                </div> )



            }






        export {AddSetComp,ShowSetsComp};