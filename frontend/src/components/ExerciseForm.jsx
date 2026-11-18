import muscles from "../Muscles.js";
import {useState } from "react";

function  ExerciseFormComp({onAddExercise}) {


const [exerciseName,setExerciseName]=useState("");
const [primaryMuscle,setPrimaryMuscle]=useState("");
const [secondaryMuscles,setSecondaryMuscles]=useState([]);
const [currentSecondary,setCurrentSecondary]=useState("");

const handleSubmit= async ()=>{
 if(primaryMuscle){
let payload = {name:exerciseName,primaryMuscle:primaryMuscle,listOfSecondaries:secondaryMuscles }
/*          dto_var=jsx_var                                        */
const addExercise_response = await onAddExercise(payload) // its like we are calling handleAddExercise (name,primaryMuscle,listOfSecondaries)
if (addExercise_response){
setExerciseName("");
setPrimaryMuscle("");
setSecondaryMuscles([]);}
    }}


const handleAddSecondary = ()=>{
    if(currentSecondary && !secondaryMuscles.includes(currentSecondary)){
        setSecondaryMuscles([...secondaryMuscles,currentSecondary]);
        setCurrentSecondary("");
        }
    }



return (<div>

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

    <select value={currentSecondary}  //inside the select we only draw , no logic
    onChange = { (event)=> setCurrentSecondary(event.target.value) } >
    <option value=""> {"select secondary muscles"} </option>
   {muscles.map( muscle=>(                                                  // creates an option for every item in the array muscles
            <option key={muscle} value={muscle}> {muscle}  </option>  // the .map() draws the 17 muscles
         ))}   {/* inside the map we only draw, no logic  */}

      </select>

    <button onClick={handleAddSecondary}>
        add a secondary muscle
        </button>
       <ul>
           {secondaryMuscles.map(secM=>   (<li key={secM}>  {secM} </li> )    )  }
        </ul>




    <button onClick={handleSubmit}>
        add an exercise
     </button>
        </div>)

     }

 export default ExerciseFormComp;
