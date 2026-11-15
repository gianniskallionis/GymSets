

function ExercisesComp(  {exercisesList}   ){



 return (   <ul>
     {exercisesList.map(ex=> <li key={ex.id} >   {ex.name} </li> ) }
             </ul> )




    }


    export default ExercisesComp;