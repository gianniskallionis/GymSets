import { useParams} from "react-router";
import {useEffect,useState  } from "react";
import {AddSetComp,ShowSetsComp} from "../components/SetsForm.jsx"



function AWorkoutPage({setWorkoutId,handleDeleteSet,exercisesList,setsList,handleAddSet,token,handleLeaveWorkout,handleDeleteWorkout, handleGetVolume  }){
const {id}=useParams();
/* the url is "/workouts/:id" so we say i want only the id(ex:"5") instead of everything ex: { id: "5" }
  SAME AS:
const params = useParams();
const id = params.id;
*/
const [volume,setVolume]=useState(null);
useEffect( ()=>{
setWorkoutId(id);
    },[setWorkoutId,id]);

const onGetVolume= async(id)=>{
    const result= await handleGetVolume(id);
    if(result!==null){setVolume(result);}
    }






return (<div>

<p> id:{id}</p> {/*we use as parameters what is on the RIGHT side of the =  */}
<ShowSetsComp  onDeleteSet={handleDeleteSet} exercisesList={exercisesList} setsList={setsList} />
{/* EVERYTHING THAT IS INSIDE {} MUST COME AS A PARAMETER TO THE FUNCTION */}
<AddSetComp onAddSet= {handleAddSet} token={token}  exercisesList={exercisesList}    />

 <button onClick={handleLeaveWorkout}> leave workout </button>
   <button onClick={ ()=>handleDeleteWorkout(id)     }> Delete workout </button>


  <button onClick={()=>onGetVolume(id)}> Get  volume </button>
  { volume &&
      <ul>{ Object.entries(volume).map(([k, v]) => <li key={k}>{k}: {v}</li>) }</ul>        }


</div>)

    }

export default AWorkoutPage;