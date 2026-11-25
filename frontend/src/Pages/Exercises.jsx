import ExercisesComp from "../components/ExerciseList.jsx"
import ExerciseFormComp from "../components/ExerciseForm.jsx"
import {useState } from "react"



function ExercisesPage({exercisesList,handleAddExercise, handleDeleteExercise }){

const[msg,setMsg]=useState("");
const handleDeleteWithMsg= async(id) =>{
const result= await handleDeleteExercise(id);
if (result===true){ setMsg("");    }
else if (result===409){setMsg("Exercise is being used on sets");}
    };

return (<div>
 <ExercisesComp exercisesListProp={exercisesList} handleDeleteWithMsg={handleDeleteWithMsg} />
 {msg && <p>{msg}</p> }
{/*    function      variable( argument of function) =value of variable   */}
      <ExerciseFormComp onAddExercise={handleAddExercise} />
 {/*  onAddExercise is the way handleAddExercise goes from app.jsx to ExerciseForm.jsx
      onAddExercise is the second name for the same  function (handleAddExercise), the same function is known as onAddExercise by ExerciseForm
      onAddExercise is a prop(function argument )  that is passed from app->ExerciseForm. Because its a function its called callback
          app->form->onADD->app(do on handle)-> results        */}


        </div>)

}
export default ExercisesPage