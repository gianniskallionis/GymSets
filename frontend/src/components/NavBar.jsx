import { Link} from "react-router";


function NavBarComp({handleLogOut}){




   return(<nav>
<Link to={"/workouts"}>Go to workouts </Link>
<Link to={"/exercises"}>Go to exercises </Link>
<button onClick={handleLogOut}> Log out </button>
      </nav> )    }


export default NavBarComp ;