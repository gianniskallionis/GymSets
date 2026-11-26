import {RegisterComp} from "../components/RegisterLogin.jsx"
import { Link} from "react-router";


function RegisterPage({handleRegister}){


  return (<div>
<RegisterComp onRegister={handleRegister} />
<Link to={"/login"}>I already have an account </Link>

     </div> )}

export default RegisterPage;


