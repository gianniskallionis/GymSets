import { useParams} from "react-router";
import {useEffect } from "react";
import {ShowSetsComp} from "../components/SetsForm.jsx"


// we use as parameters what is on the RIGHT side of the
function AWorkoutPage({setWorkoutId,handleDeleteSet,exercisesList,setsList         }){
const {id}=useParams();
/* the url is "/workouts/:id" so we say i want only the id(ex:"5") instead of everything ex: { id: "5" }
  SAME AS:
const params = useParams();
const id = params.id;
*/

useEffect( ()=>{
setWorkoutId(id);
    },[id]);








return (<div>

<p> id:{id}</p>
<ShowSetsComp  onDeleteSet={handleDeleteSet} exercisesList={exercisesList} setsList={setsList} />
{/* EVERYTHING THAT IS INSIDE {} MUST COME AS A PARAMETER TO THE FUNCTION */}

    </div>)
    }

export default AWorkoutPage;