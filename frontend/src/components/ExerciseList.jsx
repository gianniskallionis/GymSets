



/*                                                                             variable        : value
   if you pass any word without {} as an argument in  ExercisesComp you get : exercisesListProp:[exercisesList]
  so to isolate the exercisesList you want , you use {} and get only what you need                                                   */
function ExercisesComp(  {exercisesListProp}   ){
 return (   <ul>
     {exercisesListProp.map(ex=> <li key={ex.id} >   {ex.name} </li> ) }
            </ul> )
    }


    export default ExercisesComp;