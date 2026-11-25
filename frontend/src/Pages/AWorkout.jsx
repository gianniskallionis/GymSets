import { useParams} from "react-router";


function AWorkoutPage(){
const {id}=useParams();
// the url is "/workouts/:id" so we say i want only the id(ex:"5") instead of everything ex: { id: "5" }
/*  SAME AS:
const params = useParams();
const id = params.id;
*/

return (<div>

<p> id:{id}</p>



    </div>)
    }

export default AWorkoutPage;